package asia.daijizai.ad.service;

import asia.daijizai.ad.entity.AdPlan;
import asia.daijizai.ad.exception.AdException;
import asia.daijizai.ad.vo.AdPlanGetRequest;
import asia.daijizai.ad.vo.AdPlanRequest;
import asia.daijizai.ad.vo.AdPlanResponse;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 20:35
 * @description
 */
public interface IAdPlanService {


    //创建推广计划
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

     //获取推广计划
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

     //更新推广计划
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    //删除推广计划
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
