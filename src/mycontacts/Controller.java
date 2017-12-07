package mycontacts;

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

/*  Test Data 1

       //test contacts data without using dataModel ContactData class.
        final ObservableList<Contact> data = FXCollections.observableArrayList(
                new Contact("Jacob", "Smith", "407-332-2632", "jacob.smith@example.com","jajaja"),
                new Contact("Isabella", "Johnson", "407-555- 5555", "isabella.johnson@example.com", "que loco"),
                new Contact("Ethan", "Williams", "407-333-3333", "ethan.williams@example.com", "ese no es de aqui"),
                new Contact("Emma", "Jones", "407-444-4444", "emma.jones@example.com", "nickname Kiko"),
                new Contact("Michael", "Brown", "503-888-9999","michael.brown@example.com"," la la la")
        );


     //Test data using ContactData class
        contactData.addContact(new Contact("Jacob", "Smith", "407-332-2632", "jacob.smith@example.com","ja ja ja"));
        contactData.addContact(new Contact("Isabella", "Johnson", "407-555- 5555", "isabella.johnson@example.com", "que loco"));
        contactData.addContact(new Contact("Ethan", "Williams", "407-333-3333", "ethan.williams@example.com", "ese no es de aqui"));
        contactData.addContact(new Contact("Emma", "Jones", "407-444-4444", "emma.jones@example.com", "nickname Kiko"));
        contactData.addContact(new Contact("Michael", "Brown", "503-888-9999","michael.brown@example.com"," la la la"));

        //contactData.loadContacts();
*/


        // setCellValueFactory is used to populate individual cells in the column.
        // PropertyValueFactory specifies the object class that populates the table, and the object class for the column.
        // and the property to be obtained, for example Contact.getFirstName() returns String.
        // obtained from the properties types, example TableColumn<Contact, String> firstNameColumn;
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
                //get controller for the dialog
                AddContactDialogController addContactDialogController = fxmlLoader.getController();
                if (addContactDialogController.addContact()) {
                    loop = false;
                }
            } else if (result.isPresent() && result.get() == ButtonType.CANCEL) {
                loop = false;
            }

        }
    }


}

