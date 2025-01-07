package service;

import dao.Standard;
import dao.Clause;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionStandards {
    private static final String FILE_PATH = "c:/Users/HP/gestion_audits/src/database/standards.txt";
    private GestionClauses gestionClauses = new GestionClauses();

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
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Standard standard = new Standard();
                    standard.setId(Integer.parseInt(parts[0]));
                    standard.setDescription(parts[1]);
                    standard.setReference(parts[2]);
                    List<Clause> clauses = gestionClauses.getClausesByIds(parts[3]);
                    for (Clause clause : clauses) {
                        standard.addClause(clause);
                    }
                    standards.add(standard);
                }
            }
        } catch (IOException e) {
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
        standards.removeIf(standard -> standard.getId() == id);
        saveStandardsToFile(standards);
    }

    private void saveStandardsToFile(List<Standard> standards) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Standard standard : standards) {
                StringBuilder clausesIds = new StringBuilder();
                for (Clause clause : standard.getClauses()) {
                    clausesIds.append(clause.getId()).append(";");
                }
                bw.write(standard.getId() + "," + standard.getDescription() + "," + standard.getReference() + "," + clausesIds.toString());
                bw.newLine();
            }
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
