package service;

import dao.Site;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionSites {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/sites.ser";

    public void addSite(Site site) {
        List<Site> sites = getAllSites();
        site.setId(sites.size() + 1);
        sites.add(site);
        saveSitesToFile(sites);
    }

    public Site getSite(int id) {
        List<Site> sites = getAllSites();
        for (Site site : sites) {
            if (site.getId() == id) {
                return site;
            }
        }
        return null;
    }

    public List<Site> getAllSites() {
        List<Site> sites = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            sites = (List<Site>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sites;
    }

    public void updateSite(int id, Site updatedSite) {
        List<Site> sites = getAllSites();
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getId() == id) {
                sites.set(i, updatedSite);
                saveSitesToFile(sites);
                return;
            }
        }
    }

    public void deleteSite(int id) {
        List<Site> sites = getAllSites();
        for (int i = 0; i < sites.size(); i++) {
            if (sites.get(i).getId() == id) {
                sites.remove(i);
                saveSitesToFile(sites);
                return;
            }
        }
    }

    private void saveSitesToFile(List<Site> sites) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(sites);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
