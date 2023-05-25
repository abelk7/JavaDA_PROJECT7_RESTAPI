package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private List<String> errorMessageList;

    /**
     * Read - Get all user
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/user/list")
    public String home(Model model, HttpServletResponse httpServletResponse) {
        model.addAttribute("users", userService.findAll());
        if (model.containsAttribute("success")) {
            return "user/list";
        }
        addModelAttributeUser(model, false, null, null);
        return "user/list";
    }

    /**
     * Create - Add user
     * @param user user to add
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/user/add")
    public String addUser(User user, Model model, HttpServletResponse httpServletResponse) {

        if (model.containsAttribute("success") && model.getAttribute("success").equals(true) && model.containsAttribute("user")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }
        model.addAttribute("user", user);
        return "user/add";
    }

    /**
     * Validation - Validator of user
     * @param user user to validate
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @PostMapping("/user/validate")
    public String validate(User user, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (violations.isEmpty()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.save(user);
            addModelAttributeUser(model, true, "User crée avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<User> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("user", user);
        addModelAttributeUser(model, false, null, errorMessageList);
        return addUser(user, model, httpServletResponse);
    }

    /**
     * Read - read a user
     * @param id Id of the user to read
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/user/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        User user = userService.findById(id);
        if (user != null) {
            user.setPassword("");
            model.addAttribute("user", user);
            model.addAttribute("success", true);
            return "user/update";
        }
        new IllegalArgumentException("Invalid user Id:" + id);
        addModelAttributeUser(model, false, "Aucun Utilisateur n'a été trouvé avec l'id fourni", null);
        return home(model, httpServletResponse);
    }

    /**
     * Update - Update a user
     * @param id Id of the user to update
     * @param user user with new values
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, User user, Model model, HttpServletResponse httpServletResponse) {

        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        User user1 = userService.findById(user.getId());

        if (violations.isEmpty() && user1 != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            userService.save(user);
            addModelAttributeUser(model, true, "User mis à jour avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);

        }

        for (ConstraintViolation<User> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("user", user);
        addModelAttributeUser(model, false, null, errorMessageList);
        return "user/update";
    }

    /**
     * Delete - Delete a user
     * @param id Id of the user to delete
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @RequestMapping("/user/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteUser(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        boolean isDelete = userService.deleteById(id);
        if (isDelete) {
            addModelAttributeUser(model, true, "User avec id " + id + " à été supprimé", null);
            return home(model, httpServletResponse);
        }

        errorMessageList.add("Une erreur est survenue lors de la suppression du User");
        addModelAttributeUser(model, false, null, errorMessageList);
        return home(model, httpServletResponse);
    }

    /**
     * Add values to model
     * @param model model contain readable values in template
     * @param success boolean request success(true)/fail(false)
     * @param message String message success
     * @param messageList List of message if error occurred
     */
    private void addModelAttributeUser(Model model, boolean success, String message, List<String> messageList) {
        model.addAttribute("success", success);
        model.addAttribute("message", message);
        if (messageList != null && !messageList.isEmpty()) {
            model.addAttribute("messageList", messageList);
        }
    }
}
