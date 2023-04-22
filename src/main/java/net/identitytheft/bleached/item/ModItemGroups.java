package net.identitytheft.bleached.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.identitytheft.bleached.Bleached;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
	public static final ItemGroup BLEACHED = FabricItemGroup.builder(new Identifier(Bleached.MOD_ID, "bleached"))
			.displayName(Text.translatable("itemGroup.bleached.bleached"))
			.icon(() -> new ItemStack(ModItems.BLEACH))
			.build();
}
