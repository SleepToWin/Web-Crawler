import java.util.Scanner;
import java.util.ArrayList;

public class WebCrawler {

    public static void crawler(String startingURL) {
        ArrayList<String> pendingURLs = new ArrayList<String>();
        ArrayList<String> traversedURLs = new ArrayList<String>();

        pendingURLs.add(startingURL);
        while (!pendingURLs.isEmpty() && traversedURLs.size() < 100) {
            String urlString = pendingURLs.remove(0);
            if (!traversedURLs.contains(urlString)) {
                traversedURLs.add(urlString);
                System.out.println("Crawling " + urlString);

                for (String s : getSubURLs(urlString)) {
                    if (!traversedURLs.contains(s))
                        pendingURLs.add(s);
                }
            }
        }
    }

    public static ArrayList<String> getSubURLs(String urlString) {
        ArrayList<String> subURLs = new ArrayList<String>();

        try {
            java.net.URL url = new java.net.URL(urlString);
            Scanner input = new Scanner(url.openStream());
            int current = 0;
            while (input.hasNext()) {
                String line = input.nextLine();
                current = line.indexOf("http:", current);
                while (current > 0) {
                    int endIndex = line.indexOf("\"", current);
                    if (endIndex > 0) {
                        subURLs.add(line.substring(current, endIndex));
                        current = line.indexOf("http:", endIndex);
                    } else {
                        current = -1;
                    }
                }
            }
            input.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        for (String site : subURLs) {
            System.out.println("  " + site);
        }
        return subURLs;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter an URL: ");
        String url = input.nextLine();
        input.close();
        crawler(url);
    }
}
