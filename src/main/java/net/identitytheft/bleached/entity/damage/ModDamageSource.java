package net.identitytheft.bleached.entity.damage;

import net.minecraft.entity.damage.DamageSource;

public class ModDamageSource extends DamageSource{
    public static final DamageSource DRANK_BLEACH = new ModDamageSource("drank_bleach");

    protected ModDamageSource(String name) {
        super(name);
    }
}
