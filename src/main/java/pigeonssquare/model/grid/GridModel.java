package main.java.pigeonssquare.model.grid;

import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.event.Direction;
import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.grid.event.GridModelEvent;
import main.java.pigeonssquare.model.grid.event.PigeonEvent;
import main.java.pigeonssquare.model.grid.factory.DefaultGridDataFactory;
import main.java.pigeonssquare.model.grid.factory.GridDataFactory;
import main.java.pigeonssquare.model.pigeon.Pigeon;

import java.util.Observable;
import java.util.Observer;

public class GridModel implements Observer{

    private EventManager eventManager;
    private GridData data;
    private GridDataFactory boardDataFactory;

    public GridModel() {
        eventManager = EventManager.getInstance();
        eventManager.addObserver(this);
        this.data = null;
        this.boardDataFactory = new DefaultGridDataFactory();
    }

    /**
     * Retourne le nombre de colonnes du plateau de jeu
     *
     * @return nombre de colonnes
     */
    public int getColumnCount() {
        return this.data.getColumnCount();
    }

    /**
     * Retourne le nombre de lignes du plateau de jeu
     *
     * @return nombre de ligne
     */
    public int getRowCount() {
        return this.data.getRowCount();
    }

    public Cellulable getValue(int row, int column) {
        return this.data.getCell(row, column).getValue();
    }

    /**
     * Démarre une nouvelle partie.
     */
    public void startGame() {
        this.data = boardDataFactory.createBoardData();
        this.eventManager.setChanged();
        this.eventManager.notifyObservers(new GridModelEvent(GridModelEvent.EventType.START_EVENT));
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (!(arg instanceof PigeonEvent)) {
            return;
        }

        PigeonEvent event = (PigeonEvent) arg;
        switch (event.eventType) {
            case MOVING:
                try {
                    //System.out.println(event.instance+ " ; "+data.getCell(data.getRowCount()-1,7).getValue());
                    data.moveCell(event.instance, event.direction);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void moveCell(Pigeon instance, Direction direction) {
        try {
            data.moveCell(instance, direction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
