package chmnu.vest.parser;

import chmnu.vest.entities.Category;
import chmnu.vest.entities.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PagesParser {
    private final String siteDomain;

    public PagesParser(String siteDomain) {
        this.siteDomain = siteDomain;
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

    public String getVacancy(String url) throws IOException {
        Document doc = Jsoup.connect(siteDomain + url).get();
        Elements elements = doc.select("div");

        Vacancy vacancy = null;
        for (Element el : elements) {
            if (el.hasClass("card card-hover card-visited wordwrap job-link js-hot-block")) {
                System.out.println(el.html());
                String name = el.select("h2").select("a").attr("title");
                String salary = el.select("b").text();
                String company = el.select("img").attr("alt");
                String url_ = "";

                vacancy = new Vacancy(name, salary, company, url_);
            }
        }

        return vacancy == null ? "Вакансії не знайдено :(" : vacancy.toString();
    }
}
