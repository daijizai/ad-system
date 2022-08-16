package asia.daijizai.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/15 10:20
 * @description
 */

@EnableEurekaClient
@SpringBootApplication
@EnableHystrixDashboard
public class DashboardApplication {

    public static void main(String[] args) {

        SpringApplication.run(DashboardApplication.class, args);
    }
}