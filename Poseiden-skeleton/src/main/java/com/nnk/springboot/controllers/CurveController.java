package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;
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
public class CurveController {
    private final ICurvePointService curvePointService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private List<String> errorMessageList;

    @GetMapping("/curvePoint/list")
    public String home(Model model, HttpServletResponse httpServletResponse) {
        model.addAttribute("curvPointList", curvePointService.findAll());
        if (model.containsAttribute("success")) {
            return "curvePoint/list";
        }
        addModelAttributeCurvePoint(model, false, null, null);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model, HttpServletResponse httpServletResponse) {
        if (model.containsAttribute("success") && model.getAttribute("success").equals(true) && model.containsAttribute("curvePoint")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }
        model.addAttribute("success", false);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(CurvePoint curvePoint, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);

        if (violations.isEmpty()) {
            curvePointService.save(curvePoint);
            addModelAttributeCurvePoint(model, true, "CurvePoint crée avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<CurvePoint> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("curvePoint", curvePoint);
        addModelAttributeCurvePoint(model, false, null, errorMessageList);
        return addCurvePointForm(curvePoint, model, httpServletResponse);
    }

    @GetMapping("/curvePoint/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        CurvePoint curvePoint = curvePointService.findById(id);
        if (curvePoint != null) {
            model.addAttribute("curvePoint", curvePoint);
            model.addAttribute("success", true);
            return "curvePoint/update";
        }
        addModelAttributeCurvePoint(model, false, "Aucun CurvePoint n'a été trouvé avec l'id fourni", null);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        return home(model, httpServletResponse);
    }

    @PostMapping("/curvePoint/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateCurvePoint(@PathVariable("id") Integer id, CurvePoint curvePoint, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CurvePoint>> violations = validator.validate(curvePoint);

        CurvePoint curvePoint1 = curvePointService.findById(id);

        if (violations.isEmpty() && curvePoint1 != null) {
            curvePointService.save(curvePoint);
            addModelAttributeCurvePoint(model, true, "CurvePoint mis à jour avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<CurvePoint> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        addModelAttributeCurvePoint(model, false, null, errorMessageList);
        return "curvePoint/update";
    }

    @RequestMapping("/curvePoint/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBid(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        boolean isCurvePoinrDeleted = curvePointService.deleteById(id);
        if (isCurvePoinrDeleted) {
            addModelAttributeCurvePoint(model, true, "CurvePoint avec id " + id + " à été supprimé", null);
            return home(model, httpServletResponse);
        }
        errorMessageList.add("Une erreur est survenue lors de la suppression du CurvePoint");
        addModelAttributeCurvePoint(model, false, null, errorMessageList);
        return home(model, httpServletResponse);
    }

    private void addModelAttributeCurvePoint(Model model, boolean success, String message, List<String> messageList) {
        model.addAttribute("success", success);
        model.addAttribute("message", message);
        if (messageList != null && !messageList.isEmpty()) {
            model.addAttribute("messageList", messageList);
        }
    }
}
