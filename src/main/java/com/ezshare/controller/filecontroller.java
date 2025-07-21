package com.ezshare.controller;

import com.ezshare.model.FileModel;
import com.ezshare.service.FileService;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;
import java.net.http.HttpHeaders;

@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping()
    public String login() {
        return "home";
    }

    @GetMapping("/home")
    public String listFiles(Model model) {
        model.addAttribute("files", fileService.getAll());
        return "list-files";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file,
            @RequestParam("uploadedBy") String uploadedBy) throws IOException {
        fileService.uploadFile(file, uploadedBy);
        return "redirect:/files/home";
    }

    @GetMapping("/share/{id}")
    public String shareFile(@PathVariable("id") int id, Model model) {
        FileModel fileModel = fileService.shareFile(id);
        if (fileModel.hasBody()) {
            String currentUrl = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
            model.addAttribute("shareUrl", currentUrl);
            model.addAttribute("file", fileModel.getBody());
            return "share-file";
        } else {
            return "redirect:/files";
        }
    }

    @GetMapping("/download/{id}")
    public FileModel downloadFile(@PathVariable("id") int id) {
        return fileService.getFile(id);
    }

    @PostMapping("/delete/{id}")
    public String deleteFile(@PathVariable int id) {
        ResponseEntity<?> file = fileService.deleteFile(id);
        if(file.hasBody()){
            return "redirect:/files/home";
        }
        else{
            return "redirect:/files";
        }
    }
}
