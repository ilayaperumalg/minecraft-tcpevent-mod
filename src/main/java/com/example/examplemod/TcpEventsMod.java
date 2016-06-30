package com.example.examplemod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * A simple mod that broadcasts all sorts of Minecraft events over a tcp server using Netty.
 *
 * @author Eric Bottard
 */
@Mod(modid = TcpEventsMod.MODID, version = TcpEventsMod.VERSION)
public class TcpEventsMod {

	public static final String MODID = "tcpevent";

	public static final String VERSION = "1.0";

	private BroadcastServer broadcastServer;

	@EventHandler
	public void init(FMLInitializationEvent event) {
		broadcastServer = new BroadcastServer(8080);
		Thread t = new Thread() {

			@Override
			public void run() {
				try {
					broadcastServer.run();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();

		MinecraftForge.EVENT_BUS.register(new BroadcastEventListener(broadcastServer));
	}

}
