package service;

import dao.Clause;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionClauses {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/clauses.ser";

    public void addClause(Clause clause) {
        List<Clause> clauses = getAllClauses();
        clause.setId(clauses.size() + 1);
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            clauses = (List<Clause>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
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
        for (int i = 0; i < clauses.size(); i++) {
            if (clauses.get(i).getId() == id) {
                clauses.remove(i);
                saveClausesToFile(clauses);
                return;
            }
        }
    }

    private void saveClausesToFile(List<Clause> clauses) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(clauses);
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
