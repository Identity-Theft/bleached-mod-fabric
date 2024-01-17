package net.identitytheft.bleached.item;

import net.identitytheft.bleached.Bleached;
import net.identitytheft.bleached.entity.damage.ModDamageSource;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class BleachItem extends Item {
	public BleachItem(Settings settings) {
		super(settings);
	}

	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
		boolean creativeMode = playerEntity != null ? playerEntity.getAbilities().creativeMode : false;

		user.damage(ModDamageSource.DRANK_BLEACH, Float.MAX_VALUE);

		if (playerEntity instanceof ServerPlayerEntity serverPlayerEntity) {
			Criteria.CONSUME_ITEM.trigger(serverPlayerEntity, stack);

			if (serverPlayerEntity.isAlive()) {
				serverPlayerEntity.getHungerManager().add(20,20);
				serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 600, 0));
				serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0));
			}
		}

		if (playerEntity != null)
		{
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

			if (!creativeMode) {
				stack.decrement(1);
			}
		}

		if (user.isAlive()) {
			if (playerEntity == null || !creativeMode) {
				if (stack.isEmpty()) {
					return new ItemStack(Bleached.EMPTY_JUG);
				}

				if (playerEntity != null) {
					playerEntity.getInventory().insertStack(new ItemStack(Bleached.EMPTY_JUG));
				}
			}
		} else if (!creativeMode) {

			ItemStack itemStack = new ItemStack(Bleached.EMPTY_JUG);
			playerEntity.dropItem(itemStack, true, false);
			return ItemStack.EMPTY;
		}

		user.emitGameEvent(GameEvent.DRINK);
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
		return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
	}

	@Override
	public SoundEvent getEatSound() {
		return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
}
