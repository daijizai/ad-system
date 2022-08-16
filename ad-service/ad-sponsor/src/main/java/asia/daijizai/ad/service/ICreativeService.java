package asia.daijizai.ad.service;

import asia.daijizai.ad.vo.CreativeRequest;
import asia.daijizai.ad.vo.CreativeResponse;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 22:08
 * @description
 */
public interface ICreativeService {

    CreativeResponse createCreative(CreativeRequest request);
}
