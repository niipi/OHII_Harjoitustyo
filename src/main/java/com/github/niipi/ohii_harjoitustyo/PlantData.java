package com.github.niipi.ohii_harjoitustyo;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.*;
import java.util.ArrayList;

/**
 * Stores and retrieves serialized houseplant objects from a save file.
 * @author Niina Piiroinen
 **/
public class PlantData {

    private final String FILENAME = "plants.dat";

    /**
     * Houseplant objects are written into the save file from the ArrayList 'plants'.
     * If a save file does not exist, it is created. If it exists, it is overwritten.
     * @param ArrayList<Houseplant>
     **/
    protected void saveToFile(ArrayList<Houseplant> plants) throws IOException {
        FileOutputStream fileout = new FileOutputStream(FILENAME);
        ObjectOutputStream out = new ObjectOutputStream(fileout);
        out.writeObject(plants);
        saveSuccessful();

        fileout.close();
    }

    /**
     * If a save file exists, houseplant objects are read from file and added to the ArrayList 'plants'
     * @return ArrayList<Houseplant>
     **/
    protected ArrayList<Houseplant> readFromFile() {
        File plantfile = new File(FILENAME);
        ArrayList<Houseplant> plants = new ArrayList<>();
        if (plantfile.exists()) {
            try (FileInputStream filein = new FileInputStream(FILENAME);
                 ObjectInputStream in = new ObjectInputStream(filein);){
                plants = (ArrayList<Houseplant>) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            noSaveFileFound();
        }
        return plants;
    }

    /**
     * Displays a dialog telling the user to use the save button to generate a save file for houseplant data.
     * @return Dialog
     **/
    protected Dialog noSaveFileFound() {
        Dialog noSaveFile = new Dialog();
        ButtonType ok = new ButtonType("OK");
        noSaveFile.setTitle("Tallennustiedostoa ei löytynyt");
        noSaveFile.setContentText("Tallennustiedostoa plants.dat ei löytynyt. Muista tallentaa kasviesi tiedot ennen kuin suljet ohjelman!");
        noSaveFile.getDialogPane().getButtonTypes().add(ok);
        noSaveFile.showAndWait();
        return noSaveFile;
    }

    /**
     * Displays a dialog informing the user of a successful save operation.
     * @return Dialog
     **/
    protected Dialog saveSuccessful() {
        Dialog saveOK = new Dialog();
        ButtonType ok = new ButtonType("OK");
        saveOK.setTitle("Tallennus onnistui");
        saveOK.setContentText("Tallennettu tiedostoon plants.dat");
        saveOK.getDialogPane().getButtonTypes().add(ok);
        saveOK.showAndWait();
        return saveOK;
    }
}
