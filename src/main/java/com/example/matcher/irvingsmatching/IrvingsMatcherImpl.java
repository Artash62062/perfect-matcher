package com.example.matcher.irvingsmatching;

import com.example.matcher.irvingsmatching.model.MatchingEntity;
import com.example.matcher.model.Employee;
import com.example.matcher.model.response.MatchingResponse;
import com.example.matcher.model.response.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class IrvingsMatcherImpl implements IrvingsMatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(IrvingsMatcherImpl.class);

    public List<Pair> matchEmployees(List<MatchingEntity<Employee>> matchingEntities) {

        LOGGER.info("Start Matching List of Entities -{} by Irvings matching Algorithm", matchingEntities);
        for (MatchingEntity<Employee> matchingEntity : matchingEntities) {
            matchingEntity.findPreferablePair();
        }
        for (MatchingEntity<Employee> matchingEntity : matchingEntities) {
            matchingEntity.removeALlBeforePreferable();
        }
        List<Pair> pairs = new ArrayList<>();

        for (MatchingEntity<Employee> matchingEntity : matchingEntities) {
            Pair pair = new Pair();
            pair.setFirstEmployee(matchingEntity.getEntityToMatch());
            pair.setSecondEmployee(matchingEntity.getPair().getEntityToMatch());
            pairs.add(pair);
        }
        LOGGER.debug("Successfully generated matching List with best Average percentage -{}", pairs);
        return pairs;

//        matchingEntities = matchingEntities1;
//        List<MatchingEntity<Employee>> secondPreferences = new ArrayList<>();
//        List<MatchingEntity<Employee>> lastPreferences = new ArrayList<>();
//        for (MatchingEntity<Employee> matchingEntity : matchingEntities1) {
//            matchingEntity.removeAlreadyPairedEntities(matchingEntities);
//        }

//        while (true) {
//            if (lastPreferences.isEmpty() && secondPreferences.isEmpty()) {
//                lastPreferences.add(entity);
//                entity = entity.getTheSecondUserByPriority();
//            } else {
//                entity = secondPreferences.get(secondPreferences.size() - 1);
//                MatchingEntity<Employee> theWorstPair = entity.getTheWorstUserByPriority();
//                if (lastPreferences.contains(theWorstPair)) {
//                    lastPreferences.add(theWorstPair);
//                    break;
//                }
//                lastPreferences.add(theWorstPair);
//                entity = theWorstPair.getTheSecondUserByPriority();
//            }
//            secondPreferences.add(entity);
//        }
//        for (int i = 0; i < secondPreferences.size(); i++) {
//            MatchingEntity<Employee> firstRejectionPair = secondPreferences.get(i);
//            MatchingEntity<Employee> secondRejectionPair = lastPreferences.get(i+1);
//            firstRejectionPair.getRejection(secondRejectionPair);
//            secondRejectionPair.getRejection(firstRejectionPair);
//        }
    }

}
