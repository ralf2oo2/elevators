package ralf2oo2.elevators.state.property;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum Direction implements StringIdentifiable {
    NORTH,
    EAST,
    SOUTH,
    WEST,
    NONE;

    @Override
    public String asString() {
        return this.name().toLowerCase();
    }
}
