package ralf2oo2.elevators.state.property;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum Color implements StringIdentifiable {
    BLACK,
    RED,
    GREEN,
    BROWN,
    BLUE,
    PURPLE,
    CYAN,
    LIGHT_GRAY,
    GRAY,
    PINK,
    LIME,
    YELLOW,
    LIGHT_BLUE,
    MAGENTA,
    ORANGE,
    WHITE;

    @Override
    public String asString() {
        return this.name().toLowerCase();
    }
}
