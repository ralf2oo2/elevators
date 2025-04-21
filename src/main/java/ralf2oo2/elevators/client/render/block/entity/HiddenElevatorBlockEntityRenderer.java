package ralf2oo2.elevators.client.render.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import org.lwjgl.opengl.GL11;
import ralf2oo2.elevators.block.entity.HiddenElevatorBlockEntity;
import ralf2oo2.elevators.client.ElevatorsClient;
import ralf2oo2.elevators.mixin.MinecraftAccessor;
import ralf2oo2.elevators.mixin.WorldRendererAccessor;

public class HiddenElevatorBlockEntityRenderer extends BlockEntityRenderer {
    @Override
    public void render(BlockEntity blockEntity, double x, double y, double z, float tickDelta) {
        if(blockEntity instanceof HiddenElevatorBlockEntity hiddenElevatorBlockEntity){
            BlockRenderManager blockRenderManager = ((WorldRendererAccessor)((MinecraftAccessor)ElevatorsClient.getMc()).getWorldRenderer()).getBlockRenderManager();
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            Tessellator tessellator = Tessellator.INSTANCE;
            tessellator.startQuads();
            blockRenderManager.render(Block.BLOCKS[hiddenElevatorBlockEntity.camouflageBlockId] ,(int)x, (int)y + 1, (int)z);
            tessellator.draw();
            GL11.glPopMatrix();
        }
    }
}
