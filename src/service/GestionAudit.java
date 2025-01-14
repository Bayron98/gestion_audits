package service;

import dao.Audit;
import dao.Clause;
import dao.SystemeExigence;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionAudit {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/audits.ser";

    public void addAudit(Audit audit) {
        List<Audit> audits = getAllAudits();
        audit.setId(audits.size() + 1);
        
        // Créer des systèmes d'exigences pour chaque clause dans le standard de l'audit
        if (audit.getStandard() != null) {
            List<SystemeExigence> systemeExigences = new ArrayList<>();
            for (Clause clause : audit.getStandard().getClauses()) {
                SystemeExigence systemeExigence = new SystemeExigence();
                systemeExigence.setId(audit.getId() * 100 + (systemeExigences.size() +1));
                systemeExigence.setClause(clause);
                systemeExigence.setStatus("Non évalué");
                systemeExigences.add(systemeExigence);
            }
            audit.setSysteme_exigence(systemeExigences);
        }

        audits.add(audit);
        saveAuditsToFile(audits);
    }

    public Audit getAudit(int id) {
        List<Audit> audits = getAllAudits();
        for (Audit audit : audits) {
            if (audit.getId() == id) {
                return audit;
            }
        }
        return null;
    }

    public List<Audit> getAllAudits() {
        List<Audit> audits = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            audits = (List<Audit>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return audits;
    }

    public void updateAudit(int id, Audit updatedAudit) {
        List<Audit> audits = getAllAudits();
        for (int i = 0; i < audits.size(); i++) {
            if (audits.get(i).getId() == id) {
                audits.set(i, updatedAudit);
                saveAuditsToFile(audits);
                return;
            }
        }
    }

    public void deleteAudit(int id) {
        List<Audit> audits = getAllAudits();
        for (int i = 0; i < audits.size(); i++) {
            if (audits.get(i).getId() == id) {
                audits.remove(i);
                saveAuditsToFile(audits);
                return;
            }
        }
    }

    public List<SystemeExigence> getAllSystemeExigence() {
        List<SystemeExigence> systemeExigences = new ArrayList<>();
        List<Audit> audits = getAllAudits();
        for (Audit audit : audits) {
            systemeExigences.addAll(audit.getSysteme_exigence());
        }
        return systemeExigences;
    }

    private void saveAuditsToFile(List<Audit> audits) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(audits);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
