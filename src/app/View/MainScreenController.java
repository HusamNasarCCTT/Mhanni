package app.View;

import app.Main;
import app.Model.Exam;
import app.Model.Examiner;
import app.Model.Semester;
import app.Model.Student;
import app.Utilities.DBHandler;
import app.Utilities.SessionManager;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by husam on 7/13/17.
 */
public class MainScreenController implements Initializable {

    private DBHandler dbHandler = null;
    private SessionManager sessionManager = null;
    private Examiner examiner = null;

    @FXML JFXButton newStudentButton;
    @FXML JFXButton logoutButton;
    @FXML ComboBox<String> termComboBox;
    @FXML private JFXTreeTableView<TableData> tableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            dbHandler = new DBHandler();
            sessionManager = new SessionManager(dbHandler);

            System.out.println("View and Controller both initialized successfully");

            examiner = sessionManager.getUserDetails();
            Main.getStage().setTitle(examiner.getFirstName() + " " + examiner.getLastName() + "'s dashboard");

            setTermComboBox();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        loadTableData();

    }

    @FXML protected void switchToNewStudentScreen(){
        try{
            Main.changeScene("View/newStudentScreen.fxml", 480, 640);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML protected void switchToNewExamScreen(){
        try{
            Main.changeScene("View/newClassIIExam.fxml", 862, 913);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML protected void switchToGSCExamScreen(){
        try{
            Main.changeScene("View/newGSCExam.fxml", 862, 913);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML protected void logOut(){
        try{
            sessionManager.logout();
            Main.changeScene("View/loginScreen.fxml", Main.loginScreenWidth, Main.loginScreenWidth);
        }catch (IOException ioException){
            System.out.println(ioException.getMessage());
        }
    }

    @FXML protected void loadTableData(){

        try{
            Semester semesterNoId = Semester.parseFormattedString(termComboBox.getSelectionModel().getSelectedItem());
            Semester semester = dbHandler.readSemester(semesterNoId.getYear(), semesterNoId.getTerm());

            prepareTableColumns(transformHashSetToTableDataSet(getTableData(examiner, semester)));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

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

    public Vector<TableData> transformHashSetToTableDataSet(Vector<HashMap> hashMapVector){
        Vector<TableData> tableDataSet = new Vector<>();

        if(! hashMapVector.isEmpty()){
            for (HashMap hashMap : hashMapVector){
                TableData row = new TableData(hashMap.get(DBHandler.KEY_TABLE_MNO).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_FIRSTNAME).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_LASTNAME).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_EXAM_TYPE).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_EXAM_DATE).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_GRADE).toString());

                tableDataSet.add(row);
            }
        }

        return tableDataSet;
    }

    public Vector<HashMap> getTableData(Examiner examiner, Semester semester){
        return dbHandler.readExaminerExamsPerSemester(examiner, semester);
    }

    public void prepareTableColumns(Vector<TableData> tableData){
        if (! tableData.isEmpty()){
            JFXTreeTableColumn<TableData, String> mnoCol = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_MNO);
            //mnoCol.setPrefWidth(150);
            mnoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().mno;
                }
            });

            JFXTreeTableColumn<TableData, String> firstName = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_FIRSTNAME);
            //mnoCol.setPrefWidth(150);
            mnoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().firstName;
                }
            });

            JFXTreeTableColumn<TableData, String> lastName = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_LASTNAME);
            //mnoCol.setPrefWidth(150);
            mnoCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().lastName;
                }
            });

            JFXTreeTableColumn<TableData, String> examTypeCol = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_EXAM_TYPE);
            //examTypeCol.setPrefWidth(150);
            examTypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().examType;
                }
            });

            JFXTreeTableColumn<TableData, String> examDateCol = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_EXAM_DATE);
            //examDateCol.setPrefWidth(150);
            examDateCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().date;
                }
            });

            JFXTreeTableColumn<TableData, String> gradeCol = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_GRADE);
            //gradeCol.setPrefWidth(150);
            gradeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().grade;
                }
            });

            ObservableList<TableData> tableDataObservableList = FXCollections.observableArrayList();
            tableDataObservableList.addAll(tableData);

            final TreeItem<TableData> root = new RecursiveTreeItem<TableData>(tableDataObservableList, RecursiveTreeObject::getChildren);
            tableView.getColumns().setAll(mnoCol, firstName, lastName, examTypeCol, examDateCol, gradeCol);

            tableView.setRoot(root);
            tableView.setShowRoot(false);
        }

    }

    class TableData extends RecursiveTreeObject<TableData> {

        StringProperty mno;
        StringProperty firstName;
        StringProperty lastName;
        StringProperty examType;
        StringProperty date;
        StringProperty grade;

        public TableData(String mno, String firstName, String lastName, String examType, String date, String grade) {
            this.mno = new SimpleStringProperty(mno);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            if(examType.equals("1"))
                this.examType = new SimpleStringProperty("Class II");
            else
                this.examType = new SimpleStringProperty("Full Veneer Crown");
            this.date = new SimpleStringProperty(date);
            this.grade = new SimpleStringProperty(grade);
        }

    }

}
