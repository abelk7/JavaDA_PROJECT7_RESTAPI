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

    @GetMapping("/trade/list")
    public String home(Model model, HttpServletResponse httpServletResponse) {
        model.addAttribute("tradeList", tradeService.findAll());
        if (model.containsAttribute("success")) {
            return "trade/list";
        }
        addModelAttributeTrade(model, false, null, null);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade, Model model, HttpServletResponse httpServletResponse) {
        trade.setTradeId(1);
        trade.setAccount("accountTestUpdated");
        trade.setType("typeTestUpdated");
        trade.setBuyQuantity(15.00);
        trade.setSellQuantity(20.00);
        trade.setBuyPrice(40.00);
        trade.setSellPrice(80.00);
        trade.setBenchmark("benchmarkTestUpdated");
        trade.setTradeDate(LocalDateTime.now());
        trade.setSecurity("securityTestUpdated");
        trade.setStatus("statusTestUpdated");
        trade.setTrader("traderTestUpdated");
        trade.setBook("bookTestUpdated");
        trade.setCreationName("creationNameTestUpdated");
        trade.setCreationDate(LocalDateTime.now());
        trade.setRevisionName("revisionNameTestUpdated");
        trade.setRevisionDate(LocalDateTime.now());
        trade.setDealName("dealNameTestUpdated");
        trade.setDealType("dealTypeTestUpdated");
        trade.setSourceListId("sourceListIdTestUpdated");
        trade.setSide("sideTestUpdated");

        if (model.containsAttribute("success") && model.getAttribute("success").equals(true) && model.containsAttribute("trade")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return home(model, httpServletResponse);
        }
        model.addAttribute("success", false);
        model.addAttribute("trade", trade);
        return "trade/add";
    }

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

    @PostMapping("/trade/update/{id}")
    public String updateTrade(Trade trade, Model model, HttpServletResponse httpServletResponse) {
        errorMessageList = new ArrayList<>();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Trade>> violations = validator.validate(trade);

        Trade trade1 = tradeService.findById(trade.getTradeId());

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

    private void addModelAttributeTrade(Model model, boolean success, String message, List<String> messageList) {
        model.addAttribute("success", success);
        model.addAttribute("message", message);
        if (messageList != null && !messageList.isEmpty()) {
            model.addAttribute("messageList", messageList);
        }
    }
}
