package edu.ufp.esocial.api.gui;

import edu.ufp.esocial.api.gui.user.GuiController;
import edu.ufp.esocial.api.service.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiStart extends Application {

    private static Database database;

    public static void start_gui(String[] args, Database db) {
        database = db;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("user/usergui.fxml"));
        Parent root = loader.load();

        GuiController guiController = loader.getController();
        guiController.setDatabase(database);
        Scene scene = new Scene(root);

        primaryStage.setTitle("eSocial");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
