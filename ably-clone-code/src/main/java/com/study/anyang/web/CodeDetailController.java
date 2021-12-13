package com.study.anyang.web;

import java.util.List;

import com.study.anyang.domain.CodeDetail;
import com.study.anyang.dto.CodeLabelValue;
import com.study.anyang.service.CodeDetailService;
import com.study.anyang.service.CodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/codedetail")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CodeDetailController {

	@Autowired
	private CodeDetailService codeDetailService;
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerForm(Model model) throws Exception {
		log.info("#### CodeDetailController > registerForm ####");
		CodeDetail codeDetail = new CodeDetail();
		model.addAttribute(codeDetail);
		
		List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
		model.addAttribute("groupCodeList", groupCodeList);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(CodeDetail codeDetail, RedirectAttributes rttr) throws Exception {
		log.info("#### CodeDetailController > register ####");

		codeDetailService.register(codeDetail);

		rttr.addFlashAttribute("msg", "SUCCESS");
		return "redirect:/codedetail/list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) throws Exception {
		log.info("#### CodeDetailController > list ####");

		model.addAttribute("list", codeDetailService.list());
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(CodeDetail codeDetail, Model model) throws Exception {
		log.info("#### CodeDetailController > read ####");

		model.addAttribute(codeDetailService.read(codeDetail));
		
		List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
		model.addAttribute("groupCodeList", groupCodeList);
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(CodeDetail codeDetail, RedirectAttributes rttr) throws Exception {
		log.info("#### CodeDetailController > remove ####");

		codeDetailService.remove(codeDetail);

		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/codedetail/list";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyForm(CodeDetail codeDetail, Model model) throws Exception {
		log.info("#### CodeDetailController > modifyForm ####");

		model.addAttribute(codeDetailService.read(codeDetail));
		
		List<CodeLabelValue> groupCodeList = codeService.getCodeGroupList();
		model.addAttribute("groupCodeList", groupCodeList);
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(CodeDetail codeDetail, RedirectAttributes rttr) throws Exception {
		log.info("#### CodeDetailController > modify ####");

		codeDetailService.modify(codeDetail);
		rttr.addFlashAttribute("msg", "SUCCESS");

		return "redirect:/codedetail/list";
	}

}