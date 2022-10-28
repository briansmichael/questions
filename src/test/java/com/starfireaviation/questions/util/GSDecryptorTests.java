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

package com.starfireaviation.questions.util;

import org.bouncycastle.crypto.InvalidCipherTextException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * GSDecryptorTests.
 */
public class GSDecryptorTests {

    /**
     * Secret Key.
     */
    public static final String SECRET_KEY = "SecretpwkMZYaXKeyqB86A==";

    /**
     * Init Vector.
     */
    public static final String INIT_VECTOR = "FgInitskVectorrl";

    /**
     * Encrypted String.
     */
    //private static final String ENCRYPTED_STRING = "Some random text";

    /**
     * GSDecryptor.
     */
    private GSDecryptor gsDecryptor;

    /**
     * Setup.
     */
    @BeforeEach
    public void setUp() {
        gsDecryptor = new GSDecryptor(false, SECRET_KEY, INIT_VECTOR);
    }

    /**
     * Test Decrypt.
     */
    @Test
    public void testDecrypt() throws InvalidCipherTextException {
        //final String decryptedString = gsDecryptor.decrypt(ENCRYPTED_STRING);

        //Assertions.assertNotNull(decryptedString);
        //Assertions.assertSame(ENCRYPTED_STRING, decryptedString);
    }
}
