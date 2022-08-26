package asia.daijizai.ad.index;

import asia.daijizai.ad.dump.DConstant;
import asia.daijizai.ad.dump.table.*;
import asia.daijizai.ad.handler.AdDataHandler;
import asia.daijizai.ad.mysql.constant.OpType;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/4 23:22
 * @description 全量索引的加载。读取数据文件，将文件内容反序列化为XXXTable
 */

@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    //检索系统启动时完成全量索引的加载，注意加载顺序，不同层级之间存在依赖关系
    @PostConstruct
    public void init() {

        //第2层级全量索引的加载

        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));//读取文件内容
        for (String adPlanString : adPlanStrings) {
            AdPlanTable planTable = JSON.parseObject(adPlanString, AdPlanTable.class);//反序列化
            AdDataHandler.handle(planTable, OpType.ADD);//添加索引
        }

        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        adCreativeStrings.forEach(c -> AdDataHandler.handle(
                JSON.parseObject(c, AdCreativeTable.class), OpType.ADD));

        //第3层级全量索引的加载

        List<String> adUnitStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        adUnitStrings.forEach(u -> AdDataHandler.handle(
                JSON.parseObject(u, AdUnitTable.class), OpType.ADD));

        List<String> adCreativeUnitStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        adCreativeUnitStrings.forEach(cu -> AdDataHandler.handle(
                JSON.parseObject(cu, AdCreativeUnitTable.class), OpType.ADD));

        //第4层级全量索引的加载

        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT));
        adUnitDistrictStrings.forEach(d -> AdDataHandler.handle(
                JSON.parseObject(d, AdUnitDistrictTable.class), OpType.ADD));

        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT));
        adUnitItStrings.forEach(i -> AdDataHandler.handle(
                JSON.parseObject(i, AdUnitItTable.class), OpType.ADD));

        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD));
        adUnitKeywordStrings.forEach(k -> AdDataHandler.handle(
                JSON.parseObject(k, AdUnitKeywordTable.class), OpType.ADD));
    }


    //读取文件。文件里的一行是一个string，整个文件就是一个list<string>
    private List<String> loadDumpData(String fileName) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
            return br.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
