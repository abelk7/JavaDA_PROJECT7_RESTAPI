package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class TradeController {
    private final ITradeService tradeService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private List<String> errorMessageList;

    /**
     * Read - Get all trade
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/trade/list")
    public String home(Model model, HttpServletResponse httpServletResponse) {
        model.addAttribute("tradeList", tradeService.findAll());
        if (model.containsAttribute("success")) {
            return "trade/list";
        }
        addModelAttributeTrade(model, false, null, null);
        return "trade/list";
    }

    /**
     * Create - Add trade
     * @param trade trade to add
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade, Model model, HttpServletResponse httpServletResponse) {
        if (model.containsAttribute("success") && model.getAttribute("success").equals(true) && model.containsAttribute("trade")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }
        model.addAttribute("success", false);
        model.addAttribute("trade", trade);
        return "trade/add";
    }

    /**
     * Validation - Validator of trade
     * @param trade trade to validate
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @PostMapping("/trade/validate")
    public String validate(Trade trade, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Trade>> violations = validator.validate(trade);

        if (violations.isEmpty()) {
            tradeService.save(trade);
            addModelAttributeTrade(model, true, "Trade crée avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<Trade> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("trade", trade);
        addModelAttributeTrade(model, false, null, errorMessageList);
        return addTradeForm(trade, model, httpServletResponse);
    }

    /**
     * Read - read a trade
     * @param id Id of the trade to read
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        Trade trade = tradeService.findById(id);
        if (trade != null) {
            model.addAttribute("trade", trade);
            model.addAttribute("success", true);
            return "trade/update";
        }
        addModelAttributeTrade(model, false, "Aucun Trade n'a été trouvé avec l'id fourni", null);
        return home(model, httpServletResponse);
    }

    /**
     * Update - Update a trade
     * @param id Id of the trade to update
     * @param trade trade with new values
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, Trade trade, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Trade>> violations = validator.validate(trade);

        Trade trade1 = tradeService.findById(id);

        if (violations.isEmpty() && trade1 != null) {
            tradeService.save(trade);
            addModelAttributeTrade(model, true, "Trade mis à jour avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<Trade> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("trade", trade);
        addModelAttributeTrade(model, false, null, errorMessageList);
        return "trade/update";
    }

    /**
     * Delete - Delete a trade
     * @param id Id of the trade to delete
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @RequestMapping("/trade/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTrade(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        boolean isTradeDeleted = tradeService.deleteById(id);
        if (isTradeDeleted) {
            addModelAttributeTrade(model, true, "Trade avec id " + id + " à été supprimé", null);
            return home(model, httpServletResponse);
        }
        errorMessageList.add("Une erreur est survenue lors de la suppression du Trade");
        addModelAttributeTrade(model, false, null, errorMessageList);
        return home(model, httpServletResponse);
    }

    /**
     * Add values to model
     * @param model model contain readable values in template
     * @param success boolean request success(true)/fail(false)
     * @param message String message success
     * @param messageList List of message if error occurred
     */
    private void addModelAttributeTrade(Model model, boolean success, String message, List<String> messageList) {
        model.addAttribute("success", success);
        model.addAttribute("message", message);
        if (messageList != null && !messageList.isEmpty()) {
            model.addAttribute("messageList", messageList);
        }
    }
}
