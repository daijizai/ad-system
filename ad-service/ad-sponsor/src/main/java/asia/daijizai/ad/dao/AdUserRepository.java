package asia.daijizai.ad.dao;

import asia.daijizai.ad.entity.AdUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 8:45
 * @description
 */

public interface AdUserRepository extends JpaRepository<AdUser,Long> {

    // 根据用户名查找用户记录
    AdUser findByUsername(String username);
}
