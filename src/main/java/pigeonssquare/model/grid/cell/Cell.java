package main.java.pigeonssquare.model.grid.cell;

public class Cell {

    private Cellulable value;
    private boolean active = true;
    private boolean locked;

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

    /**
     * Accesseur sur active.
     *
     * @return la valeur de active
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Accesseur sur locked.
     *
     * @return true la valeur de locked
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * Inverse l'état d'activation de la cellule. Si la cellule est verrouillée, l'état ne sera pas modifié.
     *
     * @return vrai si l'état a été modifié, faux sinon
     */
    public boolean toggleActiveState() {
        if (!this.locked) {
            this.active = !this.active;
        }
        return !this.locked;
    }

    /**
     * Inverse l'état de verrouillage de la cellule. Si la cellule est désactivée, l'état ne sera pas modifié.
     *
     * @return vrai si l'état a été modifié, faux sinon
     */
    public boolean toggleLockedState() {
        if (this.active) {
            this.locked = !this.locked;
        }
        return this.active;
    }
}
