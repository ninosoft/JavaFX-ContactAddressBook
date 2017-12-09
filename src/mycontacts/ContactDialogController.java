package mycontacts;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import mycontacts.datamodel.Contact;


public class ContactDialogController {
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


    public Contact getNewContact() {
        return new Contact(firstNameTextField.getText(), lastNameTextField.getText(),
                phoneNumberTextField.getText(), emailTextField.getText(), notesTextField.getText());
    }

    public void populateDialogFields(Contact selectedContact) {
        if (selectedContact != null) {
            firstNameTextField.setText(selectedContact.getFirstName());
            lastNameTextField.setText(selectedContact.getLastName());
            phoneNumberTextField.setText(selectedContact.getPhoneNumber());
            emailTextField.setText(selectedContact.getEmail());
            notesTextField.setText(selectedContact.getNotes());
        }
    }


    public void updateContact(Contact selectedContact) {
        if (selectedContact != null) {
            selectedContact.setFirstName(firstNameTextField.getText());
            selectedContact.setLastName(lastNameTextField.getText());
            selectedContact.setPhoneNumber(phoneNumberTextField.getText());
            selectedContact.setEmail(emailTextField.getText());
            selectedContact.setNotes(notesTextField.getText());
        }
    }


}








