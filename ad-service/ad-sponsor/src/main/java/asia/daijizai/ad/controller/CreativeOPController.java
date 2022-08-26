package asia.daijizai.ad.controller;

import asia.daijizai.ad.entity.AdUnit;
import asia.daijizai.ad.entity.Creative;
import asia.daijizai.ad.exception.AdException;
import asia.daijizai.ad.service.ICreativeService;
import asia.daijizai.ad.util.CommonUtil;
import asia.daijizai.ad.vo.creative.CreativeGetResponse;
import asia.daijizai.ad.vo.creative.CreativeRequest;
import asia.daijizai.ad.vo.creative.CreativeResponse;
import asia.daijizai.ad.vo.unit.AdUnitGetResponse;
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
 * @date 2022/8/2 11:13
 * @description
 */

@Slf4j
//@RestController
@Controller
public class CreativeOPController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @ResponseBody
    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) {
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(request));

        return creativeService.createCreative(request);
    }

    @RequestMapping(path = "/adPage/{unitId}", method = {RequestMethod.GET, RequestMethod.POST})
//    @RequestMapping(path = "/adPage/{unitId}", method = {RequestMethod.POST})
    public String getAdPage(@PathVariable("unitId") Long unitId) {

        return "/ad";
    }

    @ResponseBody
    @PostMapping("/get/creative/{unitId}")
    public List<CreativeGetResponse> getCreativeByUnitId(@PathVariable("unitId") Long unitId) throws AdException {

        List<Creative> creatives = creativeService.getByUnitId(unitId);
        List<CreativeGetResponse> resp = new ArrayList<>();
        for (Creative creative : creatives) {

            String type = CommonUtil.parseCreativeType(creative.getType());
            String materialType = CommonUtil.parseCreativeMaterialType(creative.getType());

            CreativeGetResponse getResponse = new CreativeGetResponse()
                    .setId(creative.getId().toString())
                    .setName(creative.getName())
                    .setType(type)
                    .setMaterialType(materialType)
                    .setHeight(creative.getHeight().toString())
                    .setWidth(creative.getWidth().toString())
                    .setSize(creative.getSize().toString())
                    .setDuration(creative.getDuration().toString())
                    .setUrl(creative.getUrl());

            resp.add(getResponse);
        }

        return resp;
    }
}
