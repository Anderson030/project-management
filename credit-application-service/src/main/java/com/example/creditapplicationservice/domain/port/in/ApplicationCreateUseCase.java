package com.example.creditapplicationservice.domain.port.in;

import com.example.creditapplicationservice.domain.model.CreditApplication;

public interface ApplicationCreateUseCase {
    CreditApplication create(CreditApplication application);
}
