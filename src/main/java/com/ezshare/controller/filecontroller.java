package com.ezshare.controller;

import com.ezshare.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Controller // controller returns html file as per returned
@RequestMapping("/files") // added files as the end point for controller
public class Filecontroller {

    @Autowired
    private FileService fileService;

    @GetMapping("/home")
    public String index(Model model) {
        model.addAttribute("files", fileService.getAll());
        return "list-files";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
            @RequestParam("uploadedby") String uploadedBy,
            RedirectAttributes redirectAttributes) {
        try {
            fileService.uploadFile(file, uploadedBy);
            redirectAttributes.addFlashAttribute("message", "✅ File uploaded successfully.");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "❌ Upload failed: " + e.getMessage());
        }
        return "redirect:/files/home";
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
    public String deleteFile(@PathVariable int id) {
        ResponseEntity<?> file = fileService.deleteFile(id);
        if (file.hasBody()) {
            return "redirect:/files/home";
        }
        return "redirect:/files/home";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") int id) {
        return fileService.getFile(id);
    }
    

    @GetMapping()
    public String login() {
        return "home"; // return home.html
    }

//    @GetMapping("/share")
//    public String files() {
//        return "share-file"; // return list-files.html
//    }

}
