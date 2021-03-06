package app.View;

import app.Main;
import app.Model.Examiner;
import app.Model.Exams.ClassIIExam;
import app.Model.Exams.GSCExam;
import app.Model.Semester;
import app.Model.Student;
import app.Utilities.DBHandler;
import app.Utilities.SessionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Vector;

public class NewGSCExamController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private Text examinerName;

    @FXML
    private JFXTextField mnoField;

    @FXML
    private JFXTextField toothNoField;

    @FXML
    private ComboBox<String> termComboBox;

    @FXML
    private Text firstSection;

    @FXML
    private Text q1Text;

    @FXML
    private Text q2Text;

    @FXML
    private Text q3Text;

    @FXML
    private Text q4Text;

    @FXML
    private JFXRadioButton q1a1;

    @FXML
    private ToggleGroup q1ToggleGroup;

    @FXML
    private JFXRadioButton q1a2;

    @FXML
    private JFXRadioButton q1a3;

    @FXML
    private JFXRadioButton q1a4;

    @FXML
    private JFXRadioButton q2a1;

    @FXML
    private ToggleGroup q2ToggleGroup;

    @FXML
    private JFXRadioButton q2a2;

    @FXML
    private JFXRadioButton q2a3;

    @FXML
    private JFXRadioButton q3a1;

    @FXML
    private ToggleGroup q3ToggleGroup;

    @FXML
    private JFXRadioButton q3a2;

    @FXML
    private JFXRadioButton q3a3;

    @FXML
    private JFXRadioButton q4a1;

    @FXML
    private ToggleGroup q4ToggleGroup;

    @FXML
    private JFXRadioButton q4a2;

    @FXML
    private JFXRadioButton q4a3;

    @FXML
    private Text secondSection;

    @FXML
    private Text q5Text1;

    @FXML
    private JFXRadioButton q5a1;

    @FXML
    private ToggleGroup q5ToggleGroup;

    @FXML
    private JFXRadioButton q5a2;

    @FXML
    private JFXRadioButton q5a3;

    @FXML
    private Text thirdSection;

    @FXML
    private Text q6Text;

    @FXML
    private JFXRadioButton q6a1;

    @FXML
    private ToggleGroup q6ToggleGroup;

    @FXML
    private JFXRadioButton q6a2;

    @FXML
    private JFXRadioButton q6a3;

    @FXML
    private JFXRadioButton q6a4;

    @FXML
    private ToggleGroup q6ToggleGroup1;

    @FXML
    private Text q7Text;

    @FXML
    private JFXRadioButton q7a1;

    @FXML
    private ToggleGroup q7ToggleGroup;

    @FXML
    private JFXRadioButton q7a2;

    @FXML
    private JFXRadioButton q7a3;

    @FXML
    private Text q8Text;

    @FXML
    private JFXRadioButton q8a1;

    @FXML
    private ToggleGroup q8ToggleGroup;

    @FXML
    private JFXRadioButton q8a2;

    @FXML
    private JFXRadioButton q8a3;

    @FXML
    private JFXRadioButton q8a4;

    @FXML
    private Text fourthSection;

    @FXML
    private Text q9Text;

    @FXML
    private JFXRadioButton q9a1;

    @FXML
    private ToggleGroup q9ToggleGroup;

    @FXML
    private JFXRadioButton q9a2;

    @FXML
    private JFXRadioButton q9a3;

    @FXML
    private Text q10Text;

    @FXML
    private JFXRadioButton q10a1;

    @FXML
    private ToggleGroup q10ToggleGroup;

    @FXML
    private JFXRadioButton q10a2;

    @FXML
    private JFXRadioButton q10a3;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton printButton;

    @FXML
    private JFXButton newExamButton;

    @FXML
    private JFXButton backButton;

    @FXML
    private JFXRadioButton unpreparedToothRadioButton;

    private DBHandler dbHandler = null;
    private SessionManager sessionManager = null;

    //Exam Parameters...
    private Examiner examiner = null;
    private ClassIIExam exam = null;
    private Student examinee = null;
    private Semester semester = null;

    private static final int GSC = 2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            dbHandler = new DBHandler();
            sessionManager = new SessionManager(dbHandler);

            System.out.println("View and Controller both initialized successfully");

            examiner = sessionManager.getUserDetails();
            Main.getStage().setTitle(GSCExam.title);

            setTermComboBox();
            initializeWidgets();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void switchToMainScreen(){

        try{
            Main.changeScene("View/mainScreen.fxml", 1024, 600);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }

    }

    @FXML
    protected void newExam(){
        mnoField.setText(null);
        toothNoField.setText(null);
        q1ToggleGroup.selectToggle(null);
        q2ToggleGroup.selectToggle(null);
        q3ToggleGroup.selectToggle(null);
        q4ToggleGroup.selectToggle(null);
        q5ToggleGroup.selectToggle(null);
        q6ToggleGroup.selectToggle(null);
        q7ToggleGroup.selectToggle(null);
        q8ToggleGroup.selectToggle(null);
        q9ToggleGroup.selectToggle(null);
        q10ToggleGroup.selectToggle(null);
    }

    @FXML
    protected void saveExam(){

        if(validator()){
            assessTooth();
        }
    }

    @FXML
    protected void printExam(){

        try{

            initiatePrint();

        }catch (DocumentException de){

            System.out.println(de.getMessage());

        }catch (FileNotFoundException fnfe){

            System.out.println(fnfe.getMessage());

        }catch (IOException ioe){

            System.out.println(ioe.getMessage());

        }
    }

    public void initiatePrint() throws FileNotFoundException, DocumentException, IOException{
        //Step 1- Create file in Internal Storage...

        String dir = "/Reports/ExamResults/";
        File directory = new File(dir);
        if(! directory.exists()){
            directory.mkdir();
        }
        String filename = GSCExam.title + " - " +  examinee.getMno() + " - " + semester.toFormattedString() + ".pdf";
        //Must remember to sort directory creation issue...
        String path = filename;
        File file = new File(path);
        //file.getParentFile().mkdirs();

        file.setReadable(true);
        OutputStream output = new FileOutputStream(file);

        //Step 2- Create instance of Document.getInstance() of PdfWriter...
        Document document = new Document();
        PdfWriter pdfWriter;
        pdfWriter = PdfWriter.getInstance(document, output);

        //Step 3- Add document header attributes...

        document.open();
        prepareDocument(document);
        writeContent(document);
        document.close();

        pdfWriter.close();
    }

    public Document prepareDocument(Document _document){

        _document.addAuthor(examinerName.getText());
        _document.addTitle(GSCExam.title);
        _document.addProducer();
        _document.addCreationDate();
        _document.addCreator("Examination System");

        return _document;
    }

    public Document writeContent(Document _document) throws DocumentException{

        //Table to hold Document Content...
        PdfPTable table = new PdfPTable(1);

        //setWidthPercentage() to give the Table the entire width provided by the Document...
        table.setWidthPercentage(100);

        //Set Report Parameters...
        String heading = examinee.getFirstName() + " " + examinee.getLastName();
        String heading2 = "Dundee Dental School";

        String matriculationNumber = "Matriculation number: " + String.valueOf(examinee.getMno());
        String toothNumber = "Tooth number: " + String.valueOf(exam.getTooth());
        String heading3 = "Feature of your cavity:";

        String q1 = q1Text.getText() + " " + ((JFXRadioButton) q1ToggleGroup.getSelectedToggle()).getText();
        String q2 = q2Text.getText() + " " + ((JFXRadioButton) q2ToggleGroup.getSelectedToggle()).getText();
        String q3 = q3Text.getText() + " " + ((JFXRadioButton) q3ToggleGroup.getSelectedToggle()).getText();
        String q4 = q4Text.getText() + " " + ((JFXRadioButton) q4ToggleGroup.getSelectedToggle()).getText();
        String q5 = q5Text1.getText() + " " + ((JFXRadioButton) q5ToggleGroup.getSelectedToggle()).getText();
        String q6 = q6Text.getText() + " " + ((JFXRadioButton) q6ToggleGroup.getSelectedToggle()).getText();
        String q7 = q7Text.getText() + " " + ((JFXRadioButton) q7ToggleGroup.getSelectedToggle()).getText();
        String q8 = q8Text.getText() + " " + ((JFXRadioButton) q8ToggleGroup.getSelectedToggle()).getText();
        String q9 = q9Text.getText() + " " + ((JFXRadioButton) q9ToggleGroup.getSelectedToggle()).getText();
        String q10 = q10Text.getText() + " " + ((JFXRadioButton) q10ToggleGroup.getSelectedToggle()).getText();

        String finalGrade = String.valueOf(exam.getGrade());

        String finalFeedback;
        if(exam.getGrade() == 10)
            finalFeedback = "Well done";
        else{
            if(exam.getGrade() >= 6){
                finalFeedback = "Acceptable";
            }else{
                if(exam.getGrade() >= -36){
                    finalFeedback = "Modification required (narrow or shallow)";
                }else{
                    finalFeedback = "Not acceptable (too narrow or too wide/deep)";
                }
            }
        }

        String finalFeedbackString = "Final feedback: " + finalFeedback;


        PdfPCell cell = setCell(heading, PdfPCell.ALIGN_LEFT);
        PdfPCell cell1 = setCell(heading2, PdfPCell.ALIGN_LEFT);
        PdfPCell cell2 = setCell(matriculationNumber, PdfPCell.ALIGN_LEFT);
        PdfPCell cell3 = setCell(toothNumber, PdfPCell.ALIGN_LEFT);
        PdfPCell cell4 = setCell(heading3, PdfPCell.ALIGN_LEFT);
        PdfPCell cell5 = setCell(q1, PdfPCell.ALIGN_LEFT);
        PdfPCell cell6 = setCell(q2, PdfPCell.ALIGN_LEFT);
        PdfPCell cell7 = setCell(q3, PdfPCell.ALIGN_LEFT);
        PdfPCell cell8 = setCell(q4, PdfPCell.ALIGN_LEFT);
        PdfPCell cell9 = setCell(q5, PdfPCell.ALIGN_LEFT);
        PdfPCell cell10 = setCell(q6, PdfPCell.ALIGN_LEFT);
        PdfPCell cell11 = setCell(q7, PdfPCell.ALIGN_LEFT);
        PdfPCell cell12 = setCell(q8, PdfPCell.ALIGN_LEFT);
        PdfPCell cell13 = setCell(q9, PdfPCell.ALIGN_LEFT);
        PdfPCell cell14 = setCell(q10, PdfPCell.ALIGN_LEFT);
        PdfPCell cell15 = setCell(finalFeedbackString, PdfPCell.ALIGN_LEFT);
        //PdfPCell cell14 = setCell(finalGrade, PdfPCell.ALIGN_LEFT);
        table.addCell(cell);
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.addCell(cell9);
        table.addCell(cell10);
        table.addCell(cell11);
        table.addCell(cell12);
        table.addCell(cell13);
        table.addCell(cell14);
        table.addCell(cell15);

        _document.add(table);
        return _document;
    }

    public PdfPCell setCell(String text, int alignment){
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);

        return cell;
    }

    @FXML
    protected void disableWidgets(){
        if(unpreparedToothRadioButton.isSelected()){
            toothNoField.setText(String.valueOf(0));
            toothNoField.setDisable(true);
            disableRadioButtons(true);
        }else{
            toothNoField.setText(null);
            toothNoField.setDisable(false);
            disableRadioButtons(false);
        }
    }

    public void disableRadioButtons(boolean state){
        q1a1.setDisable(state);
        q1a2.setDisable(state);
        q1a3.setDisable(state);
        q1a4.setDisable(state);
        q2a1.setDisable(state);
        q2a2.setDisable(state);
        q2a3.setDisable(state);
        q3a1.setDisable(state);
        q3a2.setDisable(state);
        q3a3.setDisable(state);
        q4a1.setDisable(state);
        q4a2.setDisable(state);
        q4a3.setDisable(state);
        q5a1.setDisable(state);
        q5a2.setDisable(state);
        q5a3.setDisable(state);
        q6a1.setDisable(state);
        q6a2.setDisable(state);
        q6a3.setDisable(state);
        q6a4.setDisable(state);
        q7a1.setDisable(state);
        q7a2.setDisable(state);
        q7a3.setDisable(state);
        q8a1.setDisable(state);
        q8a2.setDisable(state);
        q8a3.setDisable(state);
        q8a4.setDisable(state);
        q9a1.setDisable(state);
        q9a2.setDisable(state);
        q9a3.setDisable(state);
        q10a1.setDisable(state);
        q10a2.setDisable(state);
        q10a3.setDisable(state);
    }

    public void setTermComboBox(){
        ObservableList<String> semesterObservableList = FXCollections.observableArrayList();
        Vector<Semester> semesters = dbHandler.readAllSemesters();
        if(semesters != null && ! semesters.isEmpty()){
            Vector<String> semestersToString = new Vector<>();
            for (Semester semester : semesters){
                semestersToString.add(semester.toFormattedString());
            }
            semesterObservableList.addAll(semestersToString);

            termComboBox.setItems(semesterObservableList);
            termComboBox.getSelectionModel().select(semestersToString.firstElement());
        }else{
            termComboBox.setPromptText("No Semesters to display");
        }
    }

    public void initializeWidgets(){

        examinerName.setText(examinerName.getText() + ": " + examiner.getFirstName() + " " + examiner.getLastName());
        initializeRadioButtons();
    }

    public void initializeRadioButtons(){
        q1a1.setText(GSCExam.q1a1);
        q1a2.setText(GSCExam.q1a2);
        q1a3.setText(GSCExam.q1a3);
        q1a4.setText(GSCExam.q1a4);

        q2a1.setText(GSCExam.q2a1);
        q2a2.setText(GSCExam.q2a2);
        q2a3.setText(GSCExam.q2a3);

        q3a1.setText(GSCExam.q3a1);
        q3a2.setText(GSCExam.q3a2);
        q3a3.setText(GSCExam.q3a3);

        q4a1.setText(GSCExam.q4a1);
        q4a2.setText(GSCExam.q4a2);
        q4a3.setText(GSCExam.q4a3);

        q5a1.setText(GSCExam.q5a1);
        q5a2.setText(GSCExam.q5a2);
        q5a3.setText(GSCExam.q5a3);

        q6a1.setText(GSCExam.q6a1);
        q6a2.setText(GSCExam.q6a2);
        q6a3.setText(GSCExam.q6a3);
        q6a4.setText(GSCExam.q6a4);

        q7a1.setText(GSCExam.q7a1);
        q7a2.setText(GSCExam.q7a2);
        q7a3.setText(GSCExam.q7a3);

        q8a1.setText(GSCExam.q8a1);
        q8a2.setText(GSCExam.q8a2);
        q8a3.setText(GSCExam.q8a3);
        q8a4.setText(GSCExam.q8a4);

        q9a1.setText(GSCExam.q9a1);
        q9a2.setText(GSCExam.q9a2);
        q9a3.setText(GSCExam.q9a3);

        q10a1.setText(GSCExam.q10a1);
        q10a2.setText(GSCExam.q10a2);
        q10a3.setText(GSCExam.q10a3);


        initializeAnswerGrades();

    }

    public void initializeAnswerGrades(){
        //Set RadioButtons to hold grades for different answers...
        q1a1.setUserData(1);
        q1a2.setUserData(0);
        q1a3.setUserData(-4);
        q1a4.setUserData(-46);

        q2a1.setUserData(1);
        q2a2.setUserData(-4);
        q2a3.setUserData(-46);

        q3a1.setUserData(1);
        q3a2.setUserData(-4);
        q3a3.setUserData(-46);

        q4a1.setUserData(1);
        q4a2.setUserData(-4);
        q4a3.setUserData(-46);

        q5a1.setUserData(1);
        q5a2.setUserData(-4);
        q5a3.setUserData(-46);

        q6a1.setUserData(1);
        q6a2.setUserData(0);
        q6a3.setUserData(-4);
        q6a4.setUserData(-46);

        q7a1.setUserData(1);
        q7a2.setUserData(-4);
        q7a3.setUserData(-46);

        q8a1.setUserData(1);
        q8a2.setUserData(0);
        q8a3.setUserData(-4);
        q8a4.setUserData(-46);

        q9a1.setUserData(1);
        q9a2.setUserData(-4);
        q9a3.setUserData(-46);

        q10a1.setUserData(1);
        q10a2.setUserData(0);
        q10a3.setUserData(-46);
    }

    public void assessTooth(){

        try{
            semester = Semester.parseFormattedString(termComboBox.getSelectionModel().getSelectedItem().toString());
            semester = dbHandler.readSemester(semester.getYear(), semester.getTerm());

            examinee = dbHandler.readStudent(Integer.parseInt(mnoField.getText()), semester.getId());

            if(mnoValidator()){
                exam = new ClassIIExam();
                exam.setSemesterId(semester.getId());
                exam.setStudentId(examinee.getId());
                exam.setType(GSC);
                exam.setDate();
                exam.setExaminerId(examiner.getId());

                int grade = 0;
                if(unpreparedToothRadioButton.isSelected()){
                    exam.setTooth(0);
                    grade = GSCExam.FATAL_MISTAKE;
                }else{
                    exam.setTooth(Integer.parseInt(toothNoField.getText()));

                    grade += Integer.parseInt(q1ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q2ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q3ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q4ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q5ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q6ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q7ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q8ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q9ToggleGroup.getSelectedToggle().getUserData().toString());
                    grade += Integer.parseInt(q10ToggleGroup.getSelectedToggle().getUserData().toString());
                }


                exam.setGrade(grade);
                dbHandler.createExam(exam);

                createDialog(99, grade);

                System.out.println(grade);
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }




    //Validation and Dialog methods...
    public boolean validator(){
        if(! unpreparedToothRadioButton.isSelected()){
            if (mnoField.getText().isEmpty()){
                createDialog(1, 0);
                return false;
            }

            if(toothNoField.getText().isEmpty()){
                createDialog(2, 0);
                return false;
            }

            if(q1ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q2ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q3ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q4ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q5ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q6ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q7ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q8ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q9ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }

            if( q10ToggleGroup.getSelectedToggle() == null){
                createDialog(3, 0);
                return false;
            }
        }

        return true;
    }

    public boolean mnoValidator(){
        //Validating Student MNO...
        if (examinee.getId() == 0 || examinee == null){
            createDialog(4, 0);
            return false;
        }

        return true;
    }

    public void createDialog(int errorCode, int grade){

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Input error"));
        Text body = null;

        switch (errorCode){
            case 1:
                body = new Text("Please make sure to enter matriculation number");
                break;
            case 2:
                body = new Text("Please make sure to enter tooth number");
                break;
            case 3:
                body = new Text("Please make sure all questions are answered");
                break;
            case 4:
                body = new Text("Invalid matriculation number\nPlease check your input");
                break;

            default:
                content.setHeading(new Text("Operation successful"));
                body = new Text("Your score is: " + String.valueOf(grade));
                break;
        }

        content.setBody(body);
        JFXButton button = new JFXButton("Ok");
        content.setActions(button);

        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);

        button.setOnAction(e -> {
            dialog.close();
        });

        dialog.show();

    }
}
