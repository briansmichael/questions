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

package com.starfireaviation.questions.service;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.starfireaviation.questions.exception.ResourceNotFoundException;
import com.starfireaviation.questions.model.QuestionEntity;
import com.starfireaviation.questions.model.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Question Service.
 */
@Slf4j
@Service
public class QuestionService {

    /**
     * QuestionRepository.
     */
    @Autowired
    private QuestionRepository questionRepository;

    /**
     * HazelcastInstance.
     */
    private final IMap<Long, QuestionEntity> cache;

    /**
     * Constructor.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public QuestionService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("questions");
    }

    /**
     * Deletes a question.
     *
     * @param id id
     */
    public void delete(final long id) throws ResourceNotFoundException {
        cache.remove(id);
        questionRepository.delete(get(id));
    }

    /**
     * Gets a question by ID.
     *
     * @param id question ID
     * @return Question for the provided ID
     */
    public QuestionEntity get(final long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        final QuestionEntity questionEntity = questionRepository.findById(id).orElseThrow();
        cache.put(id, questionEntity);
        return questionEntity;
    }

}
