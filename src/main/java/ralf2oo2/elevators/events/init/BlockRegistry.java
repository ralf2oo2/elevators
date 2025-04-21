package ralf2oo2.elevators.events.init;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.elevators.Elevators;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.block.HiddenElevatorBlock;

public class BlockRegistry {
    public static Block elevatorBlock;
    public static Block hiddenElevatorBlock;
    @EventListener
    public void registerBlocks(BlockRegistryEvent event){
        elevatorBlock = new ElevatorBlock(Identifier.of(Elevators.NAMESPACE, "elevator_block"), Material.WOOL).setTranslationKey(Elevators.NAMESPACE, "elevator_block");
        hiddenElevatorBlock = new HiddenElevatorBlock(Identifier.of(Elevators.NAMESPACE, "hidden_elevator_block"), Material.WOOL).setTranslationKey(Elevators.NAMESPACE, "hidden_elevator_block");
    }
}
