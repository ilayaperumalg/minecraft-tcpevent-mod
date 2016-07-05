package com.example.examplemod.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Used to select which properties to serialize.
 *
 * @author Eric Bottard
 */
public abstract class MapColorMixin {

	@JsonProperty
	public int colorValue;

}
