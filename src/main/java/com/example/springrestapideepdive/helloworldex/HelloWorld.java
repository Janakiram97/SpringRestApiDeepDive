package com.example.springrestapideepdive.helloworldex;

import com.example.springrestapideepdive.helloworldex.HelloWorldBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorld {

    @GetMapping(value="/hello",produces= MediaType.APPLICATION_JSON_VALUE)
    public String hello(HttpServletRequest request)
    {
        String acceptHeader=request.getHeader("Accept");
        System.out.println("Accept Header: "+acceptHeader);
        return  " Hello World!";
    }
    @GetMapping(value="/helloworld")
    public HelloWorldBean helloworld(HttpServletRequest request)
    {
        String acceptHeader=request.getHeader("Accept");
        System.out.println("Accept Header: "+acceptHeader);
        return  new HelloWorldBean(" Hello World!");
    }

    @GetMapping(value="/helloworld/{name}")
    public String helloworldPathParam(@PathVariable String name)
    {
        return " Hello World! "+name;
    }
}
