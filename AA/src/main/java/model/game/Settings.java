package model.game;

import model.game.enums.Level;
import model.game.enums.Map;
import view.enums.HotKeys;

import java.util.HashMap;

public class Settings {
    private Level level;
    private int ballNumbers;
    private Map map;
    private boolean isMute;
    private HashMap<HotKeys, String> hotKeys;

    public Settings(int levelNumber, int ballNumbers, Map map, boolean isMute) {
        this.level = Level.getLevelByNumber(levelNumber);
        this.ballNumbers = ballNumbers;
        this.map = map;
        this.hotKeys = new HashMap<>();
        this.setHotKeys();
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

    public void setHotKeys() {
        for (HotKeys hotKey : HotKeys.values()) {
            this.hotKeys.put(hotKey, hotKey.getKeyName());
        }
    }

    public void setHotKeys(String shootKey, String shoot2Key, String freezeKey, String leftKey, String rightKey,
                           String left2Key, String right2Key) {
        this.hotKeys.put(HotKeys.SHOOT, shootKey);
        this.hotKeys.put(HotKeys.PLAYER2SHOOT, shoot2Key);
        this.hotKeys.put(HotKeys.FREEZE, freezeKey);
        this.hotKeys.put(HotKeys.LEFT, leftKey);
        this.hotKeys.put(HotKeys.RIGHT, rightKey);
        this.hotKeys.put(HotKeys.LEFT2, left2Key);
        this.hotKeys.put(HotKeys.RIGHT2, right2Key);

        updateHotkeys();
    }

    public void updateHotkeys() {
        for (HotKeys hotKey : HotKeys.values()) {
            hotKey.setKeyName(hotKeys.get(hotKey));
        }
    }
}
