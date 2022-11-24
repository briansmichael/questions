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
import com.starfireaviation.common.model.Chapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChapterService {

    /**
     * Chapter Cache.
     */
    private final IMap<Long, Chapter> cache;

    /**
     * ChapterService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public ChapterService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("questions");
    }

    /**
     * Gets a chapter by group ID.
     *
     * @param id Chapter ID
     * @return Chapter
     */
    public List<Chapter> findByGroupId(final Long id) {
        return cache
                .values()
                .stream()
                .filter(chapter -> Objects.equals(id, chapter.getGroupId()))
                .collect(Collectors.toList());
    }

    /**
     * Gets a Chapter by ID.
     *
     * @param id Chapter ID
     * @return Chapter
     */
    public Chapter get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a Chapter.
     *
     * @param chapter Chapter
     * @return Chapter
     */
    public Chapter save(final Chapter chapter) {
        if (chapter == null) {
            return null;
        } else if (chapter.getChapterId() == null) {
            chapter.setChapterId(assignId());
        }
        return cache.put(chapter.getChapterId(), chapter);
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
