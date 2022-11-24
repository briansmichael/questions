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
import com.starfireaviation.common.model.QuestionReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionReferenceService {

    /**
     * QuestionReference Cache.
     */
    private final IMap<Long, QuestionReference> cache;

    /**
     * QuestionReferenceService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public QuestionReferenceService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("questionreference");
    }

    /**
     * Gets a QuestionReference by question ID.
     *
     * @param questionId Question ID
     * @return QuestionReference
     */
    public List<QuestionReference> findByQuestionId(final Long questionId) {
        return cache
                .values()
                .stream()
                .filter(questionReference -> Objects.equals(questionReference.getQuestionId(), questionId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a QuestionReference by question ID.
     *
     * @param refId Reference ID
     * @return QuestionReference
     */
    public List<QuestionReference> findByRefId(final Long refId) {
        return cache
                .values()
                .stream()
                .filter(questionReference -> Objects.equals(questionReference.getQuestionId(), refId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a QuestionReference by ID.
     *
     * @param id QuestionReference ID
     * @return QuestionReference
     */
    public QuestionReference get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a QuestionReference.
     *
     * @param questionReference QuestionReference
     * @return QuestionReference
     */
    public QuestionReference save(final QuestionReference questionReference) {
        if (questionReference == null) {
            return null;
        } else if (questionReference.getId() == null) {
            questionReference.setId(assignId());
        }
        return cache.put(questionReference.getId(), questionReference);
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
