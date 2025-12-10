package com;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    
    @RequestMapping("/")
    public String index() {
        return "index.html";
    }
    
    @RequestMapping("/admin")
    public String admin() {
        return "admin.html";
    }
    
    @RequestMapping("/track")
    public String track() {
        return "track.html";
    }
}
