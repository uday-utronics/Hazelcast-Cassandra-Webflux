package com.hazelcast.demo.config;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.demo.model.JobDto;
import com.hazelcast.map.IMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {
    @Bean
    public HazelcastInstance hazelcastInstance() {
        Config config = new Config();
        //config.setProperty("hazelcast.jmx", "true");
        return Hazelcast.newHazelcastInstance(config);
    }

    @Bean
    public IMap<String, JobDto> userCache(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("user-cache");
    }
}
