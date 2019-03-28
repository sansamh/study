package netty.intro;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {

    private String host;
    private int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception{
        new EchoClient("127.0.0.1", 8888).start();
    }

    public void start() throws Exception{
        Bootstrap bootstrap = new Bootstrap();

        EventLoopGroup worker = new NioEventLoopGroup();

        try{
            bootstrap.group(worker)
                    .channel(NioSocketChannel.class)    //指定NIO的传输方式
                    .remoteAddress(host, port)
                    .handler(new ChannelInitializer<SocketChannel>(){ //向channel的pipeline添加handler
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            // 连接到远程节点，阻塞直到连接完成
            ChannelFuture channelFuture = bootstrap.connect().sync();
            System.out.println("client wait");
            // 阻塞直到连接关闭
            channelFuture.channel().closeFuture().sync();
            System.out.println("client connect over");

        } finally {
            System.out.println("shutdown");
            worker.shutdownGracefully().sync();
        }
    }
}
