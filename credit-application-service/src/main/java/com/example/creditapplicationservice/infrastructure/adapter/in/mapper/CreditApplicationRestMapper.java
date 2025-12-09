package com.example.creditapplicationservice.infrastructure.adapter.in.mapper;

import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.infrastructure.adapter.in.dto.CreditApplicationRequest;
import com.example.creditapplicationservice.infrastructure.adapter.in.dto.CreditApplicationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditApplicationRestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "evaluation", ignore = true)
    @Mapping(target = "applicationDate", ignore = true)
    CreditApplication toDomain(CreditApplicationRequest request);

    CreditApplicationResponse toResponse(CreditApplication domain);
}
