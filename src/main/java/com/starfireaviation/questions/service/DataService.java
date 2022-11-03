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

import com.starfireaviation.model.User;
import com.starfireaviation.questions.config.ApplicationProperties;
import com.starfireaviation.questions.model.ACSRepository;
import com.starfireaviation.questions.model.AnswerRepository;
import com.starfireaviation.questions.model.BinaryDataRepository;
import com.starfireaviation.questions.model.ChapterRepository;
import com.starfireaviation.questions.model.FigureSectionRepository;
import com.starfireaviation.questions.model.GroupRepository;
import com.starfireaviation.questions.model.ImageRepository;
import com.starfireaviation.questions.model.LibraryRepository;
import com.starfireaviation.questions.model.QuestionACSRepository;
import com.starfireaviation.questions.model.QuestionRefImageRepository;
import com.starfireaviation.questions.model.QuestionReferenceRepository;
import com.starfireaviation.questions.model.QuestionRepository;
import com.starfireaviation.questions.model.QuestionTestRepository;
import com.starfireaviation.questions.model.RefRepository;
import com.starfireaviation.questions.model.SourceRepository;
import com.starfireaviation.questions.model.SubjectMatterCodeRepository;
import com.starfireaviation.questions.model.TestRepository;
import com.starfireaviation.questions.model.TextConstRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * DataService.
 */
@Slf4j
@Service
public class DataService {

    /**
     * ApplicationProperties.
     */
    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * QuestionRepository.
     */
    @Autowired
    private QuestionRepository questionRepository;

    /**
     * AnswerRepository.
     */
    @Autowired
    private AnswerRepository answerRepository;

    /**
     * ACSRepository.
     */
    @Autowired
    private ACSRepository acsRepository;

    /**
     * ChaptersRepository.
     */
    @Autowired
    private ChapterRepository chaptersRepository;

    /**
     * FigureSectionsRepository.
     */
    @Autowired
    private FigureSectionRepository figureSectionsRepository;

    /**
     * GroupsRepository.
     */
    @Autowired
    private GroupRepository groupsRepository;

    /**
     * BinaryDataRepository.
     */
    @Autowired
    private BinaryDataRepository binaryDataRepository;

    /**
     * ImageRepository.
     */
    @Autowired
    private ImageRepository imageRepository;

    /**
     * LibrarysRepository.
     */
    @Autowired
    private LibraryRepository librarysRepository;

    /**
     * QuestionACSRepository.
     */
    @Autowired
    private QuestionACSRepository questionACSRepository;

    /**
     * QuestionRefImagesRepository.
     */
    @Autowired
    private QuestionRefImageRepository questionRefImagesRepository;

    /**
     * QuestionReferencesRepository.
     */
    @Autowired
    private QuestionReferenceRepository questionReferencesRepository;

    /**
     * QuestionTestsRepository.
     */
    @Autowired
    private QuestionTestRepository questionTestsRepository;

    /**
     * RefsRepository.
     */
    @Autowired
    private RefRepository refsRepository;

    /**
     * TestsRepository.
     */
    @Autowired
    private TestRepository testsRepository;

    /**
     * SubjectMatterCodesRepository.
     */
    @Autowired
    private SubjectMatterCodeRepository subjectMatterCodesRepository;

    /**
     * SourcesRepository.
     */
    @Autowired
    private SourceRepository sourcesRepository;

    /**
     * TextConstRepository.
     */
    @Autowired
    private TextConstRepository textConstRepository;

    public DataService() {
    }

    /**
     * Get User.
     *
     * @param userName username
     * @return User
     */
    public User getUser(final String userName) {
        return null;
    }

}
