package com.aligungor.htmlparse;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by AliGungorGYT on 07/08/15.
 */
public class HTMLParser extends AsyncTask<Void, Void, Void>{
    private String url;

    public HTMLParser(String url) {
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (url == null) {
            return null;
        }
        try {
            StringBuffer buffer = new StringBuffer();
            Document doc = Jsoup.connect(url).get();
            // Get document (HTML page) title
            String title = doc.title();
            buffer.append("Title: " + title + "\r\n");

            // Get meta info
            Elements metaElems = doc.select("meta");
            buffer.append("META DATA\r\n");
            for (Element metaElem : metaElems) {
                String name = metaElem.attr("name");
                String content = metaElem.attr("content");
                buffer.append("name ["+name+"] - content ["+content+"] \r\n");
            }

            Elements topicList = doc.select("h2.topic");
            buffer.append("Topic list\r\n");
            for (Element topic : topicList) {
                String data = topic.text();
                buffer.append("Data ["+data+"] \r\n");
            }

            System.out.println(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
