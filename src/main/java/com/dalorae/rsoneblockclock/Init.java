package com.dalorae.rsoneblockclock;

import com.dalorae.rsoneblockclock.block.ClockBlock;
import com.dalorae.rsoneblockclock.container.ClockBlockContainer;
import com.dalorae.rsoneblockclock.tileentity.ClockBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Init {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RSOneBlockClock.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RSOneBlockClock.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TET = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, RSOneBlockClock.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERTYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, RSOneBlockClock.MOD_ID);
    //public static final DeferredRegister<TileEntity> TILEENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, RSOneBlockClock.MOD_ID);
    //Blocks
    public static final RegistryObject<Block> Block_RSOBC = BLOCKS
            .register("rsobc", () -> new ClockBlock());
    //Items
    public static final RegistryObject<BlockItem> Item_RSOBC = ITEMS
            .register("rsobc", () -> new BlockItem(Block_RSOBC.get(), new Item.Properties().tab(ItemGroup.TAB_REDSTONE)));
    //TETypes
    public static final RegistryObject<TileEntityType<ClockBlockTileEntity>> TET_RSOBC = TET
            .register("rsobc", () -> TileEntityType.Builder.of(ClockBlockTileEntity::new, Block_RSOBC.get()).build(null));
    //Containers
    public static final RegistryObject<ContainerType<ClockBlockContainer>> Container_RSOBC = CONTAINERTYPES
            .register("rsobc", () -> IForgeContainerType.create((windowId, playerInv, data) -> new ClockBlockContainer(windowId, playerInv, new Inventory(9))));
}
