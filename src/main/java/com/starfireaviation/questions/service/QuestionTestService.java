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
import com.starfireaviation.common.model.QuestionTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionTestService {

    /**
     * QuestionTest Cache.
     */
    private final IMap<Long, QuestionTest> cache;

    /**
     * QuestionTestService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public QuestionTestService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("questiontest");
    }

    /**
     * Gets a QuestionTest by question ID.
     *
     * @param questionId question ID
     * @return QuestionTest
     */
    public List<QuestionTest> findByQuestionId(final Long questionId) {
        return cache
                .values()
                .stream()
                .filter(questionTest -> Objects.equals(questionTest.getQuestionId(), questionId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a QuestionTest by test ID.
     *
     * @param testId test ID
     * @return QuestionTest
     */
    public List<QuestionTest> findByTestId(final Long testId) {
        return cache
                .values()
                .stream()
                .filter(questionTest -> Objects.equals(questionTest.getTestId(), testId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a QuestionTest by ID.
     *
     * @param id QuestionTest ID
     * @return QuestionTest
     */
    public QuestionTest get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a QuestionTest.
     *
     * @param questionTest QuestionTest
     * @return QuestionTest
     */
    public QuestionTest save(final QuestionTest questionTest) {
        if (questionTest == null) {
            return null;
        } else if (questionTest.getId() == null) {
            questionTest.setId(assignId());
        }
        return cache.put(questionTest.getId(), questionTest);
    }

    /**
     * Finds an ID to assign to an entity.
     *
     * @return next ID value
     */
    private Long assignId() {
        Long max = Long.MIN_VALUE;
        for (final Long id : cache.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max + 1;
    }
}
