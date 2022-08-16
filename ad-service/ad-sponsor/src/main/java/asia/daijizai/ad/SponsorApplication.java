package asia.daijizai.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/7/31 21:06
 * @description
 */

@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
public class SponsorApplication {

    public static void main(String[] args) {

        SpringApplication.run(SponsorApplication.class, args);
    }
}
