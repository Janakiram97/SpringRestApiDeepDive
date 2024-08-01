package com.example.springrestapideepdive.surveys;

import com.example.springrestapideepdive.EnumPoc.Level;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Stream;

import static java.util.spi.ToolProvider.findFirst;

@Service
public class SurveyService {
    private static final List<Survey> surveys = new ArrayList<>();

    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", Level.gcloud.getValue(), "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", Level.gcloud.getValue(), "Oracle Cloud"), Level.gcloud.getValue());
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);

    }

    public List<Survey> retriveAllSurveys() {
        return surveys;
    }

    public Survey retriveSurveyById(String id) {
        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate -> predicate.getId().equalsIgnoreCase(id)).findFirst();
        if (optionalSurvey.isEmpty()) return null;
        return optionalSurvey.get();
    }

    public List<Question> getAllQuestionsForSurvey(String surveyId) throws SurveyNotFoundException {
        Survey survey = retriveSurveyById(surveyId);
        if (survey == null) {
            throw new SurveyNotFoundException("Survey not found" + surveyId);
        } else {
            return survey.getQuestions();
        }
    }

    public Question getQuestionsForSurvey(String surveyid, String question) throws SurveyNotFoundException {
        //   Optional<Survey> optionalSurvey = surveys.stream().filter(predicate -> predicate.getId().equalsIgnoreCase(surveyid)).findFirst();
       /* Survey survey = retriveSurveyById(surveyid);
        if (survey == null) {
            throw new SurveyNotFoundException("Survey not found");
        } else {
            Optional<Question> optionalQuestion = survey.getQuestions().stream().filter(predicate -> predicate.getId().equalsIgnoreCase(question)).findFirst();
            if (optionalQuestion.isEmpty())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
            else {
                return optionalQuestion.get();
            }
        }*/
      /*  if(optionalSurvey.isEmpty())
        {
            throw  new SurveyNotFoundException("Survey not found");
        }else{
            Optional<Question> questionStream = optionalSurvey.get().getQuestions().stream().filter(predicate -> predicate.getId().equalsIgnoreCase(question)).findFirst();
            if(questionStream.isEmpty())
            {
                throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
            }else{
                return questionStream.get();
            }
        }
    }*/
        List<Question> allQuestionsForSurvey = getAllQuestionsForSurvey(surveyid);
        Optional<Question> questionStream = allQuestionsForSurvey.stream().filter(q -> q.getId().equalsIgnoreCase(question)).findFirst();
        if (questionStream.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found");
        else {
            return questionStream.get();
        }
    }

    public String addQuestion(String surveyId,Question question) throws SurveyNotFoundException {
        String randomId = getRandomId();
        question.setId(randomId);
        getAllQuestionsForSurvey(surveyId).add(question);
        return randomId;
    }

    private static String getRandomId() {
     SecureRandom random = new SecureRandom();
     String randomId = new BigInteger(32, random).toString();
       /* Random random = new Random();
        String randomId= String.valueOf( random.nextInt(1000));*/
        return randomId;
    }

    public boolean deleteQuestion(String surveyId,String questionId) throws SurveyNotFoundException {
        List<Question> allQuestionsForSurvey = getAllQuestionsForSurvey(surveyId);
       return allQuestionsForSurvey.removeIf(r -> r.getId().equalsIgnoreCase(questionId));
    }

    public void updateQuestion(String surveyId,String questionId,Question question) throws SurveyNotFoundException {
        List<Question> allQuestionsForSurvey = getAllQuestionsForSurvey(surveyId);
        allQuestionsForSurvey.removeIf(r ->r.getId().equalsIgnoreCase(questionId));
        allQuestionsForSurvey.add(question);
    }
}
