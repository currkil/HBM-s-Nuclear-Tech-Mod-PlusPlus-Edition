package currkill.hbms_ntm_pp.api.util;

/**
 * 一个简单的二元组。
 * <p>
 * 移植自 HBM 的{@code com.hbm.util.Tuple.Pair}，改用record实现；额外提供HBM风格的
 * {@link #getKey()}与{@link #getValue()}，让移植过来的调用点可以少改。
 * </p>
 *
 * @param <A> 第一个元素的类型
 * @param <B> 第二个元素的类型
 * @param key 第一个元素
 * @param value 第二个元素
 * @author currkill-deepseek
 */
public record Pair<A, B>(A key, B value) {

    /**
     * 获取第一个元素
     * @return 第一个元素
     */
    public A getKey() {
        return key;
    }

    /**
     * 获取第二个元素
     * @return 第二个元素
     */
    public B getValue() {
        return value;
    }
}
