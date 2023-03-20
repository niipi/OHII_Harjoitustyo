package com.github.niipi.ohii_harjoitustyo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Main program draws the GUI and contains event handlers. Event handling may require a separate class when the project grows.
 * @author Niina Piiroinen
 **/
public class Main extends Application {

    private TextField[] fields = new TextField[3];
    private PlantData datafile = new PlantData();
    private ArrayList<Houseplant> addedPlants = datafile.readFromFile();
    private WateringScheduler scheduler = new WateringScheduler(addedPlants);

    /**
     * Displays addedPlants.
     * @return Text
     **/
    private Text plantDescription() {
        String plantstring = "";
        for (Houseplant h : addedPlants) {
            plantstring.replace(plantstring, plantstring+h.toString()+"\n");
        }
        return new Text(plantstring);
    }

    /**
     * Creates a FlowPane object containing textfields for adding new houseplant objects.
     * @return FlowPane
     **/
    private FlowPane newPlantPane() {
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
        Houseplant h = new Houseplant(n, l, d);
        addedPlants.add(h);
    }

    /**
     * Creates a button for writing houseplant objects into a save file.
     * @return Button
     **/
    public Button saveButton() {
        Button save = new Button("Tallenna");
        save.setOnAction(e -> {
            try {
                datafile.saveToFile(addedPlants);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        return save;
    }

    /**
     * Draws a white cell in the calendar view.
     * @return Rectangle
     **/
    private Rectangle whiteRectangle() {
        Rectangle r = new Rectangle(100,100);
        r.setFill(Color.WHITE);
        r.setStroke(Color.BLACK);
        return r;
    }

    /**
     * Draws a gray cell in the calendar view.
     * @return Rectangle
     **/
    private Rectangle grayRectangle() {
        Rectangle r = new Rectangle(100,100);
        r.setFill(Color.GRAY);
        r.setStroke(Color.BLACK);
        return r;
    }

    /**
     * Draws a calendar view and creates an instance of the WateringCalendar class.
     * @return VBOX
     **/
    public VBox calendarView() {
        CalendarView calendarView = new CalendarView();
        VBox calpage = new VBox();
        GridPane cal = new GridPane();

        int empties = calendarView.howManyDaysOfWeekToFrameInCalendar(); // Returns the number of empty gray cells at the beginning of a calendar month
        int weeksTotal = calendarView.howManyWeeksInCurrentMonth();
        int dayNum = 1;
        for (int weeknum = 0; weeknum < weeksTotal; weeknum++) {
            for (int weekday = 0; weekday < 7; weekday++) {
                StackPane cell = new StackPane();
                cell.getChildren().add(whiteRectangle());
                if (empties > 0) {
                    cell.getChildren().add(grayRectangle());
                    empties--;
                }
                else if (weeknum == weeksTotal-1 && calendarView.isItTheLastDayOfThisMonth()) {
                    dayNum = 1;
                    cell.getChildren().add(grayRectangle());
                }
                else {
                    Text day = new Text(String.valueOf(dayNum));
                    calendarView.calendar.set(java.util.Calendar.DAY_OF_MONTH, dayNum);
                    cell.getChildren().add(day);
                    dayNum++;
                }
                cal.add(cell, weekday, weeknum);
            }
        }
        calpage.getChildren().addAll(calendarView.month, cal);
        return calpage;
    }

    @Override
    public void start(Stage stage) {
        BorderPane panel = new BorderPane();
        Text testing = new Text("Veden tarve viikossa: "+scheduler.waterPerWeek()+" litraa");
        VBox plants = new VBox(new Pane(plantDescription()), newPlantPane());
        panel.setTop(plants);
        panel.setCenter(calendarView());
        panel.setRight(saveButton());
        panel.setBottom(testing);
        Scene scene = new Scene(panel);
        stage.setTitle("Huonekasvien kastelun aikatauluttaja");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}