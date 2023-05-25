package view.enums;

public enum HotKeys {
    FREEZE("Shift"),
    LEFT("Z"),
    RIGHT("X"),
    SHOOT("Space"),
    PLAYER2SHOOT("Q")
    ;
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

    @Override
    public String toString() {
        return this.keyName;
    }
}
