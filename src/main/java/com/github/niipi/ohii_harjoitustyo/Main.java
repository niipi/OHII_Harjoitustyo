package com.github.niipi.ohii_harjoitustyo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Main program draws the GUI and contains event handlers. Event handling may require a separate class when the project grows.
 * @author Niina Piiroinen
 **/
public class Main extends Application {

    private TextField[] fields = new TextField[3];
    private PlantData datafile = new PlantData();
    private ArrayList<Houseplant> addedPlants = datafile.readFromFile();
    private WateringScheduler scheduler = new WateringScheduler(addedPlants);
    private FlowPane plantdesc = new FlowPane();
    private FlowPane wateringDates = new FlowPane();
    private BorderPane panel = new BorderPane();


    /**
     * Resets lastWatered values of houseplants, updates wateringScheduler and calendarView so UI responds to changes in plant data.
     **/
    private void updateView() {
        wateringDates.getChildren().clear();
        for (Houseplant h : addedPlants) {
            h.setLastWatered("1.3.");
        }
        scheduler.setPlants(addedPlants);
        scheduler.setDateStrings(new String[addedPlants.size()]);
        panel.setCenter(calendarView());
    }

    /**
     * Displays a dialog with the average water consumption per month.
     * @return Dialog
     **/
    private Dialog averageWaterDialog() {
        Dialog avgWater = new Dialog();
        ButtonType close = new ButtonType("Sulje");
        avgWater.setTitle("Keskimääräinen vedenkulutus");
        avgWater.setContentText("Vedenkulutus tässä kuussa on noin "+String.format("%.2f",scheduler.waterPer28Days())+ " litraa.");
        avgWater.getDialogPane().getButtonTypes().add(close);
        avgWater.showAndWait();
        return avgWater;
    }

    /**
     * Displays basic information of addedPlants and includes removal buttons for each plant object.
     * @return FlowPane
     **/
    private FlowPane plantDescription() {
        plantdesc.setOrientation(Orientation.VERTICAL);
        plantdesc.setRowValignment(VPos.CENTER);
        plantdesc.getChildren().add(new Text("KASVIT:"));
        if (addedPlants.size() > 0) {
            plantdesc.getChildren().clear();
            for (Houseplant h : addedPlants) {
                Text plantstring = new Text(h.getName().toString() + ", vedentarve: " + h.getLitresOfWater() + " litraa");
                plantstring.setFont(Font.font("Verdana", 14));
                plantstring.setTextAlignment(TextAlignment.CENTER);
                Button remove = new Button("Poista");
                remove.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 12;");
                remove.setOnAction(e -> {
                    addedPlants.remove(h);
                    plantdesc.getChildren().removeAll(plantstring, remove);
                    updateView();
                });
                plantdesc.setPadding(new Insets(10, 10, 10, 10));
                plantdesc.getChildren().addAll(plantstring, remove);
            }
        }
        else {
            plantdesc.getChildren().add(new Text("Kasveja ei löytynyt, lisää kasvi"));
        }
        return plantdesc;
    }

    /**
     * Creates a FlowPane object containing textfields for adding new houseplant objects.
     * @return FlowPane
     **/
    private FlowPane newPlantPane() {
        Label[] labels = {new Label("Lajike:"), new Label("Vedentarve litroina:"), new Label("Päiviä kasteluiden välillä:")};
        FlowPane newPlantInfo = new FlowPane();
        newPlantInfo.setOrientation(Orientation.VERTICAL);
        newPlantInfo.setPrefWrapLength(200.0);
        for (int i = 0; i < 3; i++) {
            Label lb = labels[i];
            TextField tf = new TextField();
            fields[i] = tf;
            newPlantInfo.getChildren().addAll(lb, tf);
        }
        return newPlantInfo;
    }

    /**
     * EventHandler function for the button used to add new houseplants. Constructs a new Houseplant object based on user input, which is read from TextFields.
     **/
    public void addPlant() {
        String plantName = "";
        double litresOfWater = 0.0;
        int daysBetweenWatering = 0;
        boolean nameFilled = false;
        boolean litresFilled = false;
        boolean daysFilled = false;
        for (int i = 0; i < 3; i++) {
            fields[i].setStyle("-fx-text-box-border: lightgrey;");
            if (fields[i].getText() != "") {
                if (i == 0) {
                    plantName = fields[i].getText();
                    nameFilled = true;
                } else if (i == 1) {
                    try {
                        if (fields[i].getText().contains(",")) {
                            int index = fields[i].getText().indexOf(",");
                            String fixed = fields[i].getText().substring(0, index) + "." + fields[i].getText().substring(index + 1);
                            litresOfWater = Double.parseDouble(fixed);
                            litresFilled = true;
                        } else {
                            litresOfWater = Double.parseDouble(fields[i].getText());
                            litresFilled = true;
                        }
                    } catch (NumberFormatException e) {
                        fields[i].setStyle("-fx-text-box-border: RED;");
                    }
                } else {
                    try {
                        daysBetweenWatering = Integer.parseInt(fields[i].getText());
                        daysFilled = true;
                    } catch (NumberFormatException e) {
                        fields[i].setStyle("-fx-text-box-border: RED;");
                    }
                }
                fields[i].clear();
            }
            else {
                fields[i].setStyle("-fx-text-box-border: RED;");
            }
        }
        if (nameFilled && litresFilled && daysFilled) {
            Houseplant h = new Houseplant(plantName, litresOfWater, daysBetweenWatering);
            addedPlants.add(h);
            updateView();
            plantDescription();
        }
    }

    /**
     * Creates a button for writing houseplant objects into a save file.
     * @return Button
     **/
    public Button saveButton() {
        Button save = new Button("Tallenna");
        save.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 16;");
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
     * Creates a button for adding new houseplant objects.
     * @return Button
     **/
    private Button addButton() {
        Button addButton = new Button("Lisää");
        addButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 14;");
        addButton.setOnAction(e -> addPlant());
        return addButton;
    }

    /**
     * Creates a button for showing average water requirements.
     * @return Button
     **/
    private Button averageWaterButton() {
        Button avgWaterButton = new Button("Laske vedenkulutus");
        avgWaterButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-size: 16;");
        avgWaterButton.setOnAction(e -> {
            averageWaterDialog();
        });
        return avgWaterButton;
    }

    /**
     * Draws a cell in the calendar view.
     * @param color
     * @return Rectangle
     **/
    private Rectangle drawRectangle(Color color) {
        Rectangle r = new Rectangle(100,100);
        r.setFill(color);
        r.setStroke(Color.BLACK);
        return r;
    }

    /**
     * Draws one day (cell) in the calendar page. If day is a watering day, houseplants and watering needs are listed and the cell will be painted light green.
     * @param calendarView
     * @return StackPane
     **/
    private StackPane drawDayInCalendar(CalendarView calendarView) {
        StackPane cell = new StackPane();
        Text day = new Text(String.valueOf(calendarView.whatDayNumberIsIt()));
        ArrayList<String> plantsToWaterToday = scheduler.plantsToWaterToday(calendarView);
        if (plantsToWaterToday.size() > 0) {
            cell.getChildren().add(drawRectangle(Color.LIGHTGREEN));
            FlowPane allPlantInfos = new FlowPane();
            allPlantInfos.setOrientation(Orientation.VERTICAL);
            allPlantInfos.setAlignment(Pos.CENTER);
            for (String plantinfo : plantsToWaterToday) {
                allPlantInfos.getChildren().add(new Text(plantinfo));
            }
            cell.getChildren().add(allPlantInfos);
        }
        else {
            cell.getChildren().add(day);
        }
        calendarView.addOneDay(); // Bump calendar one day forward
        return cell;
    }

    /**
     * Draws a calendar view and creates an instance of the CalendarView class.
     * @return VBox
     **/
    public VBox calendarView() {
        CalendarView calendarView = new CalendarView();
        VBox calpage = new VBox();
        calpage.setPadding(new Insets(20, 20, 20, 20));
        GridPane cal = new GridPane();
        Text monthname = new Text(calendarView.whatMonthIsIt().toUpperCase());
        monthname.setFont(Font.font("Verdana", 27.5));
        int empties = calendarView.howManyDaysOfWeekToFrameInCalendar(); // Returns the number of empty gray cells at the beginning of a calendar month
        boolean end = false;
        for (int weeknum = 0; weeknum < calendarView.howManyWeeksInCurrentMonth(); weeknum++) {
            for (int weekday = 0; weekday < 7; weekday++) {
                StackPane cell = new StackPane();
                cell.getChildren().add(drawRectangle(Color.WHITE));
                if (empties > 0) { // Fill in empty days at the beginning of month
                    cell.getChildren().add(drawRectangle(Color.GRAY));
                    empties--;
                }
                else if (calendarView.isItTheLastDayOfThisMonth()) { // Draw last day of month, set end of month
                    cell.getChildren().add(drawDayInCalendar(calendarView));
                    end = true;
                }
                else if (end) { // Fill in empty days at the end of the month
                    cell.getChildren().add(drawRectangle(Color.GRAY));
                }
                else { // Draw all other days in the calendar
                    cell.getChildren().add(drawDayInCalendar(calendarView));
                }
                cal.add(cell, weekday, weeknum);
            }
        }
        calpage.getChildren().addAll(monthname, cal);
        String[] plantWateringInfo = scheduler.whenToWaterAPlant();
        showPlantWateringDates(plantWateringInfo);
        return calpage;
    }

    /**
     * Displays houseplant watering times by adding the strings to wateringDates FlowPane.
     **/
    private void showPlantWateringDates(String[] plantWateringInfo) {
        wateringDates.getChildren().clear();
        Text plantInfo;
        for (String info : plantWateringInfo) {
            plantInfo = new Text(info);
            plantInfo.setFont(Font.font("Verdana", 14));
            wateringDates.getChildren().add(plantInfo);
        }
    }

    @Override
    public void start(Stage stage) {
        wateringDates.setOrientation(Orientation.VERTICAL);
        wateringDates.setPadding(new Insets(0, 0, 0, 10));
        FlowPane buttons = new FlowPane(saveButton(), averageWaterButton());
        buttons.setHgap(10);
        Text plantPaneTitle = new Text("Lisää huonekasveja");
        plantPaneTitle.setFont(Font.font("Verdana", 18));
        VBox plants = new VBox(plantPaneTitle, newPlantPane(), addButton(), plantDescription(), buttons);
        plants.setPadding(new Insets(10));
        panel.setLeft(plants);
        panel.setCenter(calendarView());
        panel.setBottom(wateringDates);
        updateView();
        Scene scene = new Scene(panel, 1150, 750);
        stage.setTitle("Huonekasvien kastelun aikatauluttaja");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}