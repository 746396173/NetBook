package com.xzhou.book.models;

import android.annotation.SuppressLint;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public abstract class HtmlParse {
    protected String TAG = "HtmlParse";

    public List<Entities.Chapters> parseChapters(String readUrl) {
        try {
            trustEveryone();
            Document document = Jsoup.connect(readUrl).timeout(10000).get();
            return parseChapters(readUrl, document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract List<Entities.Chapters> parseChapters(String readUrl, Document document);

    public Entities.ChapterRead parseChapterRead(String chapterUrl) {
        try {
            trustEveryone();
            Document document = Jsoup.connect(chapterUrl).timeout(10000).get();
            return parseChapterRead(chapterUrl, document);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract Entities.ChapterRead parseChapterRead(String chapterUrl, Document document);

    protected void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                @SuppressLint("TrustAllX509TrustManager")
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

    protected void logd(String str) {
//        int max_str_length = 2001;
//        while (str.length() > max_str_length) {
//            Log.d(TAG, str.substring(0, max_str_length));
//            str = str.substring(max_str_length);
//        }
//        Log.d(TAG, str);
    }

    protected void logi(String str) {
//        int max_str_length = 2001;
//        while (str.length() > max_str_length) {
//            Log.i(TAG, str.substring(0, max_str_length));
//            str = str.substring(max_str_length);
//        }
//        Log.i(TAG, str);
    }
}
