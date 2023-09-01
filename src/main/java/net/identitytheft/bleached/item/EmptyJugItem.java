package net.identitytheft.bleached.item;

import net.identitytheft.bleached.Bleached;
import net.minecraft.block.BlockState;
import net.minecraft.block.FluidDrainable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class EmptyJugItem extends Item {
	public EmptyJugItem(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);

		BlockHitResult hitResult = this.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
		if (((HitResult)hitResult).getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = hitResult.getBlockPos();
			if (!world.canPlayerModifyAt(user, blockPos)) {
				return TypedActionResult.pass(itemStack);
			}
			if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
				world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0f, 1.0f);
				world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);
				return TypedActionResult.success(this.fill(itemStack, user, new ItemStack(Bleached.WATER_JUG)));
			}
		}

		BlockPos blockPos = hitResult.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);

		FluidDrainable fluidDrainable;
		ItemStack itemStack2;
		if (blockState.getBlock() instanceof FluidDrainable && !(itemStack2 = (fluidDrainable = (FluidDrainable)(blockState.getBlock())).tryDrainFluid(world, blockPos, blockState)).isEmpty()) {
			user.incrementStat(Stats.USED.getOrCreateStat(this));

			fluidDrainable.getBucketFillSound().ifPresent(sound -> user.playSound(sound, 1.0f, 1.0f));
			world.emitGameEvent(user, GameEvent.FLUID_PICKUP, blockPos);

			itemStack2 = new ItemStack(Bleached.WATER_JUG);
			ItemStack itemStack3 = ItemUsage.exchangeStack(itemStack, user, itemStack2);
			return TypedActionResult.success(itemStack3, world.isClient());
		}
		return TypedActionResult.fail(itemStack);
	}

	protected ItemStack fill(ItemStack stack, PlayerEntity player, ItemStack outputStack) {
		player.incrementStat(Stats.USED.getOrCreateStat(this));
		return ItemUsage.exchangeStack(stack, player, outputStack);
	}
}
