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

package com.starfireaviation.questions.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * SourcesRepository.
 */
@Repository
public interface SourceRepository extends JpaRepository<SourceEntity, Long> {

    /**
     * Gets a Source by remote ID.
     *
     * @param id remote ID
     * @return Source
     */
    Optional<SourceEntity> findByRemoteId(Long id);

    /**
     * Saves a Source.
     *
     * @param source Source
     * @return Source
     */
    SourceEntity save(SourceEntity source);

    /**
     * Gets all Source.
     *
     * @return list of Source
     */
    List<SourceEntity> findAll();

    /**
     * Deletes a Source.
     *
     * @param source Source
     */
    void delete(SourceEntity source);

    /**
     * Gets a Source.
     *
     * @param id Long
     * @return Source
     */
    SourceEntity findById(long id);

}
