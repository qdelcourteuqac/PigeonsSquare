package main.java.pigeonssquare.model.pigeon;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.event.Direction;
import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.grid.event.PigeonEvent;
import main.java.pigeonssquare.model.grid.event.SimulationEvent;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static main.java.pigeonssquare.model.grid.event.PigeonEvent.PigeonEventType.MOOVING;


public abstract class Pigeon implements Observer, Runnable, Cellulable {
    protected static int SIZE;
    protected static int SPEED;
    protected EventManager eventManager;

    public Pigeon(EventManager instance) {
        this.eventManager = instance;

        eventManager.addObserver(this);
    }

    @Override
    public void run() {
        Random random = new Random();
        int randomValue = random.nextInt(4);
        Direction direction;
        if (randomValue == 1) {
            direction = Direction.NORTH;
        } else if (randomValue == 2) {
            direction = Direction.EAST;
        } else if (randomValue == 3) {
            direction = Direction.SOUTH;
        } else {
            direction = Direction.WEST;
        }
        this.eventManager.setChanged();
        this.eventManager.notifyObservers(new PigeonEvent(MOOVING, direction));
    }


    @Override
    public void update(Observable observable, Object arg) {
        if (!(arg instanceof SimulationEvent)) {
            return;
        }

        SimulationEvent simulationEvent = (SimulationEvent) arg;
        switch (simulationEvent.eventType) {

            case START_SIMULATION:
                this.run();
                break;

            case STOP_SIMULATION:
                Thread.currentThread().interrupt();
                break;

        }
    }
}
