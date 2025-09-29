package object;

public enum ObjectType {
    NONE(0),
    KEY(1),
    COIN(2),
    DOOR_CLOSED(3),
    DOOR_OPENED(4),
    HATCH_CLOSED(5),
    HATCH_OPENED(6),
    COIN_RED(7),
    COIN_PUR(8);

    private final int id;

    ObjectType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ObjectType fromId(int id) {
        for (ObjectType type : values()) {
            if (type.id == id) return type;
        }
        return NONE;
    }
}