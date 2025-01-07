package service;

import dao.Clause;
import dao.Standard;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionClauses {
    private static final String FILE_PATH = "c:/Users/HP/gestion_audits/src/database/clauses.txt";
    private GestionStandards gestionStandards = new GestionStandards();

    public void addClause(Clause clause) {
        List<Clause> clauses = getAllClauses();
        clauses.add(clause);
        saveClausesToFile(clauses);
    }

    public Clause getClause(int id) {
        List<Clause> clauses = getAllClauses();
        for (Clause clause : clauses) {
            if (clause.getId() == id) {
                return clause;
            }
        }
        return null;
    }

    public List<Clause> getAllClauses() {
        List<Clause> clauses = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Clause clause = new Clause();
                    clause.setId(Integer.parseInt(parts[0]));
                    clause.setDescription(parts[1]);
                    clause.setReference(parts[2]);
                    List<Standard> standards = gestionStandards.getStandardsByIds(parts[3]);
                    for (Standard standard : standards) {
                        clause.addStandard(standard);
                    }
                    clauses.add(clause);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return clauses;
    }

    public void updateClause(int id, Clause updatedClause) {
        List<Clause> clauses = getAllClauses();
        for (int i = 0; i < clauses.size(); i++) {
            if (clauses.get(i).getId() == id) {
                clauses.set(i, updatedClause);
                saveClausesToFile(clauses);
                return;
            }
        }
    }

    public void deleteClause(int id) {
        List<Clause> clauses = getAllClauses();
        clauses.removeIf(clause -> clause.getId() == id);
        saveClausesToFile(clauses);
    }

    private void saveClausesToFile(List<Clause> clauses) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Clause clause : clauses) {
                StringBuilder standardsIds = new StringBuilder();
                for (Standard standard : clause.getStandards()) {
                    standardsIds.append(standard.getId()).append(";");
                }
                bw.write(clause.getId() + "," + clause.getDescription() + "," + clause.getReference() + "," + standardsIds.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Clause> getClausesByIds(String ids) {
        List<Clause> clauses = new ArrayList<>();
        for (String id : ids.split(";")) {
            Clause clause = getClause(Integer.parseInt(id));
            if (clause != null) {
                clauses.add(clause);
            }
        }
        return clauses;
    }
}
