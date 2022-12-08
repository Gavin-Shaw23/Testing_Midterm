package com.example.Testing_Midterm.controllers;

import com.example.Testing_Midterm.beans.SiteUser;
import com.example.Testing_Midterm.database.DatabaseAccess;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    //declaring an arraylist for the beans Class SiteUser
    private ArrayList<SiteUser> userList = new ArrayList<>();
    //Declaring a database access
    private DatabaseAccess database;

    public HomeController(DatabaseAccess database) {this.database = database;}


    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("user", new SiteUser());
        model.addAttribute("array", userList);
        return "index";
    }

    @RequestMapping("/login")
    public String login(@ModelAttribute SiteUser user, Model model){
        List<SiteUser> users = database.getUsers();
        model.addAttribute("userList", users);
        model.addAttribute("user", user);
        if(database.getUser(user) != null){
            userList.clear();
            return "welcome";
        } else {
            userList.clear();
            return "invalid";
        }
    }

    //This method is
    @GetMapping("/register")
    public String registration(Model model){
        model.addAttribute("user", new SiteUser());
        return "register";
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute SiteUser user){
        int returnValue = database.addUser(user);
        userList.add(user);
        System.out.println("Return value is: " + returnValue);
        return "redirect:/";
    }

    @GetMapping("/invalid")
    public String invalid(@ModelAttribute SiteUser user){
        return "invalid";
    }
}

