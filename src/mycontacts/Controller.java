package mycontacts;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

import mycontacts.datamodel.Contact;
import mycontacts.datamodel.ContactData;

import java.io.IOException;
import java.util.Optional;


public class Controller {

    @FXML
    private BorderPane mainLayout;
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

    private ContactData contactData;
    private Dialog<ButtonType> dialog;
    private FXMLLoader fxmlLoader;

    public void initialize() {
        // Create ContactData instance;
        contactData = ContactData.getInstance();

        //set contact data
        contactsTableView.setItems(contactData.getContacts());

        //Set TableView column resize policy, to allow the columns to grow when the window is modified.
        //contactsTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //set selection mode.
        contactsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

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
        /* This two fields were defined on the fxml file, just to practice...
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));*/


        // Set Sort Order for initialization
        // After initialization will use the TableView class built-in capabilities.
        // Users can alter the order of data by clicking column headers.
        // Note: Should not use SortedList, it will cause the TableView to be always sorted by the SortedList criteria.
        emailColumn.setSortable(false);
        notesColumn.setSortable(false);
        phoneNumberColumn.setSortable(false);
        firstNameColumn.setSortType(TableColumn.SortType.ASCENDING); //this is the default, not needed.
        contactsTableView.getSortOrder().add(firstNameColumn);

        //Select first item (first row)
        contactsTableView.getSelectionModel().selectFirst();

        //Set cell editing
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
       /* TableCell implementation that draws a TextField node inside the cell.
          By default, the TextFieldTableCell is rendered as a Label when not being edited,
          and as a TextField when in editing mode.*/
        firstNameColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Contact, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Contact, String> t) {
                        (t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setFirstName(t.getNewValue());
                    }
                }
        );

        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameColumn.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setLastName(t.getNewValue())
        );

        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setPhoneNumber(t.getNewValue())
        );

        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setEmail(t.getNewValue())
        );

        notesColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        notesColumn.setOnEditCommit(
                t -> (t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setNotes(t.getNewValue())
        );


    } //End initialization


    //METHODS
    @FXML
    public void handleMenuDelete() {
        Contact selectedContact = contactsTableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Deleting Contact");
            alert.setHeaderText("Are you sure you want to delete contact: " + selectedContact.getFirstName() + " "
                    + selectedContact.getLastName());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                contactData.deleteContact(selectedContact);
                contactData.saveContacts();
            }
        }
    }

    @FXML
    public void handleContextMenuDelete() {
        handleMenuDelete();
    }

    @FXML
    public void handleMenuAdd() {
        setContactDialog("Add New Contact", "Fill in the new contact information.");

        //show dialog
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //get contact dialog controller
            ContactDialogController contactDialogController = fxmlLoader.getController();
            //get the newContact
            Contact newContact = contactDialogController.getNewContact();
            //add the new contact to the contact data
            contactData.addContact(newContact);
            //save the contacts to the file
            contactData.saveContacts();
            //contact was added, select the last contact and scroll to new contact
            contactsTableView.getSelectionModel().select(newContact);
            contactsTableView.scrollTo(newContact);
        }
    }

    @FXML
    public void handleMenuEdit() {
        setContactDialog("Edit Contact", "Edit contact information");

        //select contact to be edited
        Contact selectedContact = contactsTableView.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            //get contact dialog controller
            ContactDialogController contactDialogController = fxmlLoader.getController();
            //populate dialog pane with selected contact info.
            contactDialogController.populateDialogFields(selectedContact);
            //show dialog
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //update the contact obj
                contactDialogController.updateContact(selectedContact);
                //save the contacts to the file
                contactData.saveContacts();
            }
        }
    }


    //setup contact dialog,  helper method
    private void setContactDialog(String title, String headerText) {
        //defining custom dialog
        dialog = new Dialog<>();

        //Specifies the owner Window or null for a top-level, unowned stage.
        dialog.initOwner(mainLayout.getScene().getWindow());
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);

        //Set the dialog fXML layout  file location.
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("contact_dialog.fxml"));

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
    }


    @FXML
    public void handleContextMenuAdd() {
        handleMenuAdd();
    }

    @FXML
    public void handleMenuClose() {
        /* Causes to terminate, will call the Application stop method.
         *  Note: Data will be saved, stop() method was override in Main class.
         *  */
        Platform.exit();
    }


}

