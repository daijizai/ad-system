package asia.daijizai.ad.search.vo;

import asia.daijizai.ad.search.vo.feature.DistrictFeature;
import asia.daijizai.ad.search.vo.feature.FeatureRelation;
import asia.daijizai.ad.search.vo.feature.ItFeature;
import asia.daijizai.ad.search.vo.feature.KeywordFeature;
import asia.daijizai.ad.search.vo.media.AdSlot;
import asia.daijizai.ad.search.vo.media.App;
import asia.daijizai.ad.search.vo.media.Device;
import asia.daijizai.ad.search.vo.media.Geo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/13 13:26
 * @description
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    // 媒体方的请求标识
    private String mediaId;
    // 请求基本信息
    private RequestInfo requestInfo;
    // 匹配信息
    private FeatureInfo featureInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {

        private String requestId;//唯一请求id

        private List<AdSlot> adSlots;//广告位信息
        private App app;//终端信息
        private Geo geo;//地域信息
        private Device device;//设备信息
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo {

        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;

//        private FeatureRelation relation = FeatureRelation.AND;
        private FeatureRelation relation;
    }
}
