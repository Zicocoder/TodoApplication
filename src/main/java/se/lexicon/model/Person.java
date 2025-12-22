package se.lexicon.model;

public class Person {
    private int personId;
    private String firstName;
    private String lastName;



    public Person(int personId, String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            throw new IllegalArgumentException("Fields cannot be null");
        }

        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName) {
        this(0, firstName, lastName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null) {
            throw new IllegalArgumentException("lastName cannot be null");
        }
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null) {
            throw new IllegalArgumentException("firstName cannot be null");
        }
        this.firstName = firstName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getSummary() {
        return "(id: " + personId +
                ", name " + firstName + " " + lastName +")";
    }

}
