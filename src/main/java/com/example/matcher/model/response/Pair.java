package com.example.matcher.model.response;

import com.example.matcher.model.Employee;

import java.util.Objects;

public class Pair implements Comparable<Pair> {
    private Employee firstEmployee;
    private Employee secondEmployee;
    private double matchingPercentage;

    public Employee getFirstEmployee() {
        return firstEmployee;
    }

    public void setFirstEmployee(Employee firstEmployee) {
        this.firstEmployee = firstEmployee;
    }

    public Employee getSecondEmployee() {
        return secondEmployee;
    }

    public void setSecondEmployee(Employee secondEmployee) {
        this.secondEmployee = secondEmployee;
    }

    public double getMatchingPercentage() {
        return matchingPercentage;
    }

    public void setMatchingPercentage(double matchingPercentage) {
        this.matchingPercentage = matchingPercentage;
    }

    public boolean hasSamePairs (Pair pair) {
        if(
                pair.getFirstEmployee().equals(this.getSecondEmployee())
                ||pair.getSecondEmployee().equals(this.getFirstEmployee())
                ||pair.getFirstEmployee().equals(this.firstEmployee)
                ||pair.getSecondEmployee().equals(this.secondEmployee)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Pair pair) {
        return this.equals(pair) ? 0 : Double.compare(pair.getMatchingPercentage(), this.matchingPercentage);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Double.compare(pair.matchingPercentage, matchingPercentage) == 0 && Objects.equals(firstEmployee, pair.firstEmployee) && Objects.equals(secondEmployee, pair.secondEmployee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstEmployee, secondEmployee, matchingPercentage);
    }
}
