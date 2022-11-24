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
import com.starfireaviation.common.model.QuestionRefImage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class QuestionRefImageService {

    /**
     * QuestionRefImage Cache.
     */
    private final IMap<Long, QuestionRefImage> cache;

    /**
     * QuestionRefImageService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public QuestionRefImageService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("questionrefimage");
    }

    /**
     * Gets a QuestionRefImages.
     *
     * @param questionId Question ID
     * @return QuestionRefImages
     */
    public List<QuestionRefImage> findByQuestionId(final Long questionId) {
        return cache
                .values()
                .stream()
                .filter(questionRefImage -> Objects.equals(questionRefImage.getQuestionId(), questionId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a QuestionRefImages.
     *
     * @param imageId Long
     * @return QuestionRefImages
     */
    public List<QuestionRefImage> findByImageId(final Long imageId) {
        return cache
                .values()
                .stream()
                .filter(questionRefImage -> Objects.equals(questionRefImage.getImageId(), imageId))
                .collect(Collectors.toList());
    }

    /**
     * Gets a QuestionRefImage by ID.
     *
     * @param id QuestionRefImage ID
     * @return QuestionRefImage
     */
    public QuestionRefImage get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a QuestionRefImage.
     *
     * @param questionRefImage QuestionRefImage
     * @return QuestionRefImage
     */
    public QuestionRefImage save(final QuestionRefImage questionRefImage) {
        if (questionRefImage == null) {
            return null;
        } else if (questionRefImage.getId() == null) {
            questionRefImage.setId(assignId());
        }
        return cache.put(questionRefImage.getId(), questionRefImage);
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
