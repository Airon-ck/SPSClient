package com.nbxuanma.spsclient;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientThread implements Runnable {

    private static final String TAG = "TAG";
    private OutputStream os;
    private BufferedReader br;
    private Socket socket;

    //用于向UI发送消息
    private Handler handler;
    //接收UI线程的消息（当用户点击发送）
    public Handler revHandler;

    public ClientThread(Handler handler) {
        this.handler = handler;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        //创建一个无连接的Socket
        socket = new Socket();
        try {
            //连接到指定的IP和端口号，并指定10s的超时时间
            socket.connect(new InetSocketAddress("119.3.58.181", 8085), 10000);
            //接收服务端的数据
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            //向服务端发送数据
            os = socket.getOutputStream();

            //读取数据会阻塞，所以创建一个线程来读取
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //接收服务器的消息，发送显示在主界面
                    String content;
                    try {
                        while ((content = br.readLine()) != null) {
                            Message msg = new Message();
                            msg.what = 1;
                            msg.obj = content;
                            handler.sendMessage(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            //非UI线程，自己创建
            Looper.prepare();
            revHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    //将用户输入的内容写入到服务器
                    try {
                        os.write(((msg.obj) + "\n").getBytes("utf-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            Looper.loop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
