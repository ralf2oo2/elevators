package ralf2oo2.elevators.network.packet.s2c;

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
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;
import ralf2oo2.elevators.Elevators;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.state.property.Direction;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class PlaySoundPacket extends Packet implements ManagedPacket<PlaySoundPacket> {
    public static final PacketType<PlaySoundPacket> TYPE = PacketType.builder(true, false, PlaySoundPacket::new).build();

    private double x;
    private double y;
    private double z;
    private String sound;
    private float volume;
    private float pitch;

    public PlaySoundPacket(){}
    public PlaySoundPacket(double x, double y, double z, String sound, float volume, float pitch){
        this.x = x;
        this.y = y;
        this.z = z;
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public @NotNull PacketType<PlaySoundPacket> getType() {
        return TYPE;
    }

    @Override
    public void read(DataInputStream stream) {
        try {
            this.x = stream.readDouble();
            this.y = stream.readDouble();
            this.z = stream.readDouble();
            this.sound = readString(stream, 100);
            this.volume = stream.readFloat();
            this.pitch = stream.readFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void write(DataOutputStream stream) {
        try {
            stream.writeDouble(x);
            stream.writeDouble(y);
            stream.writeDouble(z);
            writeString(sound, stream);
            stream.writeFloat(volume);
            stream.writeFloat(pitch);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return 3 * Double.BYTES + sound.length() + 2 * Float.BYTES;
    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        PlayerEntity playerEntity = PlayerHelper.getPlayerFromPacketHandler(networkHandler);
        if(playerEntity != null){
            playerEntity.world.playSound(x, y, z, sound, volume, pitch);
        }
    }
}
