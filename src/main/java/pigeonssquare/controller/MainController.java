package main.java.pigeonssquare.controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import main.java.pigeonssquare.model.grid.GridModel;
import main.java.pigeonssquare.model.grid.GridView;
import main.java.pigeonssquare.model.grid.cell.Cellulable;
import main.java.pigeonssquare.event.EventManager;
import main.java.pigeonssquare.event.SimulationEvent;
import main.java.pigeonssquare.model.cellulable.Food;
import main.java.pigeonssquare.model.cellulable.Rock;

public class MainController {

    @FXML
    public AnchorPane control;

    @FXML
    public AnchorPane grid;

    @FXML
    public AnchorPane details;

    @FXML
    public ToggleButton actionSimulation;

    @FXML
    public RadioButton none;

    @FXML
    public RadioButton food;

    @FXML
    public RadioButton rock;

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
                actionSimulation.setText("Arrêter");
                this.onStartSimulation();
            } else {
                actionSimulation.setText("Démarrer");
                this.onStopSimulation();
            }
        });

        ToggleGroup actions = new ToggleGroup();
        this.none.setToggleGroup(actions);
        this.food.setToggleGroup(actions);
        this.rock.setToggleGroup(actions);

        actions.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            Class<? extends Cellulable> action;
            switch (((RadioButton) newValue).getId()) {
                case "rock":
                    action = Rock.class;
                    break;
                case "food":
                    action = Food.class;
                    break;
                default:
                    action = null;
            }

            gridView.getController().setAction(action);
        });
    }


    private void onStartSimulation() {
        System.out.println("Start simulation");
        this.eventManager.notify(new SimulationEvent(SimulationEvent.SimulationEventType.START_SIMULATION));
    }

    private void onStopSimulation() {
        System.out.println("Stop simulation");
        this.eventManager.notify(new SimulationEvent(SimulationEvent.SimulationEventType.STOP_SIMULATION));
    }

}
