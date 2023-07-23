package com.example.ExpenseTrackerAPI.repository;

import com.example.ExpenseTrackerAPI.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface IExpenseRepo extends JpaRepository<Expense,Integer> {

    List<Expense> findExpensesByDate(LocalDate date);

    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);

    Expense findByExpensePrice(double price);
}
