package net.identitytheft.bleached.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.identitytheft.bleached.Bleached;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class ModItems {

	public static Item EMPTY_JUG;
	public static Item WATER_JUG;
	public static Item BLEACH;

	private static Item register(Block block) {
		return register(Registries.BLOCK.getId(block), new BlockItem(block, new FabricItemSettings()));
	}

	private static Item register(String id, Item item) {
		return register(new Identifier(Bleached.MOD_ID, id), item);
	}

	private static Item register(Identifier id, Item item) {
		return Registry.register(Registries.ITEM, id, item);
	}

	private static void addItemToGroup(ItemGroup group, Item item) {
		ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
	}

	public static void registerItems() {
		Bleached.LOGGER.info("Registering Items for " + Bleached.MOD_ID);

		EMPTY_JUG = register("empty_jug", new EmptyJugItem(new FabricItemSettings()));
		WATER_JUG = register("water_jug", new WaterJugItem(new FabricItemSettings().maxCount(1)));
		BLEACH = register("bleach", new BleachItem(new FabricItemSettings().food(ModFoodComponents.BLEACH).maxCount(1).recipeRemainder(EMPTY_JUG)));

		addItemToGroup(ModItemGroups.BLEACHED, EMPTY_JUG);
		addItemToGroup(ModItemGroups.BLEACHED, WATER_JUG);
		addItemToGroup(ModItemGroups.BLEACHED, BLEACH);
	}
}
