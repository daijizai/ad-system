package asia.daijizai.ad.service.impl;

import asia.daijizai.ad.dao.CreativeRepository;
import asia.daijizai.ad.entity.Creative;
import asia.daijizai.ad.service.ICreativeService;
import asia.daijizai.ad.vo.CreativeRequest;
import asia.daijizai.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 22:21
 * @description
 */

@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createCreative(CreativeRequest request) {
        Creative creative = creativeRepository.save(request.convertToEntity());

        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
