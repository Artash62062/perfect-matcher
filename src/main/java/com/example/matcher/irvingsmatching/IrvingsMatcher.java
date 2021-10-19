package com.example.matcher.irvingsmatching;

import com.example.matcher.irvingsmatching.model.MatchingEntity;
import com.example.matcher.model.Employee;
import com.example.matcher.model.response.MatchingResponse;
import com.example.matcher.model.response.Pair;

import java.util.List;

public interface IrvingsMatcher {

    List<Pair> matchEmployees(List<MatchingEntity<Employee>> matchingEntities);
}
