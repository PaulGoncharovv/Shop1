package com.example.springsecurityapplication.controllers;
//steam Дядя Дуся

import com.example.springsecurityapplication.models.Person;
import com.example.springsecurityapplication.security.PersonDetails;
import com.example.springsecurityapplication.services.PersonService;
import com.example.springsecurityapplication.services.ProductService;
import com.example.springsecurityapplication.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    private final PersonValidator personValidator;
    private final PersonService personService;

    private final ProductService productService;
    public MainController(PersonValidator personValidator, PersonService personService, ProductService productService) {
        this.personValidator = personValidator;
        this.personService = personService;
        this.productService = productService;
    }

    @GetMapping("/personal_account")
    public String index(Model model) {
        //Получаем объект аутентификации с помощью SpringContextHolder обращаемся к контексту и на нем вызываем метод аутентификации. Из сессии текущего пользователя получаем объект, который был положен в данную сессию после аутентификации пользователя
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        ///////////////////////////////////////////////////
        //переход админа на admin//
        String role = personDetails.getPerson().getRole();
        if(role.equals("ROLE_ADMIN")){
            return "redirect:/admin";
        }
        System.out.println(personDetails.getPerson());
        System.out.println("ID пользователя: " + personDetails.getPerson().getId());
        System.out.println("Логин пользователя: " + personDetails.getPerson().getLogin());
        System.out.println("Пароль пользователя: " + personDetails.getPerson().getPassword());
            model.addAttribute("products", productService.getAllProduct());
        return "user/index";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("person") Person person) {
        return "registration";

    }

    @PostMapping("/registration")
    public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        personService.register(person);
        return "redirect:/personal_account";
    }
////    Idea-5d8f716=302422df-cab7-4983-ba45-c7c9fc8fd64d;
//    Idea-5d8f716=302422df-cab7-4983-ba45-c7c9fc8fd64d; JSESSIONID=2C2F091F9B352990175B09AF5CDFC9F1

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "/user/infoProduct";
    }
}
