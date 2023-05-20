package model.game.enums;

public enum Level {
    LEVEL_1(1, 20, 5, 1.2, 7),
    LEVEL_2(2, 25, 10, 1.5, 5),
    LEVEL_3(3, 30, 15, 1.8, 3),
    ;

    public static Level getLevelByNumber(int number) {
        for (Level level : values()) {
            if (level.number == number)
                return level;
        }
        return null;
    }

    Level(int number, int scorePerBall, int rotationSpeed, double windSpeed, int freezeTime) {
        this.number = number;
        this.scorePerBall = scorePerBall;
        this.rotationSpeed = rotationSpeed;
        this.windSpeed = windSpeed;
        this.freezeTime = freezeTime;
    }

    private final int number;
    private final int scorePerBall;
    private final int rotationSpeed;
    private final double windSpeed;
    private final int freezeTime;

    public int getNumber() {
        return number;
    }

    public int getScorePerBall() {
        return scorePerBall;
    }

    public int getRotationSpeed() {
        return rotationSpeed;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public int getFreezeTime() {
        return freezeTime;
    }
}
