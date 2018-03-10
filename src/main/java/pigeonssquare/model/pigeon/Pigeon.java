package main.java.pigeonssquare.model.pigeon;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.event.*;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;


public abstract class Pigeon implements Observer, Runnable, Cellulable {
    protected EventManager eventManager;

    protected Integer rowTarget;
    protected Integer columnTarget;

    public Pigeon() {
        this.eventManager = EventManager.getInstance();
        this.eventManager.addObserver(this);
        this.eventManager.subscribe(this, SimulationEvent.class);
        this.eventManager.subscribe(this, PlaceEvent.class);
    }

    @Override
    public void run() {
        while(true) {
            // Check if target has been summoned and do something
            this.onTargetSummoned();

            this.doRandomMoves();
        }
    }

    protected void onTargetSummoned() {
        if (this.rowTarget != null && this.columnTarget != null) {
            //System.out.println("New Food target at "+this.rowTarget+";"+this.columnTarget);

            // get path between pigeon and target then move
        }
    }

    protected void doRandomMoves() {
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
            this.eventManager.notify(new PigeonEvent(this, PigeonEvent.PigeonEventType.MOVING, direction));
            Thread.sleep(200);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof SimulationEvent) {
            SimulationEvent simulationEvent = (SimulationEvent) arg;

            switch (simulationEvent.eventType) {
                case START_SIMULATION:
                    new Thread(this).start();
                    break;

                case STOP_SIMULATION:
                    break;
            }
        } else if (arg instanceof PlaceEvent) {
            PlaceEvent placeEvent = (PlaceEvent) arg;

            switch (placeEvent.eventType) {
                case FOOD_SUMMONED:
                    this.rowTarget = placeEvent.row;
                    this.columnTarget = placeEvent.column;
                    break;
                case ROCK_SUMMONED:
                    //Avoid rock
                    break;
            }
        }
    }
}
