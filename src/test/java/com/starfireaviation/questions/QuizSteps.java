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

import com.starfireaviation.common.CommonConstants;
import com.starfireaviation.common.model.Quiz;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Slf4j
public class QuizSteps extends BaseSteps {

    @Before
    public void init() {
        testContext.reset();
    }

    @Given("^I have a quiz")
    public void iHaveAQuiz() throws Throwable {
        testContext.setQuiz(new Quiz());
    }

    @And("^A quiz exists$")
    public void aQuizExists() throws Throwable {
        // TODO
    }

    @And("^The quiz has title with (.*) characters$")
    public void theQuizHasTitleWithXCharacters(final int characterCount) throws Throwable {
        // TODO
    }

    @And("^The quiz's title is modified to be (.*) characters$")
    public void theQuizTitleIsModifiedToBeXCharacters(final int characterCount) throws Throwable {
        // TODO
    }

    @When("^I submit the quiz$")
    public void iAddTheQuiz() throws Throwable {
        log.info("I submit the quiz");
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (testContext.getOrganization() != null) {
            headers.add(CommonConstants.ORGANIZATION_HEADER_KEY, testContext.getOrganization());
        }
        if (testContext.getCorrelationId() != null) {
            headers.add(CommonConstants.CORRELATION_ID_HEADER_KEY, testContext.getCorrelationId());
        }
        //final HttpEntity<Question> httpEntity = new HttpEntity<>(testContext.getQuestion(), headers);
        //testContext.setResponse(restTemplate.postForEntity(URL, httpEntity, Void.class));
    }

    @When("^I get the quiz$")
    public void iGetTheQuiz() throws Throwable {
        // TODO
    }

    @When("^I delete the quiz$")
    public void iDeleteTheQuiz() throws Throwable {
        // TODO
    }
    @When("^I submit the quiz for update$")
    public void iSubmitTheQuizForUpdate() throws Throwable {
        // TODO
    }

    @When("^I get all quizzes$")
    public void iGetAllQuizzes() throws Throwable {
        // TODO
    }

    @When("^I complete a quiz$")
    public void iCompleteAQuiz() throws Throwable {
        // TODO
    }

    @When("^I start a quiz$")
    public void iStartAQuiz() throws Throwable {
        // TODO
    }

    @When("^I add a question$")
    public void iAddAQuestion() throws Throwable {
        // TODO
    }

    @When("^I remove a question$")
    public void iRemoveAQuestion() throws Throwable {
        // TODO
    }

    @And("^A quiz should be received$")
    public void aQuizShouldBeReceived() throws Throwable {
       // TODO
    }

    @And("^The quiz should be removed$")
    public void theQuizShouldBeRemoved() throws Throwable {
        // TODO
    }

}
