package main.java.pigeonssquare.model.cellulable;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.event.EventManager;
import main.java.pigeonssquare.event.SimulationEvent;

import java.util.Observable;
import java.util.Observer;

public class Food implements Cellulable, Runnable, Observer {
    // Temps en ms jusqu'auquel la nourriture n'est plus fraiche
    private static int DURABILITY = 5000;
    // Temps en ms jusqu'auquel la nourriture non fraiche disparait du plateau de jeu
    private static int UNTIL_DISPAWN = 1000;

    private boolean fresh;

    private EventManager eventManager;
    private Thread currentThread;

    public Food() {
        this.currentThread = new Thread(this);

        this.fresh = true;

        this.eventManager = EventManager.getInstance();
        this.eventManager.addObserver(this);
        this.eventManager.subscribe(this, SimulationEvent.class);
    }

    public boolean isFresh() {
        return fresh;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Wait DURABILITY
                Thread.sleep(Food.DURABILITY);
                // Food is now not fresh
                this.fresh = false;
                // Wait until DISPAWN
                Thread.sleep(Food.UNTIL_DISPAWN);
                Thread.currentThread().interrupt();
                this.currentThread.interrupt();
                int[] foodCoordinate = this.getEnvironment().getCoordinate(this);
                if (foodCoordinate != null) {
                    this.getEnvironment().destroyCell(foodCoordinate[0], foodCoordinate[1], this);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    public Thread getCurrentThread() {
        return currentThread;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof SimulationEvent)) {
            return;
        }

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
