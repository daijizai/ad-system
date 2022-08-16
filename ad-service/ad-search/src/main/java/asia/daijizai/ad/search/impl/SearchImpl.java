package asia.daijizai.ad.search.impl;

import asia.daijizai.ad.index.CommonStatus;
import asia.daijizai.ad.index.DataTable;
import asia.daijizai.ad.index.adunit.AdUnitIndex;
import asia.daijizai.ad.index.adunit.AdUnitObject;
import asia.daijizai.ad.index.creative.CreativeIndex;
import asia.daijizai.ad.index.creative.CreativeObject;
import asia.daijizai.ad.index.creativeunit.CreativeUnitIndex;
import asia.daijizai.ad.index.creativeunit.CreativeUnitObject;
import asia.daijizai.ad.index.district.UnitDistrictIndex;
import asia.daijizai.ad.index.interest.UnitItIndex;
import asia.daijizai.ad.index.keyword.UnitKeywordIndex;
import asia.daijizai.ad.search.ISearch;
import asia.daijizai.ad.search.vo.SearchRequest;
import asia.daijizai.ad.search.vo.SearchResponse;
import asia.daijizai.ad.search.vo.feature.DistrictFeature;
import asia.daijizai.ad.search.vo.feature.FeatureRelation;
import asia.daijizai.ad.search.vo.feature.ItFeature;
import asia.daijizai.ad.search.vo.feature.KeywordFeature;
import asia.daijizai.ad.search.vo.media.AdSlot;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/13 19:26
 * @description
 */
@Slf4j
@Component
public class SearchImpl implements ISearch {

    //回退的实现与启动类上的@EnableCircuitBreaker有关
    public SearchResponse fallback(SearchRequest request, Throwable e) {
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public SearchResponse fetchAds(SearchRequest request) {

        // 请求的广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        // 三个 Feature 再过滤时使用
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();

        FeatureRelation relation = request.getFeatureInfo().getRelation();

        // 构造响应对象
        SearchResponse response = new SearchResponse();

        //这个方法主要就是实现对这个map的填充
        Map<String, List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;

            //根据流量类型 获取（预筛选） 初始AdUnit
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());//预过滤

            //再筛选
            if (relation == FeatureRelation.AND) {
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                targetUnitIdSet = getORRelationUnitIds(adUnitIdSet,
                        keywordFeature, districtFeature, itFeature);
            }

            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);

            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);

            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);

            List<CreativeObject> creativeObjects = DataTable.of(CreativeIndex.class).fetch(adIds);

            //通过AdSlot实现对CreativeObject的过滤
            filterCreativeByAdSlot(creativeObjects,
                    adSlot.getWidth(), adSlot.getHeight(), adSlot.getType());


            adSlot2Ads.put(adSlot.getAdSlotCode(), buildCreativeResponse(creativeObjects));
        }

        log.info("fetchAds: {}-{}",
                JSON.toJSONString(request),
                JSON.toJSONString(response));

        return response;
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives) {

        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }

        //随机获取一个CreativeObject
        int randomIndex = Math.abs(new Random().nextInt()) % creatives.size();
        CreativeObject randomObject = creatives.get(randomIndex);

        return Collections.singletonList(SearchResponse.convert(randomObject));
    }


    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,
                                           ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.emptySet();
        }

        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItTagFeature(itUnitIdSet, itFeature);

        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet),
                        itUnitIdSet
                )
        );
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {

            CollectionUtils.filter(adUnitIds, new Predicate<Long>() {
                @Override
                public boolean evaluate(Long adUnitId) {
                    //返回值为true表示adUnitId通过过滤
                    //返回值为false表示adUnitId没通过过滤，会被从adUnitIds中删除
                    //Filter the collection by applying a Predicate to each element.
                    //If the predicate returns false, remove the element.
                    //If the input collection or predicate is null, there is no change made.
                    return DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKeywords());
                }
            });
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {

            CollectionUtils.filter(adUnitIds,
                    adUnitId -> DataTable.of(UnitDistrictIndex.class).match(adUnitId, districtFeature.getDistricts())
            );
        }
    }

    private void filterItTagFeature(Collection<Long> adUnitIds, ItFeature itFeature) {

        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {

            CollectionUtils.filter(adUnitIds,
                    adUnitId -> DataTable.of(UnitItIndex.class).match(adUnitId, itFeature.getIts())
            );
        }
    }

    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status) {

        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }

        CollectionUtils.filter(
                unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creatives,
                                        Integer width, Integer height, List<Integer> type) {

        if (CollectionUtils.isEmpty(creatives)) {
            return;
        }

        CollectionUtils.filter(
                creatives,
                creative -> creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                        && creative.getWidth().equals(width)
                        && creative.getHeight().equals(height)
                        && type.contains(creative.getType())
        );
    }


}
