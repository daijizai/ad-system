package asia.daijizai.ad.index;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/2 21:17
 * @description
 */

public interface IndexAware<K, V> {

    V get(K key);

    void add(K key, V value);

    void update(K key, V value);

    void delete(K key, V value);
}
