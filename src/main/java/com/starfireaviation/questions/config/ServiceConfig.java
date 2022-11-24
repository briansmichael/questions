/*
 *  Copyright (C) 2022 Starfire Aviation, LLC
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.starfireaviation.questions.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.starfireaviation.common.CommonConstants;
import com.starfireaviation.common.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ServiceConfig.
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
public class ServiceConfig {

    /**
     * Hazelcast Questions Instance.
     *
     * @return HazelcastInstance
     */
    @Bean("questions")
    public HazelcastInstance hazelcastQuestionsInstance() {
        return Hazelcast.newHazelcastInstance(
                new Config().addMapConfig(
                        new MapConfig("questions")
                                .setTimeToLiveSeconds(CommonConstants.THREE_HUNDRED)
                                .setMaxIdleSeconds(CommonConstants.THREE_HUNDRED)));
    }

    /**
     * DataService.
     *
     * @return DataService
     */
    @Bean
    public DataService dataservice() {
        return new DataService();
    }

}
