package com.example.creditapplicationservice.infrastructure.entrypoints.api.rest;

import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.domain.port.in.GetCreditApplicationsUseCase;
import com.example.creditapplicationservice.infrastructure.adapter.in.dto.CreditApplicationResponse;
import com.example.creditapplicationservice.infrastructure.adapter.in.mapper.CreditApplicationRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class CreditApplicationController {

    private final GetCreditApplicationsUseCase getCreditApplicationsUseCase;
    private final CreditApplicationRestMapper mapper;

    @GetMapping
    public ResponseEntity<List<CreditApplicationResponse>> getAll() {

        // 1. Get who is making the request from the Token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // The document or user

        // Extract the first role (assuming only one)
        String role = auth.getAuthorities().iterator().next().getAuthority();

        // 2. Request filtered data from the domain
        List<CreditApplication> applications = getCreditApplicationsUseCase.getByRole(username, role);

        // 3. Convert to Response
        List<CreditApplicationResponse> response = applications.stream()
                .map(mapper::toResponse) // Reuse your existing mapper
                .toList();

        return ResponseEntity.ok(response);
    }
}
