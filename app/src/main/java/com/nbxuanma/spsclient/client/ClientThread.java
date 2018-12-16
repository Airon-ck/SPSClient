package com.nbxuanma.spsclient.client;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

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
    private boolean isConnect;

    private Activity activity;

    public ClientThread(Activity activity, Handler handler) {
        this.activity = activity;
        this.handler = handler;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        //创建一个无连接的Socket
        socket = new Socket();
        try {
            SharedPreferences sp = activity.getSharedPreferences("User", Context.MODE_PRIVATE);
            String Host = sp.getString("IP", "119.3.58.181");
            int Port = sp.getInt("Port", 8085);
            //连接到指定的IP和端口号，并指定10s的超时时间
            socket.connect(new InetSocketAddress(Host, Port), 10000);
            //接收服务端的数据
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            //向服务端发送数据
            os = socket.getOutputStream();
            isConnect = socket.isConnected();
            Log.i(TAG, "isConnect:" + isConnect);
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
                            Log.i(TAG, "ReadFromServer");
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
                        if (isConnect){
                            os.write(((msg.obj) + "\n").getBytes("utf-8"));
                            Log.i(TAG, "WriteToServer");
                        }
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
