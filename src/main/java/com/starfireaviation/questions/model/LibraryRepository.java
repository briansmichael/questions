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
 * LibrarysRepository.
 */
@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {

    /**
     * Gets a Library by remote ID.
     *
     * @param id remote ID
     * @return Library
     */
    Optional<LibraryEntity> findByRemoteId(Long id);

    /**
     * Saves a Library.
     *
     * @param library Library
     * @return Library
     */
    LibraryEntity save(LibraryEntity library);

    /**
     * Gets all Librarys.
     *
     * @return list of Librarys
     */
    List<LibraryEntity> findAll();

    /**
     * Deletes a Library.
     *
     * @param library Library
     */
    void delete(LibraryEntity library);

    /**
     * Gets a Library.
     *
     * @param id Long
     * @return Library
     */
    LibraryEntity findById(long id);

}
