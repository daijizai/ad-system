package asia.daijizai.ad.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/3 12:28
 * @description
 */

@Slf4j
public class CommonUtil {


    /*
    https://www.runoob.com/java/java-hashmap-computeifabsent.html
    如果map中存在key，返回key在map中对应的value
    反之，在map中创建key，根据computeIfAbsent的第二个参数，对key赋值，返回key对应的value（刚生成的那个）
     */
    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }


    public static String stringConcat(String... args){
        StringBuilder res=new StringBuilder();
        for (String arg:args){
            res.append(arg);
            res.append("-");
        }
        res.deleteCharAt(res.length()-1);
        return res.toString();
    }

    public static Date parseStringDate(String dateString){
        try {
            DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            return DateUtils.addHours(dateFormat.parse(dateString), -8);
        }catch (ParseException e){
            log.error("parseStringDate error: {}",dateString);
            return null;
        }
    }
}
