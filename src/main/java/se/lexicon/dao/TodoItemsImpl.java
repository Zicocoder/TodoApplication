package se.lexicon.dao;

import se.lexicon.model.Person;
import se.lexicon.model.TodoItem;

import java.sql.*;
import java.util.Collection;
import java.sql.Date;
import java.util.List;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Types;

public class TodoItemsImpl implements TodoItems {


    private final People people;


    public final Connection connection;


    public TodoItemsImpl(Connection connection) {
        this.connection = connection;
        this.people = new PeopleImpl(connection);

    }

    @Override
    public TodoItem create(TodoItem todoItem) {

        String sql = "INSERT INTO todo_item (title, description, deadline, done, assignee_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            preparedStatement.setDate(3, Date.valueOf(todoItem.getDeadline()));
            preparedStatement.setBoolean(4, todoItem.isDone());

            if (todoItem.getAssignee() == null) {
                preparedStatement.setNull(5, Types.INTEGER);

            } else {
                preparedStatement.setInt(5, todoItem.getAssignee().getPersonId());
            }
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        todoItem.setId(resultSet.getInt(1));

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoItem;
    }

    @Override
    public TodoItem findById(int id) {


        String sql = "SELECT * FROM todo_item Where todo_id = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {

                    int todoId = resultSet.getInt("todo_id");
                    String title = resultSet.getString("title");
                    String description = resultSet.getString("description");

                    java.sql.Date sqlDate = resultSet.getDate("deadline");
                    java.time.LocalDate deadline = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                    boolean done = resultSet.getBoolean("done");

                    int assigneeId = resultSet.getInt("assignee_id");
                    Person assignee = resultSet.wasNull() ? null : people.findById(assigneeId);

                    return new TodoItem(todoId, title, description, deadline, done, assignee);


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<TodoItem> findAll() {

        List<TodoItem> todoItems = new java.util.ArrayList<>();
        String sql = "SELECT * FROM todo_item";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {

                int todoId = resultSet.getInt("todo_id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");

                Date sqlDate = resultSet.getDate("deadline");
                java.time.LocalDate deadline = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                boolean done = resultSet.getBoolean("done");

                int assigneeId = resultSet.getInt("assignee_id");
                Person assignee = resultSet.wasNull() ? null : people.findById(assigneeId);

                TodoItem todo = new TodoItem(todoId, title, description, deadline, done, assignee);
                todoItems.add(todo);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoItems;
    }

    @Override
    public TodoItem update(TodoItem todoItem) {
        String sql = "UPDATE todo_item SET title = ?, description = ?, deadline = ?, done = ?, assignee_id = ? WHERE todo_id = ?";


        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, todoItem.getTitle());
            preparedStatement.setString(2, todoItem.getDescription());
            preparedStatement.setDate(3, Date.valueOf(todoItem.getDeadline()));
            preparedStatement.setBoolean(4, todoItem.isDone());

            if (todoItem.getAssignee() == null) {
                preparedStatement.setNull(5, Types.INTEGER);

            } else {
                preparedStatement.setInt(5, todoItem.getAssignee().getPersonId());
            }
            preparedStatement.setInt(6, todoItem.getId());


            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                System.out.println("No todo updated (check todo_id).");

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return todoItem;

    }


    @Override
    public boolean deleteById(int id) {

        String sql = "DELETE FROM todo_item WHERE todo_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            return affectedRows > 0;

        } catch(SQLException e) {

        e.printStackTrace();
    }
        return false;

}
}
