package com.study.distruptor.IP;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        String ip = "128.2.5.81";
        Map<Integer,Future> map = new HashMap();
        // getIpAddr(ip);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 5, 0L, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), new ThreadFactoryBuilder().setNameFormat("dm-pool-%d").build(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 1; i < 10; i++) {
            Future future = pool.submit(new ThreadTest(i));
            map.put(i,future);
        }
        System.out.println("提交完成 开始遍历结果");
        for(Map.Entry<Integer,Future> entry:map.entrySet()){
            System.out.println("检查：" + entry.getKey());
            System.out.println(entry.getValue().get());
        }
        System.out.println("准备关闭线程池");
        pool.shutdown();
        System.out.println("线程池关闭完成 主线程结束");
}

    private static void getIpAddr(String ip) {
        try {
            URL url = new URL("http://freeapi.ipip.net/" + ip);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.9");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String str;
            str = br.readLine();
            if (!str.isEmpty()) {
                if (str.contains("中国")) {
                    if (str.contains("台湾")) {
                        System.out.println("境内（台湾）");
                    }
                    if (str.contains("香港")) {
                        System.out.println("境内（香港）");
                    }
                    System.out.println("境内");
                } else {
                    System.out.println("境外");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
