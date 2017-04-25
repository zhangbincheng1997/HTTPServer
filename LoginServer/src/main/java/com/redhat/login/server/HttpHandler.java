package com.redhat.login.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.redhat.login.protocol.Response;
import com.redhat.login.protocol.ResponseCode;
import com.redhat.login.util.AES;
import com.redhat.login.core.GameServer;
import com.redhat.login.util.Coder;
import com.redhat.login.util.Config;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
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
		// 服务器已开启
		if (!GameServer.getInstance().getShutdown()) {
			// 获取请求
			DefaultFullHttpRequest req = (DefaultFullHttpRequest) msg;

			// 处理get请求
			if (req.getMethod() == HttpMethod.GET) {
				getHandle(ctx, req);
			}
			// 处理POST请求
			if (req.getMethod() == HttpMethod.POST) {
				postHandle(ctx, req);
			}
		}
		// 服务器已关闭
		else {
			writeJSON(ctx, new Response(ResponseCode.SERVER_ERR, null));
		}
	}

	//////////

	// GET请求处理
	private void getHandle(final ChannelHandlerContext ctx, DefaultFullHttpRequest req) {
		// 获取消息
		// String msg = req.content().toString(CharsetUtil.UTF_8);
		writeJSON(ctx, HttpResponseStatus.NOT_IMPLEMENTED, new Response(ResponseCode.COMMON_ERR, null));
	}

	// POST请求处理
	private void postHandle(final ChannelHandlerContext ctx, final DefaultFullHttpRequest req) {
		// 获取消息
		String msg = req.content().toString(CharsetUtil.UTF_8);
		if (msg != null) {
			Filter.getInstance().doFilter(msg, ctx);
		}
	}

	//////////

	/**
	 * ctx status msg
	 */
	public static void writeJSON(ChannelHandlerContext ctx, HttpResponseStatus status, Object msg) {
		String sentMsg = null;
		if (msg instanceof String) { // String
			sentMsg = (String) msg;
		} else { // JSON
			sentMsg = JSON.toJSONString(msg);
		}
		// 加密 true使用AES false使用Base64
		sentMsg = Config.USE_AES ? AES.AESEncode(Config.AES_KEY, sentMsg) : Coder.encodeToBase64(sentMsg);
		writeJSON(ctx, status, Unpooled.copiedBuffer(sentMsg, CharsetUtil.UTF_8));
	}

	/**
	 * ctx msg
	 */
	public static void writeJSON(ChannelHandlerContext ctx, Object msg) {
		String sentMsg = null;
		if (msg instanceof String) { // String
			sentMsg = (String) msg;
		} else { // JSON
			sentMsg = JSON.toJSONString(msg);
		}
		// 加密 true使用AES false使用Base64
		sentMsg = Config.USE_AES ? AES.AESEncode(Config.AES_KEY, sentMsg) : Coder.encodeToBase64(sentMsg);
		writeJSON(ctx, HttpResponseStatus.OK, Unpooled.copiedBuffer(sentMsg, CharsetUtil.UTF_8));
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
			ctx.flush();
		}
	}
	/////

	/**
	 * Netty抛出异常
	 */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage());
	}
}
