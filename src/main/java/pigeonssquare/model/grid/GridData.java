package main.java.pigeonssquare.model.grid;

import main.java.pigeonssquare.model.grid.cell.Cell;
import main.java.pigeonssquare.model.grid.cell.Cellulable;

/**
 * Données du plateau de jeu
 */
public class GridData {

    private int columnCount;
    private int rowCount;
    private Cell[][] cells;

    /**
     * Constructeur
     *
     * @param rowCount    nombre de lignes
     * @param columnCount nombre de colonnes
     */
    public GridData(int rowCount, int columnCount)

    {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.cells = new Cell[rowCount][columnCount];
    }

    /**
     * Retourne le nombre de colonnes du plateau de jeu
     *
     * @return nombre de colonnes
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Retourne le nombre de lignes du plateau de jeu
     *
     * @return nombre de lignes
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Retourne une cellule du plateau de jeu
     *
     * @param row    ligne de la cellule
     * @param column colonne de la cellule
     * @return cellule
     */
    public Cell getCell(int row, int column) {
        return cells[row][column];
    }

    /**
     * Initialise une cellule du plateau de jeu.
     *
     * @param row    ligne de la cellule
     * @param column colonne de la cellule
     * @param value  valeur de la cellule
     */
    public void initCell(int row, int column, Cellulable value) {
        this.cells[row][column] = new Cell(value);
    }


    public synchronized void moveCell(Cellulable cell, int finalRow, int finalColumn) {
        int row;
        int column;
        for (row = 0; row < this.getRowCount(); row++) {
            for (column = 0; column < this.getColumnCount(); column++) {
                if (this.getCell(row, column) == cell) {
                    break;
                }
            }
        }
    }
}