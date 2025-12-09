package com.example.creditapplicationservice.domain.port.in;

import com.example.creditapplicationservice.domain.model.CreditApplication;

import java.util.List;

public interface GetCreditApplicationsUseCase {
    List<CreditApplication> getByRole(String username, String role);
}