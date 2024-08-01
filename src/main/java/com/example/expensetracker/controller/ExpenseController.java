package com.example.expensetracker.controller;
import com.example.expensetracker.model.Expense;
import com.example.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class ExpenseController {
    @Autowired
    private ExpenseService service;

    @GetMapping("/")
    public String viewHomePage(Model model){
        List<Expense> expenses = service.getAllExpense();
        BigDecimal totalAmount = expenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
        model.addAttribute("expenses",expenses);
        model.addAttribute("totalAmount",totalAmount);
        return "index";
    }

    @GetMapping("/addExpense")
    public String showAddExpense(Model model){
        Expense expense = new Expense();
        model.addAttribute("expense",expense);
        return "add-expense";
    }

    @PostMapping("/saveExpense")
    public String saveExpense(@ModelAttribute("expense") Expense expense, Model model){
        service.saveExpense(expense);
        return "redirect:/";
    }

    @GetMapping("/editExpense/{id}")
    public String showUpdateExpensePage(@PathVariable("id") long id,Model model){
        Expense expense = service.getExpenseByID(id);
        model.addAttribute("expense",expense);
        return "update-expense";
    }

    @PostMapping("/updateExpense/{id}")
    public String updateExpense(@PathVariable("id") long id,@ModelAttribute("expense") Expense expense){
        Expense existingExpense = service.getExpenseByID(id);
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setAmount(expense.getAmount());
        service.saveExpense(existingExpense);
        return "redirect:/";
    }

    @GetMapping("/deleteExpense/{id}")
    public String deleteExpense(@PathVariable("id") long id){
        service.deleteExpense(id);
        return "redirect:/";
    }
}
