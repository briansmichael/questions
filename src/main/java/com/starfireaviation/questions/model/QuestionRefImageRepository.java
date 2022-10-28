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
 * QuestionRefImagesRepository.
 */
@Repository
public interface QuestionRefImageRepository extends JpaRepository<QuestionRefImageEntity, Long> {

    /**
     * Gets a QuestionRefImages by remote ID.
     *
     * @param id remote ID
     * @return QuestionRefImages
     */
    Optional<QuestionRefImageEntity> findByRemoteId(Long id);

    /**
     * Saves a QuestionRefImages.
     *
     * @param questionRefImages QuestionRefImages
     * @return QuestionRefImages
     */
    QuestionRefImageEntity save(QuestionRefImageEntity questionRefImages);

    /**
     * Gets all QuestionRefImages.
     *
     * @return list of QuestionRefImages
     */
    List<QuestionRefImageEntity> findAll();

    /**
     * Deletes a QuestionRefImage.
     *
     * @param questionRefImages QuestionRefImages
     */
    void delete(QuestionRefImageEntity questionRefImages);

    /**
     * Gets a QuestionRefImages.
     *
     * @param id Long
     * @return QuestionRefImages
     */
    QuestionRefImageEntity findById(long id);

}