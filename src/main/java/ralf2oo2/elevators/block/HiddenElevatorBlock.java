package ralf2oo2.elevators.block;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.elevators.block.entity.HiddenElevatorBlockEntity;
import ralf2oo2.elevators.events.init.BlockRegistry;
import ralf2oo2.elevators.state.property.Color;

import java.util.Random;

public class HiddenElevatorBlock extends ElevatorBlock{
    public HiddenElevatorBlock(Identifier identifier, Material material, Color color) {
        super(identifier, material, color);
        BLOCKS_WITH_ENTITY[this.id] = true;
    }

    public void onPlaced(World world, int x, int y, int z) {
        super.onPlaced(world, x, y, z);
        world.setBlockEntity(x, y, z, this.createBlockEntity());
    }

    public void onBreak(World world, int x, int y, int z) {
        super.onBreak(world, x, y, z);
        world.removeBlockEntity(x, y, z);
    }

    public BlockEntity createBlockEntity(){
        return new HiddenElevatorBlockEntity();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return ElevatorBlock.colors.get(Color.WHITE).id;
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if(player.getHand() != null && player.getHand().getItem() instanceof BlockItem blockItem){
            HiddenElevatorBlockEntity blockEntity = (HiddenElevatorBlockEntity) world.getBlockEntity(x, y, z);
            blockEntity.setCamouflageBlock(blockItem);
            return true;
        }
        else {
            return super.onUse(world, x, y, z, player);
        }
    }
}
