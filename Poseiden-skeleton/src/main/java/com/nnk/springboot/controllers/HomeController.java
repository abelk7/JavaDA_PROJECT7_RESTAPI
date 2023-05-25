package com.nnk.springboot.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {

    /**
     * Read - Get home page
     * @param model model contain readable values in template
     * @return - An String, name  of the template
     */
    @GetMapping("/")
    public String home(Model model) {
        return "home";
    }

    /**
     * Read - Get admin home page
     * @param model model contain readable values in template
     * @return - An String, name  of the template
     */
    @GetMapping("/admin/home")
    public String adminHome(Model model) {
        return "redirect:/bidList/list";
    }

    /**
     * Logout a user logged
     * @param request
     * @param response
     * @return - An String, name  of the template
     */
    @PostMapping("/app-logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "home";
    }
}
