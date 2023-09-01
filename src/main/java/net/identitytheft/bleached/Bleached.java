package net.identitytheft.bleached;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.identitytheft.bleached.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bleached implements ModInitializer {
	public static final String MOD_ID = "bleached";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Item EMPTY_JUG  = register("empty_jug", new EmptyJugItem(new FabricItemSettings()));
	public static Item WATER_JUG = register("water_jug", new WaterJugItem(new FabricItemSettings().maxCount(1)));;
	public static Item BLEACH = register("bleach", new BleachItem(new FabricItemSettings().food(ModFoodComponents.BLEACH).maxCount(1).recipeRemainder(EMPTY_JUG)));

	public static final ItemGroup BLEACHED = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.bleached.bleached")).icon(() -> new ItemStack(BLEACH)).build();

	@Override
	public void onInitialize() {
		Registry.register(Registries.ITEM_GROUP, MOD_ID, BLEACHED);
		Registries.ITEM_GROUP.getKey(BLEACHED).ifPresent(key -> ItemGroupEvents.modifyEntriesEvent(key).register(entries -> {
			entries.add(EMPTY_JUG);
			entries.add(WATER_JUG);
			entries.add(BLEACH);
		}));
	}

	private static Item register(String id, Item item) {
		return register(new Identifier(Bleached.MOD_ID, id), item);
	}

	private static Item register(Identifier id, Item item) {
		return Registry.register(Registries.ITEM, id, item);
	}

}
