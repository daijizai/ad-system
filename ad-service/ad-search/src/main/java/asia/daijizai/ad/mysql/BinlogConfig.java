package asia.daijizai.ad.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/7 14:00
 * @description
 */

//https://www.jianshu.com/p/57f06f92fbb2
//@ConfigurationProperties和@Value注解用于获取配置文件中的属性定义并绑定到Java Bean或属性中

@Component
@ConfigurationProperties(prefix = "adconf.mysql")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BinlogConfig {

    private String host;
    private Integer port;
    private String username;
    private String password;

    private String binlogName;
    private Long position;
}
