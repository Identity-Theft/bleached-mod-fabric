package net.identitytheft.bleached.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.identitytheft.bleached.Bleached;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroups {
	public static final ItemGroup BLEACHED = FabricItemGroupBuilder.build(
		new Identifier(Bleached.MOD_ID, "bleached"), () -> new ItemStack(ModItems.BLEACH));
}
