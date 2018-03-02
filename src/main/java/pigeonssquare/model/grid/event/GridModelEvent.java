package main.java.pigeonssquare.model.grid.event;

import main.java.pigeonssquare.model.grid.cell.Cellulable;

public class GridModelEvent {

    public int column;
    public int row;
    public EventType eventType;
    public Cellulable instance;

    public GridModelEvent(EventType event) {
        this.eventType = event;
    }

    public GridModelEvent(EventType event, Cellulable instance, int row, int column) {
        this.eventType = event;
        this.instance = instance;
        this.row = row;
        this.column = column;
    }

    @Override
    public String toString() {
        return this.eventType + " ligne : " + this.row + " colonne : " + this.column;
    }

    public enum EventType {
        START_EVENT, UPDATE_CELL_VIEW_EVENT;
    }
}
