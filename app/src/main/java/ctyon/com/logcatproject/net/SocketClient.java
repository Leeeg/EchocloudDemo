package ctyon.com.logcatproject.net;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * CreateDate：18-9-29 on 下午1:56
 * Describe:
 * Coder: lee
 */
public class SocketClient implements Runnable{
    private static final String HOST = "192.168.0.16";//服务器地址
    private static final int PORT = 9998;//连接端口号
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;


    public SocketClient() {
        //启动线程，连接服务器，并用死循环守候，接收服务器发送过来的数据
        new Thread(this).start();
    }

    /**
     * 连接服务器
     */
    private void connection() {
        try {
            System.out.println("开始链接...");
            socket = new Socket(HOST, PORT);//连接服务器
            in = new BufferedReader(new InputStreamReader(socket
                    .getInputStream()));//接收消息的流对象
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())), true);//发送消息的流对象
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("连接失败...");
        }
    }


    /**
     * 读取服务器发来的信息，并通过Handler发给UI线程
     */
    public void run() {
        connection();// 连接到服务器
        try {
            while (true) {//死循环守护，监控服务器发来的消息
                if (!socket.isClosed()) {//如果服务器没有关闭
                    if (socket.isConnected()) {//连接正常
                        if (!socket.isInputShutdown()) {//如果输入流没有断开
                            String getLine;
                            if ((getLine = in.readLine()) != null) {//读取接收的信息
                                getLine += "\n";
                            } else {

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
