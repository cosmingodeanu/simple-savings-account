package com.ing.savingsaccount.controller;

import com.ing.savingsaccount.model.SavingsAccount;
import com.ing.savingsaccount.repo.SavingsAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;

@Controller
@Validated
public class SavingsAccountController {

    private SavingsAccountRepository savingsAccountRepository;

    private final SavingsAccount defaultAccount = new SavingsAccount(-1, 0, "", new Date());

    @Autowired
    public SavingsAccountController(SavingsAccountRepository savingsAccountRepository) {
        this.savingsAccountRepository = savingsAccountRepository;
    }

    @PostMapping("/addacount")
    public String addAccount(@Valid @ModelAttribute("account") SavingsAccount account, BindingResult result, Model model, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("account", defaultAccount);
            return "welcome";
        }

        String username = authentication.getName();
        account.setUsername(username);
        if (savingsAccountRepository.findByUsername(username).isPresent()) {
            return "redirect:/welcome";
        }
        SavingsAccount savingsAccount = savingsAccountRepository.save(account);
        model.addAttribute("account", savingsAccount);
        return "redirect:/welcome";
    }

    @GetMapping("/welcome")
    public String getAccountByUsername(Model model, Authentication authentication) {
        String username = authentication.getName();
        SavingsAccount account = savingsAccountRepository.findByUsername(username).orElse(defaultAccount);
        model.addAttribute("account", account);
        return "welcome";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RedirectView handleError(ConstraintViolationException constraintEx, RedirectAttributes atts) {
        var errorMessages = new ArrayList<String>();
        constraintEx.getConstraintViolations().forEach(violation -> errorMessages.add(violation.getMessage()));

        atts.addFlashAttribute("messages", errorMessages);
        return new RedirectView("welcome");
    }
}
