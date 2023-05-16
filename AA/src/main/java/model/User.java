package model;

import java.util.ArrayList;

public class User {
    private static ArrayList<User> users;
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
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.username.equals(username))
                return user;
        }
        return null;
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

}
