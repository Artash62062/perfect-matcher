package com.example.matcher.irvingsmatching.model;

import java.util.*;

public class MatchingEntity<T> {
    private final T entityToMatch;
    private Map<MatchingEntity<T>,Integer> priorityByEntity;
    private Map<Integer,MatchingEntity<T>> entityByPriority;
    private MatchingEntity<T> pair;

    public MatchingEntity(T entity) {
        entityToMatch = entity;
    }

    public T getEntityToMatch() {
        return entityToMatch;
    }


    public MatchingEntity<T> getPair() {
        return pair;
    }



    public void setPriorityByEntity(Map<MatchingEntity<T>, Integer> priorityByEntity) {
        this.priorityByEntity = priorityByEntity;
    }


    public void setEntityByPriority(Map<Integer, MatchingEntity<T>> entityByPriority) {
        this.entityByPriority = entityByPriority;
    }
//    public MatchingEntity<T> getTheSecondUserByPriority () {
//        Set<Integer> integers = entityByPriority.keySet();
//        List<Integer> collect = new ArrayList<>(integers);
//        Collections.sort(collect);
//        System.out.println();
//        return entityByPriority.get(collect.get(1));
//    }
//    public MatchingEntity<T> getTheWorstUserByPriority () {
//        Set<Integer> integers = entityByPriority.keySet();
//        List<Integer> collect = new ArrayList<>(integers);
//        Collections.sort(collect);
//        System.out.println();
//        return entityByPriority.get(collect.get(collect.size()-1));
//    }

    public void findPreferablePair() {
        for (int i = 1; i <= entityByPriority.size(); i++) {
            MatchingEntity<T> entity = entityByPriority.get(i);
            boolean isAgreed = suggestUsers(entity);
            if (isAgreed) {
                break;
            }
        }
    }

    public boolean suggestUsers(MatchingEntity<T> entity) {
        Boolean agreedWithSuggestions = entity.isAgreedWithSuggestions(this);
        if (!agreedWithSuggestions) {
            getRejection(entity);
        }
        return agreedWithSuggestions;
    }

    public void removeALlBeforePreferable() {
        Integer integer = priorityByEntity.get(pair);
        Optional<Integer> max = entityByPriority.keySet().stream().max(Integer::compareTo);
        for (int i = integer + 1; i <= max.get(); i++) {
            MatchingEntity<T> entity = entityByPriority.get(i);
            if(entity == null) {
                continue;
            }
            entity.getRejection(this);
            entityByPriority.remove(i);
            priorityByEntity.remove(entity);
        }
    }
    public void getRejection(MatchingEntity<T> entity) {
        entityByPriority.remove(priorityByEntity.get(entity));
        priorityByEntity.remove(entity);
    }
    public void findNewPair (MatchingEntity<T> entity) {
        Integer integer = priorityByEntity.get(entity);
        Optional<Integer> max = entityByPriority.keySet().stream().max(Integer::compareTo);
        for (int i = ++integer; i < max.get(); i++) {
            if(entityByPriority.get(i)==null) {
                continue;
            }
            boolean isAgreed = suggestUsers(entityByPriority.get(i));
            if(isAgreed) {
                break;
            }
        }
        getRejection(entity);
    }
    public Boolean isAgreedWithSuggestions(MatchingEntity<T> entity) {
        if (pair == null) {
            pair = entity;
            return true;
        }
        if (priorityByEntity.get(entity) < priorityByEntity.get(pair)) {
            entityByPriority.remove(priorityByEntity.get(pair));
            priorityByEntity.remove(pair);
            pair.findNewPair(this);
            this.pair = entity;
            return true;
        }
        entityByPriority.remove(priorityByEntity.get(entity));
        priorityByEntity.remove(entity);
        return false;
    }

}
