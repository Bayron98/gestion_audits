package service;

import dao.Responsable;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionResponsables {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/responsables.ser";

    public void addResponsable(Responsable responsable) {
        List<Responsable> responsables = getAllResponsables();
        responsable.setId(responsables.size() + 1);
        responsables.add(responsable);
        saveResponsablesToFile(responsables);
    }

    public Responsable getResponsable(int id) {
        List<Responsable> responsables = getAllResponsables();
        for (Responsable responsable : responsables) {
            if (responsable.getId() == id) {
                return responsable;
            }
        }
        return null;
    }

    public List<Responsable> getAllResponsables() {
        List<Responsable> responsables = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            responsables = (List<Responsable>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return responsables;
    }

    public void updateResponsable(int id, Responsable updatedResponsable) {
        List<Responsable> responsables = getAllResponsables();
        for (int i = 0; i < responsables.size(); i++) {
            if (responsables.get(i).getId() == id) {
                responsables.set(i, updatedResponsable);
                saveResponsablesToFile(responsables);
                return;
            }
        }
    }

    public void deleteResponsable(int id) {
        List<Responsable> responsables = getAllResponsables();
        for (int i = 0; i < responsables.size(); i++) {
            if (responsables.get(i).getId() == id) {
                responsables.remove(i);
                saveResponsablesToFile(responsables);
                return;
            }
        }
    }

    private void saveResponsablesToFile(List<Responsable> responsables) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(responsables);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
