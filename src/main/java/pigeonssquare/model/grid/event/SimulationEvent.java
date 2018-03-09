package main.java.pigeonssquare.model.grid.event;

public class SimulationEvent extends Event {

    public SimulationEventType eventType;

    public SimulationEvent(SimulationEventType event) {
        this.eventType = event;
    }

    public enum SimulationEventType {
        START_SIMULATION, STOP_SIMULATION;
    }
}
