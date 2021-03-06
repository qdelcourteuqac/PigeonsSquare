package main.java.pigeonssquare.model.grid;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import main.java.pigeonssquare.controller.GridController;
import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.event.EventManager;
import main.java.pigeonssquare.event.GridModelEvent;
import main.java.pigeonssquare.model.cellulable.Pigeon;

import java.util.Observable;
import java.util.Observer;

public class GridView extends GridPane implements Observer {

    private GridController controller = new GridController(this);

    private GridModel model;
    private CellView[][] cellViews;
    private EventManager eventManager;

    public GridView() {
        this.eventManager = EventManager.getInstance();
        this.eventManager.subscribe(this, GridModelEvent.class);
    }

    /**
     * Accesseur sur le GridController
     *
     * @return le GridController
     */
    public GridController getController() {
        return controller;
    }

    /**
     * Accesseur sur le GridModel
     *
     * @return le GridModel
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
    public synchronized void update(Observable o, Object arg) {
        if (!(arg instanceof GridModelEvent)) {
            return;
        }

        GridModelEvent event = (GridModelEvent) arg;
        switch (event.eventType) {
            case START_EVENT:
                this.init();
                break;
            case UPDATE_CELL_VIEW_EVENT:
                this.cellViews[event.row][event.column].updateView(event.instance);
                break;
        }
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

            Cellulable cellulable = GridView.this.model.getValue(row, column);

            // We must have a css class by class name
            this.updateView(cellulable);
        }

        /**
         * Met à jour la vue de la cellule
         *
         * @param instance cellule à mettre à jour
         */
        public void updateView(Cellulable instance) {
            Platform.runLater(() -> {
                if (instance instanceof Pigeon) {
                    this.setText(((Pigeon) instance).getScore() + "");
                } else {
                    this.setText("");
                }

                this.getStyleClass().clear();
                this.getStyleClass().add("Cell");
                this.getStyleClass().add(instance.getClass().getSimpleName());
            });
        }
    }
}