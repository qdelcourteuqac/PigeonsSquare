package main.java.pigeonssquare.model.grid;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.java.pigeonssquare.controller.GridController;
import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.grid.event.GridModelEvent;

import java.util.Observable;
import java.util.Observer;

public class GridView extends GridPane implements Observer {

    private GridController controller = new GridController(this);

    private GridModel model;
    private CellView[][] cellViews;
    private EventManager eventManager;

    public GridView() {
        this.eventManager = EventManager.getInstance();
    }

    public class CellView extends Label {

        final int row;
        final int column;

        /**
         * Constructeur
         *
         * @param row    ligne
         * @param column colonne
         */
        CellView(int row, int column) {
            super("");

            this.setOnMouseClicked(event -> GridView.this.controller.onCellClicked(event, row, column));
            this.row = row;
            this.column = column;
            this.updateActiveState();
            this.updateLockedState();

            Class cellulable = GridView.this.model.getValue(row, column).getClass();

            // We must have a css class by class name
            this.getStyleClass().add(cellulable.getSimpleName());
        }

        /**
         * Met à jour le style de la vue ("active" ou "inactive", en fonction de l'état du modèle)
         */
        void updateActiveState() {

        }

        /**
         * Met à jour le style de la vue ("locked" ou non, en fonction de l'état du modèle)
         */
        void updateLockedState() {
        }
    }

    /**
     * Accesseur sur le BoardModel
     *
     * @return le BoardModel
     */
    public GridModel getModel() {
        return this.model;
    }

    /**
     * Associe un modèle cette vue, qui devient observateur du modèle
     *
     * @param model modèle à associer
     */
    public void setModel(GridModel model) {
        this.model = model;
        this.eventManager.addObserver(this);
    }

    /**
     * Initialise le plateau de jeu en fonction du modèle
     */
    private void init() {
        this.cellViews = new CellView[this.model.getRowCount()][this.model.getColumnCount()];
        for (int row = 0; row < this.model.getRowCount(); row++) {
            for (int column = 0; column < this.model.getColumnCount(); column++) {
                CellView cellView = new CellView(row, column);
                this.cellViews[row][column] = cellView;
                this.add(cellView, column + 1, row + 1);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(arg instanceof GridModelEvent)) {
            return;
        }

        GridModelEvent event = (GridModelEvent) arg;
        switch (event.eventType) {

            case START_EVENT:
                this.init();
                break;
        }
    }
}