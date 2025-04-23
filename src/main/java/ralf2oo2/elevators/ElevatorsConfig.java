package ralf2oo2.elevators;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;
import net.glasslauncher.mods.gcapi3.api.ConfigRoot;

public class ElevatorsConfig {
    @ConfigRoot(value = "config", visibleName = "Elevators Config")
    public static ConfigFields config = new ConfigFields();
    public static class ConfigFields{
        @ConfigEntry(
                name = "Elevator Distance Limit",
                description = "The max amount of blocks the elevator will search for another elevator block",
                maxLength = 1000,
                minLength = 1,
                multiplayerSynced = true
        )
        public Integer elevatorDistanceLimit = 20;

        @ConfigEntry(
                name = "Block Pass-through Limit",
                description = "The max amount of blocks the elevator can pass through",
                maxLength = 1000,
                minLength = 0,
                multiplayerSynced = true
        )
        public Integer blockPassthroughLimit = 4;

        @ConfigEntry(
                name = "Ignore Non-suffocating Blocks Checking Limit",
                description = "Makes non-suffocating blocks not count towards the block pass-through limit",
                multiplayerSynced = true
        )
        public Boolean ignoreBlocksThatDontSuffocatePlayerCheckingLimit = true;

        @ConfigEntry(
                name = "Ignore Non-suffocating Blocks Checking Safety",
                description = "Ignore non-suffocating blocks when checking if the area above the elevator isn't solid",
                multiplayerSynced = true
        )
        public Boolean ignoreBlocksThatDontSuffocatePlayerCheckingSafety = true;

        @ConfigEntry(
                name = "Show Arrow",
                description = "Show an arrow that points at the direction of a directional elevator when sneaking"
        )
        public Boolean renderArrow = true;
    }
}
