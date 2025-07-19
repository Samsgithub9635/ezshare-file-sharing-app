package com.ezshare.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ezshare.service.FileService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // controller returns html file as per returned
@RequestMapping("/files") // added files as the end point for controller
public class Filecontroller {

    @Autowired
    private FileService fileService;

    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("files", fileService.getAll());
        return "home";
    }

    @PostMapping("/upload")
    public String postMethodName(@RequestParam("file") MultipartFile file,
            @RequestParam("uploadedby") String uploadedBy) throws IOException {
        fileService.uploadFile(file, uploadedBy);
        return "redirect:/files/home"; // redirect to this page again
    }

    @GetMapping("/share/{id}")
    public String shareFile(@PathVariable int id, Model model) {
        ResponseEntity<?> fileModel = fileService.shareFile(id); // ✅ Method call now valid
        if (fileModel.hasBody()) {
            String currentUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
            model.addAttribute("shareUrl", currentUrl);
            model.addAttribute("file", fileModel.getBody());
            return "share-file";
        } else {
            return "redirect:/files/home";
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable int id) {
        return fileService.deleteFile(id);
    }

    @GetMapping("/login")
    public String login() {
        return "home"; // return home.html
    }

    @GetMapping("/share")
    public String files() {
        return "share-file"; // return list-files.html
    }

}
