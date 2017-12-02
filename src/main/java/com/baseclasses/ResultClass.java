package com.baseclasses;

public class ResultClass {

    private String name;
    private long totalBudget;
    private int numOfExplosion;

    public ResultClass() {
        // Empty constructor
    }

    public ResultClass(String name, int totalBudget, int numOfExplosion) {
        this.name = name;
        this.totalBudget = totalBudget;
        this.numOfExplosion = numOfExplosion;
    }

    public long getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(int totalBudget) {
        this.totalBudget = totalBudget;
    }

    public int getNumOfExplosion() {
        return numOfExplosion;
    }

    public void setNumOfExplosion(int numOfExplosion) {
        this.numOfExplosion = numOfExplosion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ResultClass{" +
                "name='" + name + '\'' +
                ", totalBudget=" + totalBudget +
                ", numOfExplosion=" + numOfExplosion +
                '}';
    }
}
