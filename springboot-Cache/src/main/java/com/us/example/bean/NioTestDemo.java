package com.us.example.bean;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import jdk.internal.org.objectweb.asm.Handle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author beegxj
 */
public class NioTestDemo {
    private Selector selector;

    public static void main(String[] args) throws IOException {
        NioTestDemo nio = new NioTestDemo();
        nio.initServer(8888);
        nio.listenselector();
    }
    public void initServer(int port) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务已经启动");

    }

    public void listenselector() throws IOException {
        //轮询监听selectorkey
        while(true){
            this.selector.select();
            Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
            while(iterator.hasNext()){
                SelectionKey next = iterator.next();
                //处理请求
                iterator.remove();
                handler(next);

            }
        }
    }

    private void handler(SelectionKey next) throws IOException {
        if(next.isAcceptable()){
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) next.channel();
           SocketChannel socketChannel =  serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
           socketChannel.register(selector,SelectionKey.OP_READ);
        }else if(next.isReadable()){
                //处理读的信息
            SocketChannel socketChannel = (SocketChannel) next.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
          int readDate =  socketChannel.read(buffer);
          if(readDate > 0){
            String info = new String(buffer.array(),"GBK").trim();
              System.out.println("服务端接收到数据");
          }else{

              System.out.println("客户端关闭了");
              next.cancel();
            }
        }
    }



}
