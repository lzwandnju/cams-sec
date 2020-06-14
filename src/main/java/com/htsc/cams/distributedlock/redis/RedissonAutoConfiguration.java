package com.htsc.cams.distributedlock.redis;

/**
 * Created with IntelliJ IDEA.
 * User: 015348
 * Date: 2020/6/9
 * Time: 19:27
 * Description: No Description
 */

import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {


    @Autowired
    private RedissonProperties redissonProperties;

    @Bean
    @ConditionalOnProperty(name = "redisson.master-name")
    RedissonClient redissonSentinel(){
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissonProperties.getSentinelAddresses())
                .setMasterName(redissonProperties.getMasterName())
                .setTimeout(redissonProperties.getTimeout())
                .setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());
        if(StringUtils.isNotBlank(redissonProperties.getPassword())){
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create(config);
    }


    @Bean
    @ConditionalOnProperty(name="redisson.address")
    RedissonClient redissonSingle(){
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redissonProperties.getAddress())
                .setTimeout(redissonProperties.getTimeout())
                .setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
        if(StringUtils.isNotBlank(redissonProperties.getPassword())){
            serverConfig.setPassword(redissonProperties.getPassword());
        }
        return Redisson.create();
    }

    @Bean
    RedissLockUtil redissLockUtil(RedissonClient redissonClient){
        RedissLockUtil redissLockUtil = new RedissLockUtil();
        redissLockUtil.setRedissonClient(redissonClient);
        return redissLockUtil;
    }


}
