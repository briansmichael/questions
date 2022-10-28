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

import com.starfireaviation.model.Question;
import com.starfireaviation.questions.exception.InvalidPayloadException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * QuestionValidator.
 */
@Slf4j
@Component
public class QuestionValidator extends UserValidator {

    /**
     * Question Validation.
     *
     * @param question Question
     * @throws InvalidPayloadException when question information is invalid
     */
    public void validate(final Question question) throws InvalidPayloadException {
        empty(question);
    }

    /**
     * Ensures question object is not null.
     *
     * @param question Question
     * @throws InvalidPayloadException when lesson plan is null
     */
    private static void empty(final Question question) throws InvalidPayloadException {
        if (question == null) {
            String msg = "No question information was provided";
            log.warn(msg);
            throw new InvalidPayloadException(msg);
        }
    }

}
