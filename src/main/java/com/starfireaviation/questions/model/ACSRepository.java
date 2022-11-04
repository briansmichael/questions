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
 * ACSRepository.
 */
@Repository
public interface ACSRepository extends JpaRepository<ACSEntity, Long> {

    /**
     * Gets an ACS by Remote ID.
     *
     * @param id remote ID
     * @return ACS
     */
    Optional<ACSEntity> findByRemoteId(Long id);

    /**
     * Finds the distinct list of ACS codes for the provided list of ACS Ids.
     *
     * @param acsIds list of ACS Ids
     * @return distinct list of ACS codes
     */
    Optional<List<String>> findDistinctCodeByRemoteIdIn(List<Long> acsIds);

    /**
     * Saves an ACS.
     *
     * @param acs ACS
     * @return ACS
     */
    ACSEntity save(ACSEntity acs);

    /**
     * Gets all ACS.
     *
     * @return list of ACS
     */
    List<ACSEntity> findAll();

    /**
     * Deletes an ACS.
     *
     * @param acs ACS
     */
    void delete(ACSEntity acs);

    /**
     * Gets an ACS.
     *
     * @param id Long
     * @return ACS
     */
    ACSEntity findById(long id);

}
