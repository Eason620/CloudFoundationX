package com.ziyao.cfx.im.client.core;

import com.ziyao.cfx.im.api.Event;
import com.ziyao.cfx.im.api.Live;
import com.ziyao.cfx.im.api.Packet;
import com.ziyao.cfx.im.client.NettyClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/6/30
 */
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<Packet> {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(ClientHandler.class);

    private PacketReceiver packetReceiver;

    private final NettyClient nettyClient;

    public ClientHandler(PacketReceiver packetReceiver, NettyClient nettyClient) {
        this.packetReceiver = packetReceiver;
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOGGER.info("Connection SUCCESS!");
        Channel channel = ctx.channel();
        // Send heartbeat
        channel.writeAndFlush(new Packet(Event.HEARTBEAT, Live.PING));
        Packet open = new Packet();
        open.setEvent(Event.OPEN);
        open.setReceivedBys(List.of("ziyao"));
        ctx.channel().writeAndFlush(open);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) {
        getPacketReceiver().onReceive(packet, ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        nettyClient.start();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    public PacketReceiver getPacketReceiver() {
        return packetReceiver;
    }

    public void setPacketReceiver(PacketReceiver packetReceiver) {
        this.packetReceiver = packetReceiver;
    }
}
