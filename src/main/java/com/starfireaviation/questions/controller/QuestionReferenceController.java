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

import com.starfireaviation.common.model.QuestionReference;
import com.starfireaviation.questions.service.QuestionReferenceService;
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
@RequestMapping("/api/questionreference")
public class QuestionReferenceController {

    /**
     * QuestionReferenceService.
     */
    @Autowired
    private QuestionReferenceService questionReferenceService;

    /**
     * Gets a QuestionReference by ID.
     *
     * @param id QuestionReference ID
     * @return QuestionReference
     */
    @GetMapping(path = "/{id}")
    public QuestionReference getQuestionReference(@PathVariable("id") final Long id) {
        return questionReferenceService.get(id);
    }

    /**
     * Saves a QuestionReference.
     *
     * @param questionReference QuestionReference
     * @return QuestionReference
     */
    @PostMapping
    @PutMapping
    public QuestionReference save(final QuestionReference questionReference) {
        return questionReferenceService.save(questionReference);
    }

}
