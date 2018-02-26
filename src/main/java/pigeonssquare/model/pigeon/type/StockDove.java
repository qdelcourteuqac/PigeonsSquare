package main.java.pigeonssquare.model.pigeon.type;

import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.pigeon.Pigeon;

import java.util.Observable;

/**
 * Un colombin
 */
public class StockDove extends Pigeon {
    protected static int SIZE = 1;
    protected static int SPEED = 1;

    public StockDove(EventManager instance) {
        super(instance);
    }

    @Override
    public void run() {
        System.out.println("StockDove");
    }

}
