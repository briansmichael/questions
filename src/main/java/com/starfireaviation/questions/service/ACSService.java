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
import com.starfireaviation.common.model.ACS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ACSService {

    /**
     * ACS Cache.
     */
    private final IMap<Long, ACS> cache;

    /**
     * ACSService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public ACSService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("acs");
    }

    /**
     * Gets ACS by group ID.
     *
     * @param groupId group ID
     * @return list of ACS
     */
    public List<ACS> findByGroupId(final Long groupId) {
        return cache
                .values()
                .stream()
                .filter(acs -> Objects.equals(acs.getGroupId(), groupId))
                .collect(Collectors.toList());
    }

    /**
     * Gets ACS by code.
     *
     * @param code code
     * @return list of ACS
     */
    public List<ACS> findByCode(final String code) {
        if (code == null) {
            return new ArrayList<>();
        }
        return cache
                .values()
                .stream()
                .filter(acs -> code.equalsIgnoreCase(acs.getCode()))
                .collect(Collectors.toList());
    }

    /**
     * Gets an ACS by ID.
     *
     * @param id ACS ID
     * @return ACS
     */
    public ACS get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves an ACS.
     *
     * @param acs ACS
     * @return ACS
     */
    public ACS save(final ACS acs) {
        if (acs == null) {
            return null;
        } else if (acs.getId() == null) {
            acs.setId(assignId());
        }
        return cache.put(acs.getId(), acs);
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
