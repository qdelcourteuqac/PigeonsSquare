package main.java.pigeonssquare.model.pigeon;

import main.java.pigeonssquare.model.grid.cell.Cell;
import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.event.Direction;
import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.grid.event.PigeonEvent;
import main.java.pigeonssquare.model.grid.event.SimulationEvent;

import java.util.*;


public abstract class Pigeon implements Observer, Runnable, Cellulable {

    protected Thread currentThread;

    protected EventManager eventManager;

    protected int score;

    public Pigeon() {
        this.currentThread = new Thread(this);

        this.eventManager = EventManager.getInstance();
        this.eventManager.addObserver(this);
        this.eventManager.subscribe(this, SimulationEvent.class);

        this.score = 0;
    }

    public void updateScore() {
        this.score += 1;
    }

    public int getScore() {
        return this.score;
    }

    @Override
    public void run() {
        try {

            while (!Thread.currentThread().isInterrupted()) {

                this.move();

                Thread.sleep(200);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    /**
     * Moving strategy
     */
    protected void move() {
        List<Cell> foodCells = this.getEnvironment().getCells(Food.class);
        if (!foodCells.isEmpty()) {
            this.goEating(foodCells);
        } else {
            this.doRandomMoves();
        }
    }

    private void goEating(List<Cell> foodCells) {
        TreeMap<Double, Cell> distances = new TreeMap<>();

        foodCells.forEach((Cell foodCell) -> {
            try {
                double distance = this.getEnvironment().getDistance(this, foodCell.getValue());
                distances.put(distance, foodCell);
            } catch (IllegalStateException e){ }
        });


        if (!distances.isEmpty()) {
            Cell nearFood = distances.firstEntry().getValue();

            int[] nearFoodCoordinate = this.getEnvironment().getCoordinate(nearFood.getValue());
            int[] ourCoordinate = this.getEnvironment().getCoordinate(this);

            int[] vectorDirection = new int[]{
                    nearFoodCoordinate[0] - ourCoordinate[0],
                    nearFoodCoordinate[1] - ourCoordinate[1],
            };

//            System.out.println(vectorDirection[0] + " " + vectorDirection[1]);
            Direction direction;
            if (vectorDirection[0] > vectorDirection[1]) {
                if (vectorDirection[0] > 0) {
                    direction = Direction.SOUTH;
                } else {
                    direction = Direction.EAST;
                }
            } else {
                if (vectorDirection[1] > 0) {
                    direction = Direction.WEST;
                } else {
                    direction = Direction.NORTH;
                }
            }
            this.eventManager.notify(new PigeonEvent(this, PigeonEvent.PigeonEventType.MOVING, direction));
        }
    }

    protected void doRandomMoves() {
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
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (arg instanceof SimulationEvent) {
            SimulationEvent simulationEvent = (SimulationEvent) arg;

            switch (simulationEvent.eventType) {
                case START_SIMULATION:
                    if (!this.currentThread.isAlive()) {
                        this.currentThread = new Thread(this);
                        this.currentThread.start();
                    }
                    break;

                case STOP_SIMULATION:
                    if (this.currentThread != null) {
                        this.currentThread.interrupt();
                    }
                    break;
            }
        }
    }
}
