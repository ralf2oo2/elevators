package ralf2oo2.elevators.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.elevators.Elevators;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.block.HiddenElevatorBlock;
import ralf2oo2.elevators.state.property.Color;

public class BlockRegistry {
    public static Block[] elevatorBlocks;
    public static Block hiddenElevatorBlock;
    @EventListener
    public void registerBlocks(BlockRegistryEvent event){
        Color[] colors = Color.values();
        elevatorBlocks = new ElevatorBlock[colors.length];
        for(int i = 0; i < colors.length; i++){
            elevatorBlocks[i] = new ElevatorBlock(Identifier.of(Elevators.NAMESPACE, colors[i].asString() + "_elevator_block"), Material.WOOL, colors[i]).setTranslationKey(Elevators.NAMESPACE, colors[i].asString() + "_elevator_block");
        }
        hiddenElevatorBlock = new HiddenElevatorBlock(Identifier.of(Elevators.NAMESPACE, "hidden_elevator_block"), Material.WOOL, null).setTranslationKey(Elevators.NAMESPACE, "hidden_elevator_block");
    }
}
