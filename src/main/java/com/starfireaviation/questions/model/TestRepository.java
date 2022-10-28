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
 * TestsRepository.
 */
@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {

    /**
     * Gets a Test by Test ID.
     *
     * @param id test ID
     * @return Test
     */
    Optional<TestEntity> findByTestId(Long id);

    /**
     * Saves a Test.
     *
     * @param test Test
     * @return Test
     */
    TestEntity save(TestEntity test);

    /**
     * Gets all Test.
     *
     * @return list of Test
     */
    List<TestEntity> findAll();

    /**
     * Deletes a Test.
     *
     * @param test Test
     */
    void delete(TestEntity test);

    /**
     * Gets a Test.
     *
     * @param id Long
     * @return Test
     */
    TestEntity findById(long id);

}
