package main.java.pigeonssquare.model.grid.event;

public class PigeonEvent {

    public PigeonEventType eventType;
    public Direction direction;

    public PigeonEvent(PigeonEventType event, Direction direction) {
        this.eventType = event;
        this.direction = direction;
    }

    public enum PigeonEventType {
        MOOVING;
    }
}
