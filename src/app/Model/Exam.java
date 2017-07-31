package app.Model;


import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by husam on 7/2/17.
 */


public class Exam {

    private int id = 0,
                type = 0,
                examinerId = 0,
                semesterId = 0,
                studentId = 0,
                tooth = 0,
                grade = 0;

    private String date = null;

    public Exam() {
    }

    public Exam(int type, int examinerId, int semesterId, int studentId, int tooth, int grade, String date) {
        this.type = type;
        this.examinerId = examinerId;
        this.semesterId = semesterId;
        this.studentId = studentId;
        this.tooth = tooth;
        this.grade = grade;
        this.date = date;
    }

    public Exam(int id, int type, int examinerId, int semesterId, int studentId, int tooth, int grade, String date) {
        this.id = id;
        this.type = type;
        this.examinerId = examinerId;
        this.semesterId = semesterId;
        this.studentId = studentId;
        this.tooth = tooth;
        this.grade = grade;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getExaminerId() {
        return examinerId;
    }

    public void setExaminerId(int examinerId) {
        this.examinerId = examinerId;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTooth() {
        return tooth;
    }

    public void setTooth(int tooth) {
        this.tooth = tooth;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate() {
        this.date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", type=" + type +
                ", examinerId=" + examinerId +
                ", semesterId=" + semesterId +
                ", date='" + date + '\'' +
                '}';
    }
}
