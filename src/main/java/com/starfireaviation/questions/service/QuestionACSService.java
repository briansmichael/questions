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
import com.starfireaviation.common.model.QuestionACS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionACSService {

    /**
     * QuestionACS Cache.
     */
    private final IMap<Long, QuestionACS> cache;

    /**
     * GroupService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public QuestionACSService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("questionacs");
    }

    /**
     * Gets a QuestionACS by ACS ID.
     *
     * @param acsId ACS ID
     * @return QuestionACS
     */
    public List<QuestionACS> findByAcsId(final Long acsId) {
        return cache
                .values()
                .stream()
                .filter(questionACS -> Objects.equals(questionACS.getAcsId(), acsId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a QuestionACS by question ID.
     *
     * @param questionId Question ID
     * @return QuestionACS
     */
    public List<QuestionACS> findByQuestionId(final Long questionId) {
        return cache
                .values()
                .stream()
                .filter(questionACS -> Objects.equals(questionACS.getQuestionId(), questionId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a QuestionACS by ID.
     *
     * @param id QuestionACS ID
     * @return QuestionACS
     */
    public QuestionACS get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a QuestionACS.
     *
     * @param questionACS QuestionACS
     * @return QuestionACS
     */
    public QuestionACS save(final QuestionACS questionACS) {
        if (questionACS == null) {
            return null;
        } else if (questionACS.getId() == null) {
            questionACS.setId(assignId());
        }
        return cache.put(questionACS.getId(), questionACS);
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
