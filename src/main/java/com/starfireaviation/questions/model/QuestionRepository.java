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
 * QuestionRepository.
 */
@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    /**
     * Gets question by question ID.
     *
     * @param questionId question ID
     * @return Question
     */
    Optional<QuestionEntity> findByQuestionId(Long questionId);

    /**
     * Gets questions by chapter ID.
     *
     * @param chapterId chapter ID
     * @return list of Questions
     */
    Optional<List<QuestionEntity>> findByChapterId(Long chapterId);

    /**
     * Gets questions by subject matter code.
     *
     * @param smcId subject matter code ID
     * @return list of questions
     */
    Optional<List<QuestionEntity>> findBySmcId(Long smcId);

    /**
     * Gets questions for Learning Statement Code.
     *
     * @param lscId Learning Statement Code ID
     * @return list of questions
     */
    Optional<List<QuestionEntity>> findByLscId(Long lscId);

}
