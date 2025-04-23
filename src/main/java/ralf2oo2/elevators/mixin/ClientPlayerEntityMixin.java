package ralf2oo2.elevators.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.network.packet.PacketHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ralf2oo2.elevators.Elevators;
import ralf2oo2.elevators.ElevatorsConfig;
import ralf2oo2.elevators.block.ElevatorBlock;
import ralf2oo2.elevators.client.ElevatorsClient;
import ralf2oo2.elevators.network.packet.c2s.TeleportToElevatorPacket;
import ralf2oo2.elevators.state.property.Color;

import javax.annotation.Nullable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends Entity {

    public ClientPlayerEntityMixin(World world) {
        super(world);
    }

    @Inject(method = "updateKey", at = @At("HEAD"), cancellable = true)
    void elevator_updateKey(int key, boolean state, CallbackInfo ci){
        boolean cancelInput = false;
        if(world == null) return;
        if((key == ElevatorsClient.getMc().options.sneakKey.code || key == ElevatorsClient.getMc().options.jumpKey.code) && state){
            PlayerEntity playerEntity = PlayerEntity.class.cast(this);
            if(playerEntity.onGround){
                int playerX = (int)Math.floor(x);
                int playerY = (int)Math.floor(y - Elevators.PLAYER_HEIGHT + 0.5);
                int playerZ = (int)Math.floor(z);
                int belowId = world.getBlockId(playerX, playerY - 1, playerZ);
                if(Block.BLOCKS[belowId] instanceof ElevatorBlock elevatorBlock){
                    BlockPos origin = new BlockPos(playerX, playerY - 1, playerZ);
                    BlockPos elevatorPos = null;
                    if(key == ElevatorsClient.getMc().options.sneakKey.code){
                        elevatorPos = findElevator(origin, ElevatorsConfig.config.elevatorDistanceLimit, elevatorBlock.color, false);
                    }
                    else if(key == ElevatorsClient.getMc().options.jumpKey.code){
                        elevatorPos = findElevator(origin, ElevatorsConfig.config.elevatorDistanceLimit, elevatorBlock.color, true);
                    }
                    if(elevatorPos != null){
                        moveToElevator(new BlockPos(playerX, playerY, playerZ), elevatorPos);
                        if(key == ElevatorsClient.getMc().options.jumpKey.code){
                            cancelInput = true;
                        }
                    }
                }
            }
        }
        if(cancelInput){
            ci.cancel();
        }
    }

    private BlockPos findElevator(BlockPos origin, int searchLimit, @Nullable Color color, boolean searchUpward){
        int start = origin.getY() + (searchUpward ? 1 : -1);
        int end = origin.getY() + (searchUpward ? searchLimit + 1 : -searchLimit - 1);
        int step = searchUpward ? 1 : -1;

        int blockCount = 0;

        for(int y = start; searchUpward ? y < end : y > end; y += step){
            if (y < world.getBottomY() || y > world.getTopY()) break;
            int blockId = world.getBlockId(origin.getX(), y, origin.getZ());
            if(blockCount > ElevatorsConfig.config.blockPassthroughLimit) break;

            if(shouldCountTowardsLimit(origin.getX(), y, origin.getZ(), ElevatorsConfig.config.ignoreBlocksThatDontSuffocatePlayerCheckingLimit, true)) blockCount++;
            if(Block.BLOCKS[blockId] instanceof ElevatorBlock){
                boolean isSafe = !shouldCountTowardsLimit(origin.getX(), y + 1, origin.getZ(), ElevatorsConfig.config.ignoreBlocksThatDontSuffocatePlayerCheckingSafety, false)
                        && !shouldCountTowardsLimit(origin.getX(), y + 2, origin.getZ(), ElevatorsConfig.config.ignoreBlocksThatDontSuffocatePlayerCheckingSafety, false);

                if(isSafe){
                    if(color != null){
                        BlockState state = world.getBlockState(origin.getX(), y, origin.getZ());
                        if (state.getBlock() instanceof ElevatorBlock elevator && elevator.color == color){
                            return new BlockPos(origin.getX(), y, origin.getZ());
                        }
                    }
                    else{
                        return new BlockPos(origin.getX(), y, origin.getZ());
                    }
                }
            }
        }
        return null;
    }

    public void moveToElevator(BlockPos origin, BlockPos elevatorPos){
        if(world.isRemote){
            if(ElevatorsClient.multiplayerDelayTicks == 0){
                PacketHelper.send(new TeleportToElevatorPacket(origin, elevatorPos));
                ElevatorsClient.multiplayerDelayTicks = 20;
            }
        }
        else {
            PacketHelper.send(new TeleportToElevatorPacket(origin, elevatorPos));
        }
    }

    private boolean shouldCountTowardsLimit(int x, int y, int z, boolean allowNonSuffocatingBlocks, boolean ignoreElevator){
        int blockId = world.getBlockId(x, y, z);
        if(blockId == 0) return false;
        Block block = Block.BLOCKS[blockId];
        if(block instanceof ElevatorBlock && ignoreElevator) return false;
        if(!world.shouldSuffocate(x, y, z) && allowNonSuffocatingBlocks) return false;
        return true;
    }
}
