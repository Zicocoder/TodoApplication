package se.lexicon.model;

import java.time.LocalDate;

public class TodoItem {
    private int todoId;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private Person assignee;

    public int getId() {
        return todoId;
    }

    public void setId(int id) {
        this.todoId = id;
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

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Person getAssignee() {
        return assignee;
    }

    public void setAssignee(Person assignee) {
        this.assignee = assignee;
    }

    public TodoItem(int todoId, String title, String description, LocalDate deadline, boolean done, Person assignee) {
        if (title == null || title.isEmpty() || deadline == null) {
            throw new IllegalArgumentException("Title and deadline cannot be null or empty");
        }
        this.todoId = todoId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.assignee = assignee;
        this.done = done;

    }

    public TodoItem(String title, String description, LocalDate deadline, Person assignee) {
        this(0, title, description, deadline, false, assignee);
    }


    public boolean isOverdue(){


        return !done && LocalDate.now().isAfter(deadline);

    }
    public String getSummary() {
        return "{id " + todoId +
                ", done: " + done +
                ", deadline " + deadline +
                ", assignee: " + (assignee != null ? assignee.getFirstName() + " " + assignee.getLastName() : "none") +
                "}";
    }

}
