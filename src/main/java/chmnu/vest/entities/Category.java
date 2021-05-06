package chmnu.vest.entities;

public class Category {
    private final String name;
    private final String category;
    private final String url;

    public Category(String name, String vacancies, String url) {
        this.name = name;
        this.category = vacancies;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name + " (" + category + ")";
    }
}