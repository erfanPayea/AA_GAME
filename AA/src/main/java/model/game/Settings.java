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

    public void setMap(String mapName) {
        if (mapName != null)
            this.map = Map.getMapByName(mapName);
    }
    public void setLevel(String level) {
        if (level != null)
            this.level = Level.getLevelByNumber(Integer.parseInt(level));
    }
    public void setBallNumbers(String ballNumbers) {
        if (ballNumbers != null)
            this.ballNumbers = Integer.parseInt(ballNumbers);
    }
    public void setMute(boolean isMute) {
        this.isMute = isMute;
    }
}
