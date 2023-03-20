package com.github.niipi.ohii_harjoitustyo;

import java.util.ArrayList;

/**
 * This class attempts to calculate an optimal schedule for watering all houseplants it is given.
 * @author Niina Piiroinen
 **/
public class WateringScheduler implements WateringNeeds {

    private ArrayList<Houseplant> plants;
    private ArrayList<Houseplant> low;
    private ArrayList<Houseplant> high;

    public WateringScheduler(ArrayList<Houseplant> plants) {
        this.plants = plants;
    }

    public ArrayList<Houseplant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Houseplant> plants) {
        this.plants = plants;
    }

    public ArrayList<Houseplant> getLow() {
        return low;
    }

    public void setLow(ArrayList<Houseplant> low) {
        this.low = low;
    }

    public ArrayList<Houseplant> getHigh() {
        return high;
    }

    public void setHigh(ArrayList<Houseplant> high) {
        this.high = high;
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

    public void sortPlantsByWateringNeeds() {
        double perWeek = waterPerWeek();
        double perMonth = waterPer28Days();
        for (Houseplant houseplant : plants) {
            if (houseplant.waterPer28Days() < 0.5) {
                low.add(houseplant);
            }
            else if (houseplant.waterPer28Days() < 0.9 && houseplant.waterPerWeek() < 0.15) {
                low.add(houseplant);
            }
            else {
                high.add(houseplant);
            }
        }
    }

    /**
     * Checks if there are more plants with high frequency watering needs. **/
    public boolean hasMoreHighWateringNeeds() {
        return high.size() > low.size();
    }
}
