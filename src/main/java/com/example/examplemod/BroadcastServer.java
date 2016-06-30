package com.example.examplemod;

import com.example.examplemod.mixin.BlockEventMixin;
import com.example.examplemod.mixin.BlockEventPlaceEventMixin;
import com.example.examplemod.mixin.BlockPosMixin;
import com.example.examplemod.mixin.EntityPlayerMixin;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;

/**
 * Creates a Netty server that can accept connections and then broadcast JSON
 * serialized representations of objects to all connected clients.
 *
 * <p>Messages are zero terminated.</p>
 *
 * @author Eric Bottard
 */
public class BroadcastServer {

	private int port;

	private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	private ObjectMapper objectMapper = new ObjectMapper();

	private final static byte[] ZERO = new byte[1];

	public BroadcastServer(int port) {
		this.port = port;

		// Ignore everything by default
		objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
				.with(JsonAutoDetect.Visibility.NONE));

		// Then selectively add per type via mixins
		objectMapper.addMixIn(BlockEvent.class, BlockEventMixin.class);
		objectMapper.addMixIn(BlockEvent.PlaceEvent.class, BlockEventPlaceEventMixin.class);
		objectMapper.addMixIn(BlockPos.class, BlockPosMixin.class);
		objectMapper.addMixIn(EntityPlayer.class, EntityPlayerMixin.class);
	}

	public void broadcast(Object o) {
		try {
			channels.writeAndFlush(Unpooled.copiedBuffer(
					objectMapper.writeValueAsString(o) + "\n",
					CharsetUtil.UTF_8)).await();
			channels.writeAndFlush(Unpooled.copiedBuffer(ZERO)).await();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap(); // (2)
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class) // (3)
					.childHandler(new ChannelInitializer<SocketChannel>() { // (4)

						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							channels.add(ch);
						}

					})
					.option(ChannelOption.SO_BACKLOG, 128)          // (5)
					.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

			// Bind and start to accept incoming connections.
			ChannelFuture f = b.bind(port).sync(); // (7)

			// Wait until the server socket is closed.
			// In this example, this does not happen, but you can do that to gracefully
			// shut down your server.
			f.channel().closeFuture().sync();
		}
		finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}

}
