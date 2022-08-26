package asia.daijizai.ad.util;

import asia.daijizai.ad.constant.AdUnitConstants;
import asia.daijizai.ad.constant.CreativeMetrialType;
import asia.daijizai.ad.constant.CreativeType;
import asia.daijizai.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
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

    public static String parseDateString(Date date){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");

        return sdf3.format(date);
    }

    public static String parseUnitPositionType(Integer positionType){
        switch (positionType) {
            case AdUnitConstants.POSITION_TYPE.KAIPING:
                return "开屏";
            case AdUnitConstants.POSITION_TYPE.TIEPIAN:
                return "贴片";
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_MIDDLE:
                return "中贴片";
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_PAUSE:
                return "暂停贴片";
            case AdUnitConstants.POSITION_TYPE.TIEPIAN_POST:
                return "结束贴片";
            default:
                return "未知位置类型";
        }
    }

    public static String parseCreativeType(Integer type){
        if (type==CreativeType.IMAGE.getType()){
            return CreativeType.IMAGE.getDesc();
        }

        if (type==CreativeType.VIDEO.getType()){
            return CreativeType.VIDEO.getDesc();
        }

        if (type==CreativeType.TEXT.getType()){
            return CreativeType.TEXT.getDesc();
        }

        return "未知创意类型";
    }

    public static String parseCreativeMaterialType(Integer type){
        if (type== CreativeMetrialType.JPG.getType()){
            return CreativeMetrialType.JPG.getDesc();
        }

        if (type== CreativeMetrialType.BMP.getType()){
            return CreativeMetrialType.BMP.getDesc();
        }

        if (type== CreativeMetrialType.MP4.getType()){
            return CreativeMetrialType.MP4.getDesc();
        }

        if (type== CreativeMetrialType.AVI.getType()){
            return CreativeMetrialType.AVI.getDesc();
        }

        if (type== CreativeMetrialType.TXT.getType()){
            return CreativeMetrialType.TXT.getDesc();
        }

        return "未知创意子类型";
    }


}
