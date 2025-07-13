package com.ezshare.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller //controller returns html file as per returned 
@RequestMapping() // added files as the end point for controller
public class filecontroller {
    @GetMapping("/home")
    public String login() {
        return "home"; //return home.html
    }

    @GetMapping("/files")
    public String files() {
        return "list-files"; //return list-files.html
    }

    @GetMapping("/share")
    public String share() {
        return "share-file"; // return share-file.html
    }
    
    
}
