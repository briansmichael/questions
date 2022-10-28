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

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * QuestionTest.
 */
@Data
@Entity
@Table(name = "QUESTIONS_TESTS")
public class QuestionTestEntity extends BaseEntity {

    /**
     * Remote ID.
     */
    private Long remoteId;

    /**
     * Question ID.
     */
    private Long questionId;

    /**
     * Test ID.
     */
    private Long testId;

    /**
     * Is Linked.
     */
    private Long isLinked;

    /**
     * Sort By.
     */
    private Long sortBy;

    /**
     * Link Chapter.
     */
    private Long linkChapter;

    /**
     * Is Important.
     */
    private Long isImportant;

}
