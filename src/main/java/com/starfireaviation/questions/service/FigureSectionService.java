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
import com.starfireaviation.common.model.FigureSection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FigureSectionService {

    /**
     * FigureSection Cache.
     */
    private final IMap<Long, FigureSection> cache;

    /**
     * FigureSectionService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public FigureSectionService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("figuresection");
    }

    /**
     * Gets an FigureSection by ID.
     *
     * @param id FigureSection ID
     * @return FigureSection
     */
    public FigureSection get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a FigureSection.
     *
     * @param figureSection FigureSection
     * @return FigureSection
     */
    public FigureSection save(final FigureSection figureSection) {
        if (figureSection == null) {
            return null;
        } else if (figureSection.getFigureSectionId() == null) {
            figureSection.setFigureSectionId(assignId());
        }
        return cache.put(figureSection.getFigureSectionId(), figureSection);
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
