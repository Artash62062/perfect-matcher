package com.example.matcher.criteria.resolver;


import com.example.matcher.criteria.processors.CriteriaProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public class CriteriaProcessorResolverImpl implements CriteriaProcessorResolver {

    private final EnumMap<CriteriaType, CriteriaProcessor> criteriaTypeToCriteriaProcessorMap;

    public CriteriaProcessorResolverImpl() {
        this.criteriaTypeToCriteriaProcessorMap = new EnumMap<CriteriaType, CriteriaProcessor>(CriteriaType.class);
    }


    @Override
    public CriteriaProcessor resolve(CriteriaType criteriaType) {
        return criteriaTypeToCriteriaProcessorMap.get(criteriaType);
    }

    @Override
    public void registerCriteriaInResolver(@NonNull CriteriaType criteriaType, CriteriaProcessor criteria) {
        criteriaTypeToCriteriaProcessorMap.put(criteriaType, criteria);
    }
}
