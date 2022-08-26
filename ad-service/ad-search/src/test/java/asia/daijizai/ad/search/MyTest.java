package asia.daijizai.ad.search;

import asia.daijizai.ad.Application;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/25 22:27
 * @description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MyTest {

    @Test
    public void test(){

        List<Integer> arr=new ArrayList<>();

        System.out.println(CollectionUtils.isNotEmpty(arr));
    }
}
