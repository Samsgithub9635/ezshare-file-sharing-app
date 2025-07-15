package com.ezshare.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ezshare.service.FileService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;





@Controller //controller returns html file as per returned 
@RequestMapping() // added files as the end point for controller
public class Filecontroller {

    @Autowired
    private FileService fileService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("files", fileService.getAll());
        return new String();
    }

    @PostMapping("/upload")
    public String postMethodName(@RequestParam("file") MultipartFile file, @RequestParam("uploadedby") String uploadedBy) throws IOException {
        fileService.uploadFile(file, uploadedBy);
        return "redirect:/"; // redirect to this page again
    }
    
    
    @GetMapping("/login")
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
