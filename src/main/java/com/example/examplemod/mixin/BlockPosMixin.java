package com.example.examplemod.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Used to select which properties to serialize.
 *
 * @author Eric Bottard
 */
public abstract class BlockPosMixin {

	@JsonProperty
	public abstract int getX();

	@JsonProperty
	public abstract int getY();

	@JsonProperty
	public abstract int getZ();
}
