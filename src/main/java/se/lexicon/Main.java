import java.time.LocalDate;
import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;


public class Main {
    public static void main(String[] args) {
        Person p1 = new Person(1, "Nisse", "Olsson", "nisse@gmail.com");
        TodoItem item = new TodoItem(101, "Change tires", "Winter is coming", LocalDate.of(2025, 12, 1), p1);

        System.out.println(item.getSummary());
        System.out.println("Is overdue? " + item.isOverdue());
    }
}