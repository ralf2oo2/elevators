package ralf2oo2.elevators.block;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.client.model.block.BlockWithWorldRenderer;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.elevators.block.entity.HiddenElevatorBlockEntity;
import ralf2oo2.elevators.state.property.Color;

import java.util.List;
import java.util.Random;

public class HiddenElevatorBlock extends ElevatorBlock implements BlockWithWorldRenderer {
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
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if(player.getHand() != null && player.getHand().getItem() instanceof BlockItem blockItem){
            HiddenElevatorBlockEntity blockEntity = (HiddenElevatorBlockEntity) world.getBlockEntity(x, y, z);
            blockEntity.setCamouflageBlock(blockItem);
            world.setBlockDirty(x, y, z);
            return true;
        }
        else {
            return super.onUse(world, x, y, z, player);
        }
    }

    @Override
    public boolean renderWorld(BlockRenderManager blockRenderManager, BlockView blockView, int x, int y, int z) {
        if(blockView.getBlockEntity(x, y, z) instanceof HiddenElevatorBlockEntity blockEntity && blockEntity.camouflageBlock != null){
            blockRenderManager.renderBlock(BlockRegistry.INSTANCE.get(blockEntity.camouflageBlock), x, y, z);
        }
        return true;
    }
}
