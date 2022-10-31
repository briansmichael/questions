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
import com.starfireaviation.questions.model.BaseEntity;
import com.starfireaviation.questions.model.QuestionEntity;
import com.starfireaviation.questions.model.QuestionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Gets a list of question IDs for provided search criteria.
     *
     * @param course optional course
     * @param acsId optional ACS ID
     * @param learningStatementCode optional learning statement code
     * @param unit optional unit
     * @param subUnit option subUnit
     * @return list of question IDs
     */
    public List<Long> getQuestions(final String course,
                                   final Long acsId,
                                   final String learningStatementCode,
                                   final String unit,
                                   final String subUnit) {
        final List<Long> questionIds = new ArrayList<>();
        if (course != null) {
            questionIds.addAll(questionRepository
                    .findByCourse(course)
                    .orElseThrow()
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toList()));
        }
        if (acsId != null) {
            final List<Long> list = questionRepository
                    .findByAcsId(acsId)
                    .orElseThrow()
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
        if (learningStatementCode != null) {
            final List<Long> list = questionRepository
                    .findByLearningStatementCode(learningStatementCode)
                    .orElseThrow()
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
        if (unit != null) {
            final List<Long> list = questionRepository
                    .findByUnit(unit)
                    .orElseThrow()
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
        if (subUnit != null) {
            final List<Long> list = questionRepository
                    .findBySubUnit(subUnit)
                    .orElseThrow()
                    .stream()
                    .map(BaseEntity::getId)
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
        return questionIds;
    }
}
