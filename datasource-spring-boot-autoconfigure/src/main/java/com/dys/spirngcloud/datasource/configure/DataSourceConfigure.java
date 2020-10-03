package com.dys.spirngcloud.datasource.configure;

import com.dys.spirngcloud.datasource.properties.*;
import com.dys.spirngcloud.datasource.service.DataSourceService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(value = { First.class, Second.class, Third.class, Fourth.class, Fifth.class })
@ConditionalOnClass(value = {DataSourceService.class})
public class DataSourceConfigure {

  @Resource
  private First first;
  
  @Resource
  private Second second;

  @Resource
  private Third third;

  @Resource
  private Fourth fourth;

  @Resource
  private Fifth fifth;
  
  @Bean
  @ConditionalOnProperty(name = "spring.datasource.first.enabled", havingValue = "true")
  public DataSource firstDataSource() {
     return new DataSourceService().firstDataSource(first);
  }
  
  @Bean
  @ConditionalOnProperty(name = "spring.datasource.second.enabled", havingValue = "true")
  public DataSource secondDataSource() {
     return new DataSourceService().secondDataSource(second);
  }

  @Bean
  @ConditionalOnProperty(name = "spring.datasource.third.enabled", havingValue = "true")
  public DataSource thirdDataSource() {
    return new DataSourceService().thirdDataSource(third);
  }

  @Bean
  @ConditionalOnProperty(name = "spring.datasource.fourth.enabled", havingValue = "true")
  public DataSource fourthDataSource() {
    return new DataSourceService().fourthDataSource(fourth);
  }

  @Bean
  @ConditionalOnProperty(name = "spring.datasource.fifth.enabled", havingValue = "true")
  public DataSource fifthDataSource() {
    return new DataSourceService().fifthDataSource(fifth);
  }

}
