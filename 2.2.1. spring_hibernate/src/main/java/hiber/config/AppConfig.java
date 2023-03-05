package hiber.config;

import hiber.model.Car;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

//Этот класс является конфигурационным классом для настройки приложения, использующего Hibernate для работы с базой данных.
@Configuration// указывает, что это конфигурационный класс, который будет создавать бины для приложения.
@PropertySource("classpath:db.properties")//указывает, что файл db.properties будет использоваться для загрузки свойств приложения.
@EnableTransactionManagement// указывает, что транзакции будут использоваться в приложении.
@ComponentScan(value = "hiber")//указывает на то, что все компоненты (классы, помеченные аннотацией @Component, @Service, @Repository и т.д.) должны сканироваться в пакете hiber.
public class AppConfig {

   @Autowired
   private Environment env;//Это означает, что Spring автоматически внедрит экземпляр Environment в эту переменную.
   // Environment - это объект, который содержит свойства приложения, определенные в файлах свойств и системных свойства

  //создает объект DriverManagerDataSource, который представляет собой конфигурацию базы данных.
  // В этом методе мы устанавливаем свойства для создания соединения с базой данных, используя свойства,
  // загруженные из файла свойств.


   //Метод getDataSource() создает объект DriverManagerDataSource, который представляет собой конфигурацию
   // базы данных. В этом методе мы устанавливаем свойства для создания соединения с базой данных,
   // используя свойства, загруженные из файла свойств.
   @Bean
   public DataSource getDataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();//- создание нового объекта DriverManagerDataSource, который будет использоваться для установления соединения с базой данных.
      dataSource.setDriverClassName(env.getProperty("db.driver"));// - установка значения свойства driverClassName в объекте dataSource из переменной окружения, содержащей имя драйвера для соответствующей базы данных.
      dataSource.setUrl(env.getProperty("db.url"));//- установка значения свойства URL в объекте dataSource из переменной окружения, содержащей URL-адрес базы данных.
      dataSource.setUsername(env.getProperty("db.username"));//- установка значения свойства username в объекте dataSource из переменной окружения, содержащей имя пользователя для соответствующей базы данных.
      dataSource.setPassword(env.getProperty("db.password"));//установка значения свойства password в объекте dataSource из переменной окружения, содержащей пароль для соответствующей базы данных..
      return dataSource;
   }
   //Метод getSessionFactory() создает объект LocalSessionFactoryBean, который представляет собой сессию Hibernate.
   // Здесь мы устанавливаем DataSource, используя метод getDataSource(), устанавливаем аннотированные классы,
   // которые будут использоваться Hibernate, и устанавливаем свойства Hibernate, такие как show_sql и hbm2ddl.auto,
   // используя свойства из файла свойств.

   @Bean
   public LocalSessionFactoryBean getSessionFactory() {
      LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
      factoryBean.setDataSource(getDataSource());
      
      Properties props=new Properties();
      props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
      props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));

      factoryBean.setHibernateProperties(props);
      factoryBean.setAnnotatedClasses(User.class, Car.class);
      return factoryBean;
   }

  // Метод getTransactionManager() создает объект HibernateTransactionManager, который представляет собой менеджер
   // транзакций для Hibernate. Здесь мы устанавливаем сессию, используя метод getSessionFactory().getObject(),
   // что позволяет менеджеру транзакций использовать эту сессию для управления транзакциями в приложении.

   @Bean
   public HibernateTransactionManager getTransactionManager() {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(getSessionFactory().getObject());
      return transactionManager;
   }
}
//Каждый метод возвращает созданный бин, который будет использоваться в приложении,
// чтобы работать с базой данных через Hibernate.
