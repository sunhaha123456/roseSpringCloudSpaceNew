package com.rose;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class JumpPageControler {

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/home")
    public String home(HttpServletRequest request) {
        request.setAttribute("userId", request.getParameter("userId"));
        request.setAttribute("token", request.getParameter("token"));
        return "home";
    }
}