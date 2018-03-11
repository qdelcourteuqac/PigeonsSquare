package main.java.pigeonssquare.model.cellulable;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.event.EventManager;
import main.java.pigeonssquare.event.SimulationEvent;

import java.util.Observable;
import java.util.Observer;

public class Rock implements Cellulable, Runnable, Observer {
    public static int RADIUS_AREA = 6;
    private static int TIME = RADIUS_AREA * 1000;

    private EventManager eventManager;
    private Thread currentThread;

    private boolean repulsive;

    public Rock() {
        this.currentThread = new Thread(this);

        this.repulsive = true;

        this.eventManager = EventManager.getInstance();
        this.eventManager.addObserver(this);
        this.eventManager.subscribe(this, SimulationEvent.class);
    }

    public boolean isRepulsive() {
        return repulsive;
    }

    public Thread getCurrentThread() {
        return this.currentThread;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(Rock.TIME);
                this.repulsive = false;
                Thread.currentThread().interrupt();
                this.currentThread.interrupt();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
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
