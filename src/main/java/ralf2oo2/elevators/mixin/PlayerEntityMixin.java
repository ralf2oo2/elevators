package ralf2oo2.elevators.mixin;

import net.fabricmc.loader.impl.lib.sat4j.specs.IConstr;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.HasMetaNamedBlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.elevators.ElevatorsConfig;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.events.init.BlockRegistry;
import ralf2oo2.elevators.state.property.Color;
import ralf2oo2.elevators.state.property.Direction;

import javax.annotation.Nullable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {

    private static float PLAYER_HEIGHT = 1.6F;

    boolean movedPlayer = false;
    boolean hasJumped;
    public PlayerEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    void elevator_tickMovement(CallbackInfo ci){
        if(hasJumped) System.out.println("hasjumped = true");
        PlayerEntity playerEntity = PlayerEntity.class.cast(this);
        if(playerEntity.onGround || hasJumped){
            int playerX = (int)Math.floor(x);
            int playerY = (int)Math.floor(y - PLAYER_HEIGHT);
            int playerZ = (int)Math.floor(z);
            int belowId = world.getBlockId(playerX, playerY - 1, playerZ);
            if(Block.BLOCKS[belowId] instanceof ElevatorBlock){
                BlockPos elevatorPos = null;
                if(playerEntity.isSneaking()){
                    if(!movedPlayer){
                        movedPlayer = true;
                        elevatorPos = findElevatorBelow(playerX, playerY, playerZ, ElevatorsConfig.config.elevatorDistanceLimit, null);
                    }
                }
                else if(this.hasJumped){
                    if(!movedPlayer){
                        movedPlayer = true;
                        elevatorPos = findElevatorAbove(playerX, playerY, playerZ, ElevatorsConfig.config.elevatorDistanceLimit, null);
                    }
                }
                else {
                    movedPlayer = false;
                }
                if(elevatorPos != null){
                    moveToElevator(elevatorPos);
                }
            }
        }
        this.hasJumped = false;
    }

    @Inject(method = "jump", at = @At("HEAD"))
    void elevator_jump(CallbackInfo ci){
        System.out.println("jump");
        this.hasJumped = true;
    }

    public BlockPos findElevatorBelow(int x, int y, int z, int searchLimit, @Nullable Color elevatorColor){
        BlockPos elevatorPos = null;
        for(int i = y - 2; i > y - 2 - searchLimit; i--){
            System.out.println(i - (y - 2));
            if(i < world.getBottomY()){
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

    public void moveToElevator(BlockPos elevatorPos){

        BlockState elevatorBlockState = world.getBlockState(elevatorPos);
        if(elevatorBlockState.contains(ElevatorBlock.DIRECTION_ENUM_PROPERTY)){
            Direction direction = elevatorBlockState.get(ElevatorBlock.DIRECTION_ENUM_PROPERTY);
            if(direction != Direction.NONE){
                int playerYaw = switch (direction){
                    case EAST -> 270;
                    case SOUTH -> 0;
                    case WEST -> 90;
                    default-> 180;
                };
                this.setRotation(playerYaw, pitch);
            }
        }
        this.setPosition(elevatorPos.x + 0.5d, elevatorPos.y + PLAYER_HEIGHT + 1.1, elevatorPos.z + 0.5D);
        this.setVelocityClient(0,0,0);
        System.out.println(elevatorPos.y);
        world.playSound(elevatorPos.x, elevatorPos.y + 1, elevatorPos.z, "elevators:block.warp", 1.0F, 1.0F);
    }
}
