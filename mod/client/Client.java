package net.ledestudio.example.mod.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.logging.Logger;

public class Client {
    private String host;
    private int port;
    private Channel channel;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        try {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ClientInitializer());

            // Start the client.
            ChannelFuture f = b.connect(host, port).sync(); // (5)

            Logger.getLogger("Network").info("Client started");

            // Wait until the connection is closed.
            // f.channel().closeFuture().sync();
        } finally {
            // workerGroup.shutdownGracefully();
        }
    }

    public void sendPacket(ByteBuf buf) {
        if(channel != null && channel.isActive()) {
            channel.writeAndFlush(buf);
        }
    }
}
