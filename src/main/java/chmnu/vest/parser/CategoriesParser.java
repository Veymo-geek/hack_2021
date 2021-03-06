package chmnu.vest.parser;

import chmnu.vest.entities.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class CategoriesParser {
    private final String siteDomain;

    public CategoriesParser(String siteDomain) {
        this.siteDomain = siteDomain;
    }

    public String getCategoriesString() throws IOException {
        StringBuilder message = new StringBuilder();
        List<Category> categories = getCategories();

        categories.forEach(m -> message.append(m).append("\n"));

        return message.toString();
    }

    public List<Category> getCategories() throws IOException {
        Document doc = Jsoup.connect(siteDomain + "/jobs/by-category/").get();
        Elements elements = doc.select("li");

        List<Category> categories = new LinkedList<>();
        for (Element el : elements) {
            Elements a_query = el.select("a");

            if (a_query.attr("href").contains("advs") && !a_query.attr("href").contains("noresume")) {
                categories.add(new Category(a_query.text(), el.ownText(), a_query.attr("href")));
            }
        }

        categories.forEach(System.out::println);
        return categories;
    }
}
