package asia.daijizai.ad.client;

import asia.daijizai.ad.client.vo.AdPlan;
import asia.daijizai.ad.client.vo.AdPlanGetRequest;
import asia.daijizai.ad.vo.CommonResponse;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 18:41
 * @description
 */

/*

 */
//https://blog.51cto.com/knifeedge/5139843
@Component
public class SponsorClientHystrix implements SponsorClient {

    @Override
    public CommonResponse<List<AdPlan>> getAdPlans(AdPlanGetRequest request) {
        return new CommonResponse<>(-1, "eureka-client-ad-sponsor error");
    }
}
