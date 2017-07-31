package app.View;

import app.Main;
import app.Model.Examiner;
import app.Model.Semester;
import app.Model.Student;
import app.Utilities.DBHandler;
import app.Utilities.SessionManager;
import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by husam on 7/16/17.
 */
public class NewStudentScreenController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXTextField mnoField;

    @FXML
    private JFXTextField firstnameField;

    @FXML
    private JFXTextField lastnameField;

    @FXML
    private JFXTextField yearField;

    @FXML
    private JFXRadioButton fallRadioButton;

    @FXML
    private ToggleGroup studentTerm;

    @FXML
    private JFXRadioButton springRadioButton;

    private DBHandler dbHandler = null;
    private SessionManager sessionManager = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            dbHandler = new DBHandler();
            sessionManager = new SessionManager(dbHandler);

            System.out.println("View and Controller both initialized successfully");
            fallRadioButton.setUserData("Fall");
            springRadioButton.setUserData("Spring");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML protected void switchToMainScreen(){

            try{
                Main.changeScene("View/mainScreen.fxml", 1024, 600);
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
    }
    @FXML protected void registerStudent(){
        try{
            if(validator()){
                String firstname = firstnameField.getText(),
                        lastname = lastnameField.getText();

                int mno = Integer.parseInt(mnoField.getText());
                int year = Integer.parseInt(yearField.getText());
                int term;
                if(studentTerm.getSelectedToggle().getUserData().equals("Spring"))
                    term = 1;
                else
                    term = 2;
                Student student = new Student();

                student.setMno(mno);
                student.setFirstName(firstname);
                student.setLastName(lastname);

                Semester semester = dbHandler.readOrCreateAndReadSemester(year, term);

                student.setSemesterId(semester.getId());

                dbHandler.createStudent(student);
                createDialog(5);
                System.out.println(student.toString());

            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public boolean validator(){
        if (mnoField.getText().isEmpty()){
            createDialog(1);
            return false;
        }

        if(firstnameField.getText().isEmpty()){
            createDialog(2);
            return false;
        }

        if(lastnameField.getText().isEmpty()){
            createDialog(3);
            return false;
        }

        if(yearField.getText().isEmpty()){
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
                body = new Text("Please make sure to enter Matriculation Number");
                break;
            case 2:
                body = new Text("Please make sure to enter first name");
                break;
            case 3:
                body = new Text("Please make sure to enter last name");
                break;
            case 4:
                body = new Text("Please make sure to enter course year");
                break;

            default:
                content.setHeading(new Text("Operation successful"));
                body = new Text("Student registered successfully");
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
                    Main.changeScene("View/mainScreen.fxml", 1024, 600);
                }catch (IOException ioException){
                    System.out.println(ioException.getMessage());
                }
            }
        });


        dialog.show();

    }
}
