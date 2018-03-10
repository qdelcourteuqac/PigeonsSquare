package main.java.pigeonssquare.model.grid.cell;

import main.java.pigeonssquare.model.grid.GridData;

public interface Cellulable {

    default GridData getEnvironment() {
        return GridData.getInstance();
    }
}
