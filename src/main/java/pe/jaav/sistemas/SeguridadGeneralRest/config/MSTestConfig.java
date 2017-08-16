package pe.jaav.sistemas.SeguridadGeneralRest.config;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class MSTestConfig {
//
//	  @Autowired
//	  private Environment environment;
	  
	@Autowired
	HibernateConfigProperties hibernateProperties;
	  
    /** Data source : Propiedades leidas desde el archivoo .YML o .Proerties
     * @return
     */
    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {    	
    	DataSource ds = DataSourceBuilder.create().build(); 
        return ds;
    }    

    /** Session Factory como BEAN, diferenciar la clase : "LocalSessionFactoryBean" 
     * de "HibernateJpaSessionFactoryBean", además set las propiedades
     * respectivas y el DATASOURCE
     * @return
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
    	LocalSessionFactoryBean  sessionFact =  new LocalSessionFactoryBean();
    	sessionFact.setDataSource(dataSource());    	
    	sessionFact.setPackagesToScan(hibernateProperties.getPackagesToScan());    	
    	sessionFact.setHibernateProperties(additionalProperties());
    	
        return sessionFact;
    }
    
    public Properties additionalProperties() {
        Properties properties = new Properties();
        
        properties.setProperty("hibernate.dialect", hibernateProperties.getDialect());
        properties.setProperty("hibernate.show_sql", hibernateProperties.getShow_sql());
        properties.setProperty("hibernate.format_sql", hibernateProperties.getFormat_sql());
        
        return properties;
    }    
    
    /** Como Autowired, manejara las Transacciones de @Transactional
     * Diferenciar la clase : "HibernateTransactionManager"
     * @param sessionFactory
     * @return
     */
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(
    	     SessionFactory sessionFactory) {
    	  
    	      HibernateTransactionManager txManager
    	       = new HibernateTransactionManager();
    	      txManager.setSessionFactory(sessionFactory);
    	 
    	      return txManager;
    	   }

   @Bean
   public MessageSource messageSource() {
       ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
       messageSource.setBasename("/messages");
       messageSource.setDefaultEncoding("UTF-8");
       return messageSource;
   }



//    @Bean(name = "org.dozer.Mapper")
//    public DozerBeanMapper dozerBean() {
//        return new DozerBeanMapper();
//    }

}
