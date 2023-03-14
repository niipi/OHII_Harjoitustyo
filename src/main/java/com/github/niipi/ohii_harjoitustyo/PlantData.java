package com.github.niipi.ohii_harjoitustyo;

import java.io.*;
import java.util.ArrayList;

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

        for (int i = 0; i < plants.size(); i++) {
            out.writeObject(plants.get(i));
        }

        fileout.close();
    }

    /**
     * If a save file exists, houseplant objects are read from file and added to the ArrayList 'plants'
     **/
    protected static void readFromFile() throws IOException, ClassNotFoundException {
        File plantfile = new File(filename);
        if (plantfile.exists()) {
            FileInputStream filein = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(filein);
            try {
                Object o;
                while ((o = in.readObject()) != null) {
                    if (o instanceof Houseplant)
                        plants.add((Houseplant) o);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                filein.close();
            }
        }
    }
}
