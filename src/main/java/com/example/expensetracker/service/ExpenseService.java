package com.example.expensetracker.service;

import com.example.expensetracker.model.Expense;
import com.example.expensetracker.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo repo;

    public List<Expense> getAllExpense(){
        return repo.findAll();
    }

    public void saveExpense(Expense expense){
        repo.save(expense);
    }

    public Expense getExpenseByID(Long id){
        return repo.findById(id).orElse(null);
    }

    public void deleteExpense(Long id){
        repo.deleteById(id);
    }
}
