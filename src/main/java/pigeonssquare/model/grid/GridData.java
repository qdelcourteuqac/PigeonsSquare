package main.java.pigeonssquare.model.grid;

import main.java.pigeonssquare.model.grid.cell.Cell;
import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.cell.Ground;
import main.java.pigeonssquare.model.grid.event.Direction;
import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.grid.event.GridModelEvent;
import main.java.pigeonssquare.model.grid.factory.CellulableFactory;
import main.java.pigeonssquare.model.pigeon.Pigeon;

/**
 * Données du plateau de jeu
 */
public class GridData {

    private final EventManager eventManager;
    private int columnCount;
    private int rowCount;
    private Cell[][] cells;

    /**
     * Constructeur
     *
     * @param rowCount    nombre de lignes
     * @param columnCount nombre de colonnes
     */
    public GridData(int rowCount, int columnCount) {
        this.eventManager = EventManager.getInstance();
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

    /**
     * Place un objet Cellulable sur le plateau de jeu
     *
     * @param action type de la cellule
     * @param row    ligne de la cellule
     * @param column colonne de la cellule
     */
    public void place(Class<? extends Cellulable> action, int row, int column) {
        Cell cell = this.getCell(row, column);
        if (cell.getValue().getClass() == Ground.class) {
            System.out.println("Summon "+ action + " at "+row+";"+column);
            Cellulable cellulable = CellulableFactory.getInstanceOf(action);
            this.initCell(row, column, cellulable);
            this.eventManager.notify(new GridModelEvent(GridModelEvent.EventType.UPDATE_CELL_VIEW_EVENT, cellulable, row, column));
        }
    }

    /**
     * Déplacer la cellule suivant une direction.
     *
     * @param cell      cellule à déplacer
     * @param direction direction
     * @throws Exception
     */
    public synchronized void moveCell(Cellulable cell, Direction direction) throws Exception {
        // check existence of cell in grid
        int cellRow = -1;
        int cellColumn = -1;
        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                if (this.getCell(row, column).getValue() == cell) {
                    cellRow = row;
                    cellColumn = column;
                    break;
                }
            }
        }

        if (cellRow == -1) {
            //throw new Exception("Cell does not exist in grid");
            return;
        }

        // determine new coord row and column with direction
        int newRow = cellRow;
        int newColumn = cellColumn;
        switch(direction) {
            case NORTH:
                newRow -= 1;
                break;
            case SOUTH:
                newRow += 1;
                break;
            case EAST:
                newColumn -= 1;
                break;
            case WEST:
                newColumn += 1;
                break;
        }

        // check: out of grid
        if (newRow < 0 || newRow >= this.getRowCount() || newColumn < 0 || newColumn >= this.getColumnCount()) {
            //throw new Exception("Impossible move : Out of grid !");
            return;
        }

        // collisions
        // TODO: collisions with food !
        Cell nextCell = this.getCell(newRow, newColumn);
        if (!nextCell.getValue().getClass().equals(Ground.class)) {
            //throw new Exception("Collision with another object which stand already there");
            return;
        }

        // do move and replace by ground former cell
        this.initCell(newRow, newColumn, cell);
        Cellulable former = new Ground();
        this.initCell(cellRow, cellColumn, former);
        this.eventManager.notify(new GridModelEvent(GridModelEvent.EventType.UPDATE_CELL_VIEW_EVENT, former, cellRow, cellColumn));
        this.eventManager.notify(new GridModelEvent(GridModelEvent.EventType.UPDATE_CELL_VIEW_EVENT, cell, newRow, newColumn));
    }
}