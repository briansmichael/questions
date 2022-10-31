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

import com.starfireaviation.questions.exception.ResourceNotFoundException;
import com.starfireaviation.questions.model.ImageEntity;
import com.starfireaviation.questions.model.ImageRepository;
import com.starfireaviation.questions.model.QuestionRefImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ImageService.
 */
@Service
public class ImageService {

    /**
     * ImageRepository.
     */
    @Autowired
    private ImageRepository imageRepository;

    /**
     * QuestionRefImageRepository.
     */
    @Autowired
    private QuestionRefImageRepository questionRefImageRepository;

    /**
     * Deletes an Image.
     *
     * @param id Long
     * @throws ResourceNotFoundException when no image is found
     */
    public void delete(final long id) throws ResourceNotFoundException {
        imageRepository.delete(get(id));
    }

    /**
     * Gets an image.
     *
     * @param id Long
     * @return Answer
     */
    public ImageEntity get(final long id) {
        return imageRepository.findById(id).orElseThrow();
    }

    /**
     * Gets all images for a question.
     *
     * @param id Long
     * @return Image
     */
    public List<ImageEntity> getImageForQuestionId(final long id) {
        return questionRefImageRepository
                .findByQuestionId(id)
                .orElseThrow()
                .stream()
                .map(questionRefImageEntity -> imageRepository
                        .findById(questionRefImageEntity.getImageId()).orElseThrow())
                .collect(Collectors.toList());
    }
}
