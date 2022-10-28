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

package com.starfireaviation.questions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static org.assertj.core.api.Assertions.fail;

@Slf4j
public class CommonSteps extends BaseSteps {

    @Given("^I am an authenticated user$")
    public void iAmAnAuthenticatedUser() throws Throwable {
        // TODO
    }

    @Given("^I provide an organization$")
    public void iProvideAnOrganization() throws Throwable {
        testContext.setOrganization(ORGANIZATION);
    }

    @Given("^I provide a correlation id")
    public void iProvideACorrelationId() throws Throwable {
        testContext.setCorrelationId(UUID.randomUUID().toString());
    }

    @Given("^I provide a client id")
    public void iProvideAClientId() throws Throwable {
        testContext.setClientId(UUID.randomUUID().toString());
    }

    @Then("^I should receive (.*)$")
    public void iShouldReceive(final String expectedResult) throws Throwable {
        switch (expectedResult) {
            case "a question added response":
                log.info("I should receive a question added response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "an event added response":
                log.info("I should receive an event added response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "a lesson added response":
                log.info("I should receive a lesson added response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "a lesson plan added response":
                log.info("I should receive a lesson plan added response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "a quiz added response":
                log.info("I should receive a quiz added response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "a reference material added response":
                log.info("I should receive a reference material added response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "a report added response":
                log.info("I should receive a report added response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "a user added response":
                log.info("I should receive a user added response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "an InvalidPayloadException":
                log.info("I should receive an InvalidPayloadException");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "a success response":
                log.info("I should receive a success response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "an unauthenticated response":
                log.info("I should receive an unauthenticated response");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "the QuizCompletionChart report":
                log.info("I should receive the QuizCompletionChart report");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "the QuizUserResultsChart report":
                log.info("I should receive the QuizUserResultsChart report");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            case "the QuizResultsChart report":
                log.info("I should receive the QuizResultsChart report");
                //Assertions.assertSame(HttpStatus.OK, testContext.getResponse().getStatusCode());
                break;
            default:
                fail("Unexpected error");
        }
    }
}
