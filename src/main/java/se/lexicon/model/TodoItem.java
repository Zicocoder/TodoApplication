package se.lexicon.model;

import java.time.LocalDate;

public class TodoItem {
    private int id;
    private String title;
    private String description;
    private LocalDate deadLine;
    private boolean done;
    private Person creator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        this.deadLine = deadLine;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public TodoItem(int id, String title, String description, LocalDate deadLine, Person creator) {
        if (title == null || title.isEmpty() || deadLine == null) {
            throw new IllegalArgumentException("Title and deadline cannot be null or empty");
        }
        this.id = id;
        this.title = title;
        this.description = description;
        this.deadLine = deadLine;
        this.creator = creator;
        this.done = false;

    }

    public boolean isOverdue(){


        return LocalDate.now().isAfter(deadLine);

    }
    public String getSummary() {
        return "{id " + id +
                ", done: " + done +
                ", deadline " + deadLine +
                ", creator: " + (creator != null ? creator.getFirstName() + " " + creator.getLastName() : "none") +
                "}";
    }

}
