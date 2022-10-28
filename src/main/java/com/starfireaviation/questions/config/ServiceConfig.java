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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.starfireaviation.model.CommonConstants;
import com.starfireaviation.questions.util.GSDecryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * ServiceConfig.
 */
@Slf4j
@Configuration
@EnableAsync
@EnableTransactionManagement
@EnableConfigurationProperties({ ApplicationProperties.class })
public class ServiceConfig {

    /**
     * HttpClient.
     *
     * @return HttpClient
     */
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }

    /**
     * ObjectMapper.
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    /**
     * GroundSchool data decryptor.
     *
     * @param props GroundSchoolProperties
     * @return GSDecryptor
     */
    @Bean
    public GSDecryptor gsDecryptor(final ApplicationProperties props) {
        log.info("Secret Key: {}; Init Vector: {}", props.getSecretKey(), props.getInitVector());
        return new GSDecryptor(props.isGsDecryptorEnabled(), props.getSecretKey(), props.getInitVector());
    }

    /**
     * Creates a rest template with default timeout settings. The bean definition
     * will be updated to accept timeout
     * parameters once those are part of the Customer settings.
     *
     * @param restTemplateBuilder RestTemplateBuilder
     * @param props   ApplicationProperties
     *
     * @return Rest Template with request, read, and connection timeouts set
     */
    @Bean
    public RestTemplate restTemplate(
            final RestTemplateBuilder restTemplateBuilder,
            final ApplicationProperties props) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(props.getConnectTimeout()))
                .setReadTimeout(Duration.ofMillis(props.getReadTimeout()))
                .additionalMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

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
     * Hazelcast Lock Instance.
     *
     * @return HazelcastInstance
     */
    @Bean("lock")
    public HazelcastInstance hazelcastLockInstance() {
        final int ttl = CommonConstants.SIXTY * CommonConstants.SIXTY * CommonConstants.SIX;
        return Hazelcast.newHazelcastInstance(
                new Config().addMapConfig(
                        new MapConfig("lock").setTimeToLiveSeconds(ttl).setMaxIdleSeconds(ttl)));
    }

}