package com.example.ExpenseTrackerAPI.repository;

import com.example.ExpenseTrackerAPI.model.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuthTokenRepo extends JpaRepository<AuthenticationToken,Integer> {
    AuthenticationToken findFirstByTokenValue(String authTokenValue);
}
