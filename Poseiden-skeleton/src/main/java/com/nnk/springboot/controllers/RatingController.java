package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class RatingController {
    private final IRatingService ratingService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private List<String> errorMessageList;

    @GetMapping("/rating/list")
    public String home(Model model, HttpServletResponse httpServletResponse) {
        model.addAttribute("ratingList", ratingService.findAll());
        if (model.containsAttribute("success")) {
            return "rating/list";
        }
        addModelAttributeRating(model, false, null, null);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model, HttpServletResponse httpServletResponse) {
        if (model.containsAttribute("success") && model.getAttribute("success").equals(true) && model.containsAttribute("rating")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }
        model.addAttribute("success", false);
        model.addAttribute("rating", rating);
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        Rating rating = ratingService.findById(id);
        if (rating != null) {
            model.addAttribute("rating", rating);
            model.addAttribute("success", true);
            return "rating/update";
        }
        addModelAttributeRating(model, false, "Aucun Rating n'a été trouvé avec l'id fourni", null);
        return home(model, httpServletResponse);
    }

    @PostMapping("/rating/validate")
    public String validate(Rating rating, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Rating>> violations = validator.validate(rating);

        if (violations.isEmpty()) {
            ratingService.save(rating);
            addModelAttributeRating(model, true, "Rating crée avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<Rating> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("rating", rating);
        addModelAttributeRating(model, false, null, errorMessageList);
        return addRatingForm(rating, model, httpServletResponse);
    }

    @PostMapping("/rating/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateRating(Rating rating, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Rating>> violations = validator.validate(rating);

        Rating rating1 = ratingService.findById(rating.getId());

        if (violations.isEmpty() && rating1 != null) {
            ratingService.save(rating);
            addModelAttributeRating(model, true, "Rating mis à jour avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<Rating> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        addModelAttributeRating(model, false, null, errorMessageList);
        return "rating/update";
    }

    @RequestMapping("/rating/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRating(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        boolean isRatingDeleted = ratingService.deleteById(id);
        if (isRatingDeleted) {
            addModelAttributeRating(model, true, "Rating avec id " + id + " à été supprimé", null);
            return home(model, httpServletResponse);
        }
        errorMessageList.add("Une erreur est survenue lors de la suppression du Rating");
        addModelAttributeRating(model, false, null, errorMessageList);
        return home(model, httpServletResponse);
    }

    private void addModelAttributeRating(Model model, boolean success, String message, List<String> messageList) {
        model.addAttribute("success", success);
        model.addAttribute("message", message);
        if (messageList != null && !messageList.isEmpty()) {
            model.addAttribute("messageList", messageList);
        }
    }
}
