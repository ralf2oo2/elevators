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
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.events.init.TextureRegistry;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow private Minecraft client;

    // TODO: make this method more readable
    @Inject(method = "renderFrame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderBlockOutline(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/hit/HitResult;ILnet/minecraft/item/ItemStack;F)V"))
    void elevators_renderFrame(float time, long par2, CallbackInfo ci){
        PlayerEntity player = (PlayerEntity)client.camera;
        if(client.crosshairTarget.type == HitResultType.BLOCK && player.isSneaking()){
            int blockId = player.world.getBlockId(client.crosshairTarget.blockX, client.crosshairTarget.blockY, client.crosshairTarget.blockZ);
            if(Block.BLOCKS[blockId] instanceof ElevatorBlock){
                int blockMeta = player.world.getBlockMeta(client.crosshairTarget.blockX, client.crosshairTarget.blockY, client.crosshairTarget.blockZ);
                if(blockMeta == 1){
                    BlockState elevatorBlockState = player.world.getBlockState(client.crosshairTarget.blockX, client.crosshairTarget.blockY, client.crosshairTarget.blockZ);
                    if(elevatorBlockState.contains(ElevatorBlock.DIRECTION_ENUM_PROPERTY)){
                        GL11.glPushMatrix();
                        GL11.glDisable(GL11.GL_DEPTH_TEST);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glColor4f(1f, 1f, 1f, 1f);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glTranslatef((float) (client.crosshairTarget.blockX - MathHelper.lerp(time, player.prevX, player.x)), (float) (client.crosshairTarget.blockY - MathHelper.lerp(time, player.prevY, player.y)), (float) (client.crosshairTarget.blockZ - MathHelper.lerp(time, player.prevZ, player.z)));
                        Tessellator tessellator = Tessellator.INSTANCE;
                        tessellator.startQuads();
                        System.out.println("meta");
                        int rotation = switch (elevatorBlockState.get(ElevatorBlock.DIRECTION_ENUM_PROPERTY)){
                            case EAST -> 1;
                            case SOUTH -> 3;
                            case WEST -> 2;
                            default -> 0;
                        };
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
            }
        }
    }

    Vec3d getPlayerPos(PlayerEntity player, float delta){
        double x = MathHelper.lerp(delta, player.prevX, player.x);
        double y = MathHelper.lerp(delta, player.prevY, player.y);
        double z = MathHelper.lerp(delta, player.prevZ, player.z);
        return Vec3d.create(x, y, z);
    }
}
