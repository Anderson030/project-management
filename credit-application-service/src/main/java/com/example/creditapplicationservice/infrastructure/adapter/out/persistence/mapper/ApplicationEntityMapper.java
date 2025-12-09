package com.example.creditapplicationservice.infrastructure.adapter.out.persistence.mapper;

import com.example.creditapplicationservice.domain.model.CreditApplication;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.CreditApplicationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        uses = {EvaluationEntityMapper.class, AffiliateEntityMapper.class}
)
public interface ApplicationEntityMapper {
    @Mapping(target = "affiliateId", source = "affiliate.id")
    CreditApplication toDomain(CreditApplicationEntity entity);

    @Mapping(target = "affiliate", ignore = true)
    CreditApplicationEntity toEntity(CreditApplication domain);
}
