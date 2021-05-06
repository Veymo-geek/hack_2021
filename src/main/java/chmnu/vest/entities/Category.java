package chmnu.vest.entities;

public class Category {
    private final String name;
    private final String vacancies;
    private final String url;

    public Category(String name, String vacancies, String url) {
        this.name = name;
        this.vacancies = vacancies;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getVacancies() {
        return vacancies;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return name + " (" + vacancies + ")";
    }
}