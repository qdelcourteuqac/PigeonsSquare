package main.java.pigeonssquare.model.pigeon.type;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.pigeon.Pigeon;

/**
 * Un ramier
 */
public class CollaredDove extends Pigeon implements Cellulable {
    protected static int SIZE = 1;
    protected static int SPEED = 1;

    public CollaredDove(EventManager instance) {
        super(instance);
    }


    @Override
    public void run() {
        System.out.println("CollaredDove");
    }
}
