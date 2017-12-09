package mycontacts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import mycontacts.datamodel.ContactData;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main_layout.fxml"));
        primaryStage.setTitle("My Contacts " );
        Image image = new Image("file:contacts-icon32.png");
        primaryStage.getIcons().add(image);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(800.0);
        primaryStage.setScene(new Scene(root, 870, 600));
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
