package main.java.pigeonssquare.model.grid.cell;

public class Cell {

    private Cellulable value;

    /**
     * Constructeur de Cell
     *
     * @param value value de type int
     */
    public Cell(Cellulable value) {
        this.value = value;
    }

    /**
     * Accesseur sur value.
     *
     * @return la valeur de value
     */
    public Cellulable getValue() {
        return this.value;
    }
}
