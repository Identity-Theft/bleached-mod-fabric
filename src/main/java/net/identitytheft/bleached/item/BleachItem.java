package net.identitytheft.bleached.item;

import net.identitytheft.bleached.entity.damage.ModDamageSource;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class BleachItem extends Item {
	public BleachItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		super.finishUsing(stack, world, user);

		user.damage(ModDamageSource.DRANK_BLEACH, Float.MAX_VALUE);

		if (user instanceof ServerPlayerEntity) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)user;
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);
			serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

			if (serverPlayerEntity.isAlive()) {
				serverPlayerEntity.getHungerManager().add(20,20);
				serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
				serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
			}
		}

		if (user.isAlive() && !((PlayerEntity)user).getAbilities().creativeMode) {
			return new ItemStack(ModItems.EMPTY_JUG);
		} else if (!((PlayerEntity)user).getAbilities().creativeMode) {
			PlayerEntity playerEntity = (PlayerEntity)user;

			ItemStack itemStack = new ItemStack(ModItems.EMPTY_JUG);
			playerEntity.dropItem(itemStack, true, false);
			return ItemStack.EMPTY;
		}

		return stack;
	}

	@Override
	public int getMaxUseTime(ItemStack stack) {
		return 40;
	}

	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.DRINK;
	}

	@Override
	public SoundEvent getDrinkSound() {
		return SoundEvents.ENTITY_GENERIC_DRINK;
	}

	@Override
	public SoundEvent getEatSound() {
		return SoundEvents.ENTITY_GENERIC_DRINK;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}
