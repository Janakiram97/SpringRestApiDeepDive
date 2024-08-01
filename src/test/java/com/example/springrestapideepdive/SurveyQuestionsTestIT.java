package com.example.springrestapideepdive;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SurveyQuestionsTestIT {

    @Autowired
    TestRestTemplate template;
    private final static String SPECIFIED_QUESTION_URL="/surveys/survey1/questions/question1";
   // /surveys/{surveyId}/questions/question1
    String questionJson= """
           {
           "id": "Question1",
           "description": "Most Popular Cloud Platform Today",
           "options": [
           "AWS",
           "Azure",
           "Google Cloud",
           "Oracle Cloud"
           ],
           "correctAnswer": "AWS"
           }
           """;

    @Test
    void getQuestionsForSurveyIT() throws JSONException {
        ResponseEntity<String> response = template.getForEntity(SPECIFIED_QUESTION_URL, String.class);
        //we can check and assert status code and content type
        assertTrue(response.getStatusCode().is2xxSuccessful());
        System.out.println(response.getHeaders().get("Content-Type").get(0));
        assertEquals("application/json",response.getHeaders().get("Content-Type").get(0));
        JSONAssert.assertEquals(questionJson,response.getBody(),true);

    }

    String surveyJson= """
            {
            "id": "Survey1",
            "title": "My Favorite Survey",
            "description": "Description of the Survey",
            "questions": [
            {
            "id": "Question1",
            "description": "Most Popular Cloud Platform Today",
            "options": [
            "AWS",
            "Azure",
            "Google Cloud",
            "Oracle Cloud"
            ],
            "correctAnswer": "AWS"
            },
            {
            "id": "Question2",
            "description": "Fastest Growing Cloud Platform",
            "options": [
            "AWS",
            "Azure",
            "Google Cloud",
            "Oracle Cloud"
            ],
            "correctAnswer": "Google Cloud"
            },
            {
            "id": "Question3",
            "description": "Most Popular DevOps Tool",
            "options": [
            "Kubernetes",
            "Docker",
            "Terraform",
            "Azure DevOps"
            ],
            "correctAnswer": "Kubernetes"
            }
            ]
            }
            """;
   // /surveys/{surveyId}
    private static final String SPECIFIED_SURVEY_URL="/surveys/survey1";
    @Test
    void getSurveyIT() throws JSONException {
        ResponseEntity<String> response = template.getForEntity(SPECIFIED_SURVEY_URL, String.class);
        System.out.println(response.getBody());
        JSONAssert.assertEquals(surveyJson,response.getBody(),true);
    }

   private static final String ALl_Survey_Questions_URL="/surveys/Survey1/questions";
    String jsonArrayExpected= """
            [
            {
            "id": "Question1"
            },
            {
            "id": "Question2"
            },
            {
            "id": "Question3"
            }
            ]
            """;
    @Test
    void questionsIT() throws JSONException {
        ResponseEntity<String> response = template.getForEntity(ALl_Survey_Questions_URL, String.class);
        System.out.println(response.getHeaders().get("Content-Type").get(0));
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", Objects.requireNonNull(response.getHeaders().get("Content-Type")).get(0));
    }


    @Test
    void createQuestionForSurveyIT() {
        //TODO
        //request Body
        //Need to set http headers
        //post
        //assert
        //201
        String requestBody= """
                 {
                "description": "your favourate Today",
                "options": [
                "Java",
                "Python",
                "C++",
                "Javascript"
                ],
                "correctAnswer": "AWS"
                }
                """;
        String url="/surveys/Survey1/questions";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        HttpEntity<String> entity= new HttpEntity<>(requestBody,headers);
        ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, entity, String.class);
        //[Location:"http://localhost:51196/surveys/Survey1/questions/4085477584", Content-Length:"0", Date:"Fri, 22 Mar 2024 05:11:58 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
        System.out.println(response.getHeaders());
        System.out.println(response.getStatusCode());
        assertEquals(201,response.getStatusCode().value());
        assertTrue(response.getHeaders().get("Location").get(0).contains("/surveys/Survey1/questions/"));

        //sometimes post the data and it will fail because of the data already exists so it will update the data.so we need to delete after post.

        //delete the data
        //take location from the response
        String location = response.getHeaders().get("Location").get(0);
        template.delete(location);
    }
}
