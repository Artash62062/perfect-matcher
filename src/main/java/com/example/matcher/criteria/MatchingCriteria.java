package com.example.matcher.criteria;

import com.example.matcher.criteria.resolver.CriteriaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MatchingCriteria {

    CriteriaType criteriaType();
}
