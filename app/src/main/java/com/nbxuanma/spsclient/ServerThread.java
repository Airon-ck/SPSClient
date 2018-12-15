package com.nbxuanma.spsclient;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Iterator;

public class ServerThread implements Runnable {

    private Socket socket;
    private BufferedReader br;

    public ServerThread(Socket socket) throws IOException {
        this.socket = socket;
        br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
    }

    @Override
    public void run() {
        String content;
        //不断把客服端的数据读取出来
        while ((content = readFromClient()) != null) {

            String str = null;
            try {
                str = String.valueOf((content + "\n").getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Log.i("tag", str);

//            //把收到的消息遍历发给每一个连接了的客户端
//            for (Iterator<Socket> iterator = MyServer.listSocket.iterator(); iterator.hasNext(); ) {
//                Socket socket = iterator.next();
//                //打印出客服端的Ip和端口号
//                System.out.println("IP:" + socket.getInetAddress() + "    port:" + socket.getPort());
//                try {
//                    OutputStream os = socket.getOutputStream();
//                    os.write((content + "\n").getBytes("utf-8"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

        }
    }

    private String readFromClient() {
        try {
            return br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            MyServer.listSocket.remove(socket);
        }
        return null;
    }

}

