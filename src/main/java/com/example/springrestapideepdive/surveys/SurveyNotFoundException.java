package com.example.springrestapideepdive.surveys;


public class SurveyNotFoundException extends Exception{
    public SurveyNotFoundException(String message) {
       super(message);
    }
 public String getMessage() {
        return super.getMessage();
 }
}
