package com.example.examplemod.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.minecraft.util.math.BlockPos;

/**
 * Used to select which properties to serialize.
 *
 * @author Eric Bottard
 */
public abstract class BlockEventMixin {

	@JsonProperty
	public abstract BlockPos getPos();

}
