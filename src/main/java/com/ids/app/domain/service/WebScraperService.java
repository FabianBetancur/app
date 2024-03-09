package com.ids.app.domain.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScraperService {
    public List<String> scrapeWeb() {
        String url = "https://losprecios.co/d1_t1";
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36";
        List<String> result = new ArrayList<>();
        if(getStatusSite(url,userAgent) == 200){
            System.out.println("status page ok ");
            Document html = getDocumentHtml(url,userAgent);
            Elements name = html.getElementsByClass("eít-enl-p");
            Elements prices = html.getElementsByClass("t-ed-pr");
            Elements sizes = html.getElementsByClass("no-lín");
           for(Element element : name){
               System.out.println(element.text());
           }
           for(Element price :prices){
               System.out.println(price.text());
           }
           for(Element size : sizes){
               System.out.println(size.text());
           }
        }
        return null;
    }
    public int getStatusSite(String url,String userAgent){
        Connection.Response response = null;
        try{
            response = Jsoup.connect(url).userAgent(userAgent).timeout(100000).ignoreHttpErrors(true).execute();
        } catch(IOException ex){
            System.out.println("Jsoup Exception status page  :: "+ex.getMessage());
        }
        return response.statusCode();
    }

    public Document getDocumentHtml(String url,String userAgent){
        Document html = null;
        try {
            html =  Jsoup.connect(url).userAgent(userAgent).timeout(100000).get();
        } catch(IOException ex){
            System.out.println("Jsoup Exception get html :: "+ex.getMessage());
        }
        return html;
    }
}
