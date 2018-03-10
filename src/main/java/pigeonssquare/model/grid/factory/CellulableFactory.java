package main.java.pigeonssquare.model.grid.factory;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.pigeon.Food;
import main.java.pigeonssquare.model.pigeon.Rock;

public class CellulableFactory {

    public static Cellulable getInstanceOf(Class<? extends Cellulable> cellulable) {
        Cellulable instance;

        if (Food.class == cellulable) {
            instance = new Food();
        } else if (Rock.class == cellulable) {
            instance = new Rock();
        } else {
            throw new IllegalArgumentException("Invalid type");
        }

        return instance;
    }
}
