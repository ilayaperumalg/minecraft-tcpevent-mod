package com.example.examplemod.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Used to select which properties to serialize.
 *
 * @author Eric Bottard
 */
public abstract class BlockEventMixin extends BlockEvent{

	// Won't be used
	private BlockEventMixin() {
		super(null, null, null);
	}

	@Override
	@JsonProperty
	public abstract BlockPos getPos();

}
