package pigeonssquare;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pigeonssquare.model.CollaredDove;
import pigeonssquare.model.Pigeon;
import pigeonssquare.model.RockDove;
import pigeonssquare.model.StockDove;

public class Main extends Application {

    public void start(Stage primaryStage) throws Exception {
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("view/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        StockDove stockdove = new StockDove();
        RockDove rockDove = new RockDove();
        CollaredDove collaredDove = new CollaredDove();

        Thread t1 = new Thread(stockdove);
        Thread t2 = new Thread(rockDove);
        Thread t3 = new Thread(collaredDove);

        t1.start();
        t2.start();
        t3.start();

        launch(args);
    }
}