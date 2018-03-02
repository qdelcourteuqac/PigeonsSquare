package main.java.pigeonssquare.model.grid.event;

import main.java.pigeonssquare.model.pigeon.Pigeon;

public class PigeonEvent {

    public Pigeon instance;
    public PigeonEventType eventType;
    public Direction direction;

    public PigeonEvent(Pigeon instance, PigeonEventType event, Direction direction) {
        this.instance = instance;
        this.eventType = event;
        this.direction = direction;
    }

    public enum PigeonEventType {
        MOVING;
    }
}
