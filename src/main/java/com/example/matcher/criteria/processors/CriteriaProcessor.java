package com.example.matcher.criteria.processors;

import com.example.matcher.criteria.resolver.CriteriaType;
import com.example.matcher.criteria.resolver.CriteriaProcessorResolver;
import com.example.matcher.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;

public interface CriteriaProcessor {


    CriteriaType getCriteriaType();

    Integer getMatchingPercent(Employee employee1, Employee employee2);

    @Autowired
    default void registerMySefInCriteriaTypeResolver(CriteriaProcessorResolver resolver) {
        resolver.registerCriteriaInResolver(getCriteriaType(),this);
    }
}
