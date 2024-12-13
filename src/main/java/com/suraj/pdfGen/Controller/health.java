package com.suraj.pdfGen.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abc")
public class health {
    @GetMapping("/hi")
    public String  sayhi(){
        return  "hi folks !!";
    }
}
