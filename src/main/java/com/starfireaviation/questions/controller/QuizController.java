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

package com.starfireaviation.questions.controller;

import com.starfireaviation.common.exception.AccessDeniedException;
import com.starfireaviation.common.exception.InvalidPayloadException;
import com.starfireaviation.common.exception.ResourceNotFoundException;
import com.starfireaviation.common.model.Quiz;
import com.starfireaviation.questions.service.QuizService;
import com.starfireaviation.questions.validation.QuizValidator;
import com.starfireaviation.questions.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * QuizController.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping({ "/api/quizzes" })
public class QuizController {

        /**
         * QuizService.
         */
        @Autowired
        private QuizService quizService;

        /**
         * QuizValidator.
         */
        @Autowired
        private QuizValidator quizValidator;

        /**
         * UserValidator.
         */
        @Autowired
        private UserValidator userValidator;

        /**
         * Creates a quiz.
         *
         * @param quiz      Quiz
         * @param principal Principal
         * @return Quiz
         * @throws AccessDeniedException     when user doesn't have permission to
         *                                   perform operation
         * @throws InvalidPayloadException   when invalid data is provided
         */
        @PostMapping
        @PutMapping
        public Quiz post(@RequestBody final Quiz quiz,
                         final Principal principal)
                throws InvalidPayloadException,
                AccessDeniedException {
                quizValidator.validate(quiz);
                userValidator.accessAdminOrInstructor(principal);
                return quizService.save(quiz);
        }

        /**
         * Gets a quiz.
         *
         * @param quizId    Long
         * @param principal Principal
         * @return Quiz
         * @throws ResourceNotFoundException when quiz is not found
         * @throws AccessDeniedException     when user doesn't have permission to
         *                                   perform operation
         */
        @GetMapping(path = {"/{quizId}" })
        public Quiz get(@PathVariable("quizId") final long quizId, final Principal principal)
                throws ResourceNotFoundException, AccessDeniedException {
                userValidator.accessAnyAuthenticated(principal);
                return quizService.get(quizId);
        }

}
