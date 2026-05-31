package ralf2oo2.elevators.block.entity;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;

public class HiddenElevatorBlockEntity extends BlockEntity {
    public Identifier camouflageBlock;
    public void setCamouflageBlock(BlockItem blockItem){
        this.camouflageBlock = BlockRegistry.INSTANCE.getId(blockItem.getBlock());
    }
}