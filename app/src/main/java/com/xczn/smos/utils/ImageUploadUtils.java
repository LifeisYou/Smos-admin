package com.xczn.smos.utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zhangxiao
 * Date on 2018/5/9.
 */
public class ImageUploadUtils {

    private static final int TIME_OUT = 100000; // 超时时间
    private static final String CHARSET = "utf-8"; // 设置编码
    private static final int UPLOADIMAGE = 0;
    public static final int UPLOADAVATAR = 1;
    public static boolean uploadFile(Handler handler, Map<String ,Bitmap> bitmapMap ,int uploadType) {

        String CONTENT_TYPE = "multipart/form-data"; // 内容类型
        String RequestURL = "";
        if (uploadType == UPLOADIMAGE) {
            RequestURL = SharedPreferencesUtils.getInstance().getBaseUrl()+"pictures";
        } else if (uploadType == UPLOADAVATAR){
            RequestURL = SharedPreferencesUtils.getInstance().getBaseUrl()+"avatars";
        }
        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
        String PREFIX = "--", LINE_END = "\r\n";
        URL url;
        DataOutputStream dos;
        HttpURLConnection conn;
        try {
            url = new URL(RequestURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
                    + BOUNDARY);
            OutputStream outputSteam = conn.getOutputStream();
            dos = new DataOutputStream(outputSteam);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = 12;
            message.obj = "error";
            handler.sendMessage(message);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = 12;
            message.obj = "error";
            handler.sendMessage(message);
            return true;
        }

        try {
            Iterator it = bitmapMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry entry = (Map.Entry)it.next();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ((Bitmap)entry.getValue()).compress(Bitmap.CompressFormat.JPEG, 50, baos);
                InputStream is = new ByteArrayInputStream(baos .toByteArray());

                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(PREFIX);
                stringBuffer.append(BOUNDARY);
                stringBuffer.append(LINE_END);

                //这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                //filename是文件的名字，包含后缀名的 比如:abc.png
                stringBuffer.append("Content-Disposition: form-data; name=\"img\"; filename=\""
                        + entry.getKey() + "\"" + LINE_END);
                stringBuffer.append("Content-Type: application/octet-stream; charset="
                        + CHARSET + LINE_END);
                stringBuffer.append(LINE_END);
                System.out.print(stringBuffer.toString());
                dos.write(stringBuffer.toString().getBytes());
                //InputStream is = new FileInputStream(file);

                byte[] bytes = new byte[1024];
                int len;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());

            }
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
            dos.close();

            //获取响应码 200=成功 当响应成功，获取响应的流
            if (conn.getResponseCode() == 200) {
                Message message = new Message();
                message.what = 11;
                handler.sendMessage(message);
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = 12;
            handler.sendMessage(message);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = 12;
            handler.sendMessage(message);
            return true;
        }
        return false;
    }
}

