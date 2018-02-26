package main.java.pigeonssquare.model.pigeon;

import main.java.pigeonssquare.model.grid.cell.Cellulable;

public class Food implements Cellulable {
    private static int SIZE = 1;
    // Temps en ms jusqu'auquel la nourriture n'est plus fraiche
    private static int DURABILITY = 5000;

    private boolean fresh;

    public Food() {
        this.fresh = true;
    }

    public boolean isFresh() {
        return this.fresh;
    }

    public void setFresh(boolean isFresh) {
        this.fresh = isFresh;
    }
}
