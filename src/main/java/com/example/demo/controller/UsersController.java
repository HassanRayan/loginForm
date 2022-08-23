package com.example.demo.controller;

import com.example.demo.model.UsersModel;
import com.example.demo.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("RegisterRequset",new UsersModel());
        return "register_page";
    }
    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("LoginRequset",new UsersModel());
        return "login_page";
    }
    @PostMapping("/register")
    public String Register(@ModelAttribute UsersModel usersModel){
        System.out.println("Register Request : "+usersModel);
        UsersModel RegisterUser= usersService.registerUser(usersModel.getLogin(),usersModel.getPassword(),usersModel.getEmail());
        return RegisterUser == null ?"/error_page" : "redirect:/login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel , Model model){
        System.out.println("login Request : "+usersModel);
        UsersModel authenticate= usersService.authenticate(usersModel.getLogin(),usersModel.getPassword());
        if(authenticate != null) {
            model.addAttribute("userlogin", authenticate.getLogin());
            return "personal_page";
        }
        else
            return "error_page";
    }

}
