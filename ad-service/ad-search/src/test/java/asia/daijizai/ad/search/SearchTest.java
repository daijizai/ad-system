package asia.daijizai.ad.search;

import asia.daijizai.ad.Application;
import asia.daijizai.ad.search.vo.SearchRequest;
import asia.daijizai.ad.search.vo.feature.DistrictFeature;
import asia.daijizai.ad.search.vo.feature.FeatureRelation;
import asia.daijizai.ad.search.vo.feature.ItFeature;
import asia.daijizai.ad.search.vo.feature.KeywordFeature;
import asia.daijizai.ad.search.vo.media.AdSlot;
import asia.daijizai.ad.search.vo.media.App;
import asia.daijizai.ad.search.vo.media.Device;
import asia.daijizai.ad.search.vo.media.Geo;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/16 17:15
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {
    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds() {

        SearchRequest request = new SearchRequest();
        request.setMediaId("daijizai-ad");

        // 第一个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "aaa",
                Collections.singletonList(new AdSlot(
                        "ad-x", 1, 1080, 720, 1, 1000
                )),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马", "大众"),
                Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省", "合肥市")),
                Arrays.asList("台球", "游泳"),
                FeatureRelation.OR
        ));
        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));

        // 第二个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "aaa",
                Collections.singletonList(new AdSlot(
                        "ad-y", 1, 1080, 720, 1, 1000
                )),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马", "大众", "标志"),
                Collections.singletonList(new DistrictFeature.ProvinceAndCity("安徽省", "合肥市")),
                Arrays.asList("台球", "游泳"),
                FeatureRelation.AND
        ));
        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));


        // 第三个测试条件
        request.setRequestInfo(new SearchRequest.RequestInfo(
                "aaa",
                Collections.singletonList(new AdSlot(
                        "ad-z", 1, 720, 1080, 1, 1000
                )),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeatureInfo(
                Collections.emptyList(),
                Collections.singletonList(new DistrictFeature.ProvinceAndCity()),
                Collections.emptyList(),
                FeatureRelation.AND
        ));
        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));
    }

    private SearchRequest.FeatureInfo buildExampleFeatureInfo(
            List<String> keywords,
            List<DistrictFeature.ProvinceAndCity> provinceAndCities,
            List<String> its,
            FeatureRelation relation
    ) {
        return new SearchRequest.FeatureInfo(
                new KeywordFeature(keywords),
                new DistrictFeature(provinceAndCities),
                new ItFeature(its),
                relation
        );
    }

    private App buildExampleApp() {
        return new App("daijizai", "daijizai",
                "asia.daijizai", "video");
    }

    private Geo buildExampleGeo() {
        return new Geo((float) 100.28, (float) 88.61,
                "郑州市", "河南省");
    }

    private Device buildExampleDevice() {

        return new Device(
                "iphone",
                "0xxxxx",
                "127.0.0.1",
                "x",
                "1080 720",
                "1080 720",
                "123456789"

        );
    }
}
