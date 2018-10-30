package ctyon.com.logcatproject.mqtt;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

import ctyon.com.logcatproject.echocloud.model.VoiceMessage;
import ctyon.com.logcatproject.util.VoiceUtil;

/**
 * CreateDate：18-9-15 on 上午10:33
 * Describe:
 * Coder: lee
 */
public class MQTTService extends Service {
    public static final String TAG = "Lee_log_MQTT";

    private static MqttAndroidClient client;
    private MqttConnectOptions conOpt;

    private String host = "tcp://47.106.253.152:1883";
    private String userName = "admin";
    private String passWord = "admin";
    private String clientId;
    private boolean isConnected;
    private String topics = "2184";

    private static final String MQTT_TALKBACK = "com.android.mqtt.talkback";
    private static final String MQTT_ISTOPIC = "isTopics";
    private static final String MQTT_KEY = "topics";
    private static final String MQTT_TIME = "time";

    private MyMqttCallback myMqttCallback;
    private Handler handler;

    public void setMqttCallback(MyMqttCallback myMqttCallback) {
        this.myMqttCallback = myMqttCallback;
    }

    public MQTTService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MqttService : onCreate");
        handler = new Handler(getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (null != myMqttCallback)
                    myMqttCallback.mqttData("" + msg.obj);
            }
        };

        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getBooleanExtra(MQTT_ISTOPIC, false)) {
            topics = intent.getStringExtra(MQTT_KEY);
            subTopics();

        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 注册topic
     */
    private void subTopics() {
        if (null != topics && !topics.isEmpty() && isConnected) {
            Log.i(TAG, "subTopics ： topics = " + topics);
            try {
                // 订阅myTopic话题
                client.subscribe(topics, 1);
            } catch (MqttException e) {
                Log.e(TAG, "subTopics ERROR : " + e);
            }

//            String[] tos = topics.split("\\|");
//            int[] qos = new int[tos.length];
//            try {
//                client.subscribe(tos, qos);
//            }catch (MqttException e){
//
//            }
        }
    }

    private void init() {
        Log.d(TAG, "MqttService : init");
        sendBack("init MqttService");
        clientId = UUID.randomUUID().toString();
        // 服务器地址（协议+地址+端口号）
        String uri = host;
        client = new MqttAndroidClient(this, uri, clientId);
        // 设置MQTT监听并且接受消息
        client.setCallback(mqttCallback);

        conOpt = new MqttConnectOptions();
        // 清除缓存
        conOpt.setCleanSession(true);
        // 设置超时时间，单位：秒
        conOpt.setConnectionTimeout(10);
        // 心跳包发送间隔，单位：秒
        conOpt.setKeepAliveInterval(20);
        // 用户名
        conOpt.setUserName(userName);
        // 密码
        conOpt.setPassword(passWord.toCharArray());


        if (!isConnected) {
            doClientConnection();
        }

    }

    private void sendBack(String msg){
        Message message = handler.obtainMessage();
        message.obj = msg;
        handler.sendMessage(message);
    }

    @Override
    public void onDestroy() {
        sendBack("destroy MqttService");
        try {
            client.disconnect();
        } catch (MqttException e) {
            Log.e(TAG, "MqttService ： ERROR : " + e);
            e.printStackTrace();
        }
        super.onDestroy();
    }

    /**
     * 连接MQTT服务器
     */
    private void doClientConnection() {
        sendBack("start to connect to MQTTService");
        Log.d(TAG, "MqttService : doClientConnection");
        if (!client.isConnected() && isConnectIsNomarl()) {
            try {
                client.connect(conOpt, null, iMqttActionListener);
            } catch (MqttException e) {
                Log.e(TAG, "MqttService ： ERROR : " + e);
                e.printStackTrace();
            }
        }

    }

    /**
     * MQTT是否连接成功
     */
    private IMqttActionListener iMqttActionListener = new IMqttActionListener() {

        @Override
        public void onSuccess(IMqttToken arg0) {
            Log.i(TAG, "连接成功 ");
            sendBack("connect to MQTTService success");
            isConnected = true;
            subTopics();
        }

        @Override
        public void onFailure(IMqttToken arg0, Throwable arg1) {
            arg1.printStackTrace();
            Log.e(TAG, "连接失败   重连-------");
            sendBack("connect to MQTTService failed and try reConnect");
            isConnected = false;
            if (!isConnected) {
//                doClientConnection();
            }
        }
    };

    /**
     * MQTT监听并且接受消息
     */
    private MqttCallback mqttCallback = new MqttCallback() {

        @Override
        public void messageArrived(String topic, MqttMessage message) {

            String str2 = topic + ";qos: " + message.getQos() + ";   retained: " + message.isRetained();
            Log.d(TAG, str2);

            sendBack("received MQTT message : " + str2);

//            Log.e(TAG, "Time : " + System.currentTimeMillis() + "   VoiceNumber = " + VoiceUtil
//                    .getSequenceNumberFromVoice(message.getPayload())
//             + "   VoiceId = " + VoiceUtil.getVoiceId(message.getPayload()));

//            send(message.getPayload());

//            sendBroadcast(new Intent(MQTT_TALKBACK)
//                    .putExtra(MQTT_KEY, message.getPayload())
//                    .putExtra(MQTT_TIME, String.valueOf(System.currentTimeMillis())));

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken arg0) {

        }

        @Override
        public void connectionLost(Throwable arg0) {
            Log.e(TAG, "失去连接   重连-------");
            sendBack("lost connect");
            isConnected = false;
            if (!isConnected) {
                doClientConnection();
            }
        }
    };

    /**
     * 判断网络是否连接
     */
    private boolean isConnectIsNomarl() {
        Log.d(TAG, "MqttService ： isConnectIsNomarl : ");
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            String name = info.getTypeName();
            Log.i(TAG, "MQTT当前网络名称：" + name);
            sendBack("net type : " + name);
            return true;
        } else {
            Log.i(TAG, "MQTT 没有可用网络");
            sendBack("do not have Internet");
            return false;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MqttBinder();
    }

    public class MqttBinder extends Binder {
        public MQTTService getService() {
            return MQTTService.this;
        }
    }


    /**
     * 获取ip地址
     *
     * @param context
     * @return
     */
    private String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }


            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "" +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

}
