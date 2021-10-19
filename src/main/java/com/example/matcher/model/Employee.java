package com.example.matcher.model;

import com.example.matcher.criteria.resolver.CriteriaType;
import com.example.matcher.criteria.MatchingCriteria;
import com.opencsv.bean.CsvBindByName;

public class Employee {

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String email;

    @MatchingCriteria(criteriaType = CriteriaType.DIVISION)
    @CsvBindByName
    private String division;

    @MatchingCriteria(criteriaType = CriteriaType.AGE)
    @CsvBindByName
    private Integer age;

    @MatchingCriteria(criteriaType = CriteriaType.TIMEZONE)
    @CsvBindByName
    private Integer timeZone;


    public Employee() {
    }

    public Employee(String name, String email, String division, Integer age, Integer timeZone) {
        this.name = name;
        this.email = email;
        this.division = division;
        this.age = age;
        this.timeZone = timeZone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(Integer timeZone) {
        this.timeZone = timeZone;
    }
}
