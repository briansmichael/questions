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

package com.starfireaviation.questions.service;

import com.starfireaviation.questions.model.AnswerEntity;
import com.starfireaviation.questions.model.AnswerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AnswerService.
 */
@Slf4j
@Service
public class AnswerService {

    /**
     * AnswerRepository.
     */
    @Autowired
    private AnswerRepository answerRepository;

    /**
     * Gets an answer.
     *
     * @param id Long
     * @return Answer
     */
    public AnswerEntity get(final long id) {
        return answerRepository.findById(id).orElseThrow();
    }

    /**
     * Gets all answers for a question.
     *
     * @param remoteId Long
     * @return Answer
     */
    public List<AnswerEntity> getAnswerForQuestionId(final long remoteId) {
        return answerRepository.findByQuestionId(remoteId).orElseThrow();
    }

}
