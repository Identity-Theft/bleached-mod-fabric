package identitytheft.bleached.entity.damage;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static identitytheft.bleached.Bleached.MOD_ID;

public class ModDamageTypes {
	public static final RegistryKey<DamageType> DRANK_BLEACH = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(MOD_ID, "drank_bleach"));

	public static DamageSource of(World world, RegistryKey<DamageType> key)
	{
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
	}
}
