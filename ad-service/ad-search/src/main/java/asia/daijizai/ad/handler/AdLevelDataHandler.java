package asia.daijizai.ad.handler;

import asia.daijizai.ad.dump.table.*;
import asia.daijizai.ad.index.DataTable;
import asia.daijizai.ad.index.IndexAware;
import asia.daijizai.ad.index.adplan.AdPlanIndex;
import asia.daijizai.ad.index.adplan.AdPlanObject;
import asia.daijizai.ad.index.adunit.AdUnitIndex;
import asia.daijizai.ad.index.adunit.AdUnitObject;
import asia.daijizai.ad.index.creative.CreativeIndex;
import asia.daijizai.ad.index.creative.CreativeObject;
import asia.daijizai.ad.index.creativeunit.CreativeUnitIndex;
import asia.daijizai.ad.index.creativeunit.CreativeUnitObject;
import asia.daijizai.ad.index.district.UnitDistrictIndex;
import asia.daijizai.ad.index.interest.UnitItIndex;
import asia.daijizai.ad.index.keyword.UnitKeywordIndex;
import asia.daijizai.ad.mysql.constant.OpType;
import asia.daijizai.ad.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/4 13:37
 * @description 在文件中存储的数据拿出来后是AdPlanTable，我们要先把他变成AdPlanObject，然后添加到索引中
 * 1.索引之间存在层级的划分，也就是依赖关系的划分
 * 2.加载全量索引其实是增量索引“添加”的一种特殊实现
 */

@Slf4j
public class AdLevelDataHandler {

    //第二层级索引服务的加载--推广计划
    //为什么第二层级？不与其他的索引服务有依赖关系
    public static void handleLevel2(AdPlanTable planTable, OpType type) {

        //把从文件中获取到的格式转换成构建索引需要的格式（AdPlanTable -> AdPlanObject）
        AdPlanObject planObject = new AdPlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getPlanStatus(),
                planTable.getStartDate(),
                planTable.getEndDate()
        );

        handleBinlogEvent(
                DataTable.of(AdPlanIndex.class),
                planObject.getPlanId(),
                planObject,
                type
        );
    }

    //第二层级索引服务的加载--创意
    public static void handleLevel2(AdCreativeTable creativeTable, OpType type) {
        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getAdId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getAdUrl()
        );
        handleBinlogEvent(
                DataTable.of(CreativeIndex.class),
                creativeObject.getAdId(),
                creativeObject,
                type
        );
    }

    //第三层索引服务的加载--推广单元
    public static void handleLevel3(AdUnitTable unitTable, OpType type){

        //索引之间有层级依赖关系，unit依赖plan
        AdPlanObject adPlanObject=DataTable.of(AdPlanIndex.class).get(unitTable.getPlanId());
        if(null==adPlanObject){
            log.error("handleLevel3 found AdPlanObject error: {}", unitTable.getPlanId());
            return;
        }

        AdUnitObject unitObject = new AdUnitObject(
                unitTable.getUnitId(),
                unitTable.getUnitStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                adPlanObject
        );

        handleBinlogEvent(
                DataTable.of(AdUnitIndex.class),
                unitTable.getUnitId(),
                unitObject,
                type
        );
    }

    //第三层索引服务的加载--创意与单元的关联关系
    public static void handleLevel3(AdCreativeUnitTable creativeUnitTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("CreativeUnitIndex not support update");
            return;
        }

        //索引之间有层级依赖关系，依赖unit和creative
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getAdId());

        if (null == unitObject || null == creativeObject) {
            log.error("AdCreativeUnitTable index error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }

        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                creativeUnitTable.getAdId(),
                creativeUnitTable.getUnitId()
        );

        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtil.stringConcat(
                        creativeUnitObject.getAdId().toString(),
                        creativeUnitObject.getUnitId().toString()
                ),
                creativeUnitObject,
                type
        );
    }

    public static void handleLevel4(AdUnitDistrictTable unitDistrictTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("district index can not support update");
            return;
        }

        //depend on unit
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitDistrictTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitDistrictTable index error: {}", unitDistrictTable.getUnitId());
            return;
        }

        String key = CommonUtil.stringConcat(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(Collections.singleton(unitDistrictTable.getUnitId()));

        handleBinlogEvent(DataTable.of(UnitDistrictIndex.class), key, value, type);
    }

    public static void handleLevel4(AdUnitItTable unitItTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("it index can not support update");
            return;
        }

        //depend on unit
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(unitItTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitItTable index error: {}", unitItTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(Collections.singleton(unitItTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitItIndex.class), unitItTable.getItTag(), value, type);
    }

    public static void handleLevel4(AdUnitKeywordTable keywordTable, OpType type) {

        if (type == OpType.UPDATE) {
            log.error("keyword index can not support update");
            return;
        }

        //depend on unit
        AdUnitObject unitObject = DataTable.of(AdUnitIndex.class).get(keywordTable.getUnitId());
        if (unitObject == null) {
            log.error("AdUnitKeywordTable index error: {}", keywordTable.getUnitId());
            return;
        }

        Set<Long> value = new HashSet<>(Collections.singleton(keywordTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitKeywordIndex.class), keywordTable.getKeyword(), value, type);
    }

    /**
     * 对索引的处理。对哪个索引（index）进行什么样的处理（type）
     */
    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {

        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
