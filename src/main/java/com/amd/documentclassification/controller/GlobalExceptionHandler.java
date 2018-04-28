package com.amd.documentclassification.controller;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    //https://jira.spring.io/browse/SPR-14651
    //Spring 4.3.5 supports RedirectAttributes
    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error", "Error evaluating file, please try again.");
        return "redirect:/uploadStatus";

    }
    
    @ExceptionHandler(value = FileNotFoundException.class)
    public String handle(FileNotFoundException ex,  RedirectAttributes redirectAttributes) throws IOException {
        System.out.println("handling file not found exception");
        redirectAttributes.addFlashAttribute("error", "Something went wrong, please try again later.");
        return "redirect:/uploadStatus";
    }

    @ExceptionHandler(value = IOException.class)
    public String handle(IOException ex, RedirectAttributes redirectAttributes) throws IOException {
    	System.out.println("handling file not found exception");
        redirectAttributes.addFlashAttribute("error", "Something went wrong, please try again later.");
        return "redirect:/uploadStatus";
    }

}