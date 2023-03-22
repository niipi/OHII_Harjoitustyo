package com.github.niipi.ohii_harjoitustyo;

import java.util.ArrayList;

/**
 * This class attempts to calculate an optimal schedule for watering all houseplants it is given.
 * @author Niina Piiroinen
 **/
public class WateringScheduler implements WateringNeeds {

    private ArrayList<Houseplant> plants;


    public WateringScheduler(ArrayList<Houseplant> plants) {
        this.plants = plants;
    }

    public ArrayList<Houseplant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Houseplant> plants) {
        this.plants = plants;
    }


    /**
     * Calculates weekly water need for all houseplants entered into the program.
     * @return double
     **/
    @Override
    public double waterPerWeek() {
        double litres = 0;
        for (Houseplant houseplant : plants) {
            litres += houseplant.waterPerWeek();
        }
        return litres;
    }

    /**
     * Calculates 28-day water need for all houseplants entered into the program.
     * @return double
     **/
    @Override
    public double waterPer28Days() {
        double litres = 0;
        for (Houseplant houseplant : plants) {
            litres += houseplant.waterPer28Days();
        }
        return litres;
    }

    /**
     * Checks if current day in CalendarView object is a watering day for any of the plants.
     * @return boolean
     **/
    public boolean isItTimeToWaterThisPlant(CalendarView calendarView) {
        boolean wateringTime = false;
        for (Houseplant h : plants) {
            if (h.getDaysBetweenWatering() == calendarView.whatDayNumberIsIt()) {
                wateringTime = true;
            }
        }
        return wateringTime;
    }

    /**
     * Prints a string about watering dates for a houseplant.
     * @return String
     **/
    public String whenToWaterAPlant(CalendarView calendarView) {
        String wateringDay = "";
        for (Houseplant h : plants) {
            if (h.getDaysBetweenWatering() == calendarView.whatDayNumberIsIt()) {
                wateringDay = h.getName().toString()+" on kasteltava "+calendarView.whatDayIsIt();
            }
        }
        return wateringDay;
    }
}
