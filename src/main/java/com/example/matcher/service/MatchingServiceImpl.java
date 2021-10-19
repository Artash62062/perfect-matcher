package com.example.matcher.service;


import com.example.matcher.criteria.processors.CriteriaProcessor;
import com.example.matcher.criteria.resolver.CriteriaProcessorResolver;
import com.example.matcher.criteria.resolver.CriteriaType;
import com.example.matcher.criteria.MatchingCriteria;
import com.example.matcher.fileProcessor.CvsFileProcessor;
import com.example.matcher.irvingsmatching.IrvingsMatcher;
import com.example.matcher.irvingsmatching.model.MatchingEntity;
import com.example.matcher.model.Employee;
import com.example.matcher.model.response.MatchingResponse;
import com.example.matcher.model.response.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MatchingServiceImpl implements MatchingService {


    private static final Logger LOGGER = LoggerFactory.getLogger(MatchingServiceImpl.class);
    private final CvsFileProcessor fileProcessor;
    private final CriteriaProcessorResolver criteriaProcessorResolver;
    private final IrvingsMatcher matcher;
    private final Map<Employee, MatchingEntity<Employee>> entityMap = new HashMap<>();

    public MatchingServiceImpl(final IrvingsMatcher matcher, final CvsFileProcessor cvsFileProcessor, final CriteriaProcessorResolver criteriaProcessorResolver) {
        this.fileProcessor = cvsFileProcessor;
        this.criteriaProcessorResolver = criteriaProcessorResolver;
        this.matcher = matcher;
    }

    @Override
    public MatchingResponse matchEmployeesFromFile(MultipartFile file) {
        LOGGER.info("Starting matching of employees from file - {}", file.getOriginalFilename());
        List<Employee> employeesFromFile = fileProcessor.getEmployeesFromFile(file);
        createMatchingEntitiesForEmployees(employeesFromFile);
        List<MatchingEntity<Employee>> matchingEntities = createMatchingEntities(employeesFromFile);
        List<Pair> pairs = matcher.matchEmployees(matchingEntities);
        LOGGER.debug("Successfully processed Employees for file - {}", file.getOriginalFilename());
        return getMatchingResponse(pairs);
    }

    private List<MatchingEntity<Employee>> createMatchingEntities(List<Employee> employees) {
        LOGGER.debug("Creating Matching entities for matching employees -{}", employees);
        List<MatchingEntity<Employee>> matchingEntities = new ArrayList<>();
        for (Employee employee : employees) {
            List<Employee> otherEmployees = new ArrayList<>(employees);
            otherEmployees.remove(employee);
            List<Pair> priorityListOfPairs = getPriorityList(employee, otherEmployees);
            Map<MatchingEntity<Employee>, Integer> priorityByEntity = new HashMap<>();
            Map<Integer, MatchingEntity<Employee>> entityBYPriority = new HashMap<>();
            for (int i = 1; i <= priorityListOfPairs.size(); i++) {
                Pair pair = priorityListOfPairs.get(i - 1);
                priorityByEntity.put(entityMap.get(pair.getSecondEmployee()), i);
                entityBYPriority.put(i, entityMap.get(pair.getSecondEmployee()));
            }
            entityMap.get(employee).setPriorityByEntity(priorityByEntity);
            entityMap.get(employee).setEntityByPriority(entityBYPriority);
            MatchingEntity<Employee> employeeMatchingEntity = entityMap.get(employee);
            matchingEntities.add(employeeMatchingEntity);

        }
        LOGGER.debug("Successfully created Matching entities for matching employees - {} with result -{}", employees, matchingEntities);
        return matchingEntities;
    }

    private void createMatchingEntitiesForEmployees(List<Employee> employees) {
        for (Employee employee : employees) {
            entityMap.put(employee, new MatchingEntity<>(employee));
        }
    }

    private List<Pair> getPriorityList(Employee employee, List<Employee> employees) {
        LOGGER.debug("Constructing Priority List for matching for employee -{}", employee);
        List<Field> matchingFields = Arrays
                .stream(employee.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(MatchingCriteria.class))
                .collect(Collectors.toList());

        List<Pair> allPairsForSelectedEmployee = new ArrayList<>();
        for (Employee employee1 : employees) {
            Integer matchingPercentage = 0;
            for (Field matchingField : matchingFields) {
                CriteriaType criteriaType = matchingField.getAnnotation(MatchingCriteria.class).criteriaType();
                CriteriaProcessor criteriaProcessor = criteriaProcessorResolver.resolve(criteriaType);
                matchingPercentage += criteriaProcessor.getMatchingPercent(employee, employee1);
            }
            Pair pair = new Pair();
            pair.setFirstEmployee(employee);
            pair.setSecondEmployee(employee1);
            pair.setMatchingPercentage(matchingPercentage);
            allPairsForSelectedEmployee.add(pair);
        }
        allPairsForSelectedEmployee.sort(Pair::compareTo);
        LOGGER.debug("Successfully generated priority List for employee-{} with result-{}", employee, allPairsForSelectedEmployee);
        return allPairsForSelectedEmployee;
    }

    private double getMatchingPercentage(Employee employee, Employee employee1) {
        LOGGER.debug("getting Matching percentage between employee - {} and employee -{}", employee, employee1);
        List<Field> matchingFields = Arrays
                .stream(employee.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(MatchingCriteria.class))
                .collect(Collectors.toList());
        Integer matchingPercentage = 0;
        for (Field matchingField : matchingFields) {
            CriteriaType criteriaType = matchingField.getAnnotation(MatchingCriteria.class).criteriaType();
            CriteriaProcessor criteriaProcessor = criteriaProcessorResolver.resolve(criteriaType);
            matchingPercentage += criteriaProcessor.getMatchingPercent(employee, employee1);
        }
        LOGGER.debug("successfully generated Matching percentage with result-{}", matchingPercentage);
        return matchingPercentage;
    }

    private MatchingResponse getMatchingResponse(List<Pair> pairs) {
        LOGGER.debug("Generating Matching result based on list of pairs - {}", pairs);
        MatchingResponse response = new MatchingResponse();
        ArrayList<Pair> responsePairs = new ArrayList<>();
        for (Pair pair : pairs) {
            boolean hasAlready = false;
            for (Pair responsePair : responsePairs) {
                hasAlready = responsePair.hasSamePairs(pair);
                if (hasAlready) break;
            }
            if (hasAlready) {
                continue;
            }
            pair.setMatchingPercentage(getMatchingPercentage(pair.getFirstEmployee(), pair.getSecondEmployee()));
            responsePairs.add(pair);
        }
        Optional<Double> allPercentagesSum = responsePairs.stream().map(Pair::getMatchingPercentage).reduce(Double::sum);
        response.setMatchedPairs(responsePairs);

        response.setAveragePercentage(allPercentagesSum.get() / responsePairs.size());
        LOGGER.debug("successfully generated Matching result - {}", response);
        return response;

    }


}
