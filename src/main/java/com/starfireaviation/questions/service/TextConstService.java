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
import com.starfireaviation.common.model.TextConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TextConstService {

    /**
     * TextConst Cache.
     */
    private final IMap<Long, TextConst> cache;

    /**
     * TextConstService.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public TextConstService(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("textconst");
    }

    /**
     * Gets a TextConst by ID.
     *
     * @param id TextConst ID
     * @return TextConst
     */
    public TextConst get(final Long id) {
        return cache.get(id);
    }

    /**
     * Saves a TextConst.
     *
     * @param textConst TextConst
     * @return TextConst
     */
    public TextConst save(final TextConst textConst) {
        if (textConst == null) {
            return null;
        } else if (textConst.getId() == null) {
            textConst.setId(assignId());
        }
        return cache.put(textConst.getId(), textConst);
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
