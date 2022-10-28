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

import com.starfireaviation.model.CommonConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Question.
 */
@Data
@Entity
@Table(name = "QUESTIONS")
public class QuestionEntity extends BaseEntity {

    /**
     * Remote Question ID.
     */
    private Long remoteId;

    /**
     * Old Question ID.
     */
    private Long oldQuestionId;

    /**
     * Question Text.
     */
    @Column(name = "text", length = CommonConstants.TWO_THOUSAND)
    private String text;

    /**
     * Chapter ID.
     */
    private Long chapterId;

    /**
     * SMC ID.
     */
    private Long smcId;

    /**
     * ACS ID.
     */
    private Long acsId;

    /**
     * Source.
     */
    private String source;

    /**
     * Course.
     */
    @Column(name = "course")
    private String course;

    /**
     * Last Modified.
     */
    private Date lastModified;

    /**
     * Unit.
     */
    @Column(name = "unit")
    private String unit;

    /**
     * SubUnit.
     */
    @Column(name = "sub_unit")
    private String subUnit;

    /**
     * Explanation.
     */
    @Column(name = "explanation", length = CommonConstants.FIVE_HUNDRED_THOUSAND)
    private String explanation;

    /**
     * LearningStatementCode.
     */
    @Column(name = "learning_statement_code", length = CommonConstants.TEN)
    private String learningStatementCode;

}
