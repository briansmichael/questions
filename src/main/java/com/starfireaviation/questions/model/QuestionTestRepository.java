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
 * QuestionTestsRepository.
 */
@Repository
public interface QuestionTestRepository extends JpaRepository<QuestionTestEntity, Long> {

    /**
     * Gets a QuestionTest by question ID.
     *
     * @param id question ID
     * @return QuestionTest
     */
    Optional<List<QuestionTestEntity>> findByQuestionId(Long id);

    /**
     * Gets a QuestionTest by test ID.
     *
     * @param id test ID
     * @return QuestionTest
     */
    Optional<List<QuestionTestEntity>> findByTestId(Long id);

    /**
     * Gets a QuestionTest.
     *
     * @param id Long
     * @return QuestionTest
     */
    Optional<QuestionTestEntity> findById(Long id);

}
