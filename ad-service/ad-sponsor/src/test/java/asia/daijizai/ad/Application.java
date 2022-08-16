package asia.daijizai.ad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/4 11:56
 * @description 创建一个子服务，实现广告投放数据全量数据的导出服务（将数据库里的数据导出到文件中）。
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
