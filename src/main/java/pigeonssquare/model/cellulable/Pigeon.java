package main.java.pigeonssquare.model.cellulable;

import main.java.pigeonssquare.model.grid.cell.Cell;
import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.event.Direction;
import main.java.pigeonssquare.event.EventManager;
import main.java.pigeonssquare.event.PigeonEvent;
import main.java.pigeonssquare.event.SimulationEvent;

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

    /**
     * Met à jour le score: +1 à chaque fois qu'il touche de la nourriture
     */
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
    protected synchronized void move() {
        List<Direction> availableDirections = this.getAvailableDirection();

        // Direction à adopter si a proximité d'un rock
        Direction avoidRockDirection = this.avoidRock();
        // Direction à adopter pour aller vers la nourriture la plus proche
        Direction preferedDirection = this.goEating();
        // Direction finale choisi
        Direction directionChoosen;

        // Choix de la direction
        if (avoidRockDirection != null && availableDirections.contains(avoidRockDirection)) {
            directionChoosen = avoidRockDirection;
        } else if (availableDirections.contains(preferedDirection)) {
            directionChoosen = preferedDirection;
        } else {
            directionChoosen = this.doRandomMoves();
        }

        // Notifie les observers de la demande de déplacement du cellulable
        this.eventManager.notify(new PigeonEvent(this, PigeonEvent.PigeonEventType.MOVING, directionChoosen));
    }

    /**
     * Retourne les directions disponibles selon la position du cellulable sur le plateau
     *
     * @return
     */
    private List<Direction> getAvailableDirection() {
        List<Direction> availableDirections = new ArrayList<>(4);

        int[] ourCoordinate = this.getEnvironment().getCoordinate(this);

        if (ourCoordinate == null) {
            return availableDirections;
        }

        int row = ourCoordinate[0];
        int column = ourCoordinate[1];

        Cell north = this.getEnvironment().getCell(row - 1, column);
        Cell south = this.getEnvironment().getCell(row + 1, column);
        Cell east = this.getEnvironment().getCell(row, column + 1);
        Cell west = this.getEnvironment().getCell(row, column - 1);

        if (north != null && north.getValue().getClass() == Ground.class) {
            availableDirections.add(Direction.NORTH);
        }

        if (east != null && east.getValue().getClass() == Ground.class) {
            availableDirections.add(Direction.EAST);
        }

        if (south != null && south.getValue().getClass() == Ground.class) {
            availableDirections.add(Direction.SOUTH);
        }

        if (west != null && west.getValue().getClass() == Ground.class) {
            availableDirections.add(Direction.WEST);
        }

        return availableDirections;
    }

    /**
     * Retourne la direction à adopter si le cellulable se trouve dans le périmètre d'un rock (encore répulsif)
     *
     * @return Direction
     */
    private Direction avoidRock() {
        List<Cell> rockCells = this.getEnvironment().getCells(Rock.class);
        TreeMap<Double, Cell> distances = new TreeMap<>();

        Direction direction;

        rockCells.forEach((Cell rockCell) -> {
            Rock rock = (Rock) rockCell.getValue();
            if (rock.isRepulsive()) {
                Double distance = this.getEnvironment().getDistance(this, rock);
                if (distance != null && distance <= Rock.RADIUS_AREA) {
                    distances.put(distance, rockCell);
                }
            }
        });

        if (distances.isEmpty()) {
            return null;
        }

        Cell nearRock = distances.firstEntry().getValue();

        int[] nearRockCoordinate = this.getEnvironment().getCoordinate(nearRock.getValue());
        int[] ourCoordinate = this.getEnvironment().getCoordinate(this);

        int[] vectorDirection = new int[]{
                nearRockCoordinate[0] - ourCoordinate[0],
                nearRockCoordinate[1] - ourCoordinate[1],
        };

        if (vectorDirection[0] > vectorDirection[1]) {
            if (vectorDirection[0] > 0) {
                direction = Direction.NORTH;
            } else {
                direction = Direction.WEST;
            }
        } else {
            if (vectorDirection[1] > 0) {
                direction = Direction.EAST;
            } else {
                direction = Direction.SOUTH;
            }
        }

        return direction;
    }

    /**
     * Retourne la direction à adopter pour aller vers la nourriture la plus proche (encore fraiche)
     *
     * @return Direction
     */
    private Direction goEating() {

        List<Cell> foodCells = this.getEnvironment().getCells(Food.class);
        TreeMap<Double, Cell> distances = new TreeMap<>();

        foodCells.forEach((Cell foodCell) -> {
            try {
                Double distance = this.getEnvironment().getDistance(this, foodCell.getValue());
                if (distance != null) {
                    distances.put(distance, foodCell);
                }
            } catch (IllegalStateException e) {
            }
        });


        if (distances.isEmpty()) {
            return null;
        }

        Cell nearFood = distances.firstEntry().getValue();

        int[] nearFoodCoordinate = this.getEnvironment().getCoordinate(nearFood.getValue());
        int[] ourCoordinate = this.getEnvironment().getCoordinate(this);

        int[] vectorDirection = new int[]{
                nearFoodCoordinate[0] - ourCoordinate[0],
                nearFoodCoordinate[1] - ourCoordinate[1],
        };

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

        return direction;
    }

    /**
     * Retourne une direction aléatoire
     *
     * @return
     */
    protected Direction doRandomMoves() {
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
        return direction;
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
