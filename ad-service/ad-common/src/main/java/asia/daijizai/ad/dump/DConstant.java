package asia.daijizai.ad.dump;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/4 11:06
 * @description 定义将mysql中数据导出的目录名、文件名。有多少个索引对象，就有多少个表数据的存储文件。
 */
public class DConstant {

    public static final String DATA_ROOT_DIR = "E:/java-idea/ad/mysql_data/";

    // 各个表数据的存储文件名
    public static final String AD_PLAN = "ad_plan.data";
    public static final String AD_UNIT = "ad_unit.data";
    public static final String AD_CREATIVE = "ad_creative.data";
    public static final String AD_CREATIVE_UNIT = "ad_creative_unit.data";
    public static final String AD_UNIT_IT = "ad_unit_it.data";
    public static final String AD_UNIT_DISTRICT = "ad_unit_district.data";
    public static final String AD_UNIT_KEYWORD = "ad_unit_keyword.data";
}
