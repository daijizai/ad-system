package asia.daijizai.ad.dao.unit_condition;

import asia.daijizai.ad.entity.AdPlan;
import asia.daijizai.ad.entity.unit_condition.CreativeUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 8:57
 * @description
 */

public interface CreativeUnitRepository extends JpaRepository<CreativeUnit, Long> {

    List<CreativeUnit> findAllByUnitId(Long userId);
}
