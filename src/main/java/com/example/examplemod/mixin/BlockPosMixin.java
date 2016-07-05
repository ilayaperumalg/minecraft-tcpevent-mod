package com.example.examplemod.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.minecraft.util.math.BlockPos;

/**
 * Used to select which properties to serialize.
 *
 * @author Eric Bottard
 */
public abstract class BlockPosMixin extends BlockPos{

	// Won't be used
	private BlockPosMixin() {
		super(0, 0, 0);
	}

	@Override
	@JsonProperty
	public abstract int getX();

	@Override
	@JsonProperty
	public abstract int getY();

	@Override
	@JsonProperty
	public abstract int getZ();
}
