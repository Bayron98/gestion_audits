package service;

import dao.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionUsers {
    private static final String FILE_PATH = "c:/Users/badrb/gestion_audits/src/database/users.txt";

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
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    User user = new User(parts[1], parts[2], parts[3], parts[4]);
                    user.setId(Integer.parseInt(parts[0]));
                    users.add(user);
                }
            }
        } catch (IOException e) {
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
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                bw.write(user.getId() + "," + user.getName() + "," + user.getUsername() + "," + user.getPassword() + "," + user.getRole());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
