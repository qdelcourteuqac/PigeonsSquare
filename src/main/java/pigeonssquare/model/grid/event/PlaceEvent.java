package main.java.pigeonssquare.model.grid.event;


import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.pigeon.Food;
import main.java.pigeonssquare.model.pigeon.Rock;

public class PlaceEvent extends Event {

    public Class cellulable;
    public PlaceEvent.PlaceEventType eventType;

    public int row;
    public int column;

    public PlaceEvent(Class<? extends Cellulable> cellulable, int row, int column) {
        this.cellulable = cellulable;
        this.row = row;
        this.column = column;

        if (cellulable == Food.class) {
            this.eventType = PlaceEventType.FOOD_SUMMONED;
        } else if (cellulable == Rock.class) {
            this.eventType = PlaceEventType.ROCK_SUMMONED;
        }
    }

    public enum PlaceEventType {
        FOOD_SUMMONED, ROCK_SUMMONED
    }
}
