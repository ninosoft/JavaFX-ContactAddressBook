package mycontacts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import mycontacts.datamodel.ContactData;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main_layout.fxml"));
        primaryStage.setTitle("My Contacts " );
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();

    }

    @Override
    public void init() throws Exception {
        ContactData.getInstance().loadContacts();
        super.init();
    }

    @Override
    public void stop() throws Exception {
        ContactData.getInstance().saveContacts();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
