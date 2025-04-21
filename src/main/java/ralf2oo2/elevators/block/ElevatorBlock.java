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
        if(player.getHand().getDamage() >= Color.values().length) return false;

        BlockState elevatorBlockState = world.getBlockState(x, y, z);
        Direction direction = elevatorBlockState.get(ElevatorBlock.DIRECTION_ENUM_PROPERTY);
        Color dyeColor = Color.values()[player.getHand().getDamage()];
        int blockMeta = world.getBlockMeta(x, y, z);

        if(color != dyeColor && colors.containsKey(dyeColor)){
            world.setBlockStateWithMetadataWithNotify(x, y, z, colors.get(dyeColor).getDefaultState().with(ElevatorBlock.DIRECTION_ENUM_PROPERTY, direction), blockMeta);
            player.getHand().count--;
            return true;
        }
        return false;
    }

    @Override
    protected int getDroppedItemMeta(int blockMeta) {
        return blockMeta;
    }
}
