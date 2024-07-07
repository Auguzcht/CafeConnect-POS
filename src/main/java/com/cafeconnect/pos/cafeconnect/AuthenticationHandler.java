package com.cafeconnect.pos.cafeconnect;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationHandler {
    private final String credentialsFile = "credentials.txt";

    public boolean authenticate(String username, String password) {
        try {
            List<User> users = readCredentials();
            for (User user : users) {
                if (username.equals(user.username) && password.equals(user.password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkAccountExists(String username) {
        try {
            List<User> users = readCredentials();
            for (User user : users) {
                if (username.equals(user.username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAccount(String username, String password, String staffName, String schedule) {
        try {
            if (checkAccountExists(username)) {
                return false;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(credentialsFile, true))) {
                writer.write(username + "," + password + "," + staffName + "," + schedule);
                writer.newLine();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<User> readCredentials() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(credentialsFile);
        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length == 4) {
                    users.add(new User(parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim()));
                }
            }
        }
        return users;
    }

    public User getUser(String username) {
        try {
            List<User> users = readCredentials();
            for (User user : users) {
                if (username.equals(user.username)) {
                    return user;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class User {
        String username;
        String password;
        String staffName;
        String schedule;

        public User(String username, String password, String staffName, String schedule) {
            this.username = username;
            this.password = password;
            this.staffName = staffName;
            this.schedule = schedule;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getStaffName() {
            return staffName;
        }

        public String getSchedule() {
            return schedule;
        }
    }
}