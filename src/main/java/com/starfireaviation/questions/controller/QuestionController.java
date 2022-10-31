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

import com.starfireaviation.model.Answer;
import com.starfireaviation.model.Image;
import com.starfireaviation.model.Question;
import com.starfireaviation.questions.model.AnswerEntity;
import com.starfireaviation.questions.model.ImageEntity;
import com.starfireaviation.questions.model.QuestionEntity;
import com.starfireaviation.questions.service.AnswerService;
import com.starfireaviation.questions.service.DataService;
import com.starfireaviation.questions.service.ImageService;
import com.starfireaviation.questions.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/questions")
public class QuestionController {

    /**
     * DataService.
     */
    @Autowired
    private DataService dataService;

    /**
     * QuestionService.
     */
    @Autowired
    private QuestionService questionService;

    /**
     * AnswerService.
     */
    @Autowired
    private AnswerService answerService;

    /**
     * ImageService.
     */
    @Autowired
    private ImageService imageService;

    /**
     * Updates all questions for all courses.
     */
    @PostMapping(path = "/update")
    public void updateAllCourses() {
        dataService.updateAllCourses();
    }

    /**
     * Updates questions for a course.
     *
     * @param course course
     */
    @PostMapping(path = "/update/{course}")
    public void updateCourse(@PathVariable("course") final String course) {
        dataService.updateCourse(course);
    }

    /**
     * Gets list of question IDs matching search criteria.
     *
     * @param course optional course
     * @param acsId optional ACS ID
     * @param learningStatementCode optional learning statement code
     * @param unit optional unit
     * @param subUnit optional sub unit
     * @return list of question ids
     */
    @GetMapping
    public List<Long> getQuestions(@RequestParam("course") final String course,
                                   @RequestParam("acs") final Long acsId,
                                   @RequestParam("lsc") final String learningStatementCode,
                                   @RequestParam("unit") final String unit,
                                   @RequestParam("subunit") final String subUnit) {
        return questionService.getQuestions(course, acsId, learningStatementCode, unit, subUnit);
    }

    /**
     * Gets a question by ID.
     *
     * @param id question ID
     * @return Question
     */
    @GetMapping(path = "/{id}")
    public Question getQuestion(@PathVariable("id") final Long id) {
        final Question question = map(questionService.get(id));
        question.setAnswers(getQuestionAnswers(id));
        question.setImages(getQuestionImages(id));
        return question;
    }

    /**
     * Gets answers by Question ID.
     *
     * @param id question ID
     * @return Answer list
     */
    @GetMapping(path = "/{id}/answers")
    public List<Answer> getQuestionAnswers(@PathVariable("id") final Long id) {
        return answerService
                .getAnswerForQuestionId(id)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    /**
     * Gets images by Question ID.
     *
     * @param id question ID
     * @return Image list
     */
    @GetMapping(path = "/{id}/images")
    public List<Image> getQuestionImages(@PathVariable("id") final Long id) {
        return imageService
                .getImageForQuestionId(id)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    /**
     * Map AnswerEntity to Answer.
     *
     * @param answerEntity AnswerEntity
     * @return Answer
     */
    private Answer map(final AnswerEntity answerEntity) {
        final Answer answer = new Answer();
        answer.setId(answerEntity.getId());
        answer.setLastModified(answerEntity.getLastModified());
        answer.setRemoteId(answerEntity.getRemoteId());
        answer.setQuestionId(answerEntity.getQuestionId());
        answer.setChoice(answerEntity.getChoice());
        answer.setCorrect(answerEntity.getCorrect());
        answer.setText(answerEntity.getText());
        answer.setDiscussion(answerEntity.getDiscussion());
        return answer;
    }

    /**
     * Map ImageEntity to Image.
     *
     * @param imageEntity ImageEntity
     * @return Image
     */
    private Image map(final ImageEntity imageEntity) {
        final Image image = new Image();
        image.setId(imageEntity.getId());
        image.setRemoteId(imageEntity.getRemoteId());
        image.setBinImage(imageEntity.getBinImage());
        image.setImageName(imageEntity.getImageName());
        image.setLastModified(imageEntity.getLastModified());
        image.setDescription(imageEntity.getDescription());
        image.setFigureSectionId(imageEntity.getFigureSectionId());
        image.setFileName(imageEntity.getFileName());
        image.setGroupId(imageEntity.getGroupId());
        image.setImageLibraryId(imageEntity.getImageLibraryId());
        image.setPicType(imageEntity.getPicType());
        image.setPixelsPerNM(imageEntity.getPixelsPerNM());
        image.setSortBy(imageEntity.getSortBy());
        image.setTestId(imageEntity.getTestId());
        return image;
    }

    /**
     * Maps QuestionEntity to Question.
     *
     * @param questionEntity QuestionEntity
     * @return Question
     */
    private Question map(final QuestionEntity questionEntity) {
        final Question question = new Question();
        question.setId(questionEntity.getId());
        question.setLastModified(questionEntity.getLastModified());
        question.setOldQuestionId(questionEntity.getOldQuestionId());
        question.setRemoteId(questionEntity.getRemoteId());
        question.setCourse(questionEntity.getCourse());
        question.setAcsId(questionEntity.getAcsId());
        question.setChapterId(questionEntity.getChapterId());
        question.setExplanation(questionEntity.getExplanation());
        question.setLearningStatementCode(questionEntity.getLearningStatementCode());
        question.setSmcId(questionEntity.getSmcId());
        question.setSource(questionEntity.getSource());
        question.setSubUnit(questionEntity.getSubUnit());
        question.setText(questionEntity.getText());
        question.setUnit(questionEntity.getUnit());
        return question;
    }

}
