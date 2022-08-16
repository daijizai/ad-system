package asia.daijizai.ad.controller;

import asia.daijizai.ad.service.ICreativeService;
import asia.daijizai.ad.vo.CreativeRequest;
import asia.daijizai.ad.vo.CreativeResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 11:13
 * @description
 */

@Slf4j
@RestController
public class CreativeOPController {

    private final ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) {
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(request));

        return creativeService.createCreative(request);
    }
}
