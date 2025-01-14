package service;

import dao.Organisation;
import java.io.*;

public class GestionOrganisation {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/organisation.ser";

    public void addOrganisation(Organisation organisation) {
        saveOrganisationToFile(organisation);
        System.out.println("Organisation ajoutée : " + organisation.getName());
    }

    public Organisation getOrganisation() {
        Organisation organisation = null;
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                organisation = (Organisation) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le fichier n'existe pas encore.");
        }
        return organisation;
    }

    public void updateOrganisation(Organisation updatedOrganisation) {
        saveOrganisationToFile(updatedOrganisation);
        System.out.println("Organisation mise à jour : " + updatedOrganisation.getName());
    }

    public void deleteOrganisation() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
            System.out.println("Organisation supprimée.");
        }
    }

    private void saveOrganisationToFile(Organisation organisation) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(organisation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
