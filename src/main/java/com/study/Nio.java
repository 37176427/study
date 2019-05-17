package com.study;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * 描述 ：NIO学习简记
 * 作者 ：WYH
 * 时间 ：2019/4/18 16:19
 **/
public class Nio {

    public static void main(String[] args) throws IOException {
//        java.util.Observer ob1 = new Obs1();
//        Cup p = new Cup(10);
//        p.addObserver(ob1);
//        p.setPrice(111);
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        RandomAccessFile randomAccessFile = new RandomAccessFile("C:\\Users\\rzx\\Desktop\\allDomain.txt", "rw");
        RandomAccessFile to = new RandomAccessFile("C:\\Users\\rzx\\Desktop\\2.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();
        FileChannel toChannel = to.getChannel();

        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("localhost",8080), 128);
        serverSocketChannel.accept();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8080));
        if (socketChannel.finishConnect()){
            //连接成功后/...配合选择器与异步模式可高效运行
        }
        SelectionKey key = serverSocketChannel.register(selector,SelectionKey.OP_READ);


        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        for (SelectionKey selectionKey:selectionKeys){
            //if (selectionKey.isAcceptable()){}else if (selectionKey.isConnectable())...
            SelectableChannel channel1 = selectionKey.channel();
            selectionKeys.remove(selectionKey);
        }

        toChannel.transferFrom(channel,0,channel.size());
        randomAccessFile.close();
        toChannel.close();

        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.socket().bind(new InetSocketAddress(8008));
        datagramChannel.receive(byteBuffer);//读取数据
        byteBuffer.put(new String("adsadad").getBytes());
        datagramChannel.send(byteBuffer,new InetSocketAddress(8008));

        Pipe pipe = Pipe.open();
        pipe.sink().write(byteBuffer);//向管道写数据
        pipe.source().read(byteBuffer);//读数据到缓冲区

        //aio
        Path path = Paths.get("rzx/1.txt");
        AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        Future<Integer> readFuture = fileChannel.read(byteBuffer, 0);
        while (readFuture.isDone()){
            //future方式
        }
        //第二种方式
        fileChannel.read(byteBuffer, 0, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {

            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {

            }
        });
    }
}
