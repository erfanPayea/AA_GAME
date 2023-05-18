package controller;

import model.User;

import java.util.LinkedList;

public class ScoreboardController {

    public LinkedList<User> getRanks(int level) {
        LinkedList<User> ranks = new LinkedList<>();
        int index;
        for (User user : User.getUsers()) {
            index = 0;
            for (int i = 0; i < ranks.size(); i++) {
                if (ranks.get(i).getHighScores()[level] <= user.getHighScores()[level]) {
                    index = i;
                    break;
                }
            }
            ranks.add(index, user);
        }
        return ranks;
    }
}
