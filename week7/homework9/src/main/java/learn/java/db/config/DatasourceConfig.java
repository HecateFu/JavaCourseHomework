package learn.java.db.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 定义数据配置、数据源bean
 */
@Slf4j
@Configuration
public class DatasourceConfig {

    /**
     * @return 主库配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSourceProperties masterProps(){
        return new DataSourceProperties();
    }

    /**
     * @return 从库配置
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSourceProperties slaveProps(){
        return new DataSourceProperties();
    }

    /**
     * @param masterProps 主库配置
     * @return 主库数据源
     */
    @Bean
    public DataSource masterDatasource(DataSourceProperties masterProps){
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl(masterProps.getUrl());
        source.setPassword(masterProps.getPassword());
        source.setUsername(masterProps.getUsername());
        source.setDriverClassName(masterProps.getDriverClassName());
        log.info("masterDatasource: {}",source);
        return source;
    }

    /**
     * @param slaveProps 从库配置
     * @return 从库数据源
     */
    @Bean
    public DataSource slaveDatasource(DataSourceProperties slaveProps){
        HikariDataSource source = new HikariDataSource();
        source.setJdbcUrl(slaveProps.getUrl());
        source.setPassword(slaveProps.getPassword());
        source.setUsername(slaveProps.getUsername());
        source.setDriverClassName(slaveProps.getDriverClassName());
        log.info("slaveDatasource: {}",source);
        return source;
    }
}
