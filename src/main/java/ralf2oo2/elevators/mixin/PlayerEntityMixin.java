package ralf2oo2.elevators.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.lib.sat4j.specs.IConstr;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.HasMetaNamedBlockItem;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.elevators.Elevators;
import ralf2oo2.elevators.ElevatorsConfig;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.client.ElevatorsClient;
import ralf2oo2.elevators.events.init.BlockRegistry;
import ralf2oo2.elevators.network.packet.c2s.TeleportToElevatorPacket;
import ralf2oo2.elevators.state.property.Color;
import ralf2oo2.elevators.state.property.Direction;

import javax.annotation.Nullable;

@Mixin(ClientPlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {

    public PlayerEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "updateKey", at = @At("HEAD"))
    void elevator_tickMovement(int key, boolean state, CallbackInfo ci){
        if(world == null) return;
        if((key == ElevatorsClient.getMc().options.sneakKey.code || key == ElevatorsClient.getMc().options.jumpKey.code) && state){
            PlayerEntity playerEntity = PlayerEntity.class.cast(this);
            if(playerEntity.onGround){ //playerEntity.onGround
                int playerX = (int)Math.floor(x);
                int playerY = (int)Math.floor(y - Elevators.PLAYER_HEIGHT + 0.1);
                int playerZ = (int)Math.floor(z);
                int belowId = world.getBlockId(playerX, playerY - 1, playerZ);
                if(Block.BLOCKS[belowId] instanceof ElevatorBlock elevatorBlock){
                    BlockPos elevatorPos = null;
                    if(key == ElevatorsClient.getMc().options.sneakKey.code){
                        System.out.println("Distance limit" + ElevatorsConfig.config.elevatorDistanceLimit);
                        elevatorPos = findElevatorBelow(playerX, playerY, playerZ, ElevatorsConfig.config.elevatorDistanceLimit, elevatorBlock.color);
                    }
                    else if(key == ElevatorsClient.getMc().options.jumpKey.code){
                        elevatorPos = findElevatorAbove(playerX, playerY, playerZ, ElevatorsConfig.config.elevatorDistanceLimit, elevatorBlock.color);
                    }
                    if(elevatorPos != null){
                        moveToElevator(new BlockPos(playerX, playerY, playerZ), elevatorPos);
                    }
                }
            }
        }
    }

    public BlockPos findElevatorBelow(int x, int y, int z, int searchLimit, @Nullable Color elevatorColor){
        BlockPos elevatorPos = null;
        for(int i = y - 2; i > y - 2 - searchLimit; i--){
            System.out.println(i - (y - 2));
            if(world == null)break;
            if(i < world.getBottomY()){
                break;
            }
            int currentBlockId = world.getBlockId(x, i, z);
            if(Block.BLOCKS[currentBlockId] instanceof ElevatorBlock){
                boolean isSafe = true;
                if(i + 1 <= world.getTopY() && world.shouldSuffocate(x, i + 1, z)) isSafe = false;;
                if(i + 2 <= world.getTopY() && world.shouldSuffocate(x, i + 2, z)) isSafe = false;
                if(isSafe){
                    if(elevatorColor != null){
                        BlockState elevatorBlockState = world.getBlockState(x, i, z);
                        if(elevatorBlockState.getBlock() instanceof ElevatorBlock elevatorBlock && elevatorBlock.color == elevatorColor){
                            elevatorPos = new BlockPos(x, i, z);
                            break;
                        }
                    }
                    else {
                        elevatorPos = new BlockPos(x, i, z);
                        break;
                    }
                }
            }
            else {
                world.setBlock(x, y, z, Block.GLASS.id);
            }
        }
        return elevatorPos;
    }

    public BlockPos findElevatorAbove(int x, int y, int z, int searchLimit, @Nullable Color elevatorColor){
        BlockPos elevatorPos = null;
        for(int i = y; i < y + searchLimit; i++){
            System.out.println(i - y);
            if(i > world.getTopY()){
                break;
            }
            if(Block.BLOCKS[world.getBlockId(x, i, z)] instanceof ElevatorBlock){
                boolean isSafe = true;
                if(i + 1 <= world.getTopY() && world.shouldSuffocate(x, i + 1, z)) isSafe = false;;
                if(i + 2 <= world.getTopY() && world.shouldSuffocate(x, i + 2, z)) isSafe = false;
                if(isSafe){
                    if(elevatorColor != null){
                        BlockState elevatorBlockState = world.getBlockState(x, i, z);
                        if(elevatorBlockState.getBlock() instanceof ElevatorBlock elevatorBlock && elevatorBlock.color == elevatorColor){
                            elevatorPos = new BlockPos(x, i, z);
                            break;
                        }
                    }
                    else {
                        elevatorPos = new BlockPos(x, i, z);
                        break;
                    }
                }
            }
        }
        return elevatorPos;
    }

    public void moveToElevator(BlockPos origin, BlockPos elevatorPos){

        PacketHelper.send(new TeleportToElevatorPacket(origin, elevatorPos));
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT){
            this.setVelocityClient(0,0,0);
        }
        System.out.println(elevatorPos.y);
    }
}
