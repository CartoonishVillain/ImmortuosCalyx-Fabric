package com.cartoonishvillain.immortuoscalyx.blocks;

import com.cartoonishvillain.immortuoscalyx.component.InfectionComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;

import static com.cartoonishvillain.immortuoscalyx.component.ComponentStarter.INFECTION;

public class InfectionScanner extends Block {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public InfectionScanner() {
        super(Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops());
        this.registerDefaultState(defaultBlockState().setValue(POWERED, false));
    }

    @Override
    public boolean isSignalSource(BlockState p_149744_1_) {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public void stepOn(Level world, BlockPos blockPos, BlockState p_152433_, Entity entity) {
        BlockState blockState = world.getBlockState(blockPos);
        if(!world.isClientSide()){
            blockState = redstoneStrength(entity, blockState);}
        world.setBlockAndUpdate(blockPos, blockState.setValue(POWERED, blockState.getValue(POWERED)));    }


    private BlockState redstoneStrength(Entity infected, BlockState state) {
        InfectionComponent h = INFECTION.get(infected);
        boolean logic = h.getInfectionProgress() > 0;
        return state.setValue(POWERED, logic);
    }
    @Override
    public int getSignal (BlockState state, BlockGetter blockAccess, BlockPos pos, Direction side) { return state.getValue(POWERED) ? 15 : 0; }

}
