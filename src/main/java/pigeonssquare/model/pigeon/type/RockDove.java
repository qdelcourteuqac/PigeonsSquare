package main.java.pigeonssquare.model.pigeon.type;

import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.pigeon.Pigeon;

/**
 * Un Biset (pigeon domestique)
 */
public class RockDove extends Pigeon {
    protected static int SIZE = 1;
    protected static int SPEED = 1;

    public RockDove(EventManager instance) {
        super(instance);
    }

    @Override
    public void run() {
        System.out.println("RockDove");
    }
}
