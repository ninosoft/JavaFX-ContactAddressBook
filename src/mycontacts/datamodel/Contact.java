package mycontacts.datamodel;

import javafx.beans.property.SimpleStringProperty;


public class Contact {
    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty phoneNumber;
    private final SimpleStringProperty email;
    private final SimpleStringProperty notes;

    //Constructor,  package-private (no explicit modifier).
     Contact() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.phoneNumber = new SimpleStringProperty("");
        this.notes = new SimpleStringProperty("");
    }


     public Contact(String firstName, String lastName, String phoneNumber, String email, String notes) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.notes = new SimpleStringProperty(notes);
    }

    public String getFirstName() {
        return firstName.get();
    }

/*    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }*/

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    /*public SimpleStringProperty lastNameProperty() {
        return lastName;
    }*/

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    /*public SimpleStringProperty phoneNumberProperty() {
        return phoneNumber;
    }*/

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    /*public SimpleStringProperty emailProperty() {
        return email;
    }*/

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getNotes() {
        return notes.get();
    }

    /*public SimpleStringProperty notesProperty() {
        return notes;
    }*/

    public void setNotes(String notes) {
        this.notes.set(notes);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", notes=" + notes +
                '}';
    }
}

