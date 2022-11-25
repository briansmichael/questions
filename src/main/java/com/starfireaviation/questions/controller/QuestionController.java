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

import com.starfireaviation.common.model.ACS;
import com.starfireaviation.common.model.Answer;
import com.starfireaviation.common.model.Chapter;
import com.starfireaviation.common.model.Question;
import com.starfireaviation.common.model.Image;
import com.starfireaviation.questions.service.ACSService;
import com.starfireaviation.questions.service.AnswerService;
import com.starfireaviation.questions.service.ChapterService;
import com.starfireaviation.questions.service.GroupService;
import com.starfireaviation.questions.service.ImageService;
import com.starfireaviation.questions.service.QuestionACSService;
import com.starfireaviation.questions.service.QuestionRefImageService;
import com.starfireaviation.questions.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
     * ChapterService.
     */
    @Autowired
    private ChapterService chapterService;

    /**
     * GroupService.
     */
    @Autowired
    private GroupService groupService;

    /**
     * ACSService.
     */
    @Autowired
    private ACSService acsService;

    /**
     * QuestionACSService.
     */
    @Autowired
    private QuestionACSService questionACSService;

    /**
     * QuestionRefImageService.
     */
    @Autowired
    private QuestionRefImageService questionRefImageService;

    /**
     * Saves a Question.
     *
     * @param question Question
     * @return Question
     */
    @PostMapping
    @PutMapping
    public Question save(final Question question) {
        return questionService.save(question);
    }

    /**
     * Gets list of question IDs matching search criteria.
     *
     * @param groupAbbr optional group abbreviation
     * @param chapter optional chapter
     * @param acsCode optional ACS code
     * @param learningStatementCode optional learning statement code
     * @return list of question ids
     */
    @GetMapping
    public List<Long> getQuestions(@RequestParam(value = "groupAbbr", required = false) final String groupAbbr,
                                   @RequestParam(value = "acs", required = false) final String acsCode,
                                   @RequestParam(value = "chapter", required = false) final Long chapter,
                                   @RequestParam(value = "lsc", required = false) final String learningStatementCode) {
        final List<Long> questionIds = new ArrayList<>();
        getQuestionIdsForGroup(groupAbbr, questionIds);
        getQuestionIdsForChapter(chapter, questionIds);
        getQuestionIdsForACSCode(acsCode, questionIds);
        getQuestionIdsForLSC(learningStatementCode, questionIds);
        return questionIds.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Gets the list of chapter names for a course.
     *
     * @param groupAbbr Group abbreviation
     * @return list of chapter names
     */
    @GetMapping(path = "/{groupAbbr}/chapters")
    public List<String> getChaptersForCourse(@PathVariable("groupAbbr") final String groupAbbr) {
        final List<String> chapterNames = new ArrayList<>();
        groupService.findByGroupAbbr(groupAbbr).forEach(group ->
                chapterNames.addAll(chapterService
                        .findByGroupId(group.getGroupId())
                        .stream()
                        .map(Chapter::getChapterName)
                        .collect(Collectors.toList())));
        return chapterNames.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Gets the list of ACS codes for a course.
     *
     * @param groupAbbr Group Abbreviation
     * @return list of ACS codes
     */
    @GetMapping(path = "/{groupAbbr}/acs")
    public List<String> getACSCodesForCourse(@PathVariable("groupAbbr") final String groupAbbr) {
        final List<String> acsCodes = new ArrayList<>();
        groupService.findByGroupAbbr(groupAbbr)
                .forEach(group -> {
                    acsCodes.addAll(acsService
                            .findByGroupId(group.getGroupId())
                            .stream()
                            .map(ACS::getCode)
                            .collect(Collectors.toList()));
                });
        return acsCodes.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Gets a question by ID.
     *
     * @param id question ID
     * @return Question
     */
    @GetMapping(path = "/{questionId}")
    public Question getQuestion(@PathVariable("questionId") final Long id) {
        return questionService.get(id);
    }

    /**
     * Gets answers by Question ID.
     *
     * @param id question ID
     * @return Answer list
     */
    @GetMapping(path = "/{questionId}/answers")
    public List<Answer> getQuestionAnswers(@PathVariable("questionId") final Long id) {
        return answerService.findByQuestionId(id);
    }

    /**
     * Gets images by Question ID.
     *
     * @param questionId question ID
     * @return Image list
     */
    @GetMapping(path = "/{questionId}/images")
    public List<Image> getQuestionImages(@PathVariable("questionId") final Long questionId) {
        final List<Image> images = new ArrayList<>();
        questionRefImageService
                .findByQuestionId(questionId)
                .forEach(questionRefImage -> {
                    images.add(imageService.get(questionRefImage.getImageId()));
                });
        return images;
    }

    /**
     * Gets ACS Code for a question ID.
     *
     * @param questionId question ID
     * @return ACS code
     */
    @GetMapping(path = "/{questionId}/acs")
    public List<String> getACSCodesForQuestionId(@PathVariable("questionId") final Long questionId) {
        final List<String> acsCodes = new ArrayList<>();
        questionACSService
                .findByQuestionId(questionId)
                .forEach(questionACS -> acsCodes
                        .add(acsService.get(questionACS.getAcsId()).getCode()));
        return acsCodes.stream().distinct().sorted().collect(Collectors.toList());
    }

    /**
     * Updates list of questionIds for the given learning statement code.
     *
     * @param learningStatementCode learning statement code
     * @param questionIds question ID list
     */
    private void getQuestionIdsForLSC(final String learningStatementCode, final List<Long> questionIds) {
        if (learningStatementCode != null) {
            final List<Long> list = questionService
                    .findByLearningStatementCode(learningStatementCode)
                    .stream()
                    .map(Question::getId).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
    }

    /**
     * Updates list of questionIds for the given ACS code.
     *
     * @param acsCode ACS code
     * @param questionIds question ID list
     */
    private void getQuestionIdsForACSCode(final String acsCode, final List<Long> questionIds) {
        if (acsCode != null) {
            final List<Long> list = new ArrayList<>();
            acsService
                    .findByCode(acsCode)
                    .forEach(acs -> questionACSService
                            .findByAcsId(acs.getId())
                            .forEach(questionACS -> list.add(questionACS.getQuestionId())));
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
    }

    /**
     * Updates list of questionIds for the given group.
     *
     * @param groupAbbr group abbreviation
     * @param questionIds question ID list
     */
    private void getQuestionIdsForGroup(final String groupAbbr, final List<Long> questionIds) {
        if (groupAbbr != null) {
            groupService
                    .findByGroupAbbr(groupAbbr)
                    .forEach(group -> chapterService
                            .findByGroupId(group.getGroupId())
                            .forEach(chapter -> questionService
                                    .findByChapterId(chapter.getChapterId())
                                    .forEach(question -> questionIds.add(question.getId()))));
        }
    }

    /**
     * Updates list of questionIds for the given chapter.
     *
     * @param chapter chapter
     * @param questionIds question ID list
     */
    private void getQuestionIdsForChapter(final Long chapter, final List<Long> questionIds) {
        if (chapter != null) {
            final List<Long> list = new ArrayList<>();
            questionService
                    .findByChapterId(chapter)
                    .forEach(question -> list.add(question.getId()));
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
    }

}
