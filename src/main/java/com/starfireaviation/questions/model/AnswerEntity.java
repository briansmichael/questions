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
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Answer.
 */
@Data
@Entity
@Table(name = "ANSWERS")
public class AnswerEntity {

    /**
     * AnswerId.
     */
    @Id
    private Long answerId;

    /**
     * Answer Text.
     */
    @Column(name = "text", nullable = false, length = CommonConstants.TWO_THOUSAND)
    private String text;

    /**
     * Question ID.
     */
    private Long questionId;

    /**
     * Is Correct.
     */
    @Column(name = "correct", nullable = false)
    private Boolean correct;

    /**
     * Choice.
     */
    @Column(name = "choice", nullable = false, length = CommonConstants.FIVE)
    private String choice;

    /**
     * Last Modified.
     */
    private Date lastModified;

}
