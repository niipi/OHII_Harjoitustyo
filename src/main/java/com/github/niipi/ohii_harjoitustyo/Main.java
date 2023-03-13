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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main program draws the GUI and contains event handlers. Event handling may require a separate class
 * when the project grows.
 * @author Niina Piiroinen
 **/
public class Main extends Application {

    private TextField[] fields = new TextField[3];
    private FlowPane addedPlants = new FlowPane();

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
        Text plantDesc = new Text(h.toString());
        addedPlants.getChildren().add(plantDesc);
    }

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane panel = new BorderPane();
        VBox plants = new VBox(addedPlants, newPlantPane());
        panel.setTop(plants);
        Scene scene = new Scene(panel, 800, 500);
        stage.setTitle("Huonekasvien kastelun aikatauluttaja");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}