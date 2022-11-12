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

import com.starfireaviation.questions.model.ACSEntity;
import com.starfireaviation.questions.model.ACSRepository;
import com.starfireaviation.questions.model.ChapterEntity;
import com.starfireaviation.questions.model.ChapterRepository;
import com.starfireaviation.questions.model.GroupEntity;
import com.starfireaviation.questions.model.GroupRepository;
import com.starfireaviation.questions.model.QuestionACS;
import com.starfireaviation.questions.model.QuestionACSRepository;
import com.starfireaviation.questions.model.QuestionEntity;
import com.starfireaviation.questions.model.QuestionRepository;
import com.starfireaviation.questions.model.SubjectMatterCodeEntity;
import com.starfireaviation.questions.model.SubjectMatterCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Question Service.
 */
@Slf4j
@Service
public class QuestionService {

    /**
     * QuestionRepository.
     */
    @Autowired
    private QuestionRepository questionRepository;

    /**
     * QuestionACSRepository.
     */
    @Autowired
    private QuestionACSRepository questionACSRepository;

    /**
     * ACSRepository.
     */
    @Autowired
    private ACSRepository acsRepository;

    /**
     * ChapterRepository.
     */
    @Autowired
    private ChapterRepository chapterRepository;

    /**
     * SubjectMatterCodeRepository.
     */
    @Autowired
    private SubjectMatterCodeRepository smcRepository;

    /**
     * GroupRepository.
     */
    @Autowired
    private GroupRepository groupRepository;

    /**
     * Gets a question by ID.
     *
     * @param id question ID
     * @return Question for the provided ID
     */
    public QuestionEntity get(final long id) {
        return questionRepository.findById(id).orElseThrow();
    }

    /**
     * Gets the list of chapter names for a course.
     *
     * @param course course
     * @return list of chapter names
     */
    public List<String> getChapterNamesForCourse(final String course) {
        final List<String> chapterNames = new ArrayList<>();
        final Optional<List<GroupEntity>> groupEntityOpt = groupRepository.findByGroupAbbr(course);
        groupEntityOpt.ifPresent(groupEntities -> groupEntities.stream().distinct().forEach(groupEntity -> {
            final Optional<List<ChapterEntity>> chaptersOpt = chapterRepository.findByGroupId(groupEntity.getGroupId());
            chaptersOpt.ifPresent(chapterEntities -> chapterNames.addAll(
                    chapterEntities.stream()
                            .distinct()
                            .map(ChapterEntity::getChapterName)
                            .collect(Collectors.toList())));
        }));
        return chapterNames.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Gets the distinct list of ACS codes for a course.
     *
     * @param course course
     * @return distinct list of ACS codes
     */
    public List<String> getAcsCodesForCourse(final String course) {
        final List<String> acsCodes = new ArrayList<>();
        final Optional<List<GroupEntity>> groupEntityOpt = groupRepository.findByGroupAbbr(course);
        groupEntityOpt.ifPresent(groupEntities -> groupEntities.stream().distinct().forEach(groupEntity -> {
            final Optional<List<ACSEntity>> acsEntityOpt = acsRepository.findByGroupId(groupEntity.getGroupId());
            acsEntityOpt.ifPresent(acsEntities -> acsCodes.addAll(
                    acsEntities.stream().distinct().map(ACSEntity::getCode).collect(Collectors.toList())));
        }));
        return acsCodes.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Gets ACS Code for a question ID.
     *
     * @param questionId question ID
     * @return ACS code
     */
    public String getACSCodeForQuestionId(final Long questionId) {
        // TODO
        return null;
    }

    /**
     * Gets a list of question IDs for provided search criteria.
     *
     * @param groupAbbr optional group abbreviation
     * @param acsCode optional ACS code
     * @param learningStatementCode optional learning statement code
     * @return list of question IDs
     */
    public List<Long> getQuestions(final String groupAbbr,
                                   final String acsCode,
                                   final String learningStatementCode) {
        final List<Long> questionIds = new ArrayList<>();
        if (groupAbbr != null) {
            groupRepository.findByGroupAbbr(groupAbbr)
                .ifPresent(groups -> groups.forEach(group -> chapterRepository.findByGroupId(group.getGroupId())
                    .ifPresent(chapters -> chapters.forEach(chapter -> 
                        questionRepository.findByChapterId(chapter.getChapterId())
                            .ifPresent(questions -> questions.forEach(question -> 
                                questionIds.add(question.getQuestionId())))))));
        }
        if (acsCode != null) {
            final List<Long> list = new ArrayList<>();
            final Optional<List<ACSEntity>> acsListOpt = acsRepository.findByCode(acsCode);
            if (acsListOpt.isPresent()) {
                final List<ACSEntity> acsList = acsListOpt.get();
                log.info("acsList.size() = {}", acsList.size());
                for (final ACSEntity acs : acsList) {
                    final Optional<List<QuestionACS>> questionACSListOpt = 
                        questionACSRepository.findByAcsId(acs.getId());
                    if (questionACSListOpt.isPresent()) {
                        final List<QuestionACS> questionACSList = questionACSListOpt.get();
                        log.info("questionAcsList.size() = {}", questionACSList.size());
                        for (final QuestionACS questionACS : questionACSList) {
                            list.add(questionACS.getQuestionId());
                        }
                    }
                }
            }
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
        if (learningStatementCode != null) {
            final List<Long> list = new ArrayList<>();
            smcRepository.findByCode(learningStatementCode).ifPresent(subjectMatterCodeEntities ->
                    subjectMatterCodeEntities.forEach(smc -> questionRepository.findByLscId(smc.getId())
                            .ifPresent(questionEntities -> questionEntities.forEach(question ->
                                    list.add(question.getQuestionId())))));
            if (CollectionUtils.isEmpty(questionIds)) {
                questionIds.addAll(list);
            } else {
                questionIds.retainAll(list);
            }
        }
        return questionIds.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Gets LearningStatementCode.
     *
     * @param lscId learning statement code ID
     * @return code
     */
    public String getLearningStatementCode(final Long lscId) {
        final Optional<SubjectMatterCodeEntity> smcOpt = smcRepository.findById(lscId);
        return smcOpt.map(SubjectMatterCodeEntity::getCode).orElse(null);
    }
}
