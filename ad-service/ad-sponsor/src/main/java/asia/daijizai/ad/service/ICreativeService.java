package asia.daijizai.ad.service;

import asia.daijizai.ad.entity.Creative;
import asia.daijizai.ad.vo.creative.CreativeRequest;
import asia.daijizai.ad.vo.creative.CreativeResponse;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 22:08
 * @description
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request);

    List<Creative> getByUnitId(Long unitId);
}
