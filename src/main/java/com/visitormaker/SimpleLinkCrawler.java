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
        String startUrl = "https://example.com"; // Replace with your starting URL
        crawl(startUrl);
    }

    private static void crawl(String url) {
        if (visitedUrls.contains(url)) {
            return; // avoid visiting same link again
        }

        visitedUrls.add(url);

        try {
            System.out.println("Visiting: " + url);
            Document doc = Jsoup.connect(url).userAgent("Mozilla").get();

            // Find the first valid <a href> link
            Elements links = doc.select("a[href]");
            for (Element link : links) {
                String nextUrl = link.absUrl("href"); // get absolute URL

                if (!nextUrl.isEmpty() && !visitedUrls.contains(nextUrl)) {
                    System.out.println("Found link: " + nextUrl);
                    crawl(nextUrl); // recursive call
                    break; // move only to the first found link
                }
            }

        } catch (IOException e) {
            System.err.println("Failed to access: " + url);
        }
    }
}
