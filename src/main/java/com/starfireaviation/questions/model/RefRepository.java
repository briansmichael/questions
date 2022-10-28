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
 * RefsRepository.
 */
@Repository
public interface RefRepository extends JpaRepository<RefEntity, Long> {

    /**
     * Gets a Ref by Ref ID.
     *
     * @param id ref ID
     * @return Ref
     */
    Optional<RefEntity> findByRefId(Long id);

    /**
     * Saves a Ref.
     *
     * @param ref Ref
     * @return Ref
     */
    RefEntity save(RefEntity ref);

    /**
     * Gets all Ref.
     *
     * @return list of Ref
     */
    List<RefEntity> findAll();

    /**
     * Deletes a Ref.
     *
     * @param ref Ref
     */
    void delete(RefEntity ref);

    /**
     * Gets a Ref.
     *
     * @param id Long
     * @return Ref
     */
    RefEntity findById(long id);

}
