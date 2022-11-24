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
import com.starfireaviation.common.model.Answer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * AnswerService.
 */
@Slf4j
@Service
public class AnswerService {

    /**
     * Answer Cache.
     */
    private final IMap<Long, Answer> cache;

    /**
     * AnswerService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public AnswerService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("answers");
    }

    /**
     * Gets an answer.
     *
     * @param id Long
     * @return Answer
     */
    public Answer get(final long id) {
        return cache.get(id);
    }

    /**
     * Gets all answers for a question.
     *
     * @param questionId question ID
     * @return list of Answer
     */
    public List<Answer> findByQuestionId(final Long questionId) {
        return cache
                .values()
                .stream()
                .filter(answer -> Objects.equals(questionId, answer.getQuestionId()))
                .collect(Collectors.toList());
    }

    /**
     * Saves an Answer.
     *
     * @param answer Answer
     * @return Answer
     */
    public Answer save(final Answer answer) {
        if (answer == null) {
            return null;
        } else if (answer.getId() == null) {
            answer.setId(assignId());
        }
        return cache.put(answer.getId(), answer);
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
