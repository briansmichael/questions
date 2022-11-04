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

package com.starfireaviation.questions.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * ChaptersRepository.
 */
@Repository
public interface ChapterRepository extends JpaRepository<ChapterEntity, Long> {

    /**
     * Gets a chapter by Chapter ID.
     *
     * @param id Chapter ID
     * @return Chapter
     */
    Optional<ChapterEntity> findByChapterId(Long id);

    /**
     * Finds the distinct list of chapter names for a list of chapter Ids.
     *
     * @param ids chapter Ids
     * @return distinct list of chapter names
     */
    Optional<List<String>> findDistinctChapterNameByChapterIdIn(List<Long> ids);

    /**
     * Saves a chapter.
     *
     * @param chapter Chapter
     * @return Chapter
     */
    ChapterEntity save(ChapterEntity chapter);

    /**
     * Gets all Chapters.
     *
     * @return list of Chapters
     */
    List<ChapterEntity> findAll();

    /**
     * Deletes a chapter.
     *
     * @param chapter Chapter
     */
    void delete(ChapterEntity chapter);

    /**
     * Gets a chapter.
     *
     * @param id Long
     * @return Chapter
     */
    ChapterEntity findById(long id);

}
