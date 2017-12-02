package com.baseclasses;

public class RocketClass {
    private String name;
    private int weight;
    private int rocketCost;
    private int weightLimit;
    private double percentLaunch;
    private double percentLand;

    public RocketClass() {
        // Empty constructor
    }

    /**
     * @param name is fetched from the database
     * @param weight is fetched from the database
     * @param rocketCost is fetched from the database
     * @param weightLimit is fetched from the database
     * @param percentLaunch is fetched from the database
     * @param percentLand is fetched from the database
     */
    public RocketClass(String name, int weight, int rocketCost, int weightLimit, double percentLaunch, double percentLand) {
        this.name = name;
        this.weight = weight;
        this.rocketCost = rocketCost;
        this.weightLimit = weightLimit;
        this.percentLaunch = percentLaunch;
        this.percentLand = percentLand;
    }

    public double getPercentLaunch() {
        return percentLaunch;
    }

    public void setPercentLaunch(double percentLaunch) {
        this.percentLaunch = percentLaunch;
    }

    public double getPercentLand() {
        return percentLand;
    }

    public void setPercentLand(double percentLand) {
        this.percentLand = percentLand;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getRocketCost() {
        return rocketCost;
    }

    public void setRocketCost(int rocketCost) {
        this.rocketCost = rocketCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                .concat(" name: " + name)
                .concat( "percentLand" + percentLand)
                .concat( "percentLaunch" + percentLaunch)
                .concat(" rocketCost: " + rocketCost)
                .concat(" weight: " + weight)
                .concat(" weightLimit" + weightLimit);
    }
}
