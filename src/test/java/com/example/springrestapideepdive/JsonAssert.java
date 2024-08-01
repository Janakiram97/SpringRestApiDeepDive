package com.example.springrestapideepdive;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonAssert {
    @Test
    void jsonAssert_example() throws JSONException {
       String actual = "{id:1,name:John}";
         String expected = """
                            {"id":1,
                            "name":"John"
                            }
                            """;
        JSONAssert.assertEquals(expected,actual,true);
       // with the help of JSONAssert.assertEquals() method, we can compare the actual and expected JSON strings.
        // It won't consider spaces and new lines while comparing the strings.
    }

}
