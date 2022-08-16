package asia.daijizai.ad.index.adplan;

import asia.daijizai.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 21:54
 * @description 推广计划索引服务实现类。一个正向索引，通过id得到object
 */
@Slf4j
@Component
public class AdPlanIndex implements IndexAware<Long, AdPlanObject> {

    //索引以一个map的形式存在
    private static Map<Long, AdPlanObject> objectMap;

    static {
        //https://www.apiref.com/java11-zh/java.base/java/util/concurrent/ConcurrentHashMap.html
        //一个哈希表，支持检索的完全并发和更新的高预期并发性。
        objectMap = new ConcurrentHashMap<>();//线程安全的map
    }

    @Override
    public AdPlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, AdPlanObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, AdPlanObject value) {
        log.info("before update: {}", objectMap);

        AdPlanObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, AdPlanObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
