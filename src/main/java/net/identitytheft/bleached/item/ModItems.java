package net.identitytheft.bleached.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.identitytheft.bleached.Bleached;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

	//region Items
	public static final Item EMPTY_JUG = registerItem("empty_jug", new EmptyJugItem(new FabricItemSettings()
		.group(ModItemGroups.BLEACHED)));
	public static final Item WATER_JUG = registerItem("water_jug", new WaterJugItem(new FabricItemSettings()
		.maxCount(1)
		.group(ModItemGroups.BLEACHED)));
	public static final Item BLEACH = registerItem("bleach", new BleachItem(new FabricItemSettings()
		.food(ModFoodComponents.BLEACH)
		.maxCount(1)
		.recipeRemainder(EMPTY_JUG)
		.group(ModItemGroups.BLEACHED)));
	//endregion


	private static Item registerItem(String name, Item item) {
		return Registry.register(Registry.ITEM, new Identifier(Bleached.MOD_ID, name), item);
	}

	private static Item registerBlockItem(String name, Block block, ItemGroup group) {
		return Registry.register(Registry.ITEM, new Identifier(Bleached.MOD_ID, name),
			new BlockItem(block, new FabricItemSettings().group(group)));
	}

	public static void registerItems() {
		Bleached.LOGGER.info("Registering Items for " + Bleached.MOD_ID);
	}
}
