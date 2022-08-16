package asia.daijizai.ad.index.adunit;

/**
 * @author daijizai
 * @version 1.0
 * @date 2022/8/13 17:33
 * @description 注意到数字是二进制的编排，为了使用位运算加快检索速度
 */
public class AdUnitConstants {
    public static class POSITION_TYPE {

        public static final int KAIPING = 1;//开屏
        public static final int TIEPIAN = 2;//贴片
        public static final int TIEPIAN_MIDDLE = 4;//视频播放中显示的广告
        public static final int TIEPIAN_PAUSE = 8;//视频暂停时
        public static final int TIEPIAN_POST = 16;//视频结束时
    }
}
