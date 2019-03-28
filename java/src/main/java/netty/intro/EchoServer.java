package netty.intro;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Date;

public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        new EchoServer(8888).start();
    }

    public void start() throws Exception{
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        NioEventLoopGroup worker = new NioEventLoopGroup();

        EchoServerHandler echoServerHandler = new EchoServerHandler();

        try {
            serverBootstrap.group(worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            nioSocketChannel.pipeline().addLast(new StringEncoder());
                            nioSocketChannel.pipeline().addLast(echoServerHandler);
                        }
                    });
            System.out.println("server start up!");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            worker.shutdownGracefully().sync();
            System.out.println("worker shutdown now " + new Date());
        }

    }
}
