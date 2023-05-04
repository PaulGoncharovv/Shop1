package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthenticationController {
   @GetMapping("/authentication")
    public String login(){
    return "authentication";
    }

}
