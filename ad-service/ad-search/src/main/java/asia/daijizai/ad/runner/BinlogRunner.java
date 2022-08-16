package asia.daijizai.ad.runner;

import asia.daijizai.ad.mysql.BinlogClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/7 14:11
 * @description
 */

/*
在使用SpringBoot构建项目时，我们通常有一些预先数据的加载。那么SpringBoot提供了一个简单的方式来实现–CommandLineRunner。
CommandLineRunner是一个接口，我们需要时，只需实现该接口就行。如果存在多个加载的数据，我们也可以使用@Order注解来排序。
https://blog.csdn.net/qq_34531925/article/details/82527066
*/

@Slf4j
@Component
public class BinlogRunner implements CommandLineRunner {
    private final BinlogClient client;

    @Autowired
    public BinlogRunner(BinlogClient client) {
        this.client = client;
    }

    @Override
    public void run(String... strings) throws Exception {

        log.info("Coming in BinlogRunner...");
        client.connect();
    }
}
