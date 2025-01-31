package com.example.mediconnect;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

//    public void loginAdmin() {
//        String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
//        this.connect = database.connectDb();
//
//        try {
//            this.prepare = this.connect.prepareStatement(sql);
//            this.prepare.setString(1, this.username.getText());
//            this.prepare.setString(2, this.password.getText());
//            this.result = this.prepare.executeQuery();
//            Alert alert;
//            if (!this.username.getText().isEmpty() && !this.password.getText().isEmpty()) {
//                if (this.result.next()) {
//                    getData.username = this.username.getText();
//                    alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Information Message");
//                    alert.setHeaderText((String)null);
//                    alert.setContentText("Successfully Login");
//                    alert.showAndWait();
//                    this.loginBtn.getScene().getWindow().hide();
//                    Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("dashboard.fxml"));
//                    Stage stage = new Stage();
//                    Scene scene = new Scene(root);
//                    root.setOnMousePressed((event) -> {
//                        this.x = event.getSceneX();
//                        this.y = event.getSceneY();
//                    });
//                    root.setOnMouseDragged((event) -> {
//                        stage.setX(event.getScreenX() - this.x);
//                        stage.setY(event.getScreenY() - this.y);
//                    });
//                    stage.initStyle(StageStyle.TRANSPARENT);
//                    stage.setScene(scene);
//                    stage.show();
//                } else {
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error Message");
//                    alert.setHeaderText((String)null);
//                    alert.setContentText("Wrong Username/Password");
//                    alert.showAndWait();
//                }
//            } else {
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Error Message");
//                alert.setHeaderText((String)null);
//                alert.setContentText("Please fill all blank fields");
//                alert.showAndWait();
//            }
//        } catch (Exception var6) {
//            Exception e = var6;
//            e.printStackTrace();
//        }
//
//    }

    public void close() {
        System.exit(0);
    }

    public void initialize(URL url, ResourceBundle rb) {
    }

    public static void main(String[] args) {
        launch();
    }
}