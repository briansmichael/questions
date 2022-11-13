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

package com.starfireaviation.questions.validation;

import com.starfireaviation.common.model.Quiz;
import com.starfireaviation.common.exception.InvalidPayloadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * QuizValidator.
 */
@Slf4j
@Component
public class QuizValidator {

    /**
     * Quiz Validation.
     *
     * @param quiz Quiz
     * @throws InvalidPayloadException when quiz information is invalid
     */
    public void validate(final Quiz quiz) throws InvalidPayloadException {
        empty(quiz);
    }

    /**
     * Ensures quiz object is not null.
     *
     * @param quiz Quiz
     * @throws InvalidPayloadException when quiz is null
     */
    private static void empty(final Quiz quiz) throws InvalidPayloadException {
        if (quiz == null) {
            String msg = "No quiz information was provided";
            log.warn(msg);
            throw new InvalidPayloadException(msg);
        }
    }

}
