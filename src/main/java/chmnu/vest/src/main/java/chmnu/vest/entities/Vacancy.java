package chmnu.vest.entities;

public class Vacancy {
    private final String name;
    private final String salary;
    private final String company;
    private final String url;

    public Vacancy(String name, String salary, String company, String url) {
        this.name = name;
        this.salary = salary;
        this.company = company;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getSalary() {
        return salary;
    }

    public String getCompany() {
        return company;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "Посада: " + name + "\n" +
               "ЗП: " + salary + "\n" +
               "Компанія: " + company + "\n" +
               "" + "\n" +
               "Посилання: " + url + "\n";
    }
}
