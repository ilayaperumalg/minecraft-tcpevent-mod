package com.example.examplemod.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;

/**
 * Used to select which properties to serialize.
 *
 * @author Eric Bottard
 */
public abstract class BlockMixin extends Block {

	// Will never be called
	private BlockMixin() {
		super(null);
	}

	@JsonProperty
	public MapColor blockMapColor;

	@Override
	@JsonProperty
	public abstract String getUnlocalizedName();
}
