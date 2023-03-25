package com.github.niipi.ohii_harjoitustyo;

import java.util.*;

/**
 * Schedules watering dates for plants and keeps track of watering dates for each plant. Hopefully will also optimize watering schedules in the future implementations of the program.
 * @author Niina Piiroinen
 **/
public class WateringScheduler implements WateringNeeds {

    private ArrayList<Houseplant> plants;
    private HashMap<String, String> mapDates;
    private String [] dateStrings;

    public WateringScheduler(ArrayList<Houseplant> plants) {
        this.plants = plants;
        this.mapDates = new HashMap<>();
        this.dateStrings = new String[plants.size()];
    }

    public ArrayList<Houseplant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Houseplant> plants) {
        this.plants = plants;
    }

    public void setMapDates(HashMap<String, String> mapDates) {
        this.mapDates = mapDates;
    }

    public void setDateStrings(String[] dateStrings) {
        this.dateStrings = dateStrings;
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
     * Checks if the distance of today and houseplants last watering day match the houseplants watering frequency.
     * @params Houseplant, CalendarView
     * @return boolean
     **/
    private boolean isItTimeToWaterThisPlant(Houseplant h, CalendarView calendarView) {
        return calendarView.howManyDaysBetweenDates(h.getLastWatered(), calendarView.whatDayIsIt()) == h.getDaysBetweenWatering();
    }

    /**
     * Creates a list of plant watering information based on current date.
     * @return ArrayList
     **/
    public ArrayList<String> plantsToWaterToday(CalendarView calendarView) {
        ArrayList<String> plantsToWater = new ArrayList<>();
        for (Houseplant h : plants) {
            if (isItTimeToWaterThisPlant(h, calendarView)) {
                String plantinfo = h.getName().toString()+"\n"+h.getLitresOfWater()+" l";
                plantsToWater.add(plantinfo);
                h.setLastWatered(calendarView.whatDayIsIt());
                mapPlantsToDates(h, calendarView.whatDayIsIt());
            }
        }
        return plantsToWater;
    }

    /**
     * Maps plants to dates: a name of a houseplant in mapDates is a key with the value of all its watering dates.
     * The resulting map object is used to print and display all watering dates for the user.
     * @param h, date
     **/
    public void mapPlantsToDates(Houseplant h, String date) {
        if (date != null) {
            for (int i = 0; i < plants.size(); i++) {
                if (h == plants.get(i)) {
                    if (!mapDates.containsKey(h.getName().toString())) {
                        dateStrings[i] = date;
                        mapDates.put(h.getName().toString(), dateStrings[i]);
                    } else {
                        dateStrings[i] += ", "+ date;
                        mapDates.replace(h.getName().toString(), dateStrings[i]);
                    }
                }
            }
        }
    }

    /**
     * Creates an array of strings about watering dates for a houseplants. Magnificently horrible last minute solution before deadline, will need to be fixed.
     * @return String[]
     **/
    public String[] whenToWaterAPlant() {
        String[] plantWateringDays = new String[plants.size()];
        for (int i = 0; i < plants.size(); i++) { // Construct strings based on dates mapped to specific plant
            String plantName = plants.get(i).getName().toString();
            plantWateringDays[i] = plantName+" on kasteltava ";
            plantWateringDays[i] += dateStrings[i];
        }
        return plantWateringDays;
    }
}
