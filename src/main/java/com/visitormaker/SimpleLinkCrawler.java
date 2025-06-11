package com.visitormaker;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SimpleLinkCrawler {

    private static final Set<String> visitedUrls = new HashSet<>();

    public static void main(String[] args) {
        String startUrl = "https://vpnhead.com";
        crawl(startUrl);
    }

    private static void crawl(String url) {
        if (visitedUrls.contains(url)) {
            return;
        }

        visitedUrls.add(url);

        try {
            System.out.println("Visiting: " + url);
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();

            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String nextUrl = link.absUrl("href");

                if (!nextUrl.isEmpty() && !visitedUrls.contains(nextUrl)) {
                    System.out.println("Found link: " + nextUrl);
                    crawl(nextUrl);
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to access: " + url);
        }
    }
}
