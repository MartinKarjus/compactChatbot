package conf;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import util.FileUtil;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

//@Configuration
//@EnableTransactionManagement
//@ComponentScan(basePackages = {"db.dao", "bot"})
@PropertySource("classpath:/application.properties")
@EnableJpaRepositories(basePackages = {"db.dao", "bot"})
public class DbConfig {

    @Autowired
    public Environment env;

//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource ds = new DriverManagerDataSource();
//        ds.setDriverClassName("org.hsqldb.jdbcDriver");
//        ds.setUrl(env.getProperty("db.url"));
//
//        new JdbcTemplate(ds)
//                .update(FileUtil.readFileFromClasspath("schema.sql"));
////        new JdbcTemplate(ds)
////                .update(FileUtil.readFileFromClasspath("someData.sql"));
//
//        return ds;
//    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl(env.getProperty("spring.datasource.url"));
        ds.setUsername(env.getProperty("spring.datasource.username"));
        ds.setPassword(env.getProperty("spring.datasource.password"));
        //ds.getSchema()



//        new JdbcTemplate(ds)
//                .update(FileUtil.readFileFromClasspath("schema.sql"));
//        new JdbcTemplate(ds)
//                .update(FileUtil.readFileFromClasspath("someData.sql"));

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(
            EntityManagerFactory entityManagerFactory) {


        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getClass().getPackage().getName());
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("objects");


        return factory;
    }

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setPersistenceProviderClass(HibernatePersistenceProvider.class);
//        factory.setPackagesToScan("order", "objects");
//        factory.setDataSource(dataSource());
//        factory.setJpaProperties(additionalProperties());
//        factory.afterPropertiesSet();
//
//        return factory.getObject();
//    }
//
//    private Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "validate");
//        properties.setProperty("hibernate.dialect",
//                "org.hibernate.dialect.HSQLDialect");
//        properties.setProperty("hibernate.show_sql", "false");
//        properties.setProperty("hibernate.format_sql", "true");
//
//        return properties;
//    }

}