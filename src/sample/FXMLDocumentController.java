package sample;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Kasun Chinthaka
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField textEmail;

    @FXML
    private PasswordField textPassword;


    @FXML
    VBox vbox_1, vbox_2;

    Stage dialogStage = new Stage();
    Scene scene;

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public FXMLDocumentController() {
        connection = ConnectionUtil.connectdb();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        final Text actiontarget = new Text();
        grid.add(actiontarget, 1, 6);

        // Context Menu for error messages
        final ContextMenu usernameValidator = new ContextMenu();
        usernameValidator.setAutoHide(false);


        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        if (email.equals("")) {
//            textEmail.setText("Feild is required!");
//            textPassword.setText("Feild is required!");


            usernameValidator.getItems().clear();
            usernameValidator.getItems().add(
                    new MenuItem("Please enter username"));
            usernameValidator.show(textEmail, Side.RIGHT, 10, 0);


            textEmail.focusedProperty().addListener(
                    new ChangeListener<Boolean>() {
                        @Override
                        public void changed(
                                ObservableValue<? extends Boolean> arg0,
                                Boolean oldPropertyValue, Boolean newPropertyValue) {
                            if (newPropertyValue) {
                                // Clearing message if any
                                actiontarget.setText("");
                                // Hiding the error message
                                usernameValidator.hide();
                            }
                        }
                    });


        } else if (password.equals("")) {


            final ContextMenu passValidator = new ContextMenu();
            passValidator.setAutoHide(false);

            passValidator.getItems().clear();
            passValidator.getItems().add(
                    new MenuItem("Please enter Password"));
            passValidator.show(textPassword, Side.RIGHT, 10, 0);

            textPassword.focusedProperty().addListener(
                    new ChangeListener<Boolean>() {
                        @Override
                        public void changed(
                                ObservableValue<? extends Boolean> arg0,
                                Boolean oldPropertyValue, Boolean newPropertyValue) {
                            if (newPropertyValue) {
                                // Clearing message if any
                                actiontarget.setText("");
                                // Hiding the error message
                                usernameValidator.hide();
                            }
                        }
                    });


        } else {

            String sql = "SELECT * FROM employee WHERE email = ? and password = ?";

            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    infoBox("Enter Correct Email and Password", "Failed", null);
                } else {
                    infoBox("Login Successfull", "Success", null);
                    Node source = (Node) event.getSource();
                    dialogStage = (Stage) source.getScene().getWindow();
                    dialogStage.close();
                    scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLMenu.fxml")));
                    dialogStage.setScene(scene);
                    dialogStage.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    private void signup(ActionEvent event) {

        vbox_1.setVisible(false);
        vbox_2.setVisible(true);

        //  infoBox("This is the Sign  UP page", "Success", null);

    }

    @FXML
    private void signin(ActionEvent event) {

        vbox_1.setVisible(true);
        vbox_2.setVisible(false);

        //  infoBox("This is the Sign  UP page", "Success", null);

    }


    public static void infoBox(String infoMessage, String titleBar, String headerMessage) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}