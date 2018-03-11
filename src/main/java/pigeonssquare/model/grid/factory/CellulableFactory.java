package main.java.pigeonssquare.model.grid.factory;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.cellulable.Ground;
import main.java.pigeonssquare.model.cellulable.Food;
import main.java.pigeonssquare.model.cellulable.Rock;
import main.java.pigeonssquare.model.cellulable.CollaredDove;
import main.java.pigeonssquare.model.cellulable.RockDove;
import main.java.pigeonssquare.model.cellulable.StockDove;

public class CellulableFactory {

    public static Cellulable getInstanceOf(Class<? extends Cellulable> cellulable) {
        Cellulable instance;

        if (Food.class == cellulable) {
            instance = new Food();
        } else if (Rock.class == cellulable) {
            instance = new Rock();
        } else if (CollaredDove.class == cellulable) {
            instance = new CollaredDove();
        } else if (StockDove.class == cellulable) {
            instance = new StockDove();
        } else if (RockDove.class == cellulable) {
            instance = new RockDove();
        } else if (Ground.class == cellulable) {
            instance = new Ground();
        } else {
            throw new IllegalArgumentException("Invalid type");
        }

        return instance;
    }
}
