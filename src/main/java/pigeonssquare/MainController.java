package main.java.pigeonssquare;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import main.java.pigeonssquare.model.grid.GridModel;
import main.java.pigeonssquare.model.grid.GridView;
import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.model.grid.event.EventManager;
import main.java.pigeonssquare.model.grid.event.GridModelEvent;
import main.java.pigeonssquare.model.grid.event.SimulationEvent;

public class MainController {

    @FXML
    public AnchorPane control;

    @FXML
    public AnchorPane grid;

    @FXML
    public AnchorPane details;

    @FXML
    public ToggleButton actionSimulation;

    private GridView gridView;
    private EventManager eventManager;

    /**
     * Méthode invoquée automatiquement aprés la création de l'interface graphique
     * associée à ce controleur
     */
    public void initialize() {
        this.eventManager = EventManager.getInstance();

        this.gridView = new GridView();
        this.grid.getChildren().add(this.gridView);

        GridModel gridModel = new GridModel();
        this.gridView.setModel(gridModel);

        gridModel.startGame();

        this.actionSimulation.setOnAction(event -> {
            if (actionSimulation.isSelected()) {
                this.onStartSimulation();
            } else {
                this.onStopSimulation();
            }
        });

    }

    public void onStartSimulation() {
        System.out.println("Start simulation");
        this.eventManager.setChanged();
        this.eventManager.notifyObservers(new SimulationEvent(SimulationEvent.SimulationEventType.START_SIMULATION));
    }

    private void onStopSimulation() {
        System.out.println("Stop simulation");
        this.eventManager.setChanged();
        this.eventManager.notifyObservers(new SimulationEvent(SimulationEvent.SimulationEventType.STOP_SIMULATION));
    }

}
