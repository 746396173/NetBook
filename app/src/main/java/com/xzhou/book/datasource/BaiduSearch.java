package com.xzhou.book.datasource;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BaiduSearch {



    public static void parse(String key) {
        try {
            try {
                key = URLEncoder.encode(key, "gb2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String url = "http://www.baidu.com.cn/s?wd=" + key + "&cl=3";
            //解析Url获取Document对象
            Document document = Jsoup.connect(url).get();
            //获取网页源码文本内容
            //System.out.println(document.toString());
            //Log.i("zx",document.toString());
            //获取指定class的内容指定tag的元素
            //Elements elements = document.getAllElements();
//            Elements divs = document.getElementsByTag("div");
            Elements result = document.getElementsByClass("result c-container ");
            Log.i("zx", "title=" + document.title() + ",result size=" + result.size());
//            for (Element e : divs) {
//                Log.i("zx", "divs=" + e.toString());
//            }
            for (int i = 0; i < result.size(); i++) {
                Element e = result.get(i);
                Log.i("zx", "result id=" + e.id());
//                Log.i("zx", "result e=" + e.html());
                Elements f13 = e.getElementsByClass("f13");
//                Log.i("zx", "result f13=" + f13);
                for (int j = 0; j < f13.size(); j++) {
                    Element child = f13.get(j);
                    //Elements tag = child.getElementsByTag("a");
                    String title = child.getElementsByClass("c-tools").attr("data-tools");
                    Log.i("zx", "title=" + title);
                    //Log.i("zx", "cover=" + tag.attr("href"));
                }
//                Log.i("zx", "result html=" + e.html());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}