package com.fit.config.multidata;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
// 指定主数据库扫描对应的Mapper文件，生成代理对象
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {

    static final String PACKAGE = "com.fit.mapper.dsno1";                               //master 目录
    private static final String MAPPER_LOCATION = "classpath:mybatis/dsno1/*/*.xml";            //扫描的 xml 目录
    private static final String CONFIG_LOCATION = "classpath:mybatis/dsno1/mybatis-config.xml"; //自定义的mybatis config 文件位置
    private static final String TYPE_ALIASES_PACKAGE = "com.fit.entity";                        //扫描的 实体类 目录

    /**
     * 主数据源，Primary注解必须增加，它表示该数据源为默认数据源
     * 项目中还可能存在其他的数据源，如获取时不指定名称，则默认获取这个数据源，如果不添加，则启动时候回报错
     */
    @Primary
    @Bean(name = "masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource() {
        return new DruidDataSource();
    }

    /**
     * 事务管理器，Primary注解作用同上
     */
    @Bean(name = "masterTransactionManager")
    @Primary
    public PlatformTransactionManager dataSourceTransactionManager() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    /**
     * session工厂，Primary注解作用同上
     */
    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(MasterDataSourceConfig.CONFIG_LOCATION));
        sessionFactory.setTypeAliasesPackage(MasterDataSourceConfig.TYPE_ALIASES_PACKAGE);
        return sessionFactory.getObject();
    }
}
