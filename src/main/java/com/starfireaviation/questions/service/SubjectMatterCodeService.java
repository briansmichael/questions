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
import com.starfireaviation.common.model.SubjectMatterCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SubjectMatterCodeService {

    /**
     * SubjectMatterCode Cache.
     */
    private final IMap<Long, SubjectMatterCode> cache;

    /**
     * SubjectMatterCodeService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public SubjectMatterCodeService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("subjectmattercodes");
    }

    /**
     * Gets a Learning Statement Code by code.
     *
     * @param lscCode Learning Statement Code
     * @return SubjectMatterCode
     */
    public List<SubjectMatterCode> findByCode(final String lscCode) {
        return cache
                .values()
                .stream()
                .filter(subjectMatterCode -> lscCode.equalsIgnoreCase(subjectMatterCode.getCode()))
                .collect(Collectors.toList());
    }

    /**
     * Gets a SubjectMatterCode by ID.
     *
     * @param id SubjectMatterCode ID
     * @return SubjectMatterCode
     */
    public SubjectMatterCode get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a SubjectMatterCode.
     *
     * @param subjectMatterCode SubjectMatterCode
     * @return SubjectMatterCode
     */
    public SubjectMatterCode save(final SubjectMatterCode subjectMatterCode) {
        if (subjectMatterCode == null) {
            return null;
        } else if (subjectMatterCode.getId() == null) {
            subjectMatterCode.setId(assignId());
        }
        return cache.put(subjectMatterCode.getId(), subjectMatterCode);
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
