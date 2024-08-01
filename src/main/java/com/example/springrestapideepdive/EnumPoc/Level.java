package com.example.springrestapideepdive.EnumPoc;

public enum Level {

     Inbound("INBOUND_BATCH"),
    outbound("OUTBOUND_BATCH"),
     gcloud("Google Cloud");
     String value;
      Level(String value)
     {
         this.value=value;
     }
     public String getValue()
     {
         return value;
     }
}