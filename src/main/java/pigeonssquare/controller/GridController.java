package main.java.pigeonssquare.controller;

import javafx.scene.input.MouseEvent;
import main.java.pigeonssquare.model.grid.GridView;
import main.java.pigeonssquare.model.grid.cell.Cellulable;

public class GridController {

    private GridView gridView;

    private Class<? extends Cellulable> action;

    public GridController(GridView boardView) {
        this.gridView = boardView;
    }

    public void setAction(Class<? extends Cellulable> action) {
        this.action = action;
    }

    /**
     * Méthode invoquée en réponse à un clic sur une cellule
     *
     * @param event  événement de souris qui a déclenché l'invocation
     * @param row    ligne de la cellule cliquée
     * @param column colonne de la cellule cliquée
     */
    public void onCellClicked(MouseEvent event, int row, int column) {

        if (this.action != null) {
            this.gridView.getModel().place(action, row, column);
        }
    }

}

