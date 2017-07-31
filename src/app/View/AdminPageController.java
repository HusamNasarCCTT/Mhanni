package app.View;

import app.Main;
import app.Model.Examiner;
import app.Model.Exams.GSCExam;
import app.Model.Semester;
import app.Utilities.DBHandler;
import app.Utilities.SessionManager;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by husam on 7/19/17.
 */
public class AdminPageController implements Initializable {


    @FXML
    private ComboBox<String> termComboBox;

    @FXML
    private JFXTreeTableView<TableData> tableView;

    @FXML
    private JFXRadioButton classIIRB;

    @FXML
    private ToggleGroup examTypeToggleGroup;

    @FXML
    private JFXRadioButton gscRB;

    @FXML
    private JFXButton saveButton;

    private DBHandler dbHandler = null;
    private SessionManager sessionManager = null;
    private Examiner examiner = null;
    private Semester semester = null;
    private int examType = 1;
    private String examTypeString = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try{
            dbHandler = new DBHandler();
            sessionManager = new SessionManager(dbHandler);

            System.out.println("View and Controller both initialized successfully");

            examiner = sessionManager.getUserDetails();
            Main.getStage().setTitle(examiner.getFirstName() + " " + examiner.getLastName() + "'s admin dashboard");

            classIIRB.setUserData(1);
            gscRB.setUserData(2);

            setTermComboBox();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


        loadTableData();

    }

    @FXML
    protected void loadTableData(){

        if(! termComboBox.getSelectionModel().isEmpty()){
            try{
                Semester semesterNoId = Semester.parseFormattedString(termComboBox.getSelectionModel().getSelectedItem());
                semester = dbHandler.readSemester(semesterNoId.getYear(), semesterNoId.getTerm());

                examType = (int) examTypeToggleGroup.getSelectedToggle().getUserData();
                if(examType == 1)
                    examTypeString = "Class II";
                else
                    examTypeString = "Full Veneer Crown";
                prepareTableColumns(transformHashSetToTableDataSet(getTableData(examType, semester)));
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
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
                        hashMap.get(DBHandler.KEY_TABLE_EXAMINER1_GRADE).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_EXAMINER2_GRADE).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_EXAMINER3_GRADE).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_EXAM_DATE).toString(),
                        hashMap.get(DBHandler.KEY_TABLE_AVG_GRADE).toString());

                tableDataSet.add(row);
            }
        }

        return tableDataSet;
    }

    public Vector<HashMap> getTableData(int examType, Semester semester){
        return dbHandler.readStudentAverageGradesPerSemesterAndType(examType, semester);
        //return null;
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

            JFXTreeTableColumn<TableData, String> examiner1GradeCol = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_EXAMINER1_GRADE);
            //gradeCol.setPrefWidth(150);
            examiner1GradeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().examiner1grade;
                }
            });

            JFXTreeTableColumn<TableData, String> examiner2GradeCol = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_EXAMINER2_GRADE);
            //gradeCol.setPrefWidth(150);
            examiner2GradeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().examiner2grade;
                }
            });

            JFXTreeTableColumn<TableData, String> examiner3GradeCol = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_EXAMINER3_GRADE);
            //gradeCol.setPrefWidth(150);
            examiner3GradeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().examiner3grade;
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

            JFXTreeTableColumn<TableData, String> avgGradeCol = new JFXTreeTableColumn<>(DBHandler.KEY_TABLE_AVG_GRADE);
            //gradeCol.setPrefWidth(150);
            avgGradeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TableData, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TableData, String> param) {
                    return param.getValue().getValue().avgGrade;
                }
            });

            ObservableList<TableData> tableDataObservableList = FXCollections.observableArrayList();
            tableDataObservableList.addAll(tableData);

            final TreeItem<TableData> root = new RecursiveTreeItem<TableData>(tableDataObservableList, RecursiveTreeObject::getChildren);
            tableView.getColumns().setAll(mnoCol, firstName, lastName, examTypeCol, examiner1GradeCol, examiner2GradeCol,
                    examiner3GradeCol, examDateCol, avgGradeCol);

            tableView.setRoot(root);
            tableView.setShowRoot(false);
        }

    }


    class TableData extends RecursiveTreeObject<TableData> {

        StringProperty mno;
        StringProperty firstName;
        StringProperty lastName;
        StringProperty examType;
        StringProperty examiner1grade;
        StringProperty examiner2grade;
        StringProperty examiner3grade;
        StringProperty date;
        StringProperty avgGrade;

        public TableData(String mno, String firstName, String lastName, String examType, String examiner1grade,
                         String examiner2Grade, String examiner3Grade, String date, String avgGrade) {
            this.mno = new SimpleStringProperty(mno);
            this.firstName = new SimpleStringProperty(firstName);
            this.lastName = new SimpleStringProperty(lastName);
            if(examType.equals("1"))
                this.examType = new SimpleStringProperty("Class II");
            else
                this.examType = new SimpleStringProperty("Full Veneer Crown");
            this.examiner1grade = new SimpleStringProperty(examiner1grade);
            this.examiner2grade = new SimpleStringProperty(examiner2Grade);
            this.examiner3grade = new SimpleStringProperty(examiner3Grade);
            this.date = new SimpleStringProperty(date);
            this.avgGrade = new SimpleStringProperty(avgGrade);
        }

    }


    @FXML
    protected void saveReport(){

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

        String dir = "/Reports/AdminReports/StudentAverages";
        File directory = new File(dir);
        if(! directory.exists()){
            directory.mkdir();
        }
        String filename = examTypeString + " - " + semester.toFormattedString() + ".pdf";
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

        _document.addAuthor(examiner.getFirstName() + " " + examiner.getLastName());
        _document.addTitle(examTypeString);
        _document.addProducer();
        _document.addCreationDate();
        _document.addCreator("Examination System");

        return _document;
    }

    public Document writeContent(Document _document) throws DocumentException{

        /*//Table to hold Document Content...
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

        _document.add(table);*/
        return _document;
    }

    public PdfPCell setCell(String text, int alignment){
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);

        return cell;
    }


}
