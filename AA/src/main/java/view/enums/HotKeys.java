package view.enums;

public enum HotKeys {
    FREEZE("Shift"),
    LEFT("Z"),
    RIGHT("X"),
    LEFT2("O"),
    RIGHT2("P"),
    SHOOT("Space"),
    PLAYER2SHOOT("L");
    private String keyName;

    HotKeys(String keyName) {
        this.keyName = keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public boolean equals(String keyName) {
        return this.keyName.equals(keyName);
    }

    public String getKeyName() {
        return this.keyName;
    }
}
