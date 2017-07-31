package app.Model;

/**
 * Created by husam on 7/2/17.
 */
public class Student {

    private int id = 0,
                mno = 0,
                semesterId = 0;

    private String firstName = null,
                    lastName = null;

    public Student(){

    }

    public Student(int mno, int classId, String firstName, String lastName) {
        this.mno = mno;
        this.semesterId = classId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(int id, int mno, int classId, String firstName, String lastName) {
        this.mno = mno;
        this.id = id;
        this.semesterId = classId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(int semesterId) {
        this.semesterId = semesterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMno() {
        return mno;
    }

    public void setMno(int mno) {
        this.mno = mno;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", mno=" + mno +
                ", semesterId=" + semesterId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
