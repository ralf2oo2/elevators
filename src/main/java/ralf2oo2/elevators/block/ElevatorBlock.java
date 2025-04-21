package ralf2oo2.elevators.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.HasMetaNamedBlockItem;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.elevators.state.property.Color;
import ralf2oo2.elevators.state.property.Direction;

@HasMetaNamedBlockItem
public class ElevatorBlock extends TemplateBlock {
    public static final EnumProperty<Color> COLOR_ENUM_PROPERTY = EnumProperty.of("color", Color.class);
    public static final EnumProperty<Direction> DIRECTION_ENUM_PROPERTY = EnumProperty.of("direction", Direction.class);

    public ElevatorBlock(Identifier identifier, Material material) {
        super(identifier, material);
        this.setDefaultState(getStateManager().getDefaultState().with(COLOR_ENUM_PROPERTY, Color.WHITE).with(DIRECTION_ENUM_PROPERTY, Direction.NONE));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(COLOR_ENUM_PROPERTY, DIRECTION_ENUM_PROPERTY);
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player) {
        if(player.getHand() != null && player.getHand().itemId == Item.DYE.id && player.getHand().count > 0){
            if(useDye(world, x, y, z, player)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity placer) {
        if(placer instanceof PlayerEntity player && player.getHand() != null && player.getHand().getDamage() == 1){
            int rotation = MathHelper.floor((double)(placer.yaw * 4.0F / 360.0F) + (double)0.5F) & 3;
            BlockState elevatorState = world.getBlockState(x, y, z);
            switch (rotation){
                case 0:
                    elevatorState = elevatorState.with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, Direction.SOUTH);
                    break;
                case 1:
                    elevatorState = elevatorState.with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, Direction.WEST);
                    break;
                case 2:
                    elevatorState = elevatorState.with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, Direction.NORTH);
                    break;
                case 3:
                    elevatorState = elevatorState.with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, Direction.EAST);
                    break;
            }
            world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorState, player.getHand().getDamage());
        }
    }

    private boolean useDye(World world, int x, int y, int z, PlayerEntity player){
        BlockState elevatorBlockState = world.getBlockState(x, y, z);
        int blockMeta = world.getBlockMeta(x, y, z);
        Color currentColor = elevatorBlockState.get(COLOR_ENUM_PROPERTY);
        boolean usedDye = false;
        switch (player.getHand().getDamage()){
            case 0:
                if(currentColor != Color.BLACK){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.BLACK), blockMeta);
                    usedDye = true;
                }
                break;
            case 1:
                if(currentColor != Color.RED){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.RED), blockMeta);
                    usedDye = true;
                }
                break;
            case 2:
                if(currentColor != Color.GREEN){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.GREEN), blockMeta);
                    usedDye = true;
                }
                break;
            case 3:
                if(currentColor != Color.BROWN){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.BROWN), blockMeta);
                    usedDye = true;
                }
                break;
            case 4:
                if(currentColor != Color.BLUE){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.BLUE), blockMeta);
                    usedDye = true;
                }
                break;
            case 5:
                if(currentColor != Color.PURPLE){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.PURPLE), blockMeta);
                    usedDye = true;
                }
                break;
            case 6:
                if(currentColor != Color.CYAN){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.CYAN), blockMeta);
                    usedDye = true;
                }
                break;
            case 7:
                if(currentColor != Color.LIGHT_GRAY){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.LIGHT_GRAY), blockMeta);
                    usedDye = true;
                }
                break;
            case 8:
                if(currentColor != Color.GRAY){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.GRAY), blockMeta);
                    usedDye = true;
                }
                break;
            case 9:
                if(currentColor != Color.PINK){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.PINK), blockMeta);
                    usedDye = true;
                }
                break;
            case 10:
                if(currentColor != Color.LIME){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.LIME), blockMeta);
                    usedDye = true;
                }
                break;
            case 11:
                if(currentColor != Color.YELLOW){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.YELLOW), blockMeta);
                    usedDye = true;
                }
                break;
            case 12:
                if(currentColor != Color.LIGHT_BLUE){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.LIGHT_BLUE), blockMeta);
                    usedDye = true;
                }
                break;
            case 13:
                if(currentColor != Color.MAGENTA){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.MAGENTA), blockMeta);
                    usedDye = true;
                }
                break;
            case 14:
                if(currentColor != Color.ORANGE){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.ORANGE), blockMeta);
                    usedDye = true;
                }
                break;
            case 15:
                if(currentColor != Color.WHITE){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, elevatorBlockState.with(COLOR_ENUM_PROPERTY, Color.WHITE), blockMeta);
                    usedDye = true;
                }
                break;
        }
        if(usedDye){
            player.getHand().count--;
        }
        return usedDye;
    }
}
