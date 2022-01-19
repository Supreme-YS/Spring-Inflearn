package com.supreme.oembed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

@Service
public class OembedService {

    @Autowired
    private ProviderResponseService providerResponseService;
    /**
     * @param paramUrl
     * @return
     * @throws URISyntaxException
     * @Method : getDomainName
     * @Description : 사용자가 입력한 URL에서 도메인 값만을 추출한다.
     */
    private static String getDomainName(String paramUrl) throws URISyntaxException {
        URI uri = new URI(paramUrl);
        String domain = uri.getHost();

        // www로 시작하는 도메인일 경우
        if (domain.startsWith("www.")) {
            domain = domain.substring(4);
        }
        // .com으로 끝나는 도메인일 경우
        if (domain.endsWith(".com")) {
            domain = domain.substring(0, domain.length() - 4);
        }
        return domain;
    }

    /**
     * @param paramUrl
     * @return
     * @Method : embedProcess
     * @Description : 사용자가 입력한 url에서 domain 값을 추출하여 분기한다.
     */
    public HttpEntity<Map<String, Object>> embedProcess(String paramUrl) {
        Map<String, Object> result = new HashMap<>();
        String domain = "";

        try {
            // 유저가 입력한 도메인을 확인
            domain = getDomainName(paramUrl);
        } catch (URISyntaxException e) {
            result.put("result", "Fail");
            result.put("response", "잘못된 URL 입니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
        //TODO Domain 별 서비스 구축 필요
        if (domain.equals("youtube")) {
            return providerResponseService.getYoutubeHtml(paramUrl);
        } else if (domain.equals("twitter")) {
            return providerResponseService.getTwitterHTML(paramUrl);
        } else if (domain.equals("vimeo")) {
            return providerResponseService.getVimeoHTML(paramUrl);
        } else {
            result.put("result", "Fail");
            result.put("response", "현재 지원하지 않는 소셜이거나, 잘못된 URL 입니다.");
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}