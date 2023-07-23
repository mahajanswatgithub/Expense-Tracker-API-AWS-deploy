package com.example.ExpenseTrackerAPI.service;

import com.example.ExpenseTrackerAPI.model.AuthenticationToken;
import com.example.ExpenseTrackerAPI.repository.IAuthTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    IAuthTokenRepo authTokenRepo;

    public boolean authenticate(String email, String authTokenValue)
    {
        AuthenticationToken authToken = authTokenRepo.findFirstByTokenValue(authTokenValue);

        if(authToken == null)
        {
            return false;
        }

        String tokenConnectedEmail = authToken.getUser().getUserEmail();

        return tokenConnectedEmail.equals(email);
    }
}
