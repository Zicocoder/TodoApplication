package se.lexicon.dao;

import se.lexicon.model.Person;

import java.time.Period;
import java.util.Collection;

public interface People {
    Person create(Person person);
    Person findById(int id);
    Collection<Person> findAll();
    Collection<Person> findByName(String name);
    Person update(Person person);
    boolean deleteById(int id);
}
