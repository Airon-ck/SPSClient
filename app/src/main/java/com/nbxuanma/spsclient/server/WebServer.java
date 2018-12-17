package com.nbxuanma.spsclient.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WebServer主类
 * @author adminitartor
 *
 */
public class WebServer {

    private ServerSocket server;
    //管理用于处理客户端请求的线程
    private ExecutorService threadPool;
    public static List<Socket> listSocket = new ArrayList<>();

    /**
     * 构造方法，用来初始化服务端
     */
    public WebServer(){
        try {
            System.out.println("正在启动服务端...");
            server = new ServerSocket(8085);
            threadPool = Executors.newFixedThreadPool(100);
            System.out.println("服务端启动完毕!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 服务端启动方法
     */
    public void start(){
        try {
            while(true){
                System.out.println("等待客户端...");
                Socket socket = server.accept();
                System.out.println("一个客户端连接了!");
                listSocket.add(socket);
                //启动一个线程处理该客户端请求
                ServerThread handler = new ServerThread(socket);
                threadPool.execute(handler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServer server = new WebServer();
        server.start();
    }

}
