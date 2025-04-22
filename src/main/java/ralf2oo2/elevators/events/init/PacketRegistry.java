package ralf2oo2.elevators.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.network.packet.PacketRegisterEvent;
import net.modificationstation.stationapi.api.registry.PacketTypeRegistry;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.elevators.Elevators;
import ralf2oo2.elevators.network.packet.c2s.TeleportToElevatorPacket;
import ralf2oo2.elevators.network.packet.s2c.PlaySoundPacket;

public class PacketRegistry {
    @EventListener
    public void registerPackets(PacketRegisterEvent event){
        Registry.register(PacketTypeRegistry.INSTANCE, Identifier.of(Elevators.NAMESPACE, "teleport_to_elevator"), TeleportToElevatorPacket.TYPE);
        Registry.register(PacketTypeRegistry.INSTANCE, Identifier.of(Elevators.NAMESPACE, "play_sound"), PlaySoundPacket.TYPE);
    }
}
