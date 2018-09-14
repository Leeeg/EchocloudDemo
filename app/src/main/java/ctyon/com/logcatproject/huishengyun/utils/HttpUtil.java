package ctyon.com.logcatproject.huishengyun.utils;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ctyon.com.logcatproject.huishengyun.model.API;
import ctyon.com.logcatproject.huishengyun.model.Constant;
import ctyon.com.logcatproject.huishengyun.model.Type;

public class HttpUtil {

    private OnPostFailedCallback postFailedCallback;

    public interface OnPostFailedCallback {
        void onError(String erroeMsg);
    }

    public static String postNormal(String params) {

        if (null == params || params.isEmpty()){
            Log.e("Lee", "null == data || data.length == 0");
            return "-1";
        }
        byte[] data = params.getBytes();

        try {

            URL url = new URL(API.URL_NORMAL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);          //设置连接超时时间
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");         //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            //设置请求体的长度
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            Log.d("Lee", "postNormal response = " + response);
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            Log.e("Lee", "postNormal ERROR ： " + e);
            return "err: " + e.getMessage().toString();
        }
        return "-1";
    }

    public static String postStream(String nonce, String signature, byte[] data, Type type) {

        if (null == data || data.length == 0){
            Log.e("Lee", "null == data || data.length == 0");
            return "-1";
        }
        Log.d("Lee", "data.length = " + data.length);

        try {

            URL url = new URL(API.URL_STREAM);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
            httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
            httpURLConnection.setRequestMethod("POST");          //设置以Post方式提交数据
            httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存

            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("X-Echocloud-Version", "3");
            httpURLConnection.setRequestProperty("X-Echocloud-Channel-Uuid", Constant.CHANNEL_UUID);
            httpURLConnection.setRequestProperty("X-Echocloud-Device-ID", DataUtil.getDevicesId());
            httpURLConnection.setRequestProperty("X-Echocloud-Nonce", nonce);
            httpURLConnection.setRequestProperty("X-Echocloud-Signature", signature);
            httpURLConnection.setRequestProperty("X-Echocloud-Type", type.toString());

            //设置请求体的类型是文本类型
            httpURLConnection.setRequestProperty("Content-Type", "audio/pcm;bit=16;rate=16000");
            //设置请求体的长度
//            httpURLConnection.setRequestProperty("Transfer-Encoding", "chunked");
            //获得输出流，向服务器写入数据
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(data);

            int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
            Log.d("Lee", type.toString() + "   postStream response = " + response);
            if (response == HttpURLConnection.HTTP_OK) {
                InputStream inptStream = httpURLConnection.getInputStream();
                return dealResponseResult(inptStream);                     //处理服务器的响应结果
            }
        } catch (IOException e) {
            Log.e("Lee", "postStream ERROR ： " + e);
            return "err: " + e.getMessage().toString();
        }
        return "-1";
    }

    public static String dealResponseResult(InputStream inputStream) {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        Log.d("Lee", "postNormal resultData = " + resultData);
        return resultData;
    }

}
