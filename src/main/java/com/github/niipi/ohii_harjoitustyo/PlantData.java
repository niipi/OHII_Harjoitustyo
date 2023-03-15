package com.github.niipi.ohii_harjoitustyo;

import java.io.*;
import java.util.ArrayList;

/**
 * Stores and retrieves serialized houseplant objects from a save file.
 * @author Niina Piiroinen
 **/
public class PlantData {

    protected static ArrayList<Houseplant> plants = new ArrayList<Houseplant>();
    private static String filename = "plants.dat";

    /**
     * Houseplant objects are written into the save file from the ArrayList 'plants'.
     * If a save file does not exist, it is created. If it exists, it is overwritten.
     **/
    protected static void saveToFile() throws IOException {
        FileOutputStream fileout = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fileout);
        out.writeObject(plants);

        fileout.close();
    }

    /**
     * If a save file exists, houseplant objects are read from file and added to the ArrayList 'plants'
     **/
    protected static void readFromFile() throws IOException, ClassNotFoundException {
        File plantfile = new File(filename);
        if (plantfile.exists()) {

            try (FileInputStream filein = new FileInputStream(filename);
                 ObjectInputStream in = new ObjectInputStream(filein);){
                plants = (ArrayList<Houseplant>) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
