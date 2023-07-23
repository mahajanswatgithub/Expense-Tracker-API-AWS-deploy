package com.example.ExpenseTrackerAPI.controller;

import com.example.ExpenseTrackerAPI.model.Expense;
import com.example.ExpenseTrackerAPI.model.User;
import com.example.ExpenseTrackerAPI.model.dto.SignInInput;
import com.example.ExpenseTrackerAPI.model.dto.SignUpOutput;
import com.example.ExpenseTrackerAPI.service.AuthenticationService;
import com.example.ExpenseTrackerAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("user/signup")
    public SignUpOutput signUpUser(@RequestBody @Valid User user)
    {

        return userService.signUpUser(user);
    }

    @PostMapping("user/signIn")
    public String sigInUser(@RequestBody @Valid SignInInput signInInput)
    {
        return userService.signInUser(signInInput);
    }

    @PostMapping("expense")
    public String createExpenses(@RequestBody @Valid Expense expense, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.createExpenses(expense,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }

    @DeleteMapping("expense")
    public String removeExpenses(@RequestParam Integer expenseId, @RequestParam String email, @RequestParam String token)
    {
        if(authenticationService.authenticate(email,token)) {
            return userService.removeExpenses(expenseId,email);
        }
        else {
            return "Not an Authenticated user activity!!!";
        }
    }
    @GetMapping("users")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }



}
