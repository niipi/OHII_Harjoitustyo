package com.github.niipi.ohii_harjoitustyo;

import java.util.*;

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
     * Checks if the distance of today and houseplants last watering day match the houseplants watering frequency.
     * @params Houseplant, CalendarView
     * @return boolean
     **/
    private boolean isItTimeToWaterThisPlant(Houseplant h, CalendarView calendarView) {
        return calendarView.howManyDaysBetweenDates(h.getLastWatered(), calendarView.whatDayIsIt()) == h.getDaysBetweenWatering();
    }

    /**
     * Creates a list of plant watering information based on current date.
     * @return ArrayList<String>
     **/
    public ArrayList<String> plantsToWaterToday(CalendarView calendarView) {
        ArrayList<String> plantsToWater = new ArrayList<>();
        for (Houseplant h : plants) {
            if (isItTimeToWaterThisPlant(h, calendarView)) {
                String plantinfo = h.getName().toString()+"\n"+h.getLitresOfWater()+" l";
                plantsToWater.add(plantinfo);
                h.setLastWatered(calendarView.whatDayIsIt());
            }
        }
        return plantsToWater;
    }

    /**
     * Creates an array of strings about watering dates for a houseplants. Magnificently horrible last minute solution before deadline, will need to be fixed.
     * @return String[]
     **/
    public String[] whenToWaterAPlant(CalendarView calendarView) {
        String[] plantWateringDays = new String[plants.size()];
        Map<String, List<String>> mapDates = new HashMap<>();
        List<String> dates = new ArrayList<String>();
        for (int i = 0; i < plants.size(); i++) { // Map plants to their respective watering dates
                if (isItTimeToWaterThisPlant(plants.get(i), calendarView)) {
                    dates.add(calendarView.whatDayIsIt());
            }
            mapDates.put(plants.get(i).getName().toString(), dates);
        }
        for (int j = 0; j < plants.size(); j++) { // Construct strings based on dates mapped to specific plant
            String plantName = plants.get(j).getName().toString();
            plantWateringDays[j] = plantName+" on kasteltava ";
            List<String> datesOfPlant = mapDates.get(plantName);
            for (int k = 0; k < datesOfPlant.size(); k++) {
                if (k<datesOfPlant.size()-2) {
                    plantWateringDays[j] += datesOfPlant.get(k) + ", ";
                }
                else if (k == datesOfPlant.size()-2){
                    plantWateringDays[j] += datesOfPlant.get(k) + " ja ";
                }
                else {
                    plantWateringDays[j] += datesOfPlant.get(k);
                }
            }
        }
        return plantWateringDays;
    }
}
