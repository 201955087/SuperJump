package net.ledestudio.example.mod.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import net.ledestudio.example.common.server.ServerInboundHandler;
import org.jetbrains.annotations.NotNull;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(@NotNull SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ServerInboundHandler());
    }
}
