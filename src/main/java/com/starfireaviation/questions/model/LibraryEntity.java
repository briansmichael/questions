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

import com.starfireaviation.model.CommonConstants;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Library.
 */
@Data
@Entity
@Table(name = "LIBRARIES")
public class LibraryEntity extends BaseEntity {

    /**
     * Remote ID.
     */
    private Long remoteId;

    /**
     * Region.
     */
    private String region;

    /**
     * ParentID.
     */
    private Long parentId;

    /**
     * Name.
     */
    private String name;

    /**
     * Description.
     */
    @Column(name = "description", length = CommonConstants.FOUR_THOUSAND)
    private String description;

    /**
     * IsSection.
     */
    private Long isSection;

    /**
     * Source.
     */
    private String source;

    /**
     * Ordinal.
     */
    private Long ordinal;

    /**
     * Last Modified.
     */
    private Date lastModified;

}