package model.game.enums;

public enum Map {
    EMPTY_MAP("empty"),  // todo : delete
    AGING("aging"),
    VYING("vying"),
    ;

    public static Map getMapByName(String name) {
        for (Map map : values()) {
            if (map.name.equals(name))
                return map;
        }
        return null;
    }

    private final String name;
    Map(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
