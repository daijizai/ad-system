package asia.daijizai.ad.search.vo;

import asia.daijizai.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/13 15:27
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    //key : adSlotCode
    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    //索引对象 -> 返回给媒体方的广告创意数据。
    //检索服务实现的是对索引的检索，媒体方要的是具体的广告创意
    public static Creative convert(CreativeObject object) {
        return new Creative()
                .setAdId(object.getAdId())
                .setName(object.getName())
                .setAdUrl(object.getAdUrl())
                .setWidth(object.getWidth())
                .setHeight(object.getHeight())
                .setType(object.getType())
                .setMaterialType(object.getMaterialType());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Creative {
        private Long adId;
        private String name;
        private String adUrl;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        // 展示监测 url
        private List<String> showMonitorUrl = Arrays.asList("www.imooc.com", "www.imooc.com");
        // 点击监测 url
        private List<String> clickMonitorUrl = Arrays.asList("www.imooc.com", "www.imooc.com");
    }

}
