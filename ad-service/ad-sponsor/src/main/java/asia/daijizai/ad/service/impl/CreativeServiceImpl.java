package asia.daijizai.ad.service.impl;

import asia.daijizai.ad.dao.CreativeRepository;
import asia.daijizai.ad.dao.unit_condition.CreativeUnitRepository;
import asia.daijizai.ad.entity.Creative;
import asia.daijizai.ad.entity.unit_condition.CreativeUnit;
import asia.daijizai.ad.service.ICreativeService;
import asia.daijizai.ad.vo.creative.CreativeRequest;
import asia.daijizai.ad.vo.creative.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    private CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse  createCreative(CreativeRequest request) {
        Creative creative = creativeRepository.save(request.convertToEntity());

        return new CreativeResponse(creative.getId(), creative.getName());
    }

    @Override
    public List<Creative> getByUnitId(Long unitId) {

        List<CreativeUnit> creativeUnits = creativeUnitRepository.findAllByUnitId(unitId);

        List<Long> creativeIds=new ArrayList<>();
        for (CreativeUnit creativeUnit : creativeUnits) {
            creativeIds.add(creativeUnit.getCreativeId());
        }

        return creativeRepository.findAllById(creativeIds);
    }
}
