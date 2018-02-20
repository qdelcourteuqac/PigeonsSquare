package pigeonssquare.model;

/**
 * Un colombin
 */
public class StockDove extends Pigeon {
    protected static int SIZE = 1;
    protected static int SPEED = 1;

    public void run() {
        System.out.println("StockDove");
    }
}
