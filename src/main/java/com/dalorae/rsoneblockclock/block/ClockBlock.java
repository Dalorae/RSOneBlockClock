package com.dalorae.rsoneblockclock.block;


import com.dalorae.rsoneblockclock.Init;
import com.dalorae.rsoneblockclock.tileentity.ClockBlockTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;

public class ClockBlock extends ContainerBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private int signalStr = 0;

    public ClockBlock() {
        super(AbstractBlock.Properties
                .of(Material.STONE, MaterialColor.COLOR_BLACK)
                .harvestTool(ToolType.PICKAXE)
                .strength(10f,10f)
                .harvestLevel(1));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, Boolean.valueOf(false)));
    }

    public void toggleSignal(int signalStr, World world, BlockPos bPos) {
        this.signalStr = signalStr;
        world.updateNeighborsAt(bPos, this);
    }

    public int getSignal(BlockState p_180656_1_, IBlockReader p_180656_2_, BlockPos p_180656_3_, Direction p_180656_4_) {
        return this.signalStr;
    }

    public ActionResultType use(BlockState bState, World worldIn, BlockPos bPos, PlayerEntity player, Hand handIn, BlockRayTraceResult brtRes) {
        if (worldIn.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = worldIn.getBlockEntity(bPos);
            if (tileentity instanceof ClockBlockTileEntity) {
                player.openMenu((ClockBlockTileEntity)tileentity);
            }

            return ActionResultType.CONSUME;
        }
    }

    @Nullable
    @Override
    public ToolType getHarvestTool(BlockState state) {
        return ToolType.PICKAXE;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    public TileEntity newBlockEntity(IBlockReader ibreader) {
        return new ClockBlockTileEntity();
    }

    @Override
    public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        if (p_180633_5_.hasCustomHoverName()) {
            TileEntity tileentity = p_180633_1_.getBlockEntity(p_180633_2_);
            if (tileentity instanceof ClockBlockTileEntity) {
                ((ClockBlockTileEntity)tileentity).setCustomName(p_180633_5_.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, World world, BlockPos blockPos, BlockState blockStateD, boolean b) {
        if (!blockState.is(blockStateD.getBlock())) {
            TileEntity tileentity = world.getBlockEntity(blockPos);
            if (tileentity instanceof ClockBlockTileEntity) {
                InventoryHelper.dropContents(world, blockPos, (ClockBlockTileEntity)tileentity);
                world.updateNeighbourForOutputSignal(blockPos, this);
            }

            super.onRemove(blockState, world, blockPos, blockStateD, b);
        }
    }

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> bState) {
        bState.add(POWERED);
    }

}
