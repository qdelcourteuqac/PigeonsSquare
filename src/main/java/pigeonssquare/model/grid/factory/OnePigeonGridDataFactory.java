package main.java.pigeonssquare.model.grid.factory;

import main.java.pigeonssquare.model.grid.GridData;
import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.cellulable.Ground;
import main.java.pigeonssquare.model.cellulable.CollaredDove;
import main.java.pigeonssquare.model.cellulable.RockDove;
import main.java.pigeonssquare.model.cellulable.StockDove;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class OnePigeonGridDataFactory implements GridDataFactory {

    private static int ROW_COUNT = 30;
    private static int COLUMN_COUNT = 30;
    private static int CELLS_COUNT = ROW_COUNT * COLUMN_COUNT;

    @Override
    public GridData createBoardData() {
        final long seed = 19960604;

        Random randomGenerator = new Random();
        randomGenerator.setSeed(seed);

        GridData gridData = new GridData(ROW_COUNT, COLUMN_COUNT);
        GridData.instance = gridData;

        // The order should be consistent because using seeds
        Map<Class<? extends Cellulable>, Integer> cellulableProbabilities = new LinkedHashMap<>();

        cellulableProbabilities.put(CollaredDove.class, 3);
        cellulableProbabilities.put(RockDove.class, 3);
        cellulableProbabilities.put(StockDove.class, 3);
        cellulableProbabilities.put(Ground.class, 91);

        for (int row = 0; row < ROW_COUNT; row++) {
            for (int column = 0; column < COLUMN_COUNT; column++) {
                gridData.initCell(row, column, new Ground());
            }
        }


        gridData.initCell(15, 15, CellulableFactory.getInstanceOf(CollaredDove.class));

        return gridData;
    }
}
