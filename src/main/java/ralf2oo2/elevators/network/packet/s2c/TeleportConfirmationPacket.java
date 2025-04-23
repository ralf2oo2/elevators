package ralf2oo2.elevators.network.packet.s2c;

import net.minecraft.network.NetworkHandler;
import net.minecraft.network.packet.Packet;
import net.modificationstation.stationapi.api.network.packet.ManagedPacket;
import net.modificationstation.stationapi.api.network.packet.PacketType;
import org.jetbrains.annotations.NotNull;
import ralf2oo2.elevators.client.ElevatorsClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class TeleportConfirmationPacket extends Packet implements ManagedPacket<TeleportConfirmationPacket> {
    public static final PacketType<TeleportConfirmationPacket> TYPE = PacketType.builder(true, false, TeleportConfirmationPacket::new).build();

    @Override
    public void read(DataInputStream stream) {

    }

    @Override
    public void write(DataOutputStream stream) {

    }

    @Override
    public void apply(NetworkHandler networkHandler) {
        ElevatorsClient.multiplayerDelayTicks = 0;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public @NotNull PacketType<TeleportConfirmationPacket> getType() {
        return TYPE;
    }
}
