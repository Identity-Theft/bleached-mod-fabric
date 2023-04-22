package net.identitytheft.bleached.entity.damage;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;

public class ModDamageSource extends DamageSource {
    public static final DamageSource DRANK_BLEACH = new ModDamageSource("drank_bleach");

    protected ModDamageSource(String name) {
        super(RegistryEntry.of(new DamageType(name, 0)));
    }
}
