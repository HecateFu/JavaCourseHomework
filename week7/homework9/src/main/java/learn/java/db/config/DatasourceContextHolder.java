package learn.java.db.config;

import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 当前线程的数据源标识
 */
public class DatasourceContextHolder {
    private static final ThreadLocal<String> CONTEXT = new ThreadLocal<>();
    /**
     * 主库数据源标识
     */
    public static final String MASTER = "master";

    /**
     * 从库数据源标识
     */
    public static final String SLAVE = "slave";

    /**
     * 设置当前线程数据源标识
     * @param flag 数据源标识名称
     */
    public static void set(String flag) {
        Assert.notNull(flag, "clientDatabase cannot be null");
        CONTEXT.set(flag);
    }

    /**
     * 获取当前线程的数据源标识
     * @return 数据源标识
     */
    public static String getDatasourceFlag() {
        String flag = CONTEXT.get();
        return Objects.isNull(flag)?MASTER: flag;
    }

    /**
     * 清除线程的数据源标识
     */
    public static void clear() {
        CONTEXT.remove();
    }
}
