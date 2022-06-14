package com.sutrix.yusuf.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sutrix.yusuf.dto.PersonDTO;
import com.sutrix.yusuf.exceptions.GlobalExceptionHandler;
import com.sutrix.yusuf.service.PersonService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Java6Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    PersonService personService;

    @Test
    public void whenValidInput_thenReturnsCreated() throws Exception {
        PersonDTO personDTO = new PersonDTO("Jim", "Halpert", "jim.halpert@dm.com", 30);

        mockMvc.perform(post("/person/create")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenNotValidInput_thenReturnsBadRequest() throws Exception {
        PersonDTO personDTO = new PersonDTO(null, "Halpert", "jim.halpert@dm.com", 30);

        MvcResult mvcResult = mockMvc.perform(post("/person/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        GlobalExceptionHandler.ErrorResult expectedErrorResponse = new GlobalExceptionHandler.ErrorResult("firstName", "First Name is mandatory");

        String actualResponseBody = mvcResult.getResponse().getContentAsString();
        String expectedResponseBody = objectMapper.writeValueAsString(expectedErrorResponse);

        assertThat(actualResponseBody).isEqualToIgnoringWhitespace(expectedResponseBody);

    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/person/")).andExpect(status().isOk());
    }

}