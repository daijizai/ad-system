package asia.daijizai.ad.service;

import asia.daijizai.ad.entity.AdPlan;
import asia.daijizai.ad.exception.AdException;
import asia.daijizai.ad.vo.plan.AdPlanGetRequest;
import asia.daijizai.ad.vo.plan.AdPlanRequest;
import asia.daijizai.ad.vo.plan.AdPlanResponse;

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

    List<AdPlan> getAdPlanByUserId(Long userId) throws AdException;

    AdPlan getAdPlanById(Long id)throws AdException;

    //更新推广计划
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    //删除推广计划
    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
