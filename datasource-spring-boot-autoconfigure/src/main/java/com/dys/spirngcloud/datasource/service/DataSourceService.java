package com.dys.spirngcloud.datasource.service;

import com.dys.spirngcloud.datasource.properties.*;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceService {

   private DataSource getDataSource(String url, String username, String password, Integer initialSize, Integer maxTotal, Integer maxIdle, Integer minIdle, Long maxWaitMillis, Boolean removeAbandonedOnMaintenance, Boolean removeAbandonedOnBorrow, Boolean jdbcCompliantTruncation) {
      Properties dataSourceProperties = new Properties();
      dataSourceProperties.put("url", url);
      dataSourceProperties.put("username", username);
      dataSourceProperties.put("password", password);

      dataSourceProperties.put("initialSize", initialSize);
      dataSourceProperties.put("maxTotal", maxTotal);
      dataSourceProperties.put("maxIdle", maxIdle);
      dataSourceProperties.put("minIdle", minIdle);
      dataSourceProperties.put("maxWaitMillis", maxWaitMillis);
      dataSourceProperties.put("removeAbandonedOnMaintenance", removeAbandonedOnMaintenance);
      dataSourceProperties.put("removeAbandonedOnBorrow", removeAbandonedOnBorrow);
      dataSourceProperties.put("jdbcCompliantTruncation", jdbcCompliantTruncation);

      DataSource dataSource = null;
      try {
        dataSource = BasicDataSourceFactory.createDataSource(dataSourceProperties);
      } catch (Exception e) {
        e.printStackTrace();
      }

      return dataSource;
   }

    public DataSource firstDataSource(First first) {

        return getDataSource(first.getUrl(), first.getUsername(), first.getPassword(), first.getInitialSize(), first.getMaxTotal(), first.getMaxIdle(), first.getMinIdle(), first.getMaxWaitMillis(), first.getRemoveAbandonedOnMaintenance(), first.getRemoveAbandonedOnBorrow(), first.getJdbcCompliantTruncation());
    }

   public DataSource secondDataSource(Second second) {

      return getDataSource(second.getUrl(), second.getUsername(), second.getPassword(), second.getInitialSize(), second.getMaxTotal(), second.getMaxIdle(), second.getMinIdle(), second.getMaxWaitMillis(), second.getRemoveAbandonedOnMaintenance(), second.getRemoveAbandonedOnBorrow(), second.getJdbcCompliantTruncation());
   }

   public DataSource thirdDataSource(Third third) {
      return getDataSource(third.getUrl(), third.getUsername(), third.getPassword(), third.getInitialSize(), third.getMaxTotal(), third.getMaxIdle(), third.getMinIdle(), third.getMaxWaitMillis(), third.getRemoveAbandonedOnMaintenance(), third.getRemoveAbandonedOnBorrow(), third.getJdbcCompliantTruncation());
   }

   public DataSource fourthDataSource(Fourth fourth) {

      return getDataSource(fourth.getUrl(), fourth.getUsername(), fourth.getPassword(), fourth.getInitialSize(), fourth.getMaxTotal(), fourth.getMaxIdle(), fourth.getMinIdle(), fourth.getMaxWaitMillis(), fourth.getRemoveAbandonedOnMaintenance(), fourth.getRemoveAbandonedOnBorrow(), fourth.getJdbcCompliantTruncation());
   }

   public DataSource fifthDataSource(Fifth fifth) {

      return getDataSource(fifth.getUrl(), fifth.getUsername(), fifth.getPassword(), fifth.getInitialSize(), fifth.getMaxTotal(), fifth.getMaxIdle(), fifth.getMinIdle(), fifth.getMaxWaitMillis(), fifth.getRemoveAbandonedOnMaintenance(), fifth.getRemoveAbandonedOnBorrow(), fifth.getJdbcCompliantTruncation());
   }
}
