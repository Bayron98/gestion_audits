package service;

import dao.Action;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionAction {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/actions.ser";

    public void addAction(Action action) {
        List<Action> actions = getAllActions();
        action.setId(actions.size() + 1);
        actions.add(action);
        saveActionsToFile(actions);
    }

    public Action getAction(int id) {
        List<Action> actions = getAllActions();
        for (Action action : actions) {
            if (action.getId() == id) {
                return action;
            }
        }
        return null;
    }

    public List<Action> getAllActions() {
        List<Action> actions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            actions = (List<Action>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return actions;
    }

    public void updateAction(int id, Action updatedAction) {
        List<Action> actions = getAllActions();
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).getId() == id) {
                actions.set(i, updatedAction);
                saveActionsToFile(actions);
                return;
            }
        }
    }

    public void deleteAction(int id) {
        List<Action> actions = getAllActions();
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i).getId() == id) {
                actions.remove(i);
                saveActionsToFile(actions);
                return;
            }
        }
    }

    private void saveActionsToFile(List<Action> actions) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(actions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
