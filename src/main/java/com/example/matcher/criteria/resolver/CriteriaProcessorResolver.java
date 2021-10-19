package com.example.matcher.criteria.resolver;

import com.example.matcher.criteria.processors.CriteriaProcessor;
import org.springframework.lang.NonNull;

public interface CriteriaProcessorResolver {

    CriteriaProcessor resolve(final @NonNull CriteriaType criteriaType);

    void registerCriteriaInResolver(final @NonNull CriteriaType criteriaType, CriteriaProcessor criteria);
}
