package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;
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
public class RuleNameController {
    private final IRuleNameService ruleNameService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private List<String> errorMessageList;

    /**
     * Read - Get all ruleName
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/ruleName/list")
    public String home(Model model, HttpServletResponse httpServletResponse) {
        model.addAttribute("ruleNameList", ruleNameService.findAll());
        if (model.containsAttribute("success")) {
            return "ruleName/list";
        }
        addModelAttributeRuleName(model, false, null, null);
        return "ruleName/list";
    }

    /**
     * Create - Add ruleName
     * @param ruleName ruleName to add
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model, HttpServletResponse httpServletResponse) {
        if (model.containsAttribute("success") && model.getAttribute("success").equals(true) && model.containsAttribute("ruleName")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }
        model.addAttribute("success", false);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    /**
     * Validation - Validator of ruleName
     * @param ruleName ruleName to validate
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @PostMapping("/ruleName/validate")
    public String validate(RuleName ruleName, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);

        if (violations.isEmpty()) {
            ruleNameService.save(ruleName);
            addModelAttributeRuleName(model, true, "RuleName crée avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<RuleName> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("ruleName", ruleName);
        addModelAttributeRuleName(model, false, null, errorMessageList);
        return addRuleForm(ruleName, model, httpServletResponse);
    }

    /**
     * Read - read a ruleName
     * @param id Id of the ruleName to read
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/ruleName/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        RuleName ruleName = ruleNameService.findById(id);
        if (ruleName != null) {
            model.addAttribute("ruleName", ruleName);
            model.addAttribute("success", true);
            return "ruleName/update";
        }
        addModelAttributeRuleName(model, false, "Aucun RuleName n'a été trouvé avec l'id fourni", null);
        return home(model, httpServletResponse);
    }

    /**
     * Update - Update a ruleName
     * @param id Id of the ruleName to update
     * @param ruleName ruleName with new values
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, RuleName ruleName, Model model, HttpServletResponse httpServletResponse) {

        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RuleName>> violations = validator.validate(ruleName);

        RuleName ruleName1 = ruleNameService.findById(id);

        if (violations.isEmpty() && ruleName1 != null) {
            ruleNameService.save(ruleName);
            addModelAttributeRuleName(model, true, "RuleName mis à jour avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<RuleName> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("ruleName", ruleName);
        addModelAttributeRuleName(model, false, null, errorMessageList);
        return "ruleName/update";
    }

    /**
     * Delete - Delete a ruleName
     * @param id Id of the ruleName to delete
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @RequestMapping("/ruleName/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteRuleName(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        boolean isRuleNameDeleted = ruleNameService.deleteById(id);
        if (isRuleNameDeleted) {
            addModelAttributeRuleName(model, true, "RuleName avec id " + id + " à été supprimé", null);
            return home(model, httpServletResponse);
        }
        errorMessageList.add("Une erreur est survenue lors de la suppression du RuleName");
        addModelAttributeRuleName(model, false, null, errorMessageList);
        return home(model, httpServletResponse);
    }

    /**
     * Add values to model
     * @param model model contain readable values in template
     * @param success boolean request success(true)/fail(false)
     * @param message String message success
     * @param messageList List of message if error occurred
     */
    private void addModelAttributeRuleName(Model model, boolean success, String message, List<String> messageList) {
        model.addAttribute("success", success);
        model.addAttribute("message", message);
        if (messageList != null && !messageList.isEmpty()) {
            model.addAttribute("messageList", messageList);
        }
    }
}
