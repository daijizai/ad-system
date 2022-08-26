package asia.daijizai.ad.controller;

import asia.daijizai.ad.constant.AdUnitConstants;
import asia.daijizai.ad.entity.AdUnit;
import asia.daijizai.ad.exception.AdException;
import asia.daijizai.ad.service.IAdPlanService;
import asia.daijizai.ad.service.IAdUnitService;
import asia.daijizai.ad.util.CommonUtil;
import asia.daijizai.ad.vo.*;
import asia.daijizai.ad.vo.unit.AdUnitGetResponse;
import asia.daijizai.ad.vo.unit.AdUnitRequest;
import asia.daijizai.ad.vo.unit.AdUnitResponse;
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
 * @date 2022/8/2 11:10
 * @description
 */

@Slf4j
//@RestController
@Controller
public class AdUnitOPController {

    private final IAdUnitService adUnitService;
    @Autowired
    private IAdPlanService adPlanService;

    @Autowired
    public AdUnitOPController(IAdUnitService adUnitService) {
        this.adUnitService = adUnitService;
    }

    @ResponseBody
    @PostMapping("/create/adUnit")
    public AdUnitResponse createUnit(@RequestBody AdUnitRequest request) throws AdException {
        log.info("ad-sponsor: createUnit -> {}", JSON.toJSONString(request));

        return adUnitService.createUnit(request);
    }

    @ResponseBody
    @PostMapping("/get/adUnit")
    public List<AdUnit> getAllUnit() throws AdException {
        return adUnitService.getAll();
    }

    @ResponseBody
    @PostMapping("/get/adUnit/{planId}")
    public List<AdUnitGetResponse> getUnitByPlanId(@PathVariable("planId") Long planId) throws AdException {

        List<AdUnit> adUnits = adUnitService.getByPlanId(planId);
        List<AdUnitGetResponse> resp = new ArrayList<>();
        for (AdUnit adUnit : adUnits) {

            String positionType = CommonUtil.parseUnitPositionType(adUnit.getPositionType());

            AdUnitGetResponse getResponse = new AdUnitGetResponse()
                    .setId(adUnit.getId().toString())
                    .setUnitName(adUnit.getUnitName())
                    .setBudget(adUnit.getBudget().toString())
                    .setPositionType(positionType);

            resp.add(getResponse);
        }


        return resp;
    }

    @RequestMapping(path = "/unitPage/{planId}", method = {RequestMethod.GET, RequestMethod.POST})
//    @RequestMapping(path = "/unitPage/{planId}", method = {RequestMethod.POST})
    public String getUnitPage(@PathVariable("planId") Long planId) {

        return "/unit";
    }


    @ResponseBody
    @PostMapping("/create/unitKeyword")
    public AdUnitKeywordResponse createUnitKeyword(@RequestBody AdUnitKeywordRequest request) throws AdException {
        log.info("ad-sponsor: createUnitKeyword -> {}", JSON.toJSONString(request));

        return adUnitService.createUnitKeyword(request);
    }

    @ResponseBody
    @PostMapping("/create/unitIt")
    public AdUnitItResponse createUnitIt(@RequestBody AdUnitItRequest request) throws AdException {
        log.info("ad-sponsor: createUnitIt -> {}", JSON.toJSONString(request));

        return adUnitService.createUnitIt(request);
    }

    @ResponseBody
    @PostMapping("/create/unitDistrict")
    public AdUnitDistrictResponse createUnitDistrict(@RequestBody AdUnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor: createUnitDistrict -> {}", JSON.toJSONString(request));

        return adUnitService.createUnitDistrict(request);
    }

    @ResponseBody
    @PostMapping("/create/creativeUnit")
    public CreativeUnitResponse createCreativeUnit(@RequestBody CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor: createCreativeUnit -> {}", JSON.toJSONString(request));

        return adUnitService.createCreativeUnit(request);
    }
}
