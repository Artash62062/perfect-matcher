package com.example.matcher.criteria.processors;


import com.example.matcher.criteria.resolver.CriteriaType;
import com.example.matcher.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class AgeCriteriaProcessor implements CriteriaProcessor {

    private static final int RANGE = 5;
    private static final int PERCENTAGE_WHEN_MATCH = 30;
    private static  final int PERCENTAGE_WHEN_DONT_MATCH = 0;

    @Override
    public CriteriaType getCriteriaType() {
        return CriteriaType.AGE;
    }

    @Override
    public Integer getMatchingPercent(Employee employee1, Employee employee2) {
        if (Math.abs(employee1.getAge() - employee2.getAge()) <= 5) {
            return PERCENTAGE_WHEN_MATCH;
        } else {
            return PERCENTAGE_WHEN_DONT_MATCH;
        }
    }


}
