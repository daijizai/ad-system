package asia.daijizai.ad.controller;

import asia.daijizai.ad.entity.AdPlan;
import asia.daijizai.ad.exception.AdException;
import asia.daijizai.ad.service.IAdPlanService;
import asia.daijizai.ad.util.CommonUtil;
import asia.daijizai.ad.vo.plan.AdPlanGetRequest;
import asia.daijizai.ad.vo.plan.AdPlanGetResponse;
import asia.daijizai.ad.vo.plan.AdPlanRequest;
import asia.daijizai.ad.vo.plan.AdPlanResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 11:08
 * @description
 */

@Slf4j
//@RestController
@Controller
public class AdPlanOPController {

    private final IAdPlanService adPlanService;

    @Autowired
    public AdPlanOPController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

//    @GetMapping("get/adPlan/{userId}")
//    public String getPage(@PathVariable("userId") Integer userId) {
//
//        return "plan";
//    }

    @RequestMapping(path = "/planPage/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
//    @RequestMapping(path = "/unitPage/{planId}", method = {RequestMethod.POST})
    public String getPlanPage(@PathVariable("userId") Long userId) {

        return "/plan";
    }


    @ResponseBody
    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: createAdPlan -> {}", JSON.toJSONString(request));

        return adPlanService.createAdPlan(request);
    }

    @ResponseBody
    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor: getAdPlanByIds -> {}", JSON.toJSONString(request));

        return adPlanService.getAdPlanByIds(request);
    }

    @ResponseBody
    @PostMapping("/get/adPlan/{userId}")
    public List<AdPlanGetResponse> getAdPlanByUserId(@PathVariable("userId") Long userId) throws AdException {

        List<AdPlan> adPlans = adPlanService.getAdPlanByUserId(userId);

        List<AdPlanGetResponse> resp = new ArrayList<>();
        for (AdPlan adPlan : adPlans) {

            String startDate = CommonUtil.parseDateString(adPlan.getStartDate());
            String endDate = CommonUtil.parseDateString(adPlan.getEndDate());

            AdPlanGetResponse getResponse = new AdPlanGetResponse()
                    .setId(adPlan.getId().toString())
                    .setPlanName(adPlan.getPlanName())
                    .setStartDate(startDate)
                    .setEndDate(endDate);

            resp.add(getResponse);
        }

        return resp;
    }

    @ResponseBody
    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: updateAdPlan -> {}", JSON.toJSONString(request));

        return adPlanService.updateAdPlan(request);
    }

    @ResponseBody
    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: deleteAdPlan -> {}", JSON.toJSONString(request));

        adPlanService.deleteAdPlan(request);
    }
}
