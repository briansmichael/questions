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
 * AnswerRepository.
 */
@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    /**
     * Gets an answer by Remote ID and Course.
     *
     * @param id remote answer ID
     * @return Answer
     */
    Optional<AnswerEntity> findByRemoteId(Long id);

    /**
     * Gets all answers for a question.
     *
     * @param questionId question ID
     * @return list of Answer
     */
    Optional<List<AnswerEntity>> findAllAnswerByQuestionId(Long questionId);

    /**
     * Saves an answer.
     *
     * @param answer Answer
     * @return Answer
     */
    AnswerEntity save(AnswerEntity answer);

    /**
     * Deletes an answer.
     *
     * @param answer Answer
     */
    void delete(AnswerEntity answer);

    /**
     * Gets an answer.
     *
     * @param id Long
     * @return Answer
     */
    Optional<AnswerEntity> findById(long id);

}