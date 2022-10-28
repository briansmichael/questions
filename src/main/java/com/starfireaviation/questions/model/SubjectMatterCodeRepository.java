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
 * SubjectMatterCodesRepository.
 */
@Repository
public interface SubjectMatterCodeRepository extends JpaRepository<SubjectMatterCodeEntity, Long> {

    /**
     * Gets a SubjectMatterCode by Remote ID.
     *
     * @param id remote ID
     * @return SubjectMatterCode
     */
    Optional<SubjectMatterCodeEntity> findByRemoteId(Long id);

    /**
     * Saves a SubjectMatterCode.
     *
     * @param subjectMatterCode SubjectMatterCode
     * @return SubjectMatterCode
     */
    SubjectMatterCodeEntity save(SubjectMatterCodeEntity subjectMatterCode);

    /**
     * Gets all SubjectMatterCode.
     *
     * @return list of SubjectMatterCode
     */
    List<SubjectMatterCodeEntity> findAll();

    /**
     * Deletes a SubjectMatterCode.
     *
     * @param subjectMatterCode SubjectMatterCode
     */
    void delete(SubjectMatterCodeEntity subjectMatterCode);

    /**
     * Gets a SubjectMatterCode.
     *
     * @param id Long
     * @return SubjectMatterCode
     */
    SubjectMatterCodeEntity findById(long id);

}