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

import com.starfireaviation.common.model.Answer;
import com.starfireaviation.questions.service.AnswerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    /**
     * AnswerService.
     */
    @Autowired
    private AnswerService answerService;

    /**
     * Gets an Answer by ID.
     *
     * @param id Answer ID
     * @return Answer
     */
    @GetMapping(path = "/{id}")
    public Answer getAnswer(@PathVariable("id") final Long id) {
        return answerService.get(id);
    }

    /**
     * Updates all answers to have an answer choice value.
     */
    @PostMapping(path = "/updatechoices")
    public void updateChoices() {
        answerService
                .getAll()
                .stream()
                .filter(answer -> answer.getChoice() == null)
                .forEach(answer -> {
            answer.setChoice(answerService.deriveChoice(answer.getChoice(), answer.getQuestionId()));
            answerService.save(answer);
        });
    }

    /**
     * Saves an Answer.
     *
     * @param answer Answer
     * @return Answer
     */
    @PostMapping
    @PutMapping
    public Answer save(final Answer answer) {
        return answerService.save(answer);
    }

}
