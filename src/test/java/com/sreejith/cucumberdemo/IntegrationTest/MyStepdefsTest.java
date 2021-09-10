package com.sreejith.cucumberdemo.IntegrationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sreejith.cucumberdemo.model.StudentVo;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class MyStepdefsTest {


    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    StudentVo studentVo = new StudentVo();
    private final String createStudentAPI = "/api/student";
    private String studentGetAPI = "/api/student/%s";

    private ResultActions resultAction = null;

    @Before
    public void setUp() {
      //  this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Given("The system up and running")
    public void theSystemUpAndRunning() {
        studentVo.setCountry("India");
        studentVo.setName("Sreejith");
        studentVo.setSubject("MBBS");
        studentVo.setUniversity("MGU");
        
    }

    @When("The user calls the \\/api\\/student with GET verb and Student details")
    public void theUserCallsTheApiStudentWithGETVerbAndStudentDetails() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String payloadJson = objectMapper.writeValueAsString(studentVo);
            resultAction = mockMvc.perform(put(createStudentAPI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payloadJson)
                    .accept(MediaType.APPLICATION_JSON));
        }
        catch(Exception ex)
        {
            Assert.fail(ex.getMessage());
        }

    }

    @Then("The new student is added and new student ID is returned")
    public void theNewStudentIsAddedAndNewStudentIDIsReturned() {

        String retValue = "";
        Integer retCode = 0;

        try {
            retValue = resultAction.andReturn().getResponse().getContentAsString();
            retCode = resultAction.andReturn().getResponse().getStatus();
        }
        catch (Exception ex)
        {
            Assert.fail(ex.getMessage());
        }
        Assertions.assertEquals(200,retCode);
        Assertions.assertTrue(retValue.contains("New Student created with ID:"));
    }


    @When("The user calls \\/api\\/student\\/id endpoint with {string} id")
    public void theUserCallsApiStudentIdEndpointWithId(String arg0) {

        try{
            theUserCallsTheApiStudentWithGETVerbAndStudentDetails();
        }
        catch(Exception exception)
        {
            Assert.fail(exception.getMessage());
        }
        //The above had caused a new student to get added. Extract the UUID and delimiter is ":"

        String retValue = "";

        try {
            retValue = resultAction.andReturn().getResponse().getContentAsString();
        }
        catch (Exception ex)
        {
            Assert.fail(ex.getMessage());
        }
        String Uuid = Arrays.asList(retValue.split(":")).get(1);
        Uuid = Uuid.trim();
        String getEndPoint = String.format(studentGetAPI,Uuid).trim();

        try {
            resultAction = mockMvc.perform(get(getEndPoint)
                    .accept(MediaType.APPLICATION_JSON));
        }
        catch(Exception ex)
        {
            Assert.fail(ex.getMessage());
        }


    }


    @Then("The student details are returned {string}")
    public void theStudentDetailsAreReturned(String arg0) {

        String retValue = "";
        Integer retCode = 0;

        try {
            retValue = resultAction.andReturn().getResponse().getContentAsString();
            retCode = resultAction.andReturn().getResponse().getStatus();
        }
        catch (Exception ex)
        {
            Assert.fail(ex.getMessage());
        }
        Assertions.assertEquals(200,retCode);
    }

}
