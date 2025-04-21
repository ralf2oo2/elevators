package ralf2oo2.elevators.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.elevators.Elevators;
import ralf2oo2.elevators.block.entity.HiddenElevatorBlockEntity;

public class BlockEntityRegistry {
    @EventListener
    public void registerBlockEntities(BlockEntityRegisterEvent event){
        event.register(HiddenElevatorBlockEntity.class, Identifier.of(Elevators.NAMESPACE, "hidden_elevator_blockentity").toString());
    }
}
