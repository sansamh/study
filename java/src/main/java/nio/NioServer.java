package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    public static void main(String[] args) throws IOException {
        // 创建选择器
        Selector selector = Selector.open();
        // 创建选择器 设置非阻塞 绑定到selector和通知事件
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        ServerSocket socket = serverSocketChannel.socket();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 8888);
        socket.bind(address);
        System.out.println("nio 服务端已启动！");
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                    // 服务器会为每个新连接创建一个 SocketChannel
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    // 这个新连接主要用于从客户端读取数据
                    accept.register(selector, SelectionKey.OP_READ);

                } else if (next.isReadable()) {
                    SocketChannel channel = (SocketChannel) next.channel();
                    handleReadableChannel(channel);
                    channel.close();
                }
                // 移除已经处理的SelectionKey
                iterator.remove();
            }
        }
    }

    private static void handleReadableChannel(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        StringBuffer data = new StringBuffer();

        while (true) {
            buffer.clear();
            int n = channel.read(buffer);
            if (n == -1) {
                break;
            }

            buffer.flip();
            int limit = buffer.limit();
            char [] dst = new char[limit];

            for (int i = 0; i < limit; i++) {
                dst[i] = (char) buffer.get(i);
            }
            data.append(dst);
            buffer.clear();

        }
        System.out.println("服务端接受到消息：" + data.toString());

    }
}
