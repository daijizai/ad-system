package asia.daijizai.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 14:47
 * @description
 */

//@EnableFeignClients
//@EnableEurekaClient
//@EnableHystrix
//@EnableCircuitBreaker
//@EnableDiscoveryClient
//@EnableHystrixDashboard
//@SpringBootApplication

@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
public class SearchApplication {

    public static void main(String[] args) {

        SpringApplication.run(SearchApplication.class, args);
    }

    //ribbon实现微服务调用时使用
    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
