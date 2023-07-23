package com.example.ExpenseTrackerAPI.service;

import com.example.ExpenseTrackerAPI.model.AuthenticationToken;
import com.example.ExpenseTrackerAPI.model.Expense;
import com.example.ExpenseTrackerAPI.model.User;
import com.example.ExpenseTrackerAPI.model.dto.SignInInput;
import com.example.ExpenseTrackerAPI.model.dto.SignUpOutput;
import com.example.ExpenseTrackerAPI.repository.IAuthTokenRepo;
import com.example.ExpenseTrackerAPI.repository.IUserRepo;
import com.example.ExpenseTrackerAPI.service.utility.emailUtility.EmailHandler;
import com.example.ExpenseTrackerAPI.service.utility.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;

    @Autowired
    ExpenseService expenseService;

    @Autowired
    IAuthTokenRepo authTokenRepo;

    public SignUpOutput signUpUser(User user) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        String newEmail = user.getUserEmail();

        if(newEmail == null)
        {
            signUpStatusMessage = "Invalid email";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //check if this User email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            signUpStatusMessage = "Email already registered!!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());

            user.setUserPassword(encryptedPassword);
            userRepo.save(user);

            return new SignUpOutput(signUpStatus, "User registered successfully!!!");
        }
        catch(Exception e)
        {
            signUpStatusMessage = "Internal error occurred during sign up";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String signInUser(SignInInput signInInput) {

        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;


        }

        //check if this User email already exists ??
        User existingUser = userRepo.findFirstByUserEmail(signInEmail);

        if(existingUser == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingUser);
                authTokenRepo.save(authToken);

                EmailHandler.sendEmail("mahajanswati922@gmail.com","email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }

    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public String createExpenses(Expense expense, String email) {
        User expenseOwner = userRepo.findFirstByUserEmail(email);
        expense.setExpenseOwner(expenseOwner);
        return expenseService.createExpenses(expense);
    }

    public String removeExpenses(Integer expenseId, String email) {
        User user = userRepo.findFirstByUserEmail(email);
        return expenseService.removeExpenses(expenseId,user);
    }


}
