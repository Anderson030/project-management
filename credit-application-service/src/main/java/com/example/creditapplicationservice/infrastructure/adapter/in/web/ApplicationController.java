package com.example.creditapplicationservice.infrastructure.adapter.in.web;

import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.domain.port.in.ApplicationCreateUseCase;
import com.example.creditapplicationservice.infrastructure.adapter.in.dto.CreditApplicationRequest;
import com.example.creditapplicationservice.infrastructure.adapter.in.dto.CreditApplicationResponse;
import com.example.creditapplicationservice.infrastructure.adapter.in.mapper.CreditApplicationRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationCreateUseCase createCreditApplicationUseCase;
    private final CreditApplicationRestMapper mapper;

    @PostMapping
    public ResponseEntity<CreditApplicationResponse> create(@RequestBody @Valid CreditApplicationRequest request) {
        CreditApplication domainRequest = mapper.toDomain(request);
        CreditApplication createdApplication = createCreditApplicationUseCase.create(domainRequest);
        CreditApplicationResponse response = mapper.toResponse(createdApplication);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
