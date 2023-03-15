package com.github.niipi.ohii_harjoitustyo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class for creating individual houseplant objects. Includes three fields: name, litresOfWater and daysBetweenWatering.
 * Preliminary methods waterPerWeek and waterPer28Days are included for future needs of watering schedule optimization.
 * @author Niina Piiroinen
 **/
public class Houseplant implements Serializable {

    private String name;
    private double litresOfWater;
    private int daysBetweenWatering;

    public Houseplant() {
        // empty constructor
    }

    public Houseplant(String n, double litre, int days) {
        this.name = n;
        this.litresOfWater = litre;
        this.daysBetweenWatering = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLitresOfWater() {
        return litresOfWater;
    }

    public void setLitresOfWater(double litres) {
        this.litresOfWater = litres;
    }

    public int getDaysBetweenWatering() {
        return daysBetweenWatering;
    }

    public void setDaysBetweenWatering(int days) {
        this.daysBetweenWatering = days;
    }

    @Override
    public String toString() {
        return "Huonekasvi " +
                "lajike='" + name + '\'' +
                ", vesimäärä litroina=" + litresOfWater +
                ", kasteluväli=" + daysBetweenWatering;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Houseplant that = (Houseplant) o;
        return Double.compare(that.getLitresOfWater(), getLitresOfWater()) == 0 && getDaysBetweenWatering() == that.getDaysBetweenWatering() && Objects.equals(getName(), that.getName());
    }

    /**
    * Checks if the watering needs of two houseplant objects are identical.
    * @return boolean
    **/
    public boolean equalNeeds(Houseplant h) {
        if (h.litresOfWater == this.litresOfWater && h.daysBetweenWatering == this.daysBetweenWatering) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Calculates average amount of water in a week for the houseplant. Used to find the closest match
     * between different houseplant objects and their weekly watering needs.
     * @return double
     **/
    public double waterPerWeek() {
        return this.litresOfWater/(this.daysBetweenWatering/7);
    }

    /**
     * Calculates average amount of water in a 28 day period for the houseplant. Used to find the closest match
     * between different houseplant objects and their monthly watering needs.
     * @return double
     **/
    public double waterPer28Days() {
        return this.litresOfWater/(this.daysBetweenWatering/28);
    }
}
