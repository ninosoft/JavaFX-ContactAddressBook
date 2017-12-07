package mycontacts;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import mycontacts.datamodel.Contact;
import mycontacts.datamodel.ContactData;


public class AddContactDialogController {
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField notesTextField;

    private ContactData contactData = ContactData.getInstance();

    public boolean addContact() {
        Contact contact = findContact();
        if (contact == null) {
            if (firstNameTextField.getText().equals("")) {
                // Contact name is empty
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Contact Name is Empty ");
                alert.setHeaderText("The contact first name can't be empty!");
                alert.showAndWait();
                return false;
            } else {
                //contact name is not empty
                contactData.addContact(new Contact(firstNameTextField.getText(), lastNameTextField.getText(),
                        phoneNumberTextField.getText(), emailTextField.getText(), notesTextField.getText()));
                return true;
            }
        } else {
            // Contact already exist
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Contact Already Exists ");
            alert.setHeaderText("The following contact is already in the list: ");
            alert.setContentText(contact.getFirstName() + " " + contact.getLastName());
            alert.showAndWait();
            return false;
        }
    }

    /* Find the contact in the contact list*/
    private Contact findContact() {
        for (Contact contact : contactData.getContacts()) {
            if ((contact.getFirstName().equalsIgnoreCase(firstNameTextField.getText())) &&
                    (contact.getLastName().equalsIgnoreCase(lastNameTextField.getText()))) {
                System.out.println("contact already exists");
                return contact;
            }
        }
        return null;
    }

}






