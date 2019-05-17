package com.study.distruptor.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable{

    private static final int port = 8765;
    private ByteBuffer readBuf = ByteBuffer.allocate(1024);
    private Selector selector;

    Server() throws IOException {
        selector = Selector.open();
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //必须为非阻塞模式才能注册到选择器上
        ssc.configureBlocking(false);
        ssc.bind(new InetSocketAddress(port));
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server start, port :" + port);
    }
    public static void main(String[] args) throws IOException {
        new Thread(new Server()).start();
//        ServerSocket serverSocket = new ServerSocket(port);
//        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
//        StringBuilder stringBuilder = new StringBuilder();
//        serverSocketChannel.bind(new InetSocketAddress(port));
//        System.out.println("服务端接口建立成功 开始监听");
//        SocketChannel accept = serverSocketChannel.accept();
////        Socket socket = serverSocket.accept();
//        System.out.println("监听到客户端消息 开始处理");
//        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
//        while (accept.read(byteBuffer) != -1) {
//            byteBuffer.flip();
//            while (byteBuffer.hasRemaining()) {
//                System.out.print((char) byteBuffer.get()); // read 1 byte at a time
//            }
//            byteBuffer.clear();
//        }
//        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        PrintWriter out = new PrintWriter(socket.getOutputStream());
//        String body;
//        while (true){
//            body = in.readLine();
//            if (body == null) {
//                break;
//            }
//            System.out.println("server接收到：" + body);
//            out.println("服务端传送数据：。。。");
//            System.out.println("服務端傳送完成");
//        }
    }

    @Override
    public void run() {
        while (true){
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()){
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel sc = serverSocketChannel.accept();
                        sc.configureBlocking(false);
                        sc.register(selector,SelectionKey.OP_READ);
                    }
                    if (key.isReadable()){
                        try {
                            //1 清空缓冲区旧的数据
                            this.readBuf.clear();
                            //2 获取之前注册的socket通道对象
                            SocketChannel sc = (SocketChannel) key.channel();
                            //3 读取数据
                            int count = sc.read(this.readBuf);
                            System.out.println(count);
                            //4 如果没有数据
                            if(count == -1){
                                key.channel().close();
                                key.cancel();
                                return;
                            }
                            //5 有数据则进行读取 读取之前需要进行复位方法(把position 和limit进行复位)
                            this.readBuf.flip();
                            //6 根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
                            byte[] bytes = new byte[this.readBuf.remaining()];
                            //7 接收缓冲区数据
                            this.readBuf.get(bytes);
                            //8 打印结果
                            String body = new String(bytes).trim();
                            System.out.println("Server : " + body);

                            // 9..可以写回给客户端数据

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    iterator.remove();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}