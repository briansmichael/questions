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
import com.starfireaviation.common.model.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * QuizService.
 */
@Slf4j
@Service
public class QuizService {

    /**
     * Quiz Cache.
     */
    private final IMap<Long, Quiz> cache;

    /**
     * QuizService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public QuizService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("quiz");
    }

    /**
     * Gets a quiz.
     *
     * @param id Long
     * @return Quiz
     */
    public Quiz get(final long id) {
        return cache.get(id);
    }

    /**
     * Gets all quizzes for a given lesson plan.
     *
     * @param lessonPlanId Long
     * @return Quiz
     */
    public List<Quiz> findByLessonPlanId(final Long lessonPlanId) {
        return cache
                .values()
                .stream()
                .filter(quiz -> Objects.equals(quiz.getLessonPlanId(), lessonPlanId))
                .collect(Collectors.toList());
    }

    /**
     * Saves a Quiz.
     *
     * @param quiz Quiz
     * @return Quiz
     */
    public Quiz save(final Quiz quiz) {
        if (quiz == null) {
            return null;
        } else if (quiz.getId() == null) {
            quiz.setId(assignId());
        }
        return cache.put(quiz.getId(), quiz);
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
