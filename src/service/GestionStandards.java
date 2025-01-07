package service;

import dao.Standard;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionStandards {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/standards.ser";

    public void addStandard(Standard standard) {
        List<Standard> standards = getAllStandards();
        standards.add(standard);
        saveStandardsToFile(standards);
    }

    public Standard getStandard(int id) {
        List<Standard> standards = getAllStandards();
        for (Standard standard : standards) {
            if (standard.getId() == id) {
                return standard;
            }
        }
        return null;
    }

    public List<Standard> getAllStandards() {
        List<Standard> standards = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            standards = (List<Standard>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return standards;
    }

    public void updateStandard(int id, Standard updatedStandard) {
        List<Standard> standards = getAllStandards();
        for (int i = 0; i < standards.size(); i++) {
            if (standards.get(i).getId() == id) {
                standards.set(i, updatedStandard);
                saveStandardsToFile(standards);
                return;
            }
        }
    }

    public void deleteStandard(int id) {
        List<Standard> standards = getAllStandards();
        for (int i = 0; i < standards.size(); i++) {
            if (standards.get(i).getId() == id) {
                standards.remove(i);
                saveStandardsToFile(standards);
                return;
            }
        }
    }

    private void saveStandardsToFile(List<Standard> standards) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(standards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Standard> getStandardsByIds(String ids) {
        List<Standard> standards = new ArrayList<>();
        for (String id : ids.split(";")) {
            Standard standard = getStandard(Integer.parseInt(id));
            if (standard != null) {
                standards.add(standard);
            }
        }
        return standards;
    }
}
