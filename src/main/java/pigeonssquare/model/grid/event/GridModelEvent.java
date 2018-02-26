package main.java.pigeonssquare.model.grid.event;

public class GridModelEvent {

    public int column;
    public int row;
    public EventType eventType;

    public GridModelEvent(EventType event, int row, int column) {
        this.eventType = event;
        this.column = column;
        this.row = row;
    }

    public GridModelEvent(EventType event) {
        this.eventType = event;
    }

    @Override
    public String toString() {
        return this.eventType + " ligne : " + this.row + " colonne : " + this.column;
    }

    public enum EventType {
        START_EVENT, SOLVED_EVENT;
    }
}
