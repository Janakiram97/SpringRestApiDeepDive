package com.example.springrestapideepdive.surveys;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SurveyController {
    private SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @GetMapping("/surveys")
    public List<Survey> retriveAllSurveys() {
        return surveyService.retriveAllSurveys();
    }

    @GetMapping("/surveys/{surveyId}")
    public Survey retriveSurvey(@PathVariable String surveyId) throws SurveyNotFoundException {
        Survey survey = surveyService.retriveSurveyById(surveyId);
        if (survey == null) throw new SurveyNotFoundException("Survey not found");
        return survey;
    }

    @GetMapping("/surveys/{surveyId}/questions")
    public List<Question> getAllQuestionsForSurvey(@PathVariable String surveyId) throws SurveyNotFoundException {
        return surveyService.getAllQuestionsForSurvey(surveyId);
    }

    @GetMapping("/surveys/{surveyId}/questions/{question}")
    public Question getQuestionsForSurvey(@PathVariable String surveyId, @PathVariable String question) throws SurveyNotFoundException {
        return surveyService.getQuestionsForSurvey(surveyId, question);
    }

    @PostMapping("/surveys/{surveyId}/questions")
    public ResponseEntity<Question> createQuestionForSurvey(@PathVariable String surveyId, @RequestBody Question question) throws SurveyNotFoundException {
        String randomId = surveyService.addQuestion(surveyId, question);
        ///surveys/{surveyId}/questions/{questionId}
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(randomId).toUri();
        return  ResponseEntity.created(location).build();
    }
    @DeleteMapping("/surveys/{surveyId}/questions/{questionId}")
    public ResponseEntity<Question> deleteQuestionForSurvey(@PathVariable String surveyId,@PathVariable String questionId) throws SurveyNotFoundException {
        boolean deleteQuestion = surveyService.deleteQuestion(surveyId, questionId);
        if(!deleteQuestion) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Question not found");
        else{
            return ResponseEntity.noContent().build();
        }
    }
    @PutMapping("/surveys/{surveyId}/questions/{questionId}")
    public ResponseEntity<Question> updateQuestionForSurvey(@PathVariable String surveyId,@PathVariable String questionId,
                                                            @RequestBody Question question) throws SurveyNotFoundException {
        surveyService.updateQuestion(surveyId,questionId,question);
        return ResponseEntity.noContent().build();
    }

}
