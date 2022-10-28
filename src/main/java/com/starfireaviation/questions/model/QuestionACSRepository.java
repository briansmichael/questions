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
 * QuestionACSRepository.
 */
@Repository
public interface QuestionACSRepository extends JpaRepository<QuestionACS, Long> {

    /**
     * Gets a QuestionACS by remote ID.
     *
     * @param id remote ID
     * @return QuestionACS
     */
    Optional<QuestionACS> findByRemoteId(Long id);

    /**
     * Saves a QuestionACS.
     *
     * @param questionACS QuestionACS
     * @return QuestionACS
     */
    QuestionACS save(QuestionACS questionACS);

    /**
     * Gets all QuestionACS.
     *
     * @return list of QuestionACS
     */
    List<QuestionACS> findAll();

    /**
     * Deletes a QuestionACS.
     *
     * @param questionACS QuestionACS
     */
    void delete(QuestionACS questionACS);

    /**
     * Gets a QuestionACS.
     *
     * @param id Long
     * @return QuestionACS
     */
    QuestionACS findById(long id);

}
