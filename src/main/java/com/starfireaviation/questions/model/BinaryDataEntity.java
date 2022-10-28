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

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

/**
 * ACS.
 */
@Data
@Entity
@Table(name = "ACS")
public class BinaryDataEntity extends BaseEntity {

    /**
     * Remote ID.
     */
    private Long remoteId;

    /**
     * Category.
     */
    private Long category;

    /**
     * Group ID.
     */
    private Long groupId;

    /**
     * Image name.
     */
    private String imageName;

    /**
     * Description.
     */
    private String description;

    /**
     * Filename.
     */
    private String fileName;

    /**
     * Bin Type.
     */
    private Long binType;

    /**
     * Binary Data.
     */
    @Lob
    @Column(name = "bin_data", columnDefinition = "LONGBLOB")
    private byte[] binData;

    /**
     * Last Modified.
     */
    private Date lastModified;

}
