package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.util.Collection;

public interface TodoItems {
    TodoItem create(TodoItem todoItem);

    TodoItem findById(int id);

    Collection<TodoItem> findAll();


    TodoItem update(TodoItem todoItem);

    boolean deleteById(int id);


}
