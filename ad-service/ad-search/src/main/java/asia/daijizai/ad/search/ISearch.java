package asia.daijizai.ad.search;

import asia.daijizai.ad.search.vo.SearchRequest;
import asia.daijizai.ad.search.vo.SearchResponse;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/13 13:24
 * @description
 */
public interface ISearch {

    SearchResponse fetchAds(SearchRequest request);
}
