package ralf2oo2.elevators.state.property;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum Color implements StringIdentifiable {
    WHITE,
    ORANGE,
    MAGENTA,
    LIGHT_BLUE,
    YELLOW,
    LIME,
    PINK,
    GRAY,
    LIGHT_GRAY,
    CYAN,
    PURPLE,
    BLUE,
    BROWN,
    GREEN,
    RED,
    BLACK;

    @Override
    public String asString() {
        return this.name().toLowerCase();
    }
}
