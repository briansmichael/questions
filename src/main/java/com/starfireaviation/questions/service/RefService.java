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
import com.starfireaviation.common.model.Ref;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RefService {

    /**
     * Ref Cache.
     */
    private final IMap<Long, Ref> cache;

    /**
     * RefService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public RefService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("refs");
    }

    /**
     * Gets a Ref by ID.
     *
     * @param id Ref ID
     * @return Ref
     */
    public Ref get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a Ref.
     *
     * @param ref Ref
     * @return Ref
     */
    public Ref save(final Ref ref) {
        if (ref == null) {
            return null;
        } else if (ref.getRefId() == null) {
            ref.setRefId(assignId());
        }
        return cache.put(ref.getRefId(), ref);
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
