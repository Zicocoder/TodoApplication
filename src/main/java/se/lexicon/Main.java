package se.lexicon;

import se.lexicon.model.Person;

public class Main {
    public static void main(String[] args) {


        Person p1 = new Person(1, "Nisse", "Olsson", "nisse@gmail.com");
        System.out.println(p1.getSummary());
    }
}