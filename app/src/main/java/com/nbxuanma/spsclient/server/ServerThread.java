package com.nbxuanma.spsclient.server;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.nbxuanma.spsclient.utils.Config;
import com.nbxuanma.spsclient.utils.MyEventBus;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerThread implements Runnable {

    private static final String TAG = "TAG";
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

            Bundle bundle = new Bundle();
            bundle.putString("content", content);
            EventBus.getDefault().post(new MyEventBus(Config.ReceiveMsg, bundle));
            Log.i(TAG, "ReadMsgFromServer");

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
            WebServer.listSocket.remove(socket);
        }
        return null;
    }

}

