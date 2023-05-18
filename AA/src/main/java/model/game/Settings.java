package model.game;

import model.game.enums.Level;
import model.game.enums.Map;

public class Settings {
    private Level level;
    private int ballNumbers;
    private Map map;
    private boolean isMute;

    public Settings(int levelNumber, int ballNumbers, Map map, boolean isMute) {
        this.level = Level.getLevelByNumber(levelNumber);
        this.ballNumbers = ballNumbers;
        this.map = map;
        this.isMute = isMute;
    }

    public Level getLevel() {
        return level;
    }

    public int getBallNumbers() {
        return ballNumbers;
    }

    public Map getMap() {
        return map;
    }

    public boolean isMute() {
        return isMute;
    }
}
