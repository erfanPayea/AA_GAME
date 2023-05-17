package model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class User {
    private static ArrayList<User> users;
    public static User currentUser;
    private static final Gson gson = new Gson();
    private String username;
    private String password;
    private int score;
    private int highScore;
    private int rank;

    static {
        users = new ArrayList<>();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        users.add(this);
//        User.saveUsersToFile();
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.username.equals(username))
                return user;
        }
        return null;
    }

    public static void removeUser(User user) {
        users.remove(user);
    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public boolean isPasswordIncorrect(String password) {
        return !this.password.equals(password);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setScore(int score) {
        this.score = score;
        if (this.score > this.highScore)
            this.highScore = score;
    }

    public void remove() {
        users.remove(this);
    }

    // database :

    public static void loadUsersFromFile() {
        String filePath = "./src/main/resources/database/userDatabase.json";
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            ArrayList<User> createdUsers = gson.fromJson(json, new TypeToken<List<User>>() {
            }.getType());

            if (createdUsers != null) {
                users = createdUsers;
            }
        } catch (IOException ignored) {
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static User loadStayLoggedIn() {
        String filePath = "./src/main/resources/database/stayLoggedIn.json";
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));

            return gson.fromJson(json, new TypeToken<User>() {
            }.getType());

        } catch (IOException ignored) {
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return null;
        }
    }

    public static void setStayLoggedIn(User loggedInUser) {
        String filePath = "./src/main/resources/database/stayLoggedIn.json";
        saveToFile(filePath, gson.toJson(loggedInUser));
    }

    public static void saveUsersToFile() {
        String filePath = "./src/main/resources/database/userDatabase.json";
        saveToFile(filePath, gson.toJson(users));
    }

    private static void saveToFile(String filePath, String s) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(s);
            fileWriter.close();
        } catch (IOException ignored) {
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
