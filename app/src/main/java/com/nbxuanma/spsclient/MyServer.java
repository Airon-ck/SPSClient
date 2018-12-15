package com.nbxuanma.spsclient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    public static List<Socket> listSocket = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8085);
        while (true) {
            //等待连接
            Socket socket = serverSocket.accept();
            System.out.println("a target online");
            listSocket.add(socket);
            //每当客户端连接成功 后启动一条线程为该客户端服务
            new Thread(new ServerThread(socket)).start();
        }
    }

}

