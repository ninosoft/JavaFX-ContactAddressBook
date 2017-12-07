package mycontacts;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import mycontacts.datamodel.Contact;
import mycontacts.datamodel.ContactData;

import java.io.IOException;
import java.util.Optional;


public class Controller {

    @FXML
    private VBox mainLayoutVBox;
    @FXML
    private TableView<Contact> contactsTableView;
    @FXML
    private TableColumn<Contact, String> firstNameColumn;
    @FXML
    private TableColumn<Contact, String> lastNameColumn;
    @FXML
    private TableColumn<Contact, String> phoneNumberColumn;
    @FXML
    private TableColumn<Contact, String> emailColumn;
    @FXML
    private TableColumn<Contact, String> notesColumn;

    private ContactData contactData = ContactData.getInstance();

    public void initialize() {


        /* Associate Data with the Table Columns,
         * setCellValueFactory is used to specify a cell factory for each column and associate the data with the column.
         * PropertyValueFactory specifies the object class and the properties references ("first name") for the
         * corresponding methods. It requires the Contact Class to use SimpleStringProperty to be able to find the
         * property references.
         * Note:
         * Type <S,T> comes from the TableColumn declaration, example TableColumn<Contact, String> firstNameColumn;
         * */
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));

        //get the data
        contactsTableView.setItems(contactData.getContacts());

        //select first item (first row)
        contactsTableView.getSelectionModel().selectFirst();

    } //End initialization


    public void handleMenuDelete() {
        Contact contact = contactsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting Contact");
        alert.setHeaderText("Are you sure you want to delete contact: " + contact.getFirstName() + " "
                + contact.getLastName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            contactData.deleteContact(contact);
        }
    }

    public void handleContextMenuDelete() {
        handleMenuDelete();
    }


    public void handleMenuAdd() {
        //defining custom dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        //Specifies the owner Window or null for a top-level, unowned stage.
        dialog.initOwner(mainLayoutVBox.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        dialog.setHeaderText("Please add the new contact information.");
        //Set the dialog fXML layout  file location.
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("add_contact_dialog.fxml"));

        //load the dialog fXML layout  file
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Couldn't load the dialog");
            e.printStackTrace();
            return;
        }

        //add buttons to the dialog.
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        boolean loop = true;
        while (loop) {
            //show dialog
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //get add contact dialog controller to add the contact
                AddContactDialogController addContactDialogController = fxmlLoader.getController();
                if (addContactDialogController.addContact()) {
                    loop = false;
                } //if contact was not added, returned false, loop is true.
            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                loop = false;
            }
        }
    }


    public void handleMenuClose() {
        /* Causes to terminate, will call the Application stop method.
         *  Note: Data will be saved, stop() method was override in Main class.
         *  */
        Platform.exit();
    }

    public void handleContextMenuAdd() {
        handleMenuAdd();
    }
}

