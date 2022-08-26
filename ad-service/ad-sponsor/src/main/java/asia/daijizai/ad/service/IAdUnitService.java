package asia.daijizai.ad.service;

import asia.daijizai.ad.entity.AdUnit;
import asia.daijizai.ad.exception.AdException;
import asia.daijizai.ad.vo.*;
import asia.daijizai.ad.vo.unit.AdUnitRequest;
import asia.daijizai.ad.vo.unit.AdUnitResponse;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 22:07
 * @description
 */
public interface IAdUnitService {

    List<AdUnit> getAll()throws AdException;

    List<AdUnit> getByPlanId(Long planId)throws AdException;

    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;

    AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException;

    AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException;

    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
