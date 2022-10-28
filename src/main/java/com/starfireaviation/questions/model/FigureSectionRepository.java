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
 * FigureSectionsRepository.
 */
@Repository
public interface FigureSectionRepository extends JpaRepository<FigureSectionEntity, Long> {

    /**
     * Gets a FigureSection by FigureSection ID.
     *
     * @param id FigureSection ID
     * @return FigureSection
     */
    Optional<FigureSectionEntity> findByFigureSectionId(Long id);

    /**
     * Saves a figure section.
     *
     * @param figureSection FigureSection
     * @return FigureSection
     */
    FigureSectionEntity save(FigureSectionEntity figureSection);

    /**
     * Gets all FigureSections.
     *
     * @return list of FigureSections
     */
    List<FigureSectionEntity> findAll();

    /**
     * Deletes a FigureSection.
     *
     * @param figureSection FigureSection
     */
    void delete(FigureSectionEntity figureSection);

    /**
     * Gets a FigureSection.
     *
     * @param id Long
     * @return FigureSection
     */
    FigureSectionEntity findById(long id);

}
