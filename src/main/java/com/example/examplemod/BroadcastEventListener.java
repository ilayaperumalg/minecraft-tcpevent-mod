package com.example.examplemod;

import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Used to intercept some Minecraft events and broadcast them
 * using a {@link BroadcastServer}.
 *
 * @author Eric Bottard
 */
public class BroadcastEventListener {

	private final BroadcastServer broadcastServer;

	public BroadcastEventListener(BroadcastServer broadcastServer) {
		this.broadcastServer = broadcastServer;
	}

	@SubscribeEvent
	public void blockPlaced(BlockEvent.PlaceEvent blockEvent) {
		broadcastServer.broadcast(blockEvent);
	}

	// TODO: add more event types?

}
