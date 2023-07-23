package com.example.ExpenseTrackerAPI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer expenseId;
    @NotBlank
    private String expenseTitle;
    private String expenseDescription;
    private Double expensePrice;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "fk_user_id")
    private User expenseOwner;
}
