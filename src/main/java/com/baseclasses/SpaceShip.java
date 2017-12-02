package com.baseclasses;

public interface SpaceShip {

    boolean launch();

    boolean land();

    // Check how many items the Rocket can carry
    boolean canCarry(ItemClass item);

    // Check how many items the Rocket is carrying
    void carry(ItemClass item);
}
