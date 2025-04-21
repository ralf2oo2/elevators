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

import java.util.HashMap;
import java.util.Map;

@HasMetaNamedBlockItem
public class ElevatorBlock extends TemplateBlock {
    public Color color;
    public static final EnumProperty<Direction> DIRECTION_ENUM_PROPERTY = EnumProperty.of("direction", Direction.class);
    public static Map<Color, ElevatorBlock> colors = new HashMap<>();

    public ElevatorBlock(Identifier identifier, Material material, Color color) {
        super(identifier, material);
        this.setDefaultState(getStateManager().getDefaultState().with(DIRECTION_ENUM_PROPERTY, Direction.NONE));
        this.color = color;
        if(color != null){
            colors.put(color, this);
        }
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DIRECTION_ENUM_PROPERTY);
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
        Direction direction = elevatorBlockState.get(ElevatorBlock.DIRECTION_ENUM_PROPERTY);
        int blockMeta = world.getBlockMeta(x, y, z);
        boolean usedDye = false;
        switch (player.getHand().getDamage()){
            case 0:
                if(color != Color.BLACK && colors.containsKey(Color.BLACK)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.BLACK).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 1:
                if(color != Color.RED && colors.containsKey(Color.RED)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.RED).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 2:
                if(color != Color.GREEN && colors.containsKey(Color.GREEN)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.GREEN).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 3:
                if(color != Color.BROWN && colors.containsKey(Color.BROWN)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.BROWN).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 4:
                if(color != Color.BLUE && colors.containsKey(Color.BLUE)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.BLUE).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 5:
                if(color != Color.PURPLE && colors.containsKey(Color.PURPLE)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.PURPLE).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 6:
                if(color != Color.CYAN && colors.containsKey(Color.CYAN)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.CYAN).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 7:
                if(color != Color.LIGHT_GRAY && colors.containsKey(Color.LIGHT_GRAY)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.LIGHT_GRAY).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 8:
                if(color != Color.GRAY && colors.containsKey(Color.GRAY)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.GRAY).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 9:
                if(color != Color.PINK && colors.containsKey(Color.PINK)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.PINK).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 10:
                if(color != Color.LIME && colors.containsKey(Color.LIME)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.LIME).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 11:
                if(color != Color.YELLOW && colors.containsKey(Color.YELLOW)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.YELLOW).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 12:
                if(color != Color.LIGHT_BLUE && colors.containsKey(Color.LIGHT_BLUE)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.LIGHT_BLUE).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 13:
                if(color != Color.MAGENTA && colors.containsKey(Color.MAGENTA)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.MAGENTA).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 14:
                if(color != Color.ORANGE && colors.containsKey(Color.ORANGE)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.ORANGE).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
                    usedDye = true;
                }
                break;
            case 15:
                if(color != Color.WHITE && colors.containsKey(Color.WHITE)){
                    world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(Color.WHITE).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
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
