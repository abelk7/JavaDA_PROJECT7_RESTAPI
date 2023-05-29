package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;
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
public class BidListController {
    private final IBidListService bidListService;
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private List<String> errorMessageList;

    /**
     * Read - Get all bidlist
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/bidList/list")
    @ResponseStatus(HttpStatus.OK)
    public String home(Model model, HttpServletResponse httpServletResponse) {
        model.addAttribute("bidList", bidListService.findAll());
        if (model.containsAttribute("success")) {
            return "bidList/list";
        }
        addModelAttributeBidlist(model, false, null, null);
        return "bidList/list";
    }

    /**
     * Create - Add bidlist
     * @param bidList bidlist to add
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bidList, Model model, HttpServletResponse httpServletResponse) {
        if (model.containsAttribute("success") && model.getAttribute("success").equals(true) && model.containsAttribute("bidList")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }
        model.addAttribute("success", false);
        model.addAttribute("bidList", bidList);
        return "bidList/add";
    }

    /**
     * Validation - Validator of bidlist
     * @param bid bidlist to validate
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @PostMapping("/bidList/validate/")
    public String validate(BidList bid, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<BidList>> violations = validator.validate(bid);

        if (violations.isEmpty()) {
            bidListService.save(bid);
            addModelAttributeBidlist(model, true, "BidList crée avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_CREATED);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<BidList> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }

        model.addAttribute("bid", bid);
        addModelAttributeBidlist(model, false, null, errorMessageList);
        return addBidForm(bid, model, httpServletResponse);
    }

    /**
     * Read - read a bidlist
     * @param id Id of the bidlist to read
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @GetMapping("/bidList/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        BidList bidList = bidListService.findById(id);
        if (bidList != null) {
            model.addAttribute("bid", bidList);
            model.addAttribute("success", true);
            return "bidList/update";
        }
        addModelAttributeBidlist(model, false, "Aucun BidList n'a été trouvé avec l'id fourni", null);
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        return home(model, httpServletResponse);
    }

    /**
     * Update - Update a bidlist
     * @param id Id of the bidlist to update
     * @param bid bidlist with new values
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @PostMapping("/bidList/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateBid(@PathVariable("id") Integer id, BidList bid, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BidList>> violations = validator.validate(bid);

        BidList bidList1 = bidListService.findById(id);

        if (violations.isEmpty() && bidList1 != null) {
            bidListService.save(bid);
            addModelAttributeBidlist(model, true, "Bidlist mis à jour avec succès", null);
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }

        for (ConstraintViolation<BidList> violation : violations) {
            errorMessageList.add(violation.getPropertyPath() + " " + violation.getMessage());
        }
        model.addAttribute("bid", bid);
        addModelAttributeBidlist(model, false, null, errorMessageList);
        return "bidList/update";
    }

    /**
     * Delete - Delete a bidlist
     * @param id Id of the bidlist to delete
     * @param model model contain readable values in template
     * @param httpServletResponse response of request
     * @return - An String, name  of the template
     */
    @RequestMapping("/bidList/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteBid(@PathVariable("id") Integer id, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        boolean isBidListDeleted = bidListService.deleteById(id);
        if (isBidListDeleted) {
            addModelAttributeBidlist(model, true, "BidList avec id " + id + " à été supprimé", null);
            return home(model, httpServletResponse);
        }
        errorMessageList.add("Une erreur est survenue lors de la suppression du Bidlist");
        addModelAttributeBidlist(model, false, null, errorMessageList);
        return home(model, httpServletResponse);
    }

    /**
     * Add values to model
     * @param model model contain readable values in template
     * @param success boolean request success(true)/fail(false)
     * @param message String message success
     * @param messageList List of message if error occurred
     */
    private void addModelAttributeBidlist(Model model, boolean success, String message, List<String> messageList) {
        model.addAttribute("success", success);
        model.addAttribute("message", message);
        if (messageList != null && !messageList.isEmpty()) {
            model.addAttribute("messageList", messageList);
        }
    }
}
