package asia.daijizai.ad.controller;

import asia.daijizai.ad.annotation.IgnoreResponseAdvice;
import asia.daijizai.ad.client.SponsorClient;
import asia.daijizai.ad.client.SponsorClientHystrix;
import asia.daijizai.ad.client.vo.AdPlan;
import asia.daijizai.ad.client.vo.AdPlanGetRequest;
import asia.daijizai.ad.index.DataTable;
import asia.daijizai.ad.search.ISearch;
import asia.daijizai.ad.search.vo.SearchRequest;
import asia.daijizai.ad.search.vo.SearchResponse;
import asia.daijizai.ad.vo.CommonResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 17:32
 * @description
 */

@Slf4j
@RestController
public class SearchController {

    private final ISearch search;

    //ribbon实现微服务调用时使用
    private final RestTemplate restTemplate;

    private final SponsorClient sponsorClient;

    @Autowired
    public SearchController(RestTemplate restTemplate, SponsorClient sponsorClient, ISearch search) {
        this.restTemplate = restTemplate;
        this.sponsorClient = sponsorClient;
        this.search = search;
    }

    @PostMapping("/fetchAds")
    public SearchResponse fetchAds(@RequestBody SearchRequest request) {
        log.info("ad-search: fetchAds -> {}", JSON.toJSONString(request));
        return search.fetchAds(request);
    }

    //https://coding.imooc.com/learn/questiondetail/112583.html
    @IgnoreResponseAdvice
    @PostMapping("/getAdPlans")
    public CommonResponse<List<AdPlan>> getAdPlansByFeign(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlans -> {}", JSON.toJSONString(request));

        return sponsorClient.getAdPlans(request);
    }


    @SuppressWarnings("all")
    @IgnoreResponseAdvice//不想使用统一的响应
    @PostMapping("/getAdPlansByRibbon")
    public CommonResponse<List<AdPlan>> getAdPlansByRibbon(@RequestBody AdPlanGetRequest request) {
        log.info("ad-search: getAdPlansByRibbon -> {}", JSON.toJSONString(request));

        return restTemplate.postForEntity(
                "http://eureka-client-ad-sponsor/ad-sponsor/get/adPlan",
                request,
                CommonResponse.class//序列化响应的格式
        ).getBody();
    }
}
