package com.baseclasses;

public class Rocket implements SpaceShip {

    protected static int rocketWeight;
    protected static int weightLimit;
    protected static double percent;
    protected static int allWeightWithCargo;
    public long rocketCost;
    protected int currentRocketWeight;
    public String rocketName;

    @Override
    public boolean launch() {
        return true;
    }

    @Override
    public boolean land() {
        return true;
    }

    @Override
    public void carry(ItemClass item) {
        currentRocketWeight += item.weight;
    }

    @Override
    public boolean canCarry(ItemClass item) {
        int weightOfTheItem = currentRocketWeight + item.weight;
        return weightOfTheItem <= weightLimit;
    }
}
