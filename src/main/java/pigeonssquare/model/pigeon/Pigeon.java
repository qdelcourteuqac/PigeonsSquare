package main.java.pigeonssquare.model.pigeon;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.event.Direction;
import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.grid.event.PigeonEvent;
import main.java.pigeonssquare.model.grid.event.SimulationEvent;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import static main.java.pigeonssquare.model.grid.event.PigeonEvent.PigeonEventType.MOVING;


public abstract class Pigeon implements Observer, Runnable, Cellulable {
    protected EventManager eventManager;

    public Pigeon() {
        this.eventManager = EventManager.getInstance();

        eventManager.addObserver(this);
    }

    @Override
    public void run() {
        while(true) {
            try {
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
                this.eventManager.notifyObservers(new PigeonEvent(this, MOVING, direction));
                Thread.sleep(200);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (!(arg instanceof SimulationEvent)) {
            return;
        }

        SimulationEvent simulationEvent = (SimulationEvent) arg;
        switch (simulationEvent.eventType) {
            case START_SIMULATION:
                new Thread(this).start();
                break;

            case STOP_SIMULATION:
                break;
        }
    }
}
