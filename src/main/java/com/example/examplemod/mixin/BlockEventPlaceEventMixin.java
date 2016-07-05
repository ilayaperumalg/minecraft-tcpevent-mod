package com.example.examplemod.mixin;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Used to select which properties to serialize.
 *
 * @author Eric Bottard
 */
public abstract class BlockEventPlaceEventMixin extends BlockEvent.PlaceEvent{

	// Won't be used
	private BlockEventPlaceEventMixin() {
		super(null, null, null);
	}

	@Override
	@JsonProperty
	public abstract IBlockState getPlacedBlock();

}
