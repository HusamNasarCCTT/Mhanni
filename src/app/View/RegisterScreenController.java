package app.View;

import app.Main;
import app.Model.Exam;
import app.Model.Examiner;
import app.Utilities.DBHandler;
import app.Utilities.SessionManager;
import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by husam on 7/13/17.
 */
public class RegisterScreenController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private JFXTextField firstnameField;
    @FXML
    private JFXTextField lastnameField;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXRadioButton examinerRadioButton;
    @FXML
    private JFXRadioButton adminRadioButton;
    @FXML
    private ToggleGroup examinerTitleGroup;

    private DBHandler dbHandler = null;
    private SessionManager sessionManager = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Main.getStage().setTitle("Register for new examiner account");
        examinerRadioButton.setUserData(1);
        adminRadioButton.setUserData(2);
        try{
            dbHandler = new DBHandler();
            sessionManager = new SessionManager(dbHandler);

            System.out.println("View and Controller both initialized successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

    @FXML
    protected void switchToLoginScreen(){
        try{
            //sessionManager.logout();
            Main.changeScene("View/loginScreen.fxml", Main.loginScreenWidth, Main.loginScreenWidth);
        }catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }
    @FXML
    protected void registerExaminer(ActionEvent event) {
        try{
            if(validator()){
                String firstname = firstnameField.getText(),
                        lastname = lastnameField.getText(),
                        username = usernameField.getText(),
                        password = passwordField.getText();

                int title = (int) examinerTitleGroup.getSelectedToggle().getUserData();

                Examiner examiner = new Examiner();
                examiner.setFirstName(firstname);
                examiner.setLastName(lastname);
                examiner.setUsername(username);
                examiner.setPassword(password);
                examiner.setTitle(title);

                dbHandler.createExaminer(examiner);
                createDialog(5);
                System.out.println(examiner.toString());

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    //Validation Methods...
    public boolean validator(){
        if (firstnameField.getText().isEmpty()){
            createDialog(1);
            return false;
        }

        if(lastnameField.getText().isEmpty()){
            createDialog(2);
            return false;
        }

        if(usernameField.getText().isEmpty()){
            createDialog(3);
            return false;
        }

        if(passwordField.getText().isEmpty()){
            createDialog(4);
            return false;
        }

        return true;
    }

    public void createDialog(int errorCode){

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Input error"));
        Text body = null;

        switch (errorCode){
            case 1:
                body = new Text("Please make sure to enter first name");
                break;
            case 2:
                body = new Text("Please make sure to enter last name");
                break;
            case 3:
                body = new Text("Please make sure to enter username");
                break;
            case 4:
                body = new Text("Please make sure to enter password");
                break;

                default:
                    content.setHeading(new Text("Operation successful"));
                    body = new Text("Examiner registered successfully\nYou will now be returned to the login screen");
                    break;
        }

        content.setBody(body);
        JFXButton button = new JFXButton("Ok");
        content.setActions(button);

        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);

        button.setOnAction(e -> {
            dialog.close();
            if(errorCode == 5){
                try{
                    Main.changeScene("View/loginScreen.fxml", 300, 275);
                }catch (IOException ioException){
                    System.out.println(ioException.getMessage());
                }
            }
        });

        dialog.show();

    }
}
