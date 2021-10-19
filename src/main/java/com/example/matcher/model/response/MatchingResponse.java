package com.example.matcher.model.response;

import java.util.List;
import java.util.Objects;

public class MatchingResponse {

    List<Pair> matchedPairs;
    private double averagePercentage;

    public List<Pair> getMatchedPairs() {
        return matchedPairs;
    }

    public void setMatchedPairs(List<Pair> matchedPairs) {
        this.matchedPairs = matchedPairs;
    }

    public double getAveragePercentage() {
        return averagePercentage;
    }

    public void setAveragePercentage(double averagePercentage) {
        this.averagePercentage = averagePercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchingResponse response = (MatchingResponse) o;
        return Double.compare(response.averagePercentage, averagePercentage) == 0 && Objects.equals(matchedPairs, response.matchedPairs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchedPairs, averagePercentage);
    }

    @Override
    public String toString() {
        return "MatchingResponse{" +
                "matchedPairs=" + matchedPairs +
                ", averagePercentage=" + averagePercentage +
                '}';
    }
}
