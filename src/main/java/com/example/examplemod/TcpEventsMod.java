package com.example.examplemod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLModDisabledEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * A simple mod that broadcasts all sorts of Minecraft events over a tcp server using Netty.
 * @author Eric Bottard
 */
@Mod(modid = TcpEventsMod.MODID,
		version = TcpEventsMod.VERSION,
		canBeDeactivated = true,
		guiFactory = "com.example.examplemod.TcpEventsModGuiFactory"
)
public class TcpEventsMod {

	public static final String MODID = "tcpevent";

	public static final String VERSION = "1.0";

	/*default*/ static Configuration config;

	private BroadcastServer broadcastServer;

	@EventHandler
	public void init(FMLPreInitializationEvent event) {

		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		int port = config.get("server", "port", 8080, "port the tcp server will be listening on").getInt();

		broadcastServer = new BroadcastServer(port);
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

	@EventHandler
	public void disable(FMLModDisabledEvent event) {
		System.out.println("Disable " + event);
	}
}
