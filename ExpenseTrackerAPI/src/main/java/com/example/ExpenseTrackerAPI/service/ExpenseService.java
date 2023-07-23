package com.example.ExpenseTrackerAPI.service;

import com.example.ExpenseTrackerAPI.model.Expense;
import com.example.ExpenseTrackerAPI.model.User;
import com.example.ExpenseTrackerAPI.repository.IExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    IExpenseRepo expenseRepo;

    public List<Expense> getAllExpenses() {
        return expenseRepo.findAll();
    }

    public String createExpenses(Expense expense) {
        expenseRepo.save(expense);
        return "Expenses Created!!!!";
    }

    public String removeExpenses(Integer expenseId, User user) {
        Expense expense  = expenseRepo.findById(expenseId).orElse(null);
        if(expense != null && expense.getExpenseOwner().equals(user))
        {
            expenseRepo.deleteById(expenseId);
            return "Removed successfully";
        }
        else if (expense == null)
        {
            return "expense to be deleted does not exist";
        }
        else{
            return "Un-Authorized delete detected....Not allowed";
        }
    }

    public List<Expense> getExpensesByDate(LocalDate date) {
        return expenseRepo.findExpensesByDate(date);
    }

    public double calculateTotalExpenditure(LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepo.findByDateBetween(startDate, endDate);
        return expenses.stream().mapToDouble(Expense::getExpensePrice).sum();
    }

    public Expense updateExpensePrice(Integer expenseId, double newPrice) {
        Optional<Expense> optionalExpense = expenseRepo.findById(expenseId);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setExpensePrice(newPrice);
            return expenseRepo.save(expense);
        } else {
            throw new NoSuchElementException("Expense not found with ID: " + expenseId);
        }
    }
}
