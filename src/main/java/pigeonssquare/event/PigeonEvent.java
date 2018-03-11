package main.java.pigeonssquare.event;

import main.java.pigeonssquare.model.cellulable.Pigeon;

public class PigeonEvent extends Event {

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
