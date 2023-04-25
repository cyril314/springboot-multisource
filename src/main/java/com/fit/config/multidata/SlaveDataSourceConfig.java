package com.fit.config.multidata;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = SlaveDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "slaveSqlSessionFactory")
public class SlaveDataSourceConfig {

    static final String PACKAGE = "com.fit.mapper.dsno2";                               //master 目录
    private static final String MAPPER_LOCATION = "classpath:mybatis/dsno2/*/*.xml";            //扫描的 xml 目录
    private static final String CONFIG_LOCATION = "classpath:mybatis/dsno2/mybatis-config.xml"; //自定义的mybatis config 文件位置
    private static final String TYPE_ALIASES_PACKAGE = "com.fit.entity";                        //扫描的 实体类 目录

    /**
     * 数据源
     */
    @Bean(name = "no2DataSource")
    // 读取spring.datasource.slave前缀的配置文件映射成对应的配置对象
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() {
        return new DruidDataSource();
    }

    /**
     * 事务管理器
     */
    @Bean(name = "slaveTransactionManager")
    public PlatformTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(slaveDataSource());
    }

    /**
     * session工厂
     */

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("no2DataSource") DataSource slaveDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(slaveDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(SlaveDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(SlaveDataSourceConfig.CONFIG_LOCATION));
        sessionFactory.setTypeAliasesPackage(SlaveDataSourceConfig.TYPE_ALIASES_PACKAGE);
        return sessionFactory.getObject();
    }
}
