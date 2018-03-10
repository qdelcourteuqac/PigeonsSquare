package main.java.pigeonssquare.model.grid.factory;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.cell.Ground;
import main.java.pigeonssquare.model.pigeon.Food;
import main.java.pigeonssquare.model.pigeon.Rock;
import main.java.pigeonssquare.model.pigeon.type.CollaredDove;
import main.java.pigeonssquare.model.pigeon.type.RockDove;
import main.java.pigeonssquare.model.pigeon.type.StockDove;

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
