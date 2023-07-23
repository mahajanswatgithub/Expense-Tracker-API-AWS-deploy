package com.example.ExpenseTrackerAPI.controller;

import com.example.ExpenseTrackerAPI.model.Expense;
import com.example.ExpenseTrackerAPI.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;


    @GetMapping("expenses")
    public List<Expense> getAllExpenses()
    {
        return expenseService.getAllExpenses();
    }
    @GetMapping("expenses/{date}")
    public List<Expense> getExpensesByDate(@PathVariable LocalDate date)
    {
         return expenseService.getExpensesByDate(date);
    }
    @GetMapping("totalExpenditure")
    public double getTotalExpenditure(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return expenseService.calculateTotalExpenditure(startDate, endDate);
    }

    @PutMapping("updatePrice/{expenseId}")
    public Expense updateExpensePrice(@PathVariable Integer expenseId, @RequestParam double newPrice) {
        return expenseService.updateExpensePrice(expenseId, newPrice);
    }
}
