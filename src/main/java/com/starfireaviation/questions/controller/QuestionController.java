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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.starfireaviation.common.model.Answer;
import com.starfireaviation.common.model.Image;
import com.starfireaviation.common.model.Question;
import com.starfireaviation.questions.config.ApplicationProperties;
import com.starfireaviation.questions.model.AnswerEntity;
import com.starfireaviation.questions.model.ImageEntity;
import com.starfireaviation.questions.model.QuestionEntity;
import com.starfireaviation.questions.service.AnswerService;
import com.starfireaviation.questions.service.ImageService;
import com.starfireaviation.questions.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

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
     * ApplicationProperties.
     */
    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * Questions Cache.
     */
    private final IMap<Long, Question> cache;

    /**
     * Question IDs Cache.
     */
    private final IMap<String, List<Long>> idsCache;

    /**
     * Constructor.
     *
     * @param hazelcastInstance HazelcastInstance
     */
    public QuestionController(@Qualifier("questions") final HazelcastInstance hazelcastInstance) {
        cache = hazelcastInstance.getMap("questions");
        idsCache = hazelcastInstance.getMap("questionIds");
    }

    /**
     * Gets list of question IDs matching search criteria.
     *
     * @param group optional group abbreviation
     * @param chapter optional chapter
     * @param acsCode optional ACS code
     * @param learningStatementCode optional learning statement code
     * @return list of question ids
     */
    @GetMapping
    public List<Long> getQuestions(@RequestParam(value = "group", required = false) final String group,
                                   @RequestParam(value = "acs", required = false) final String acsCode,
                                   @RequestParam(value = "chapter", required = false) final Long chapter,
                                   @RequestParam(value = "lsc", required = false) final String learningStatementCode) {
        final String key = formKey(group, acsCode, chapter, learningStatementCode);
        if (idsCache.containsKey(key)) {
            return idsCache.get(key);
        }
        final List<Long> questionIds = questionService.getQuestions(group, chapter, acsCode, learningStatementCode);
        idsCache.put(key, questionIds);
        return questionIds;
    }

    /**
     * Gets the list of chapter names for a course.
     *
     * @param course course
     * @return list of chapter names
     */
    @GetMapping(path = "/{course}/chapters")
    public List<String> getChaptersForCourse(@PathVariable("course") final String course) {
        return questionService.getChapterNamesForCourse(course);
    }

    /**
     * Gets the list of ACS codes for a course.
     *
     * @param course course
     * @return list of ACS codes
     */
    @GetMapping(path = "/{course}/acs")
    public List<String> getACSCodesForCourse(@PathVariable("course") final String course) {
        return questionService.getAcsCodesForCourse(course);
    }

    /**
     * Gets a question by ID.
     *
     * @param id question ID
     * @return Question
     */
    @GetMapping(path = "/{id}")
    public Question getQuestion(@PathVariable("id") final Long id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        final Question question = map(questionService.get(id));
        cache.put(id, question);
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
        if (cache.containsKey(id)) {
            return cache.get(id).getAnswers();
        }
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
        if (cache.containsKey(id)) {
            return cache.get(id).getImages();
        }
        return imageService
                .getImageForQuestionId(id)
                .stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    /**
     * Forms a key from the provided values.
     *
     * @param group group
     * @param acs ACS code
     * @param chapter chapter
     * @param lsc learning statement code
     * @return key
     */
    private String formKey(final String group, final String acs, final Long chapter, final String lsc) {
        return String.format("group=%s;acs=%s;chapter=%s;lsc=%s", group, acs, chapter, lsc);
    }

    /**
     * Map AnswerEntity to Answer.
     *
     * @param answerEntity AnswerEntity
     * @return Answer
     */
    private Answer map(final AnswerEntity answerEntity) {
        final Answer answer = new Answer();
        answer.setId(answerEntity.getAnswerId());
        answer.setLastModified(answerEntity.getLastModified());
        answer.setQuestionId(answerEntity.getQuestionId());
        answer.setChoice(answerEntity.getChoice());
        answer.setCorrect(answerEntity.getCorrect());
        answer.setText(answerEntity.getText());
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
        image.setUrl(applicationProperties.getMediaUrlBase() + image.getFileName());
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
        question.setId(questionEntity.getQuestionId());
        question.setLastModified(questionEntity.getLastModified());
        question.setOldQuestionId(questionEntity.getOldQuestionId());
        question.setChapterId(questionEntity.getChapterId());
        question.setExplanation(questionEntity.getExplanation());
        question.setSmcId(questionEntity.getSmcId());
        question.setSource(questionEntity.getSource());
        question.setText(questionEntity.getText());
        question.setAcsCodes(questionService.getACSCodesForQuestionId(question.getId()));
        question.setAnswers(getQuestionAnswers(question.getId()));
        question.setImages(getQuestionImages(question.getId()));
        question.setLearningStatementCode(questionService.getLearningStatementCode(questionEntity.getLscId()));
        return question;
    }

}
