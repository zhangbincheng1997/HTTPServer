package com.redhat.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.rpc.server.HttpHandler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

/**
 * @author littleredhat
 * 
 *         Handle
 */
public class HttpHandler extends ChannelHandlerAdapter {
	private static Logger logger = LoggerFactory.getLogger(HttpHandler.class);

	public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
		// 获取请求
		DefaultFullHttpRequest req = (DefaultFullHttpRequest) msg;
		// 获取类型
		String rpcType = req.headers().get("TYPE");
		// 确认节点
		if (rpcType != null) {

			// DEBUG
			logger.info(req.content().toString(CharsetUtil.UTF_8));

			// 分发数据到RPC
			RPCServer.getInstance().handle(rpcType, new ByteBufInputStream(req.content()),
					new ByteBufOutputStream(req.content()));
			// 回传数据到Gate
			writeJSON(ctx, HttpResponseStatus.OK, req.content());
			ctx.flush();
		}
	}

	/**
	 * 发送JSON消息
	 */
	private static void writeJSON(ChannelHandlerContext ctx, HttpResponseStatus status,
			ByteBuf content /* , boolean isKeepAlive */) {
		if (ctx.channel().isWritable()) {
			FullHttpResponse msg = null;
			// 数据非空
			if (content != null) {
				msg = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
				// Http固定头部
				msg.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json; charset=utf-8");
			} else {
				msg = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
			}
			// 数据非空
			if (msg.content() != null) {
				msg.headers().set(HttpHeaders.Names.CONTENT_LENGTH, msg.content().readableBytes());
			}
			// 弱联网短连接
			ctx.write(msg).addListener(ChannelFutureListener.CLOSE);
		}
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	}

	public void messageReceived(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
	}
}
