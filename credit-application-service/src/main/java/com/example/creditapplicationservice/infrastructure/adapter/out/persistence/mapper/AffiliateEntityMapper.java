package com.example.creditapplicationservice.infrastructure.adapter.out.persistence.mapper;

import com.example.creditapplicationservice.domain.model.Affiliate;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE
)
public interface AffiliateEntityMapper {
    Affiliate toDomain(AffiliateEntity entity);
    AffiliateEntity toEntity(Affiliate domain);
}
