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
     * Gets a question by remote ID.
     *
     * @param id remote question ID
     * @param course course
     * @return Question
     */
    Optional<QuestionEntity> findByRemoteIdAndCourse(Long id, String course);

    /**
     * Gets questions for ACS.
     *
     * @param acsId ACS ID
     * @return list of questions
     */
    Optional<List<QuestionEntity>> findByAcsId(Long acsId);

    /**
     * Gets questions for Learning Statement Code.
     *
     * @param learningStatementCode LSC
     * @return list of questions
     */
    Optional<List<QuestionEntity>> findByLearningStatementCode(String learningStatementCode);

    /**
     * Gets questions for Unit.
     *
     * @param unit unit
     * @return list of questions
     */
    Optional<List<QuestionEntity>> findByUnit(String unit);

    /**
     * Gets questions for Sub Unit.
     *
     * @param subUnit sub-unit
     * @return list of questions
     */
    Optional<List<QuestionEntity>> findBySubUnit(String subUnit);

    /**
     * Gets a question by course.
     *
     * @param course Course
     * @return Question
     */
    Optional<List<QuestionEntity>> findByCourse(String course);

    /**
     * Saves a question.
     *
     * @param question Question
     * @return Question
     */
    QuestionEntity save(QuestionEntity question);

    /**
     * Deletes a question.
     *
     * @param question Question
     */
    void delete(QuestionEntity question);

}
