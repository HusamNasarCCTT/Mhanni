package app.Utilities;

import app.Model.*;
import app.Model.Exams.ClassIIExam;

import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by husam on 7/2/17.
 */


public class DBHandler {
    private final static String dbName = "exam.db";
    private final static String dbPath = "jdbc:sqlite:" + dbName;

    private final static String TABLE_EXAMINER = "EXAMINER",
                                TABLE_EXAM = "EXAM",
                                TABLE_SEMESTER = "SEMESTER",
                                TABLE_STUDENT = "STUDENT",
                                TABLE_TAKES = "TAKES";

    private final static String COLUMN_EXAMINER_ID = "_id",
                                COLUMN_EXAMINER_FIRSTNAME = "firstname",
                                COLUMN_EXAMINER_LASTNAME = "lastname",
                                COLUMN_EXAMINER_TITLE = "title",
                                COLUMN_EXAMINER_USERNAME = "username",
                                COLUMN_EXAMINER_PASSWORD = "password";

    private final static String COLUMN_EXAM_ID = "_id",
                                COLUMN_EXAM_TYPE = "type",
                                COLUMN_EXAM_DATE = "date",
                                COLUMN_EXAM_EXAM_EXAMINER = "examiner_id",
                                COLUMN_EXAM_EXAM_SEMESTER = "semester_id",
                                COLUMN_EXAM_EXAM_STUDENT = "student_id",
                                COLUMN_EXAM_EXAM_TOOTH = "tooth_no",
                                COLUMN_EXAM_EXAM_GRADE = "grade";

    private final static String COLUMN_SEMESTER_ID = "_id",
                                COLUMN_SEMESTER_YEAR = "year",
                                COLUMN_SEMESTER_TERM = "term";

    private final static String COLUMN_STUDENT_ID = "_id",
                                COLUMN_STUDENT_MNO = "mno",
                                COLUMN_STUDENT_FIRSTNAME = "firstname",
                                COLUMN_STUDENT_LASTNAME = "lastname",
                                COLUMN_STUDENT_SEMESTER = "semester_id";

    /*private final static String COLUMN_TAKES_STUDENT_ID = "student_id",
                                COLUMN_TAKES_EXAM_ID = "exam_id",
                                COLUMN_TAKES_GRADE = "grade",
                                COLUMN_TAKES_TOOTH_NO = "tooth_no";*/

    public final static String KEY_TABLE_MNO = "M-NO";
    public final static String KEY_TABLE_FIRSTNAME = "First name";
    public final static String KEY_TABLE_LASTNAME = "Last name";
    public final static String KEY_TABLE_EXAM_TYPE = "Exam";
    public final static String KEY_TABLE_EXAM_DATE = "Date";
    public final static String KEY_TABLE_GRADE = "Grade";
    public final static String KEY_TABLE_EXAMINER1_GRADE = "Examiner 1";
    public final static String KEY_TABLE_EXAMINER2_GRADE = "Examiner 2";
    public final static String KEY_TABLE_EXAMINER3_GRADE = "Examiner 3";
    public final static String KEY_TABLE_AVG_GRADE = "Average";


    private Connection conn = null;


    public DBHandler() {

        if(connectToDb())
            System.out.println("Connection successful");
        else
            System.out.println("Connection failed, check log file for more info");
    }

    public boolean connectToDb(){
        try{

            //Validation to create DB and tables...
            if(! ifExists(dbName)){
                conn = DriverManager.getConnection(dbPath);
                createTables(conn);
                disconnectFromDb();
            }

            conn = DriverManager.getConnection(dbPath);
            return true;
        }catch(SQLException e){
            return false;
        }
    }

    public void disconnectFromDb(){
        try{
            if(conn.isClosed()){
                System.out.println("Connection already closed");
            }else{
                conn.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean ifExists(String dbName){
        File file = new File(dbName);
        if(file.exists())
            return true;
        else
            return false;
    }

    public void createTables(Connection connection){
        String examinerQuery = "CREATE TABLE " + TABLE_EXAMINER
                + " ( " + COLUMN_EXAMINER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EXAMINER_FIRSTNAME + " TEXT NOT NULL, "
                + COLUMN_EXAMINER_LASTNAME + " TEXT NOT NULL, "
                + COLUMN_EXAMINER_USERNAME + " TEXT UNIQUE NOT NULL, "
                + COLUMN_EXAMINER_PASSWORD + " TEXT NOT NULL, "
                + COLUMN_EXAMINER_TITLE + " INTEGER NOT NULL" + ");";

        String examQuery = "CREATE TABLE " + TABLE_EXAM
                + " ( " + COLUMN_EXAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EXAM_TYPE + " BOOLEAN NOT NULL, "
                + COLUMN_EXAM_DATE + " TEXT NOT NULL, "
                + COLUMN_EXAM_EXAM_EXAMINER + " INTEGER NOT NULL, "
                + COLUMN_EXAM_EXAM_SEMESTER + " INTEGER NOT NULL, "
                + COLUMN_EXAM_EXAM_STUDENT + " INTEGER NOT NULL, "
                + COLUMN_EXAM_EXAM_TOOTH + " INTEGER NOT NULL, "
                + COLUMN_EXAM_EXAM_GRADE + " INTEGER NOT NULL, "
                + " FOREIGN KEY (" + COLUMN_EXAM_EXAM_EXAMINER + ") REFERENCES " + TABLE_EXAMINER + "(" + COLUMN_EXAMINER_ID + "), "
                + " FOREIGN KEY (" + COLUMN_EXAM_EXAM_SEMESTER + ") REFERENCES " + TABLE_SEMESTER + "(" + COLUMN_SEMESTER_ID + "), "
                + " FOREIGN KEY (" + COLUMN_EXAM_EXAM_STUDENT + ") REFERENCES " + TABLE_STUDENT + "(" + COLUMN_STUDENT_ID + " ));";

        String semesterQuery = "CREATE TABLE " + TABLE_SEMESTER
                + " ( " + COLUMN_SEMESTER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SEMESTER_YEAR + " INTEGER NOT NULL, "
                + COLUMN_SEMESTER_TERM + " BOOLEAN NOT NULL " + ");";

        String studentQuery = "CREATE TABLE " + TABLE_STUDENT
                + " ( " + COLUMN_STUDENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_STUDENT_MNO + " INTEGER NOT NULL, "
                + COLUMN_STUDENT_FIRSTNAME + " TEXT NOT NULL, "
                + COLUMN_STUDENT_LASTNAME + " TEXT NOT NULL, "
                + COLUMN_STUDENT_SEMESTER + " INT NOT NULL, "
                + " FOREIGN KEY (" + COLUMN_STUDENT_SEMESTER + ") REFERENCES " + TABLE_SEMESTER + "(" + COLUMN_SEMESTER_ID + " ));";

        /*String takesQuery = "CREATE TABLE " + TABLE_TAKES
                + " ( " + COLUMN_TAKES_STUDENT_ID + " INTEGER, "
                + COLUMN_TAKES_EXAM_ID + " INTEGER, "
                + COLUMN_TAKES_GRADE + " INTEGER NOT NULL, "
                + COLUMN_TAKES_TOOTH_NO + " INTEGER NOT NULL, "
                + " PRIMARY KEY (" + COLUMN_TAKES_STUDENT_ID + ", " + COLUMN_TAKES_EXAM_ID + "),"
                + " FOREIGN KEY (" + COLUMN_TAKES_STUDENT_ID + ") REFERENCES " + TABLE_STUDENT + "(" + COLUMN_STUDENT_ID + "), "
                + " FOREIGN KEY (" + COLUMN_TAKES_EXAM_ID + ") REFERENCES " + TABLE_EXAM + "(" + COLUMN_EXAM_ID + " ));";*/

        try{
            if(!connection.isClosed()){
                Statement statement = connection.createStatement();
                statement.execute(examinerQuery);
                statement.execute(examQuery);
                statement.execute(semesterQuery);
                statement.execute(studentQuery);

                System.out.println("Tables created successfully");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    //Examiner CRUD Methods...

    public void createExaminer(Examiner examiner){
        connectToDb();

        String insertStatement = "INSERT INTO " + TABLE_EXAMINER + "(" + COLUMN_EXAMINER_FIRSTNAME + ", "
                                                + COLUMN_EXAMINER_LASTNAME + ", " + COLUMN_EXAMINER_TITLE + ", "
                                                + COLUMN_EXAMINER_USERNAME + ", " + COLUMN_EXAMINER_PASSWORD + ") "
                                                + "VALUES (?, ?, ?, ?, ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);

            preparedStatement.setString(1, examiner.getFirstName());
            preparedStatement.setString(2, examiner.getLastName());
            preparedStatement.setInt(3, examiner.getTitle());
            preparedStatement.setString(4, examiner.getUsername());
            preparedStatement.setString(5, examiner.getPassword());

            preparedStatement.execute();

        }catch(SQLException e){

            System.out.println(e.getMessage());

        }finally {
            disconnectFromDb();
        }
    }

    public Examiner readExaminer(String username, String password){
        Examiner examiner = null;

        connectToDb();
        String examinerQuery = "SELECT * FROM " + TABLE_EXAMINER + " WHERE (" + COLUMN_EXAMINER_USERNAME
                                                + " = " + "\"" + username + "\"" + " AND " + COLUMN_EXAMINER_PASSWORD
                                                + " = " + "\"" + password + "\");";

        try{
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(examinerQuery);

            examiner = new Examiner();
            examiner.setId(resultSet.getInt(COLUMN_EXAMINER_ID));
            examiner.setTitle(resultSet.getInt(COLUMN_EXAMINER_TITLE));
            examiner.setFirstName(resultSet.getString(COLUMN_EXAMINER_FIRSTNAME));
            examiner.setLastName(resultSet.getString(COLUMN_EXAMINER_LASTNAME));
            examiner.setUsername(resultSet.getString(COLUMN_EXAMINER_USERNAME));
            examiner.setPassword(resultSet.getString(COLUMN_EXAMINER_PASSWORD));


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            disconnectFromDb();
            return examiner;
        }
    }

    public Vector<Examiner> readAllExaminers (){

        Vector<Examiner> examinerList = new Vector();

        connectToDb();
        String examinerQuery = "SELECT * FROM " + TABLE_EXAMINER + ";";

        try{
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(examinerQuery);

            while(resultSet.next()){
                Examiner examiner = null;
                examiner = new Examiner();
                examiner.setId(resultSet.getInt(COLUMN_EXAMINER_ID));
                examiner.setTitle(resultSet.getInt(COLUMN_EXAMINER_TITLE));
                examiner.setFirstName(resultSet.getString(COLUMN_EXAMINER_FIRSTNAME));
                examiner.setLastName(resultSet.getString(COLUMN_EXAMINER_LASTNAME));
                examiner.setUsername(resultSet.getString(COLUMN_EXAMINER_USERNAME));
                examiner.setPassword(resultSet.getString(COLUMN_EXAMINER_PASSWORD));

                examinerList.add(examiner);

            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            disconnectFromDb();
            return examinerList;
        }
    }

    public Examiner updateExaminer(Examiner examiner){
        connectToDb();

        String updateStatement = "UPDATE " + TABLE_EXAMINER + " SET "
                                            + COLUMN_EXAMINER_FIRSTNAME + "= " + "?, "
                                            + COLUMN_EXAMINER_LASTNAME + "= " + "?, "
                                            + COLUMN_EXAMINER_TITLE + "= " + "?, "
                                            + COLUMN_EXAMINER_USERNAME + "= " + "?, "
                                            + COLUMN_EXAMINER_PASSWORD + "= " + "? "
                                            + "WHERE (" + COLUMN_EXAMINER_ID + " = ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(updateStatement);

            preparedStatement.setString(1, examiner.getFirstName());
            preparedStatement.setString(2, examiner.getLastName());
            preparedStatement.setInt(3, examiner.getTitle());
            preparedStatement.setString(4, examiner.getUsername());
            preparedStatement.setString(5, examiner.getPassword());
            preparedStatement.setInt(6, examiner.getId());

            preparedStatement.executeUpdate();

        }catch(SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }

        }finally {
            disconnectFromDb();
        }

        return examiner;
    }

    public void deleteExaminer(int examinerId){
        connectToDb();

        String insertStatement = "DELETE FROM " + TABLE_EXAMINER + " WHERE ("
                                                + COLUMN_EXAMINER_ID + " = ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);
            preparedStatement.setInt(1, examinerId);

            preparedStatement.executeUpdate();

        }catch(SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }

        }finally {
            disconnectFromDb();
        }
    }

    //Student CRUD Methods...
    public void createStudent(Student student){

        connectToDb();
        String insertStatement = "INSERT INTO " + TABLE_STUDENT + "(" + COLUMN_STUDENT_MNO + ", " + COLUMN_STUDENT_FIRSTNAME + ", "
                + COLUMN_STUDENT_LASTNAME + ", " + COLUMN_STUDENT_SEMESTER + ") "
                + "VALUES (?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);
            preparedStatement.setInt(1, student.getMno());
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setInt(4, student.getSemesterId());

            preparedStatement.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
        }
    }

    public Student readStudent(int mno, int semesterId){
        Student student = null;
        connectToDb();
        String studentQuery = "SELECT * FROM " + TABLE_STUDENT + " WHERE ("
                                                + COLUMN_STUDENT_MNO + " = ?" + " AND "
                                                + COLUMN_STUDENT_SEMESTER + " =?);";

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(studentQuery);
            preparedStatement.setInt(1, mno);
            preparedStatement.setInt(2, semesterId);

            ResultSet resultSet = preparedStatement.executeQuery();

            student = new Student();
            student.setId(resultSet.getInt(COLUMN_STUDENT_ID));
            student.setFirstName(resultSet.getString(COLUMN_STUDENT_FIRSTNAME));
            student.setLastName(resultSet.getString(COLUMN_STUDENT_LASTNAME));
            student.setMno(resultSet.getInt(COLUMN_STUDENT_MNO));
            student.setSemesterId(resultSet.getInt(COLUMN_STUDENT_SEMESTER));



        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
            return student;
        }

    }

    public Vector<Student> readAllStudents(){
        Vector<Student> studentList = new Vector();

        connectToDb();
        String studentQuery = "SELECT * FROM " + TABLE_STUDENT + ";";

        try{
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(studentQuery);

            while(resultSet.next()){
                Student student = new Student();

                student = new Student();
                student.setId(resultSet.getInt(COLUMN_STUDENT_ID));
                student.setFirstName(resultSet.getString(COLUMN_STUDENT_FIRSTNAME));
                student.setLastName(resultSet.getString(COLUMN_STUDENT_LASTNAME));
                student.setMno(resultSet.getInt(COLUMN_STUDENT_MNO));
                student.setSemesterId(resultSet.getInt(COLUMN_STUDENT_SEMESTER));

                studentList.add(student);

            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
        }finally {
            disconnectFromDb();
            return studentList;
        }
    }

    public Student updateStudent(Student student){
        connectToDb();

        String updateStatement = "UPDATE " + TABLE_STUDENT + " SET "
                + COLUMN_STUDENT_FIRSTNAME + "= " + "?, "
                + COLUMN_STUDENT_LASTNAME + "= " + "?, "
                + COLUMN_STUDENT_MNO + "= " + "?, "
                + COLUMN_STUDENT_SEMESTER + "= " + "? "
                + "WHERE (" + COLUMN_STUDENT_ID + " = ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(updateStatement);

            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setInt(3, student.getMno());
            preparedStatement.setInt(4, student.getSemesterId());
            preparedStatement.setInt(5, student.getId());

            preparedStatement.executeUpdate();

        }catch(SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }

        }finally {
            disconnectFromDb();
        }

        return student;
    }

    public void deleteStudent(int studentId){
        connectToDb();

        String deleteStatement = "DELETE FROM " + TABLE_STUDENT + " WHERE ("
                + COLUMN_STUDENT_ID + " = ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, studentId);

            preparedStatement.executeUpdate();

        }catch(SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }

        }finally {
            disconnectFromDb();
        }
    }

    //Semester CRUD Methods...
    public void createSemester(Semester semester){
        connectToDb();

        String insertStatement = "INSERT INTO " + TABLE_SEMESTER + "(" + COLUMN_SEMESTER_YEAR + ", "
                + COLUMN_SEMESTER_TERM + ") "
                + "VALUES (?, ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);

            preparedStatement.setInt(1, semester.getYear());
            preparedStatement.setInt(2, semester.getTerm());

            preparedStatement.execute();

        }catch(SQLException e){

            System.out.println(e.getMessage());

        }finally {
            disconnectFromDb();
        }
    }

    public Semester readOrCreateAndReadSemester(int year, int term){
        Semester semester = null;
        semester = readSemester(year, term);
        if(semester.getId() == 0){
            createSemester(new Semester(year, term));
            semester = readSemester(year, term);
        }
        return semester;
    }

    public Semester readSemester(int year, int term){
        Semester semester = null;
        connectToDb();
        String semesterQuery = "SELECT * FROM " + TABLE_SEMESTER + " WHERE ("
                + COLUMN_SEMESTER_TERM + " = ?" + " AND "
                + COLUMN_SEMESTER_YEAR + " =?);";

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(semesterQuery);
            preparedStatement.setInt(1, term);
            preparedStatement.setInt(2, year);

            ResultSet resultSet = preparedStatement.executeQuery();

            semester = new Semester();
            semester.setId(resultSet.getInt(COLUMN_SEMESTER_ID));
            semester.setYear(resultSet.getInt(COLUMN_SEMESTER_YEAR));
            semester.setTerm(resultSet.getInt(COLUMN_SEMESTER_TERM));

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
            return semester;
        }
    }

    public Vector<Semester> readAllSemesters(){
        Vector<Semester> semesters = new Vector();
        connectToDb();
        String semesterQuery = "SELECT * FROM " + TABLE_SEMESTER + ";";

        try{
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(semesterQuery);

            while(resultSet.next()){
                Semester semester = new Semester();
                semester.setId(resultSet.getInt(COLUMN_SEMESTER_ID));
                semester.setYear(resultSet.getInt(COLUMN_SEMESTER_YEAR));
                semester.setTerm(resultSet.getInt(COLUMN_SEMESTER_TERM));

                semesters.add(semester);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
            return semesters;
        }
    }

    public Semester updateSemester(Semester semester){
        connectToDb();

        String updateStatement = "UPDATE " + TABLE_SEMESTER + " SET "
                + COLUMN_SEMESTER_YEAR + "= " + "?, "
                + COLUMN_SEMESTER_TERM + "= " + "? "
                + "WHERE (" + COLUMN_SEMESTER_ID + " = ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(updateStatement);

            preparedStatement.setInt(1, semester.getYear());
            preparedStatement.setInt(2, semester.getTerm());
            preparedStatement.setInt(3, semester.getId());

            preparedStatement.executeUpdate();

        }catch(SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }

        }finally {
            disconnectFromDb();
        }

        return semester;
    }

    public void deleteSemester(int semesterId){
        connectToDb();

        String deleteStatement = "DELETE FROM " + TABLE_SEMESTER + " WHERE ("
                + COLUMN_SEMESTER_ID + " = ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, semesterId);

            preparedStatement.executeUpdate();

        }catch(SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }

        }finally {
            disconnectFromDb();
        }
    }

    //Exam CRUD Methods...
    public void createExam(Exam exam){
        connectToDb();
        String insertStatement = "INSERT INTO " + TABLE_EXAM + "(" + COLUMN_EXAM_TYPE + ", "
                + COLUMN_EXAM_DATE + ", " + COLUMN_EXAM_EXAM_EXAMINER + ", "
                + COLUMN_EXAM_EXAM_SEMESTER + ", " + COLUMN_EXAM_EXAM_STUDENT + ", "
                + COLUMN_EXAM_EXAM_TOOTH + ", "+ COLUMN_EXAM_EXAM_GRADE + ") "
                + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(insertStatement);
            preparedStatement.setInt(1, exam.getType());
            preparedStatement.setString(2, exam.getDate());
            preparedStatement.setInt(3, exam.getExaminerId());
            preparedStatement.setInt(4, exam.getSemesterId());
            preparedStatement.setInt(5, exam.getStudentId());
            preparedStatement.setInt(6, exam.getTooth());
            preparedStatement.setInt(7, exam.getGrade());

            preparedStatement.execute();

        }catch (SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
        }
    }

    public Exam readExam(int examinerId, int type, String date){
        Exam exam = null;
        connectToDb();
        String examQuery = "SELECT * FROM " + TABLE_EXAM + " WHERE ("
                + COLUMN_EXAM_EXAM_EXAMINER + " = ?" + " AND "
                + COLUMN_EXAM_TYPE + " = ?" + " AND "
                + COLUMN_EXAM_DATE + " =?);";

        try{
            PreparedStatement preparedStatement = conn.prepareStatement(examQuery);
            preparedStatement.setInt(1, examinerId);
            preparedStatement.setInt(2, type);
            preparedStatement.setString(2, date);

            ResultSet resultSet = preparedStatement.executeQuery();

            exam = new Exam();
            exam.setId(resultSet.getInt(COLUMN_EXAM_ID));
            exam.setType(resultSet.getInt(COLUMN_EXAM_TYPE));
            exam.setDate(resultSet.getString(COLUMN_EXAM_DATE));
            exam.setExaminerId(resultSet.getInt(COLUMN_EXAM_EXAM_EXAMINER));
            exam.setSemesterId(resultSet.getInt(COLUMN_EXAM_EXAM_SEMESTER));
            exam.setStudentId(resultSet.getInt(COLUMN_EXAM_EXAM_STUDENT));
            exam.setTooth(resultSet.getInt(COLUMN_EXAM_EXAM_TOOTH));
            exam.setGrade(resultSet.getInt(COLUMN_EXAM_EXAM_GRADE));

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
            return exam;
        }
    }

    public Vector<Exam> readAllExams(){
        Vector<Exam> exams = new Vector();
        connectToDb();
        String semesterQuery = "SELECT * FROM " + TABLE_EXAM + ";";

        try{
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(semesterQuery);

            while(resultSet.next()){
                Exam exam = new Exam();
                exam.setId(resultSet.getInt(COLUMN_EXAM_ID));
                exam.setType(resultSet.getInt(COLUMN_EXAM_TYPE));
                exam.setDate(resultSet.getString(COLUMN_EXAM_DATE));
                exam.setExaminerId(resultSet.getInt(COLUMN_EXAM_EXAM_EXAMINER));
                exam.setSemesterId(resultSet.getInt(COLUMN_EXAM_EXAM_SEMESTER));
                exam.setStudentId(resultSet.getInt(COLUMN_EXAM_EXAM_STUDENT));
                exam.setTooth(resultSet.getInt(COLUMN_EXAM_EXAM_TOOTH));
                exam.setGrade(resultSet.getInt(COLUMN_EXAM_EXAM_GRADE));

                exams.add(exam);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
            return exams;
        }
    }

    public Exam updateExam(Exam exam){
        connectToDb();

        String updateStatement = "UPDATE " + TABLE_EXAM + " SET "
                + COLUMN_EXAM_TYPE + "= " + "?, "
                + COLUMN_EXAM_DATE + "= " + "?, "
                + COLUMN_EXAM_EXAM_EXAMINER + "= " + "?, "
                + COLUMN_EXAM_EXAM_SEMESTER + "= " + "?, "
                + COLUMN_EXAM_EXAM_STUDENT + "= " + "?, "
                + COLUMN_EXAM_EXAM_TOOTH + "= " + "?, "
                + COLUMN_EXAM_EXAM_GRADE + "= " + "? "
                + "WHERE (" + COLUMN_EXAM_ID + " = ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(updateStatement);

            preparedStatement.setInt(1, exam.getType());
            preparedStatement.setString(2, exam.getDate());
            preparedStatement.setInt(3, exam.getExaminerId());
            preparedStatement.setInt(4, exam.getSemesterId());
            preparedStatement.setInt(5, exam.getStudentId());
            preparedStatement.setInt(6, exam.getTooth());
            preparedStatement.setInt(7, exam.getGrade());
            preparedStatement.setInt(8, exam.getId());

            preparedStatement.executeUpdate();

        }catch(SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }

        }finally {
            disconnectFromDb();
        }

        return exam;
    }

    public void deleteExam(int examId){
        connectToDb();

        String deleteStatement = "DELETE FROM " + TABLE_EXAM + " WHERE ("
                + COLUMN_EXAM_ID + " = ?);";
        try{

            PreparedStatement preparedStatement = conn.prepareStatement(deleteStatement);
            preparedStatement.setInt(1, examId);

            preparedStatement.executeUpdate();

        }catch(SQLException e){

            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }

        }finally {
            disconnectFromDb();
        }
    }


    //Weird query methods...
    public Vector<HashMap> readExaminerExamsPerSemester(Examiner examiner, Semester semester){
        String query = "SELECT " + COLUMN_EXAM_ID + " FROM " + TABLE_EXAM
                 + " WHERE (" + COLUMN_EXAM_EXAM_EXAMINER + " = ? "
                 + " AND " + COLUMN_EXAM_EXAM_SEMESTER + " = ?);";

        final Vector<HashMap> returnedData = new Vector<>();

        String queryy = "SELECT " + COLUMN_STUDENT_MNO + ", " + COLUMN_EXAM_TYPE
                + ", " + TABLE_STUDENT + "." + COLUMN_STUDENT_FIRSTNAME + ", " + TABLE_STUDENT + "." + COLUMN_STUDENT_LASTNAME
                + ", " + COLUMN_EXAM_DATE + ", " + COLUMN_EXAM_EXAM_GRADE
                + " FROM " + TABLE_STUDENT + " JOIN " + TABLE_SEMESTER + " ON (" + TABLE_STUDENT + "." + COLUMN_STUDENT_SEMESTER + " = " + TABLE_SEMESTER + "." + COLUMN_SEMESTER_ID + ") "
                + " JOIN " + TABLE_EXAM + " ON (" + TABLE_SEMESTER + "." + COLUMN_SEMESTER_ID + " = " + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_SEMESTER + ") "
                + " WHERE (" + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_SEMESTER + " = ?"
                + " AND " + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_EXAMINER + " =?);";

        String queryyy = "SELECT " + COLUMN_STUDENT_MNO + ", " + TABLE_STUDENT + "." + COLUMN_STUDENT_FIRSTNAME + ", "
                + TABLE_STUDENT + "." + COLUMN_STUDENT_LASTNAME + ", " + COLUMN_EXAM_TYPE + ", " + COLUMN_EXAM_DATE + ", "
                + COLUMN_EXAM_EXAM_GRADE + " FROM " + TABLE_EXAM + " LEFT OUTER JOIN " + TABLE_STUDENT
                + " ON (" + TABLE_STUDENT + "." + COLUMN_STUDENT_ID + " = " + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_STUDENT
                + " AND " + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_SEMESTER + " = " + TABLE_STUDENT + "." + COLUMN_STUDENT_SEMESTER + ")"
                + " WHERE (" + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_EXAMINER + " = ? "
                + " AND " + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_SEMESTER + " = ?);";

        connectToDb();

        try{

            PreparedStatement preparedStatement = conn.prepareStatement(queryyy);
            preparedStatement.setInt(1, examiner.getId());
            preparedStatement.setInt(2, semester.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                HashMap row = new HashMap();
                row.put(KEY_TABLE_MNO, resultSet.getInt(COLUMN_STUDENT_MNO));

                row.put(KEY_TABLE_FIRSTNAME, resultSet.getString(COLUMN_STUDENT_FIRSTNAME));
                row.put(KEY_TABLE_LASTNAME, resultSet.getString(COLUMN_STUDENT_LASTNAME));
                row.put(KEY_TABLE_EXAM_TYPE, resultSet.getInt(COLUMN_EXAM_TYPE));
                row.put(KEY_TABLE_EXAM_DATE, resultSet.getString(COLUMN_EXAM_DATE));
                row.put(KEY_TABLE_GRADE, resultSet.getInt(COLUMN_EXAM_EXAM_GRADE));

                returnedData.add(row);

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
            return returnedData;
        }

    }

    public Vector<HashMap> readStudentAverageGradesPerSemesterAndType(int examType, Semester semester){

        String examTypeString = "";
        if(examType == 1){
            examTypeString = "Class II";
        }else{
            examTypeString = "Full Veneer Crown";
        }

        String query = "SELECT DISTINCT " + TABLE_STUDENT + "." + COLUMN_STUDENT_ID + ", " + COLUMN_STUDENT_MNO + ", " + COLUMN_STUDENT_FIRSTNAME + ", " + COLUMN_STUDENT_LASTNAME
                + ", " + COLUMN_EXAM_DATE + " FROM " + TABLE_EXAM + " JOIN " + TABLE_STUDENT + " ON "
                + "(" + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_STUDENT + "=" + TABLE_STUDENT + "." + COLUMN_STUDENT_ID + ")"
                + " WHERE (" + TABLE_EXAM + "." + COLUMN_EXAM_TYPE + " = ? " + " AND "
                + TABLE_EXAM + "." + COLUMN_EXAM_EXAM_SEMESTER + " = ?" + ");";

        String gradeQuery = "SELECT " + COLUMN_EXAM_EXAM_GRADE + " FROM " + TABLE_EXAM
                + " WHERE (" + COLUMN_EXAM_EXAM_STUDENT + " = ?);";

        final Vector<HashMap> returnedData = new Vector<>();

        connectToDb();

        try{

            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, examType);
            preparedStatement.setInt(2, semester.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                HashMap row = new HashMap();
                row.put(KEY_TABLE_MNO, resultSet.getInt(COLUMN_STUDENT_MNO));
                row.put(KEY_TABLE_FIRSTNAME, resultSet.getString(COLUMN_STUDENT_FIRSTNAME));
                row.put(KEY_TABLE_LASTNAME, resultSet.getString(COLUMN_STUDENT_LASTNAME));
                row.put(KEY_TABLE_EXAM_TYPE, examTypeString);
                row.put(KEY_TABLE_EXAM_DATE, resultSet.getString(COLUMN_EXAM_DATE));

                PreparedStatement internalStatement = conn.prepareStatement(gradeQuery);
                internalStatement.setInt(1, resultSet.getInt(COLUMN_STUDENT_ID));

                ResultSet internalResultSet = internalStatement.executeQuery();

                int count = 0;
                int sum = 0;
                //Grade and average calculation...
                if(internalResultSet.next()){
                    int grade = internalResultSet.getInt(COLUMN_EXAM_EXAM_GRADE);
                    row.put(KEY_TABLE_EXAMINER1_GRADE, grade);
                    count++;
                    sum += grade;
                }

                if(internalResultSet.next()){
                    int grade = internalResultSet.getInt(COLUMN_EXAM_EXAM_GRADE);
                    row.put(KEY_TABLE_EXAMINER2_GRADE, grade);
                    count++;
                    sum += grade;
                }

                if(internalResultSet.next()){
                    int grade = internalResultSet.getInt(COLUMN_EXAM_EXAM_GRADE);
                    row.put(KEY_TABLE_EXAMINER3_GRADE, grade);
                    count++;
                    sum += grade;
                }

                if(count > 0){
                    int avgGrade = sum / count;
                    row.put(KEY_TABLE_AVG_GRADE, avgGrade);
                }

                returnedData.add(row);

            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            try{
                conn.rollback();
            }catch (SQLException ee){
                System.out.println(ee.getMessage());
            }
        }finally {
            disconnectFromDb();
            return returnedData;
        }

    }

}
