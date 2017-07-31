package app.View;

import app.Model.Exam;
import app.Model.Examiner;
import app.Utilities.DBHandler;
import app.Utilities.SessionManager;
import app.Main;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {

    //
    @FXML private JFXButton loginButton,
                            registerButton;
    @FXML private JFXTextField username;
    @FXML private JFXPasswordField password;
    @FXML private StackPane stackPane;


    private DBHandler dbHandler = null;
    private SessionManager sessionManager = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            Main.getStage().setTitle("Examination System");
            dbHandler = new DBHandler();
            sessionManager = new SessionManager(dbHandler);

            System.out.println("View and Controller both initialized successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @FXML protected void attemptLogin(){
        String userName = this.username.getText();
        String passWord = this.password.getText();

        if(validator()){

            if(sessionManager.login(userName, passWord)){
                try{
                    Examiner examiner = dbHandler.readExaminer(userName, passWord);
                    if(examiner.getTitle() == 1){
                        Main.changeScene("View/mainScreen.fxml", 1024, 600);
                    }else{
                        Main.changeScene("View/adminPage.fxml", 1024, 600);
                    }
                }catch (IOException e){
                    System.out.println(e.getMessage());
                }
            }else{
                createDialog(3);
            }

        }
    }

    @FXML protected void switchToRegistryScene(){
        try{
            Main.changeScene("View/registerScreen.fxml", 480, 640);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }


    //Validation Methods...
    public boolean validator(){
        if (username.getText().isEmpty()){
            createDialog(1);
            return false;
        }

        if(password.getText().isEmpty()){
            createDialog(2);
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
                body = new Text("Please make sure to enter username");
                break;
            case 2:
                body = new Text("Please make sure to enter password");
                break;
            case 3:
                body = new Text("Incorrect username or password\nPlease try again");
                break;

            default:
                content.setHeading(new Text("Operation successful"));
                body = new Text("Login successful\nClick 'OK' to gain access");
                break;
        }

        content.setBody(body);
        JFXButton button = new JFXButton("OK");
        content.setActions(button);

        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);

        button.setOnAction(e -> {
            dialog.close();
        });

        dialog.show();

    }

}
