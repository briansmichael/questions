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

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Ref.
 */
@Data
@Entity
@Table(name = "REFS")
public class TestEntity extends BaseEntity {

    /**
     * Test ID.
     */
    private Long testId;

    /**
     * TestName.
     */
    private String testName;

    /**
     * TestAbbr.
     */
    private String testAbbr;

    /**
     * Group ID.
     */
    private Long groupId;

    /**
     * Sort By.
     */
    private Long sortBy;

    /**
     * Last Modified.
     */
    private Date lastModified;

}
