package service;

import dao.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionUsers {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/database/users.ser";

    public void addUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        saveUsersToFile(users);
    }


    public User getUser(int id) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(int id, User updatedUser) {
        List<User> users = getAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                users.set(i, updatedUser);
                saveUsersToFile(users);
                return;
            }
        }
    }

    public void deleteUser(int id) {
        List<User> users = getAllUsers();
        users.removeIf(user -> user.getId() == id);
        saveUsersToFile(users);
    }

    private void saveUsersToFile(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
