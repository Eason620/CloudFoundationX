package com.ziyao.cfx.mpusher.server.adapter;

import com.ziyao.cfx.mpusher.api.Live;
import com.ziyao.cfx.mpusher.api.Packet;
import com.ziyao.cfx.mpusher.api.State;
import com.ziyao.cfx.mpusher.core.ChannelManager;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
@ChannelHandler.Sharable
public class ShareInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(ShareInboundHandlerAdapter.class);


    /**
     * 用户上线时出发该方法
     *
     * @param ctx {@link ChannelHandlerContext}
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOGGER.info("online:{}", ctx.channel().localAddress());
        ChannelManager.storage(ctx.channel());
        ctx.channel().writeAndFlush(new Packet(State.PONG, Live.PONG));
    }

    /**
     * 用户下线触发该方法
     *
     * @param ctx Context object, containing channels{@link Channel}
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        LOGGER.info("CommonHandler: User offline=====>ip：{}", ctx.channel().localAddress());
        ChannelManager.remove(ctx);
        ctx.channel().close();
    }

    /**
     * 异常触发该方法
     *
     * @param ctx   Context object
     * @param cause Exception information
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        try {
            ChannelManager.remove(ctx);
            ctx.channel().close();
            LOGGER.error("CommonHandler: Message exception！", cause);
        } catch (Exception e) {
            LOGGER.error("处理异常信息失败！{}", e.getMessage());
        }
    }


    /**
     * 读取客户端发送信息
     *
     * @param ctx Context object, containing channels{@link io.netty.channel.Channel}
     *            channel{@link io.netty.channel.ChannelPipeline}
     * @see io.netty.channel.Channel
     * @see io.netty.channel.ChannelPipeline
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                LOGGER.debug("success!");
            } else {
                LOGGER.debug("failure!");
            }
        });
    }
}
