package ralf2oo2.elevators.block.entity;

import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;

public class HiddenElevatorBlockEntity extends BlockEntity {
    public int camouflageBlockId;
    public void setCamouflageBlock(BlockItem blockItem){
        this.camouflageBlockId = blockItem.getBlock().id;
    }
}