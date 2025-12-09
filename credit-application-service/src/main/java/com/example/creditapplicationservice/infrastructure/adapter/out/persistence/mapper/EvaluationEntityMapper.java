package com.example.creditapplicationservice.infrastructure.adapter.out.persistence.mapper;

import com.example.creditapplicationservice.domain.model.Evaluation;
import com.example.creditapplicationservice.infrastructure.adapter.out.persistence.entity.EvaluationEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EvaluationEntityMapper {

    @Mapping(target = "applicationId", ignore = true)
    Evaluation toDomain(EvaluationEntity entity);

    @InheritInverseConfiguration
    @Mapping(target = "application", ignore = true)
    EvaluationEntity toEntity(Evaluation domain);
}
