package com.example.tennisscore.cucumber;

import com.example.tennisscore.infrastructure.adapter.input.rest.data.GameRequest;
import com.example.tennisscore.infrastructure.adapter.input.rest.data.GameResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.example.tennisscore.infrastructure.adapter.input.rest.GameApiPaths.GAME_BASE_PATH;
import static com.example.tennisscore.infrastructure.adapter.input.rest.GameApiPaths.PLAY_PATH;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor
@ActiveProfiles("test")
@AutoConfigureMockMvc
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TennisSteps {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private String output;
    private ProblemDetail errorResponse;

    @When("player plays {string}")
    public void player_plays(String input) throws Exception {
        MvcResult result = mockMvc.perform(
                        post(GAME_BASE_PATH + PLAY_PATH)
                                .contentType(APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(new GameRequest(input))))
                .andReturn();
        if (result.getResponse().getStatus() == HttpStatus.OK.value()) {
            var latestResponse = objectMapper.readValue(result.getResponse().getContentAsString(), GameResponse.class);
            output = latestResponse.result();
        } else {
            errorResponse = objectMapper.readValue(result.getResponse().getContentAsString(), ProblemDetail.class);
        }

    }

    @Then("the result should contain {string}")
    public void the_result_should_contain(String expected) {
        assertTrue(output.contains(expected));
    }

    @Then("the result should not contain {string}")
    public void the_result_should_not_contain(String expected) {
        assertFalse(output.contains(expected));
    }

    @Then("an error should occur with message {string}")
    public void an_error_should_occur_with_message(String expectedMessage) {
        assertNotNull(errorResponse);
        assertNotNull(errorResponse.getTitle());
        assertTrue(errorResponse.getTitle().contains(expectedMessage));
    }
}
