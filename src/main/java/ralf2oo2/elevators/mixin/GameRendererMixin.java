package ralf2oo2.elevators.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResultType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.elevators.ElevatorsConfig;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.events.init.TextureRegistry;
import ralf2oo2.elevators.state.property.Direction;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow private Minecraft client;
    
    @Inject(method = "renderFrame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderBlockOutline(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/hit/HitResult;ILnet/minecraft/item/ItemStack;F)V"))
    void elevators_renderFrame(float time, long par2, CallbackInfo ci){
        if(!ElevatorsConfig.config.renderArrow) return;
        PlayerEntity player = (PlayerEntity)client.camera;

        if (client.crosshairTarget.type != HitResultType.BLOCK || !player.isSneaking()) return;

        int x = client.crosshairTarget.blockX;
        int y = client.crosshairTarget.blockY;
        int z = client.crosshairTarget.blockZ;

        World world = player.world;
        int blockId = world.getBlockId(x, y, z);

        if(!(Block.BLOCKS[blockId] instanceof ElevatorBlock)) return;

        int meta = world.getBlockMeta(x, y, z);
        if(meta != 1) return;

        BlockState blockState = world.getBlockState(x, y, z);
        if(!blockState.contains(ElevatorBlock.DIRECTION_ENUM_PROPERTY)) return;

        Direction direction = blockState.get(ElevatorBlock.DIRECTION_ENUM_PROPERTY);
        int rotation = switch (direction){
            case EAST -> 1;
            case SOUTH -> 3;
            case WEST -> 2;
            default -> 0;
        };

        float dx = (float) (x - MathHelper.lerp(time, player.prevX, player.x));
        float dy = (float) (y - MathHelper.lerp(time, player.prevY, player.y));
        float dz = (float) (z - MathHelper.lerp(time, player.prevZ, player.z));

        GL11.glPushMatrix();

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glTranslatef(dx, dy, dz);

        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.startQuads();

        BlockRenderManager blockRenderManager = ((WorldRendererAccessor)client.worldRenderer).getBlockRenderManager();
        ((BlockRenderManagerAccessor)blockRenderManager).setTopFaceRotation(rotation);
        blockRenderManager.renderTopFace(Block.BLOCKS[blockId], 0, 0, 0, TextureRegistry.directionalArrowTexture.index);
        tessellator.draw();
        ((BlockRenderManagerAccessor)blockRenderManager).setTopFaceRotation(0);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
