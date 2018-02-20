package pigeonssquare.model;

public class Food {
    private static int SIZE = 1;
    // Temps en ms jusqu'auquel la nourriture n'est plus fraiche
    private static int DURABILITY = 5000;

    private boolean fresh;

    public Food() {
        this.fresh = true;
    }

    public void setFresh(boolean isFresh) {
        this.fresh = isFresh;
    }

    public boolean isFresh() {
        return this.fresh;
    }
}
