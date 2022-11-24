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
import com.starfireaviation.common.model.Group;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupService {

    /**
     * Group Cache.
     */
    private final IMap<Long, Group> cache;

    /**
     * GroupService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public GroupService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("groups");
    }

    /**
     * Gets a Group by abbr.
     *
     * @param groupAbbr group abbr
     * @return GroupEntity
     */
    public List<Group> findByGroupAbbr(final String groupAbbr) {
        return cache
                .values()
                .stream()
                .filter(group -> groupAbbr.equalsIgnoreCase(group.getGroupAbbr()))
                .collect(Collectors.toList());
    }

    /**
     * Gets a Group by ID.
     *
     * @param id Group ID
     * @return Group
     */
    public Group get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a Group.
     *
     * @param group Group
     * @return Group
     */
    public Group save(final Group group) {
        if (group == null) {
            return null;
        } else if (group.getGroupId() == null) {
            group.setGroupId(assignId());
        }
        return cache.put(group.getGroupId(), group);
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
