package se.lexicon;

import java.sql.Connection;

import java.time.LocalDate;

import se.lexicon.dao.People;
import se.lexicon.dao.PeopleImpl;
import se.lexicon.dao.TodoItems;
import se.lexicon.dao.TodoItemsImpl;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;
import java.sql.SQLException;
import se.lexicon.db.DatabaseConnection;


public class Main {
    public static void main(String[] args) {

       try (Connection connection = DatabaseConnection.getConnection()) {

        People people = new PeopleImpl(connection);

        Person p = new Person("Nisse", "Olsson");
        Person saved = people.create(p);

        System.out.println("Saved: " + saved.getSummary());




        Person found = people.findById(saved.getPersonId());
        System.out.println("Found: " + (found != null ? found.getSummary() :"null"));

        saved.setFirstName("Nisse");
        saved.setLastName("Olsson");

        people.update(saved);

        Person updated = people.findById(saved.getPersonId());
           System.out.println("Updated: " + (updated != null ? updated.getSummary() : "null"));

           boolean deleted = people.deleteById(saved.getPersonId());
           System.out.println("Deleted " + deleted);

           Person afterDelete = people.findById(saved.getPersonId());
           System.out.println("After delete: " + afterDelete);

           System.out.println("All people:");
           people.findAll().forEach(p2 -> System.out.println(p2.getSummary()));




           System.out.println("Search 'Nis':");
           people.findByName("Nis").forEach(p2 -> System.out.println(p2.getSummary()));

           TodoItems todoItems = new TodoItemsImpl(connection);

           Person assignee = null;

           for (Person p2 : people.findByName("Todo")) {
               if (p2.getFirstName().equals("Todo") && p2.getLastName().equals("Assignee")) {
                   assignee = p2;
                   break;
               }
           }

           if (assignee == null)  {
               assignee = people.create(new Person("Todo", "Assignee"));
           }


           TodoItem todo = new TodoItem(
                   "Change tires",
                   "Winter is coming",
                   LocalDate.of(2025, 12, 25),
           assignee
           );

           TodoItem savedTodo = todoItems.create(todo);
           System.out.println("Saved todo " + savedTodo.getSummary());

           TodoItem foundTodo = todoItems.findById(savedTodo.getId());
           System.out.println("found todo " + (foundTodo != null ? foundTodo.getSummary() : "null"));


           var allTodos = todoItems.findAll();
           System.out.println("All todos (count: " + allTodos.size() + "):");
           allTodos.forEach(t -> System.out.println(t.getSummary()));

           savedTodo.setDone(true);
           savedTodo.setTitle("Change tires ASAP");
           todoItems.update(savedTodo);

           TodoItem updatedTodo = todoItems.findById(savedTodo.getId());
           System.out.println("Updated Todo: " + (updatedTodo != null ? updatedTodo.getSummary() : "null"));

           boolean todoDeleted = todoItems.deleteById(savedTodo.getId());
           System.out.println("Todo deleted: " + todoDeleted);

           TodoItem afterTodoDelete = todoItems.findById(savedTodo.getId());
           System.out.println("Todo after delete: " + afterTodoDelete);


    } catch (SQLException e) {
        e.printStackTrace();
        }
}


}