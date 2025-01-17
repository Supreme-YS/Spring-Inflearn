package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        //Thread A : A사용자 10000원 주문
        statefulService1.order("userA", 10000);
        //Thread B : B사용자 20000원 주문
        statefulService2.order("userB", 20000);

        //Thread A : 사용자 A 주문 금액 조회
        int price = statefulService1.getPrice();
        //Thread A : 사용자 A는 10000원을 기대했지만, 기대와 다르게 20000원 출력
        System.out.println("price = " + price);

        // 이 테스트가 통과한게 대형사고임..A가 10000원 주문헀는데 20000원이랑 같다니...
        // 서비스 접어야 함..
        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);

    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}