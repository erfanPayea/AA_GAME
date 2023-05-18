package model.game.enums;

public enum Level {
    LEVEL_1(1),
    LEVEL_2(2),
    LEVEL_3(3),
    ;
    public static Level getLevelByNumber(int number) {
        for (Level level : values()) {
            if (level.number == number)
                return level;
        }
        return null;
    }

    Level(int number) {
        this.number = number;
    }

    private final int number;

    public int getNumber() {
        return number;
    }
}
