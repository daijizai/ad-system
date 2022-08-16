package asia.daijizai.ad.util;

import asia.daijizai.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/1 12:01
 * @description
 */
public class CommonUtil {
    private static final String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String value) {
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String dateString) throws AdException {

        try {
            return DateUtils.parseDate(dateString, parsePatterns);
        } catch (Exception ex) {
            throw new AdException(ex.getMessage());
        }
    }
}
