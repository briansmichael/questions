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

import com.starfireaviation.questions.model.QuizEntity;
import com.starfireaviation.questions.model.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QuizService.
 */
@Slf4j
@Service
public class QuizService {

    /**
     * QuizRepository.
     */
    @Autowired
    private QuizRepository quizRepository;

    /**
     * Creates a quiz.
     *
     * @param quiz Quiz
     * @return Quiz
     */
    public QuizEntity store(final QuizEntity quiz) {
        if (quiz == null) {
            return null;
        }
        return quizRepository.save(quiz);
    }

    /**
     * Deletes a quiz.
     *
     * @param id Long
     */
    public void delete(final long id) {
        quizRepository.delete(get(id));
    }

    /**
     * Gets all quizzes.
     *
     * @return list of Quiz
     */
    public List<QuizEntity> getAll() {
        return quizRepository.findAll();
    }

    /**
     * Gets a quiz.
     *
     * @param id Long
     * @return Quiz
     */
    public QuizEntity get(final long id) {
        return quizRepository.findById(id).orElseThrow();
    }

}
