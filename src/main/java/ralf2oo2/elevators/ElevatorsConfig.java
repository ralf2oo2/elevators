package ralf2oo2.elevators;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class ElevatorsConfig {
    @ConfigRoot(value = "config", visibleName = "Freecam Config")
    public static ConfigFields config = new ConfigFields();
    public static class ConfigFields{
        @ConfigEntry(
                name = "Elevator Distance Limit",
                description = "The max amount of blocks the elevator will search for another elevator block",
                maxLength = 1000,
                minLength = 1
        )
        public Integer elevatorDistanceLimit = 20;
    }
}
