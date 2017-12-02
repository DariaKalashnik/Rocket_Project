package com.baseclasses;

public class ItemClass {
    int weight;
    private String name;

    /**
     * @param name of the rocket (stored in the phase1.txt or phase2.txt file)
     * @param weight of the rocket (stored in the phase1.txt or phase2.txt file)
     */
    public ItemClass(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return name + weight;
    }
}
