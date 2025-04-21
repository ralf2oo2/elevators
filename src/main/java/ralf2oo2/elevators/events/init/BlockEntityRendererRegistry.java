package ralf2oo2.elevators.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.block.entity.BlockEntityRendererRegisterEvent;
import ralf2oo2.elevators.block.entity.HiddenElevatorBlockEntity;
import ralf2oo2.elevators.client.render.block.entity.HiddenElevatorBlockEntityRenderer;

public class BlockEntityRendererRegistry {
    @EventListener
    public void registerBlockEntityRenderers(BlockEntityRendererRegisterEvent event){
        event.renderers.put(HiddenElevatorBlockEntity.class, new HiddenElevatorBlockEntityRenderer());
    }
}
