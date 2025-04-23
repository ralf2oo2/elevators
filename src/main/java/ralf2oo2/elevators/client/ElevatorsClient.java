package ralf2oo2.elevators.client;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class ElevatorsClient {
    public static int multiplayerDelayTicks;
    public static Minecraft getMc(){
        return Minecraft.class.cast(FabricLoader.getInstance().getGameInstance());
    }
}
