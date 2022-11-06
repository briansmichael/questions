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
import com.starfireaviation.model.QuizType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

/**
 * Quiz.
 */
@Data
@Entity
@Table(name = "QUIZ")
public class QuizEntity {

    /**
     * Default SerialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Id.
     */
    @Id
    private Long id;

    /**
     * Title.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * QuestionIDs.
     */
    @ElementCollection
    private List<Long> questionIds;

    /**
     * LessonPlan ID.
     */
    @Column(name = "lesson_plan_id", nullable = false)
    private Long lessonPlanId;

    /**
     * QuizType.
     */
    @Column(name = "type", length = CommonConstants.TWENTY, nullable = false)
    @Enumerated(EnumType.STRING)
    private QuizType quizType;

}
