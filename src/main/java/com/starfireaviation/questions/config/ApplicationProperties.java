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

package com.starfireaviation.questions.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Application Properties.
 */
@Getter
@Setter
@ConfigurationProperties("questions")
public class ApplicationProperties {

    /**
     * GSDecryptor enabled flag.
     */
    private boolean gsDecryptorEnabled;

    /**
     * SecretKey.
     */
    private String secretKey;

    /**
     * Init Vector.
     */
    private String initVector;

    /**
     * Database location.
     */
    private String dbLocation;

    /**
     * Content source location.
     */
    private String contentSourceLocation;

    /**
     * Image(s) directory.
     */
    private String imageDir;

    /**
     * Connect Timeout.
     */
    private int connectTimeout;

    /**
     * Read Timeout.
     */
    private int readTimeout;

}
