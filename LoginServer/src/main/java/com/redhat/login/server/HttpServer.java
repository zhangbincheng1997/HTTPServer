package com.redhat.login.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.redhat.login.util.Config;

/**
 * @author littleredhat
 * 
 *         Http
 */
public class HttpServer {
	private static HttpServer instance = null;
	private static Logger logger = LoggerFactory.getLogger(HttpServer.class);
	private NioEventLoopGroup bossGroup = null;
	private NioEventLoopGroup workGroup = null;

	// 单例模式
	public static HttpServer getInstance() {
		if (instance == null) {
			instance = new HttpServer();
		}
		return instance;
	}

	/**
	 * 开启服务器
	 */
	public void start() {
		bossGroup = new NioEventLoopGroup();
		workGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("decoder", new HttpRequestDecoder());
					pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
					pipeline.addLast("encoder", new HttpResponseEncoder());
					pipeline.addLast("http-chunked", new ChunkedWriteHandler());
					/* handler */
					pipeline.addLast("handler", new HttpHandler());
				}
			});
			ChannelFuture f = bootstrap.bind(Config.LoginLocalPort).sync();
			if (f.isSuccess()) {
				logger.info("端口绑定: " + Config.LoginLocalPort);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 关闭服务器
	 */
	public void shut() {
		workGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
		logger.info("服务器关闭");
	}
}
