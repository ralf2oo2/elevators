package ralf2oo2.elevators.network.packet.c2s;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.math.BlockPos;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.entity.player.PlayerHelper;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;
import ralf2oo2.elevators.Elevators;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.network.packet.s2c.TeleportConfirmationPacket;
import ralf2oo2.elevators.server.ElevatorsServer;
import ralf2oo2.elevators.state.property.Direction;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TeleportToElevatorPacket extends Packet implements ManagedPacket<TeleportToElevatorPacket> {
    public static final PacketType<TeleportToElevatorPacket> TYPE = PacketType.builder(false, true, TeleportToElevatorPacket::new).build();

    private BlockPos origin;
    private BlockPos target;

    public TeleportToElevatorPacket(){}
    public TeleportToElevatorPacket(BlockPos origin, BlockPos target){
        this.origin = origin;
        this.target = target;
    }

    @Override
    public @NotNull PacketType<TeleportToElevatorPacket> getType() {
        return TYPE;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            int originX = stream.readInt();
            int originY = stream.readInt();
            int originZ = stream.readInt();

            int targetX = stream.readInt();
            int targetY = stream.readInt();
            int targetZ = stream.readInt();

            this.origin = new BlockPos(originX, originY, originZ);
            this.target = new BlockPos(targetX, targetY, targetZ);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeInt(origin.getX());
            stream.writeInt(origin.getY());
            stream.writeInt(origin.getZ());

            stream.writeInt(target.getX());
            stream.writeInt(target.getY());
            stream.writeInt(target.getZ());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return 6 * Integer.BYTES;
    }

    // TODO: add some sort of checking here to avoid invalid teleports
    @Override
    public void apply(NetworkHandler networkHandler) {
        PlayerEntity playerEntity = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(playerEntity != null){
            float playerYaw = playerEntity.yaw;
            BlockState elevatorBlockState = playerEntity.world.getBlockState(target);
            if(elevatorBlockState.contains(ElevatorBlock.DIRECTION_ENUM_PROPERTY)){
                Direction direction = elevatorBlockState.get(ElevatorBlock.DIRECTION_ENUM_PROPERTY);
                if(direction != Direction.NONE){
                    playerYaw = switch (direction){
                        case EAST -> 270F;
                        case SOUTH -> 0f;
                        case WEST -> 90F;
                        default-> 180F;
                    };
                }
            }
            if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER){
                if(playerEntity instanceof ServerPlayerEntity serverPlayerEntity){
                    serverPlayerEntity.networkHandler.teleport(target.getX() + 0.5d, target.getY() + 1.1D, target.getZ() + 0.5D, playerYaw, playerEntity.pitch);
                    PacketHelper.sendTo(playerEntity, new TeleportConfirmationPacket());
                    ElevatorsServer.playSoundForPlayersInRange(serverPlayerEntity.world, target.getX(), target.getY() + 1, target.getZ(), "elevators:block.warp", 1.0F, 1.0F, 10);
                }
            }
            else {
                playerEntity.setPositionAndAngles(target.getX() + 0.5d, target.getY() + Elevators.PLAYER_HEIGHT + 1.1D, target.getZ() + 0.5D, playerYaw, playerEntity.pitch);
                playerEntity.world.playSound(target.getX(), target.getY() + 1, target.getZ(), "elevators:block.warp", 1.0F, 1.0F);
            }
        }
    }
}
