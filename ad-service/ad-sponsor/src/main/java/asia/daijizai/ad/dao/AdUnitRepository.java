package asia.daijizai.ad.dao;

import asia.daijizai.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 8:52
 * @description
 */

public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
