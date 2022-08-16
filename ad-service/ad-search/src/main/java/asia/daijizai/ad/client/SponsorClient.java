package asia.daijizai.ad.client;

import asia.daijizai.ad.client.vo.AdPlan;
import asia.daijizai.ad.client.vo.AdPlanGetRequest;
import asia.daijizai.ad.vo.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 18:32
 * @description
 */

/*
一个接口有多个实现被spring管理吗，在依赖注入式，spring会不知道注入哪个实现类就会抛出NoUniqueBeanDefinitionException异常
使用@Primary 来告诉Spring 注入哪个实现类
https://www.jb51.net/article/226099.htm#_label2

不使用@Primary注解时，SearchController中注入SponsorClient报错。
无法自动装配。存在多个 'SponsorClient' 类型的 Bean。
Beans:asia.daijizai.ad.client.SponsorClient (SponsorClient.java) sponsorClientHystrix (SponsorClientHystrix.java)
 */

//value指向我们去调用的微服务的名称
//fallback服务降级
@FeignClient(value = "eureka-client-ad-sponsor", fallback = SponsorClientHystrix.class)
@Primary//
public interface SponsorClient {

    @RequestMapping(value = "/ad-sponsor/get/adPlan", method = RequestMethod.POST)
    CommonResponse<List<AdPlan>> getAdPlans(@RequestBody AdPlanGetRequest request);
}