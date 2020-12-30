package com.dys.springcloud.sharding.jdbc.configure;

import com.dys.springcloud.sharding.jdbc.context.ShardingsphereContext;
import com.dys.springcloud.sharding.jdbc.properites.ShardingProperty;
import com.dys.springcloud.sharding.jdbc.properites.ShardingsphereContextProperty;
import com.dys.springcloud.sharding.jdbc.properites.ShardingsphereProperties;
import com.dys.springcloud.sharding.jdbc.service.PreciseShardingAlgorithmService;
import com.sun.tools.javac.util.StringUtils;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Configuration
@ConditionalOnClass(value = {PreciseShardingAlgorithmService.class})
@EnableConfigurationProperties(value = {ShardingsphereProperties.class})
public class ShardingJDBCConfigurer {

    @Resource
    private DataSource dataSource;

    @Resource
    private ShardingsphereProperties shardingsphereProperties;

    @Resource
    private PreciseShardingAlgorithmService preciseShardingAlgorithmService;

    @Bean
    public ShardingRuleConfiguration shardingRuleConfiguration() {

        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        Collection<TableRuleConfiguration> tableRuleConfigs = shardingRuleConfiguration.getTableRuleConfigs();

        for (Map.Entry<String, ShardingProperty> entries : this.shardingsphereProperties.getTable().entrySet()) {
            String logicTableName = entries.getKey();
            ShardingProperty shardingProperty = entries.getValue();
            String shardingColumn = shardingProperty.getShardingColumn();
            String keyType = shardingProperty.getKeyType();
            String keyColumn = shardingProperty.getKeyColumn();
            String actualDataNode = shardingProperty.getActualDataNodes();

            List<String> actualDataNodes = new InlineExpressionParser(actualDataNode).splitAndEvaluate();

            ShardingsphereContextProperty shardingsphereContextProperty = new ShardingsphereContextProperty();
            shardingsphereContextProperty.setShardingColumn(shardingColumn);
            shardingsphereContextProperty.setActualDataNodes(actualDataNodes);

            ShardingsphereContext.setShardingsphereContextProperty(logicTableName, shardingsphereContextProperty);

            TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(logicTableName, StringUtils.join(actualDataNodes, ","));
            tableRuleConfiguration.setDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration(shardingColumn, this.preciseShardingAlgorithmService.getDatabasePreciseShardingAlgorithm(shardingColumn)));
            tableRuleConfiguration.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration(shardingColumn, this.preciseShardingAlgorithmService.getTablePreciseShardingAlgorithm(shardingColumn)));

            if (StringUtils.isNoneBlank(keyColumn) && StringUtils.isNoneBlank(keyType)) {
                tableRuleConfiguration.setKeyGeneratorConfig(new KeyGeneratorConfiguration(keyType, keyColumn));
            }

            tableRuleConfigs.add(tableRuleConfiguration);
        }

        shardingRuleConfiguration.getBindingTableGroups().add(this.shardingsphereProperties.getBindingTableGroups());

        return shardingRuleConfiguration;
    }

    @PostConstruct
    public void postConstruct() throws Exception {

        DynamicRoutingDataSource dynamicRoutingDataSource = (DynamicRoutingDataSource) this.dataSource;
        Map<String, DataSource> dataSourceMap = dynamicRoutingDataSource.getCurrentDataSources();

        Properties properties = new Properties();
        properties.put("sql.show", true);

        ShardingDataSource shardingDataSource = (ShardingDataSource) ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration(), properties);
        dynamicRoutingDataSource.addDataSource("sharding", shardingDataSource);
    }
}