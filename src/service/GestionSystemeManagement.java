package service;

import dao.SystemeManagement;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionSystemeManagement {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/systemeManagement.ser";

    public void addSystemeManagement(SystemeManagement systemeManagement) {
        List<SystemeManagement> systemes = getAllSystemeManagement();
        systemeManagement.setId(systemes.size() + 1);
        systemes.add(systemeManagement);
        saveSystemeManagementToFile(systemes);
    }

    public SystemeManagement getSystemeManagement(int id) {
        List<SystemeManagement> systemes = getAllSystemeManagement();
        for (SystemeManagement systeme : systemes) {
            if (systeme.getId() == id) {
                return systeme;
            }
        }
        return null;
    }

    public List<SystemeManagement> getAllSystemeManagement() {
        List<SystemeManagement> systemes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            systemes = (List<SystemeManagement>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return systemes;
    }

    public void updateSystemeManagement(int id, SystemeManagement updatedSysteme) {
        List<SystemeManagement> systemes = getAllSystemeManagement();
        for (int i = 0; i < systemes.size(); i++) {
            if (systemes.get(i).getId() == id) {
                systemes.set(i, updatedSysteme);
                saveSystemeManagementToFile(systemes);
                return;
            }
        }
    }

    public void deleteSystemeManagement(int id) {
        List<SystemeManagement> systemes = getAllSystemeManagement();
        for (int i = 0; i < systemes.size(); i++) {
            if (systemes.get(i).getId() == id) {
                systemes.remove(i);
                saveSystemeManagementToFile(systemes);
                return;
            }
        }
    }

    private void saveSystemeManagementToFile(List<SystemeManagement> systemes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(systemes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
