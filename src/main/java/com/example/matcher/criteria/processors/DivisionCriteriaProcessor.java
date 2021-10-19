package com.example.matcher.criteria.processors;

import com.example.matcher.criteria.resolver.CriteriaType;
import com.example.matcher.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class DivisionCriteriaProcessor implements CriteriaProcessor {

    private static final int PERCENTAGE_WHEN_MATCH = 30;
    private static  final int PERCENTAGE_WHEN_DONT_MATCH = 0;

    @Override
    public CriteriaType getCriteriaType() {
        return CriteriaType.DIVISION;
    }

    @Override
    public Integer getMatchingPercent(Employee employee1, Employee employee2) {
        if(employee1.getDivision().equals(employee2.getDivision())) {
            return PERCENTAGE_WHEN_MATCH;
        } else  {
            return PERCENTAGE_WHEN_DONT_MATCH;
        }
    }

}
