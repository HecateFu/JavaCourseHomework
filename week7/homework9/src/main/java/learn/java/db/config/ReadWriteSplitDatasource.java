package learn.java.db.config;

import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装主、从数据源，并提供orm框架使用的数据源
 */
@Primary
@Component
public class ReadWriteSplitDatasource extends AbstractRoutingDataSource {
    @Resource(name = "masterDatasource")
    private DataSource masterDatasource;

    @Resource(name = "slaveDatasource")
    private DataSource slaveDatasource;

    @Override
    public void afterPropertiesSet() {
        setDefaultTargetDataSource(masterDatasource);
        Map<Object,Object> map = new HashMap<>();
        map.put("master",masterDatasource);
        map.put("slave",slaveDatasource);
        setTargetDataSources(map);

        super.afterPropertiesSet();
    }

    /**
     * 数据源路由策略
     * @return 返回当前线程指定的数据源
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DatasourceContextHolder.getDatasourceFlag();
    }
}
