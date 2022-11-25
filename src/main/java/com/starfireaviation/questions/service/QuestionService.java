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
import com.starfireaviation.common.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Question Service.
 */
@Slf4j
@Service
public class QuestionService {

    /**
     * Question Cache.
     */
    private final IMap<Long, Question> cache;

    /**
     * QuestionService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public QuestionService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("questions");
    }

    /**
     * Gets questions by chapter ID.
     *
     * @param chapterId chapter ID
     * @return list of Questions
     */
    public List<Question> findByChapterId(final Long chapterId) {
        return cache
                .values()
                .stream()
                .filter(question -> Objects.equals(question.getChapterId(), chapterId))
                .collect(Collectors.toList());
    }

    /**
     * Gets questions by chapter ID.
     *
     * @param lsc Learning Statement Code
     * @return list of Questions
     */
    public List<Question> findByLearningStatementCode(final String lsc) {
        return cache
                .values()
                .stream()
                .filter(question -> Objects.equals(question.getLearningStatementCode(), lsc))
                .collect(Collectors.toList());
    }

    /**
     * Gets a Question by ID.
     *
     * @param id question ID
     * @return Question for the provided ID
     */
    public Question get(final long id) {
        return cache.get(id);
    }

    /**
     * Saves a Question.
     *
     * @param question Question
     * @return Question
     */
    public Question save(final Question question) {
        if (question == null) {
            return null;
        } else if (question.getId() == null) {
            question.setId(assignId());
        }
        return cache.put(question.getId(), question);
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
