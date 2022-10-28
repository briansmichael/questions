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

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.starfireaviation.model.CommonConstants;
import com.starfireaviation.model.User;
import com.starfireaviation.questions.config.ApplicationProperties;
import com.starfireaviation.questions.model.ACSEntity;
import com.starfireaviation.questions.model.ACSRepository;
import com.starfireaviation.questions.model.AnswerEntity;
import com.starfireaviation.questions.model.AnswerRepository;
import com.starfireaviation.questions.model.BinaryDataEntity;
import com.starfireaviation.questions.model.BinaryDataRepository;
import com.starfireaviation.questions.model.ChapterEntity;
import com.starfireaviation.questions.model.ChapterRepository;
import com.starfireaviation.questions.model.FigureSectionEntity;
import com.starfireaviation.questions.model.FigureSectionRepository;
import com.starfireaviation.questions.model.GroupEntity;
import com.starfireaviation.questions.model.GroupRepository;
import com.starfireaviation.questions.model.ImageEntity;
import com.starfireaviation.questions.model.ImageRepository;
import com.starfireaviation.questions.model.LibraryEntity;
import com.starfireaviation.questions.model.LibraryRepository;
import com.starfireaviation.questions.model.QuestionEntity;
import com.starfireaviation.questions.model.QuestionACS;
import com.starfireaviation.questions.model.QuestionACSRepository;
import com.starfireaviation.questions.model.QuestionRefImageEntity;
import com.starfireaviation.questions.model.QuestionRefImageRepository;
import com.starfireaviation.questions.model.QuestionReferenceEntity;
import com.starfireaviation.questions.model.QuestionReferenceRepository;
import com.starfireaviation.questions.model.QuestionRepository;
import com.starfireaviation.questions.model.QuestionTestEntity;
import com.starfireaviation.questions.model.QuestionTestRepository;
import com.starfireaviation.questions.model.RefEntity;
import com.starfireaviation.questions.model.RefRepository;
import com.starfireaviation.questions.model.SourceEntity;
import com.starfireaviation.questions.model.SourceRepository;
import com.starfireaviation.questions.model.SubjectMatterCodeEntity;
import com.starfireaviation.questions.model.SubjectMatterCodeRepository;
import com.starfireaviation.questions.model.TestEntity;
import com.starfireaviation.questions.model.TestRepository;
import com.starfireaviation.questions.model.TextConstEntity;
import com.starfireaviation.questions.model.TextConstRepository;
import com.starfireaviation.questions.util.GSDecryptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * DataService.
 */
@Slf4j
@Service
public class DataService {

    /**
     * Answer Choices List.
     */
    public static final String ANSWER_CHOICES = "A,B,C,D,E,F,G,H";

    /**
     * Text Const.
     */
    private static final String TEXT_CONST_QUERY = "SELECT ID, ConstName, ConstValue, GroupID, TestID, LastMod "
            + "FROM TextConst";

    /**
     * Sources.
     */
    private static final String SOURCES_QUERY = "SELECT ID, Author, Title, Abbreviation, LastMod "
            + "FROM Sources";

    /**
     * Tests.
     */
    private static final String TESTS_QUERY = "SELECT TestID, TestName, TestAbbr, GroupID, SortBy, LastMod "
            + "FROM Tests";

    /**
     * Subject Matter Codes.
     */
    private static final String SUBJECT_MATTER_CODES_QUERY = "SELECT ID, Code, SourceID, Description, LastMod, IsLSC "
            + "FROM SubjectMatterCodes";

    /**
     * Refs.
     */
    private static final String REFS_QUERY = "SELECT RefID, RefText, LastMod FROM Refs";

    /**
     * Question Tests.
     */
    private static final String QUESTION_TESTS_QUERY = "SELECT ID, QuestionID, TestID, IsLinked, SortBy, LinkChapter, "
            + "IsImportant FROM QuestionsTests";

    /**
     * Question References.
     */
    private static final String QUESTION_REFERENCES_QUERY = "SELECT ID, QuestionID, RefID FROM QuestionsReferences";

    /**
     * Question Ref Images.
     */
    private static final String QUESTION_REF_IMAGES_QUERY = "SELECT ID, QuestionID, ImageID, Annotation "
            + "FROM QuestionsRefImages";

    /**
     * Question ACS.
     */
    private static final String QUESTION_ACS_QUERY = "SELECT ID, QuestionID, ACSID FROM QuestionsACS";

    /**
     * Library.
     */
    private static final String LIBRARY_QUERY = "SELECT ID, Region, ParentID, Name, Description, "
            + "IsSection, Source, Ordinal, LastMod FROM Library";

    /**
     * Groups.
     */
    private static final String GROUPS_QUERY = "SELECT GroupID, GroupName, GroupAbbr, LastMod FROM Groups";

    /**
     * Figure Sections.
     */
    private static final String FIGURE_SECTIONS_QUERY = "SELECT FigureSectionID, FigureSection, LastMod "
            + "FROM FigureSections";

    /**
     * Chapters.
     */
    private static final String CHAPTERS_QUERY = "SELECT ChapterID, ChapterName, GroupID, SortBy, LastMod "
            + "FROM Chapters";

    /**
     * ACS.
     */
    private static final String ACS_QUERY = "SELECT ID, GroupID, ParentID, Code, Description, "
            + "IsCompletedCode, LastMod FROM ACS";

    /**
     * Binary Data.
     */
    private static final String BINARY_DATA_QUERY = "SELECT ID, Category, GroupID, ImageName, "
            + "Desc, FileName, BinType, BinData, LastMod FROM BinaryData";

    /**
     * Questions.
     */
    private static final String QUESTIONS_QUERY = "SELECT QuestionID, QuestionText, ChapterID, "
            + "SMCID, SourceID, LastMod, Explanation, OldQID, LSCID FROM Questions";

    /**
     * Answers.
     */
    private static final String ANSWERS_QUERY = "SELECT AnswerID, AnswerText, QuestionID, IsCorrect, LastMod "
            + "FROM Answers";

    /**
     * Images.
     */
    private static final String IMAGES_QUERY = "SELECT ID, PicType, GroupID, TestID, ImageName, Desc, FileName, "
            + "BinImage, LastMod, FigureSectionID, PixelsPerNM, SortBy, ImageLibraryID FROM Images";

    /**
     * ApplicationProperties.
     */
    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * GSDecryptor.
     */
    @Autowired
    private GSDecryptor gsDecryptor;

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

    /**
     * Lock map to ensure only 1 course is updated at a time.
     */
    private final IMap<String, Boolean> lockMap;

    public DataService(@Qualifier("lock") final HazelcastInstance hazelcastInstance) {
        lockMap = hazelcastInstance.getMap("lock");
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

    /**
     * Updates courses questions and answers.
     */
    @Async
    public void updateAllCourses() {
//        if (lockMap.containsKey(CommonConstants.ALL)) {
//            return;
//        }
        log.info("Updating all questions for all courses...");
        lockMap.put(CommonConstants.ALL, Boolean.TRUE);
        final String[] courses = CommonConstants.COURSE_LIST.split(",");
        for (final String course : courses) {
            updateCourse(course);
        }
        lockMap.delete(CommonConstants.ALL);
        log.info("Finished updating all questions for all courses.");
    }

    /**
     * Updates questions and answers for a course.
     *
     * @param course course
     */
    @Async
    public void updateCourse(final String course) {
        if (lockMap.containsKey(course) /* || lockMap.containsKey(CommonConstants.ALL) */) {
            log.info("Not updating course: {} due to lock being set.", course);
            return;
        }
        log.info("Updating {}", course);
        lockMap.put(course, Boolean.TRUE);
        getContent(course);
        update(course);
        cleanupContentSource(course);
    }

    /**
     * Gets remote content.
     *
     * @param course course
     */
    private void getContent(final String course) {
        try {
            final String source = String.format(applicationProperties.getContentSourceLocation(),
                    getGIDCode(course), course);
            final String destination = applicationProperties.getDbLocation() + "/" + course + ".db";
            log.info("Copying {} to {}", source, destination);
            FileUtils.copyURLToFile(new URL(source), new File(destination));
            log.info("Course content retrieved for {}", course);
        } catch (IOException e) {
            log.error("Error retrieving course content.  Error message: {}", e.getMessage(), e);
        }
    }

    /**
     * Cleans up downloaded files.
     *
     * @param course course
     */
    private void cleanupContentSource(final String course) {
        try {
            final String db = applicationProperties.getDbLocation() + "/" + course + ".db";
            log.info("Deleting {}", db);
            FileUtils.delete(new File(db));
        } catch (IOException e) {
            log.error("Error cleaning up course content.  Error message: {}", e.getMessage(), e);
        }
        lockMap.delete(course);
        log.info("Finished updating {}", course);
    }

    /**
     * Updates course question (and answers).
     *
     * @param course course
     */
    private void update(final String course) {
        log.info("Updating questions and answers for course: {}", course);
        String jdbcUrl = "jdbc:sqlite:" + applicationProperties.getDbLocation() + "/" + course + ".db";
        Connection conn = null;
        try {
            DriverManager.registerDriver(new org.sqlite.JDBC());
            conn = DriverManager.getConnection(jdbcUrl);
            log.info("Getting ACS table data for course: {}", course);
            getACS(conn);
            log.info("Getting ANSWERS table data for course: {}", course);
            getAnswers(conn);
            log.info("Getting BINARY_DATA table data for course: {}", course);
            getBinaryData(conn);
            log.info("Getting CHAPTERS table data for course: {}", course);
            getChapters(conn);
            log.info("Getting FIGURE_SECTIONS table data for course: {}", course);
            getFigureSections(conn);
            log.info("Getting GROUPS table data for course: {}", course);
            getGroups(conn);
            log.info("Getting IMAGES table data for course: {}", course);
            getImages(conn);
            log.info("Getting LIBRARY table data for course: {}", course);
            getLibrary(conn);
            log.info("Getting QUESTIONS table data for course: {}", course);
            getQuestions(conn, course);
            log.info("Getting QUESTIONS_ACS table data for course: {}", course);
            getQuestionACS(conn);
            log.info("Getting QUESTION_REF_IMAGES table data for course: {}", course);
            getQuestionRefImages(conn);
            log.info("Getting QUESTION_REFERENCES table data for course: {}", course);
            getQuestionReferences(conn);
            log.info("Getting QUESTION_TESTS table data for course: {}", course);
            getQuestionTests(conn);
            log.info("Getting REFS table data for course: {}", course);
            getRefs(conn);
            log.info("Getting SUBJECT_MATTER_CODES table data for course: {}", course);
            getSubjectMatterCodes(conn);
            log.info("Getting SOURCES table data for course: {}", course);
            getSources(conn);
            log.info("Getting TESTS table data for course: {}", course);
            getTests(conn);
            log.info("Getting TEXT_CONST table data for course: {}", course);
            getTextConst(conn);
        } catch (SQLException | InvalidCipherTextException sqle) {
            log.error("Error: " + sqle.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException sqle) {
                    log.warn("Unable to close database connection: " + sqle.getMessage());
                }
            }
        }
        log.info("Completed updating questions and answers for course: {}", course);
    }

    /**
     * Gets TextConst from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getTextConst(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(TEXT_CONST_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final TextConstEntity textConst =
                        textConstRepository.findByRemoteId(remoteId).orElse(new TextConstEntity());
                textConst.setRemoteId(remoteId);
                textConst.setConstName(rs.getString(2));
                textConst.setConstValue(rs.getString(CommonConstants.THREE));
                textConst.setGroupId(rs.getLong(CommonConstants.FOUR));
                textConst.setTestId(rs.getLong(CommonConstants.FIVE));
                textConst.setLastModified(rs.getDate(CommonConstants.SIX));
                textConstRepository.save(textConst);
            }
        }
    }

    /**
     * Gets Sources from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getSources(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(SOURCES_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final SourceEntity source = sourcesRepository.findByRemoteId(remoteId).orElse(new SourceEntity());
                source.setRemoteId(remoteId);
                source.setAuthor(rs.getString(2));
                source.setTitle(rs.getString(CommonConstants.THREE));
                source.setAbbreviation(rs.getString(CommonConstants.FOUR));
                source.setLastModified(rs.getDate(CommonConstants.FIVE));
                sourcesRepository.save(source);
            }
        }
    }

    /**
     * Gets Tests from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getTests(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(TESTS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final TestEntity test = testsRepository.findByTestId(remoteId).orElse(new TestEntity());
                test.setTestId(remoteId);
                test.setTestName(rs.getString(2));
                test.setTestAbbr(rs.getString(CommonConstants.THREE));
                test.setGroupId(rs.getLong(CommonConstants.FOUR));
                test.setSortBy(rs.getLong(CommonConstants.FIVE));
                test.setLastModified(rs.getDate(CommonConstants.SIX));
                testsRepository.save(test);
            }
        }
    }

    /**
     * Gets SubjectMatterCodes from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getSubjectMatterCodes(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(SUBJECT_MATTER_CODES_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final SubjectMatterCodeEntity subjectMatterCode = subjectMatterCodesRepository
                        .findByRemoteId(remoteId).orElse(new SubjectMatterCodeEntity());
                subjectMatterCode.setRemoteId(remoteId);
                subjectMatterCode.setCode(rs.getString(2));
                subjectMatterCode.setSourceId(rs.getLong(CommonConstants.THREE));
                subjectMatterCode.setDescription(rs.getString(CommonConstants.FOUR));
                subjectMatterCode.setLastModified(rs.getDate(CommonConstants.FIVE));
                subjectMatterCode.setIsLSC(rs.getLong(CommonConstants.SIX));
                subjectMatterCodesRepository.save(subjectMatterCode);
            }
        }
    }

    /**
     * Gets Refs from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getRefs(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(REFS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final RefEntity ref = refsRepository.findByRefId(remoteId).orElse(new RefEntity());
                ref.setRefId(remoteId);
                ref.setRefText(rs.getString(2));
                ref.setLastModified(rs.getDate(CommonConstants.THREE));
                refsRepository.save(ref);
            }
        }
    }

    /**
     * Gets QuestionTests from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getQuestionTests(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(QUESTION_TESTS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final QuestionTestEntity questionTest = questionTestsRepository.findByRemoteId(remoteId)
                        .orElse(new QuestionTestEntity());
                questionTest.setRemoteId(remoteId);
                questionTest.setQuestionId(rs.getLong(2));
                questionTest.setTestId(rs.getLong(CommonConstants.THREE));
                questionTest.setIsLinked(rs.getLong(CommonConstants.FOUR));
                questionTest.setSortBy(rs.getLong(CommonConstants.FIVE));
                questionTest.setLinkChapter(rs.getLong(CommonConstants.SIX));
                questionTest.setIsImportant(rs.getLong(CommonConstants.SEVEN));
                questionTestsRepository.save(questionTest);
            }
        }
    }

    /**
     * Gets QuestionReferences from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getQuestionReferences(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(QUESTION_REFERENCES_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final QuestionReferenceEntity questionReference = questionReferencesRepository.findByRemoteId(remoteId)
                        .orElse(new QuestionReferenceEntity());
                questionReference.setRemoteId(remoteId);
                questionReference.setQuestionId(rs.getLong(2));
                questionReference.setRefId(rs.getLong(CommonConstants.THREE));
                questionReferencesRepository.save(questionReference);
            }
        }
    }

    /**
     * Gets QuestionRefImages from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getQuestionRefImages(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(QUESTION_REF_IMAGES_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final QuestionRefImageEntity questionRefImage = questionRefImagesRepository.findByRemoteId(remoteId)
                        .orElse(new QuestionRefImageEntity());
                questionRefImage.setRemoteId(remoteId);
                questionRefImage.setQuestionId(rs.getLong(2));
                questionRefImage.setImageId(rs.getLong(CommonConstants.THREE));
                questionRefImage.setAnnotation(rs.getString(CommonConstants.FOUR));
                questionRefImagesRepository.save(questionRefImage);
            }
        }
    }

    /**
     * Gets QuestionACS from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getQuestionACS(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(QUESTION_ACS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final QuestionACS questionACS = questionACSRepository.findByRemoteId(remoteId)
                        .orElse(new QuestionACS());
                questionACS.setRemoteId(remoteId);
                questionACS.setQuestionId(rs.getLong(2));
                questionACS.setAcsId(rs.getLong(CommonConstants.THREE));
                questionACSRepository.save(questionACS);
            }
        }
    }

    /**
     * Gets library from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getLibrary(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(LIBRARY_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final LibraryEntity library = librarysRepository.findByRemoteId(remoteId).orElse(new LibraryEntity());
                library.setRemoteId(remoteId);
                library.setRegion(rs.getString(2));
                library.setParentId(rs.getLong(CommonConstants.THREE));
                library.setName(rs.getString(CommonConstants.FOUR));
                library.setDescription(rs.getString(CommonConstants.FIVE));
                library.setIsSection(rs.getLong(CommonConstants.SIX));
                library.setSource(rs.getString(CommonConstants.SEVEN));
                library.setOrdinal(rs.getLong(CommonConstants.EIGHT));
                library.setLastModified(rs.getDate(CommonConstants.NINE));
                librarysRepository.save(library);
            }
        }
    }

    /**
     * Gets groups from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getGroups(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(GROUPS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final GroupEntity group = groupsRepository.findByGroupId(remoteId).orElse(new GroupEntity());
                group.setGroupId(remoteId);
                group.setGroupName(rs.getString(2));
                group.setGroupAbbr(rs.getString(CommonConstants.THREE));
                group.setLastModified(rs.getDate(CommonConstants.FOUR));
                groupsRepository.save(group);
            }
        }
    }

    /**
     * Gets figure sections from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getFigureSections(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(FIGURE_SECTIONS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final FigureSectionEntity figureSection = figureSectionsRepository.findByFigureSectionId(remoteId)
                        .orElse(new FigureSectionEntity());
                figureSection.setFigureSectionId(remoteId);
                figureSection.setFigureSection(rs.getString(2));
                figureSection.setLastModified(rs.getDate(CommonConstants.THREE));
                figureSectionsRepository.save(figureSection);
            }
        }
    }

    /**
     * Gets chapters from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getChapters(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(CHAPTERS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final ChapterEntity chapter = chaptersRepository.findByChapterId(remoteId).orElse(new ChapterEntity());
                chapter.setChapterId(remoteId);
                chapter.setChapterName(rs.getString(2));
                chapter.setGroupId(rs.getLong(CommonConstants.THREE));
                chapter.setSortBy(rs.getLong(CommonConstants.FOUR));
                chapter.setLastModified(rs.getDate(CommonConstants.FIVE));
                chaptersRepository.save(chapter);
            }
        }
    }

    /**
     * Gets ACS data from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getACS(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(ACS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final ACSEntity acs = acsRepository.findByRemoteId(remoteId).orElse(new ACSEntity());
                acs.setRemoteId(remoteId);
                acs.setGroupId(rs.getLong(2));
                acs.setParentId(rs.getLong(CommonConstants.THREE));
                acs.setCode(rs.getString(CommonConstants.FOUR));
                acs.setDescription(rs.getString(CommonConstants.FIVE));
                acs.setIsCompletedCode(rs.getLong(CommonConstants.SIX));
                acs.setLastModified(rs.getDate(CommonConstants.SEVEN));
                acsRepository.save(acs);
            }
        }
    }

    /**
     * Gets binary data from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getBinaryData(final Connection conn) throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(BINARY_DATA_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final BinaryDataEntity binaryData =
                        binaryDataRepository.findByRemoteId(remoteId).orElse(new BinaryDataEntity());
                binaryData.setRemoteId(remoteId);
                binaryData.setCategory(rs.getLong(2));
                binaryData.setGroupId(rs.getLong(CommonConstants.THREE));
                binaryData.setImageName(rs.getString(CommonConstants.FOUR));
                binaryData.setDescription(rs.getString(CommonConstants.FIVE));
                binaryData.setFileName(rs.getString(CommonConstants.SIX));
                binaryData.setBinType(rs.getLong(CommonConstants.SEVEN));
                binaryData.setBinData(rs.getBytes(CommonConstants.EIGHT));
                binaryData.setLastModified(rs.getDate(CommonConstants.NINE));
                binaryDataRepository.save(binaryData);
            }
        }
    }

    /**
     * Gets questions from remote database.
     *
     * @param conn Remote database connection
     * @param course Ground School course
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getQuestions(final Connection conn, final String course)
            throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(QUESTIONS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final QuestionEntity question = questionRepository.findByRemoteIdAndCourse(remoteId, course)
                        .orElse(new QuestionEntity());
                question.setCourse(course);
                question.setRemoteId(remoteId);
                question.setText(gsDecryptor.decrypt(rs.getString(2)));
                question.setChapterId(rs.getLong(CommonConstants.THREE));
                question.setSmcId(rs.getLong(CommonConstants.FOUR));
                question.setSource(rs.getString(CommonConstants.FIVE));
                question.setLastModified(rs.getDate(CommonConstants.SIX));
                question.setExplanation(Jsoup.parse(gsDecryptor.decrypt(rs.getString(CommonConstants.SEVEN))).text());
                question.setOldQuestionId(rs.getLong(CommonConstants.EIGHT));
                questionRepository.save(question);
            }
        }
    }

    /**
     * Gets answers from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getAnswers(final Connection conn)
            throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(ANSWERS_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final AnswerEntity answer = answerRepository.findByRemoteId(remoteId).orElse(new AnswerEntity());
                answer.setRemoteId(remoteId);
                answer.setText(Jsoup.parse(gsDecryptor.decrypt(rs.getString(2))).text());
                answer.setQuestionId(rs.getLong(CommonConstants.THREE));
                answer.setCorrect(rs.getBoolean(CommonConstants.FOUR));
                answer.setLastModified(rs.getDate(CommonConstants.FIVE));
                answer.setChoice(deriveChoice(answer.getChoice(), answer.getQuestionId()));
                answerRepository.save(answer);
            }
        }
    }

    /**
     * Gets images from remote database.
     *
     * @param conn Remote database connection
     * @throws SQLException SQLException
     * @throws InvalidCipherTextException InvalidCipherTextException
     */
    private void getImages(final Connection conn)
            throws SQLException, InvalidCipherTextException {
        try (PreparedStatement ps = conn.prepareStatement(IMAGES_QUERY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                final Long remoteId = rs.getLong(1);
                final ImageEntity image = imageRepository.findByRemoteId(remoteId).orElse(new ImageEntity());
                image.setRemoteId(rs.getLong(1));
                image.setPicType(rs.getLong(2));
                image.setGroupId(rs.getLong(CommonConstants.THREE));
                image.setTestId(rs.getLong(CommonConstants.FOUR));
                image.setImageName(rs.getString(CommonConstants.FIVE));
                image.setDescription(rs.getString(CommonConstants.SIX));
                image.setFileName(rs.getString(CommonConstants.SEVEN));
                image.setBinImage(rs.getBytes(CommonConstants.EIGHT));
                image.setLastModified(rs.getDate(CommonConstants.NINE));
                image.setFigureSectionId(rs.getLong(CommonConstants.TEN));
                image.setPixelsPerNM(rs.getDouble(CommonConstants.ELEVEN));
                image.setSortBy(rs.getLong(CommonConstants.TWELVE));
                image.setImageLibraryId(rs.getLong(CommonConstants.THIRTEEN));
                imageRepository.save(image);
            }
        }
    }

    /**
     * Gets the gid code for the given course.
     *
     * @param course course
     * @return gid
     */
    private int getGIDCode(final String course) {
        switch (course) {
            case "PVT":
                return 1;
            case "IFR":
                return 2;
            case "COM":
                return CommonConstants.THREE;
            case "CFI":
                return CommonConstants.FOUR;
            case "ATP":
                return CommonConstants.FIVE;
            case "FLE":
                return CommonConstants.SIX;
            case "AMG":
                return CommonConstants.EIGHT;
            case "AMA":
                return CommonConstants.NINE;
            case "AMP":
                return CommonConstants.TEN;
            case "PAR":
                return CommonConstants.ELEVEN;
            case "SPG":
                return CommonConstants.THIRTEEN;
            case "SPI":
                return CommonConstants.FIFTEEN;
            case "MIL":
                return CommonConstants.SIXTEEN;
            case "IOF":
                return CommonConstants.SEVENTEEN;
            case "MCI":
                return CommonConstants.EIGHTEEN;
            case "RDP":
                return CommonConstants.NINETEEN;
            default:
                return -1;
        }
    }

    /**
     * Derives choice for answer, if not already set.
     *
     * @param choice prior value
     * @param questionId question ID
     * @return derived choice value
     */
    private String deriveChoice(final String choice, final Long questionId) {
        if (choice != null) {
            return choice;
        }
        final ArrayList<String> choices = new ArrayList<>(Arrays.asList(ANSWER_CHOICES.split(",")));
        answerRepository
                .findAllAnswerByQuestionId(questionId)
                .ifPresent(answerEntities -> answerEntities
                        .stream()
                        .filter(answer -> answer.getChoice() != null)
                        .forEach(answer -> choices.remove(answer.getChoice())));
        return choices.get(0);
    }

}
