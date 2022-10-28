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
 * ImageRepository.
 */
@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    /**
     * Gets an image by Remote ID.
     *
     * @param id remote ID
     * @return Answer
     */
    Optional<ImageEntity> findByRemoteId(Long id);

    /**
     * Saves an image.
     *
     * @param image Image
     * @return Image
     */
    ImageEntity save(ImageEntity image);

    /**
     * Gets all images.
     *
     * @return list of Images
     */
    List<ImageEntity> findAll();

    /**
     * Deletes an image.
     *
     * @param image Image
     */
    void delete(ImageEntity image);

    /**
     * Gets an image.
     *
     * @param id Long
     * @return Image
     */
    ImageEntity findById(long id);

}
