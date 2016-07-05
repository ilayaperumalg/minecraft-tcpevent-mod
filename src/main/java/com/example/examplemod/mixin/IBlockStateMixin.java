package com.example.examplemod.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

/**
 * Used to select which properties to serialize.
 *
 * @author Eric Bottard
 */
public abstract class IBlockStateMixin implements IBlockState{

	@Override
	@JsonProperty
	public abstract Block getBlock();

}
