package com.study.distruptor.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    final static String ADDRESS = "127.0.0.1";
    final static int PORT = 8765;

    public static void main(String[] args) throws IOException, InterruptedException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(PORT));
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        String msg = "asdasdas111111d";
        byteBuffer.put(msg.getBytes());
        System.out.println("向服务端写数据。。。");
        byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
            socketChannel.close();
//        Socket socket = null;
//        BufferedReader in = null;
//        PrintWriter out = null;
//        try {
//            socket = new Socket(ADDRESS, PORT);
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            out = new PrintWriter(socket.getOutputStream(), true);
//            System.out.println("向服务端写数据。。。");
//            out.println("0aaa0112233");
//            String response = in.readLine();
//            System.out.println("客户端读取到：" + response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
