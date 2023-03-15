package com.github.niipi.ohii_harjoitustyo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Main program draws the GUI and contains event handlers. Event handling may require a separate class when the project grows.
 * @author Niina Piiroinen
 **/
public class Main extends Application {

    private TextField[] fields = new TextField[3];
    private FlowPane addedPlants = new FlowPane();

    /**
     * Checks if the ArrayList of houseplant objects in PlantData module has been populated from a previous save file.
     * If ArrayList is populated, the houseplants are added to the addedPlants FlowPane to be displayed.
     **/
    private void plantsFromFile() {
        // PlantData.readFromFile is called to look for a save file and populate the ArrayList if file exists.
        try {
            PlantData.readFromFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Houseplant objects are read from ArrayList and returned as Text elements in the FlowPane.
        for (int i = 0; i < PlantData.plants.size(); i++) {
            Houseplant h = PlantData.plants.get(i);
            Text plantDesc = new Text(h.toString());
            addedPlants.getChildren().add(plantDesc);
        }
    }

    /**
     * Creates a FlowPane object containing textfields for adding new houseplant objects.
     * @return FlowPane
     **/
    private FlowPane newPlantPane() {
        addedPlants.setOrientation(Orientation.VERTICAL);
        Label[] labels = {new Label("Lajike:"), new Label("Vedentarve litroina:"), new Label("Päiviä kasteluiden välillä:")};
        FlowPane newPlantInfo = new FlowPane();
        newPlantInfo.setOrientation(Orientation.HORIZONTAL);
        for (int i = 0; i < 3; i++) {
            Label lb = labels[i];
            TextField tf = new TextField();
            fields[i] = tf;
            if (i == 0) {
                lb.setPadding(new Insets(0, 5, 0, 0));
                tf.setPrefWidth(120);
            }
            else {
                lb.setPadding(new Insets(0, 5, 0, 5));
                tf.setPrefWidth(50);
            }
            newPlantInfo.getChildren().addAll(lb, tf);
        }
        Button btn = new Button("Lisää");
        btn.setOnAction(e -> addPlant());
        newPlantInfo.getChildren().add(btn);
        return newPlantInfo;
    }

    /**
     * EventHandler function for the button used to add new houseplants. Constructs a new Houseplant object based on user input, which is read from TextFields.
     **/
    public void addPlant() {
        String n = "";
        double l = 0.0;
        int d = 0;
        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                n = fields[i].getText();
            }
            else if (i == 1) {
                l = Double.parseDouble(fields[i].getText());
            }
            else {
                d = Integer.parseInt(fields[i].getText());
            }
        }
        // Plant information is shown as Text. This functionality is subject to change as project progresses.
        Houseplant h = new Houseplant(n, l, d);
        PlantData.plants.add(h);
        Text plantDesc = new Text(h.toString());
        addedPlants.getChildren().add(plantDesc);
    }

    /**
     * Creates a button for writing houseplant objects into a save file.
     * @return Button
     **/
    public Button saveButton() {
        Button save = new Button("Tallenna");
        save.setOnAction(e -> {
            try {
                PlantData.saveToFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        return save;
    }

    @Override
    public void start(Stage stage) throws IOException {
        plantsFromFile();
        BorderPane panel = new BorderPane();
        //VBox plants = new VBox(addedPlants, newPlantPane());
       // panel.setTop(plants);
        panel.setCenter(WateringCalendar.calView());
        panel.setRight(saveButton());
        Scene scene = new Scene(panel);
        stage.setTitle("Huonekasvien kastelun aikatauluttaja");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}