package ralf2oo2.elevators.server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import ralf2oo2.elevators.network.packet.s2c.PlaySoundPacket;

import java.util.ArrayList;
import java.util.List;

public class ElevatorsServer {
    public static void playSoundForPlayersInRange(World world, double x, double y, double z, String sound, float volume, float pitch, double range){
        PlayerEntity[] playerEntities = getPlayerEntitiesInRange(world, x, y, z, range);
        for(PlayerEntity player : playerEntities){
            PacketHelper.sendTo(player, new PlaySoundPacket(x, y, z, sound, volume, pitch));
        }
    }

    public static PlayerEntity[] getPlayerEntitiesInRange(World world, double x, double y, double z, double range){
        List players = new ArrayList<PlayerEntity>();
        for(int var12 = 0; var12 < world.players.size(); ++var12) {
            PlayerEntity player = (PlayerEntity)world.players.get(var12);
            double distance = player.getDistance(x, y, z);

            if (distance < range && player.dimensionId == world.dimension.id) {
                players.add(player);
            }
        }
        PlayerEntity[] playerEntityArray = new PlayerEntity[players.size()];
        playerEntityArray = (PlayerEntity[]) players.toArray(playerEntityArray);
        return playerEntityArray;
    }
}
