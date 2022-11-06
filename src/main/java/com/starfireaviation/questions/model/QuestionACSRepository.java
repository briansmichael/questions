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
    Optional<QuestionACS> findById(Long id);

    /**
     * Gets a QuestionACS by remote ID.
     *
     * @param id ACS ID
     * @return QuestionACS
     */
    Optional<List<QuestionACS>> findByAcsId(Long id);

    /**
     * Gets a QuestionACS by question ID.
     *
     * @param id Question ID
     * @return QuestionACS
     */
    Optional<List<QuestionACS>> findByQuestionId(Long id);

}
