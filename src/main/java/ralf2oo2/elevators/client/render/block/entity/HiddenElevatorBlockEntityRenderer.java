package ralf2oo2.elevators.client.render.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.lwjgl.opengl.GL11;
import ralf2oo2.elevators.block.entity.HiddenElevatorBlockEntity;
import ralf2oo2.elevators.client.ElevatorsClient;
import ralf2oo2.elevators.mixin.WorldRendererAccessor;

public class HiddenElevatorBlockEntityRenderer extends BlockEntityRenderer {
    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float tickDelta) {

    }
}
