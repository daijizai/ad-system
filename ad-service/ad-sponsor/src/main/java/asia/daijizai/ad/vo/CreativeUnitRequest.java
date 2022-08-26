package asia.daijizai.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Qinyi.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitRequest {

    private List<CreativeUnit> creativeUnits;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreativeUnit {
        private Long creativeId;
        private Long unitId;
    }
}
