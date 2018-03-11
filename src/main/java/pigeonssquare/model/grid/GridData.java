package main.java.pigeonssquare.model.grid;

import main.java.pigeonssquare.model.grid.cell.Cell;
import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.cellulable.Ground;
import main.java.pigeonssquare.event.Direction;
import main.java.pigeonssquare.event.EventManager;
import main.java.pigeonssquare.event.GridModelEvent;
import main.java.pigeonssquare.model.grid.factory.CellulableFactory;
import main.java.pigeonssquare.model.cellulable.Food;
import main.java.pigeonssquare.model.cellulable.Pigeon;
import main.java.pigeonssquare.model.cellulable.Rock;

import java.util.ArrayList;
import java.util.List;

/**
 * Données du plateau de jeu
 */
public class GridData {

    public static GridData instance;

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

    public static GridData getInstance() {
        return instance;
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
        try {
            return cells[row][column];
        } catch (ArrayIndexOutOfBoundsException $e) {
            return null;
        }
    }

    /**
     * Retourne les coordonnées d'une cellule
     *
     * @param cellulable cellule
     * @return
     */
    public int[] getCoordinate(Cellulable cellulable) {

        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                if (this.getCell(row, column).getValue() == cellulable) {
                    return new int[]{row, column};
                }
            }
        }
        return null;
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
     * Détruire une cellule et la remplace par du Ground
     *
     * @param row    ligne de la cellule
     * @param column colonne de la cellule
     * @param value  valeur de la cellule
     */
    public void destroyCell(int row, int column, Cellulable value) {
        Cellulable ground = CellulableFactory.getInstanceOf(Ground.class);
        this.initCell(row, column, ground);

        this.eventManager.notify(new GridModelEvent(GridModelEvent.EventType.UPDATE_CELL_VIEW_EVENT, ground, row, column));
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
            System.out.println("Summon " + action.getSimpleName() + " at " + row + ";" + column);
            Cellulable cellulable = CellulableFactory.getInstanceOf(action);
            this.initCell(row, column, cellulable);

            if (cellulable instanceof Food) {
                ((Food) cellulable).getCurrentThread().start();
            } else if (cellulable instanceof Rock) {
                ((Rock) cellulable).getCurrentThread().start();
            }

            this.eventManager.notify(new GridModelEvent(GridModelEvent.EventType.UPDATE_CELL_VIEW_EVENT, cellulable, row, column));
        }
    }

    /**
     * Déplacer la cellule suivant une direction.
     *
     * @param cell      cellule à déplacer
     * @param direction direction
     */
    public synchronized void moveCell(Cellulable cell, Direction direction) {
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
            return;
        }

        // determine new coord row and column with direction
        int newRow = cellRow;
        int newColumn = cellColumn;
        switch (direction) {
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
            return;
        }

        // collisions
        Cell nextCell = this.getCell(newRow, newColumn);
        if (nextCell.getValue().getClass() == Food.class && ((Food) nextCell.getValue()).isFresh()) {
            // eat food
            Pigeon pigeon = (Pigeon) cell;
            pigeon.updateScore();
        } else if (!nextCell.getValue().getClass().equals(Ground.class)) {
            return;
        }

        // do move and replace by ground former cell
        this.initCell(newRow, newColumn, cell);
        Cellulable former = new Ground();
        this.initCell(cellRow, cellColumn, former);
        this.eventManager.notify(new GridModelEvent(GridModelEvent.EventType.UPDATE_CELL_VIEW_EVENT, former, cellRow, cellColumn));
        this.eventManager.notify(new GridModelEvent(GridModelEvent.EventType.UPDATE_CELL_VIEW_EVENT, cell, newRow, newColumn));
    }

    /**
     * Retourne la liste des cellules étant du type cellType
     *
     * @param cellType Type de la cellule
     * @return
     */
    public List<Cell> getCells(Class<? extends Cellulable> cellType) {
        List<Cell> cells = new ArrayList<>();
        for (int row = 0; row < this.getRowCount(); row++) {
            for (int column = 0; column < this.getColumnCount(); column++) {
                Cell cell = this.getCell(row, column);
                if (cell.getValue().getClass() == cellType) {
                    cells.add(cell);
                }
            }
        }
        return cells;
    }

    /**
     * Retourne la distance entre deux cellules
     *
     * @param from cellule de départ
     * @param to   cellule d'arrivée
     * @return
     */
    public Double getDistance(Cellulable from, Cellulable to) {
        int[] fromCoordinate = this.getCoordinate(from);
        int[] toCoordinate = this.getCoordinate(to);

        if (fromCoordinate == null || toCoordinate == null) {
            return null;
        }
        return Math.sqrt(Math.pow((fromCoordinate[0] - toCoordinate[0]), 2) + Math.pow((fromCoordinate[1] - toCoordinate[1]), 2));
    }
}