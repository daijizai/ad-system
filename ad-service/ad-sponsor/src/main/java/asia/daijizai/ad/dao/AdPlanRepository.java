package asia.daijizai.ad.dao;

import asia.daijizai.ad.entity.AdPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 8:49
 * @description
 */

public interface AdPlanRepository extends JpaRepository<AdPlan, Long> {

    AdPlan findByIdAndUserId(Long id, Long userId);

    List<AdPlan> findAllByIdInAndUserId(List<Long> ids, Long userId);

    List<AdPlan> findAllByUserId(Long userId);

    AdPlan getByUserIdAndPlanName(Long userId, String planName);

    AdPlan getById(Long id);

    List<AdPlan> findAllByPlanStatus(Integer status);
}