package app.Model;

/**
 * Created by husam on 7/2/17.
 */
public class Semester {

    private int id = 0,
                year = 0,
                term = 0;

    public Semester(){}

    public Semester(int year, int term) {
        this.year = year;
        this.term = term;
    }

    public Semester(int id, int year, int term) {
        this.id = id;
        this.year = year;
        this.term = term;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    @Override
    public String toString() {
        return "Semester{" +
                "id=" + id +
                ", year=" + year +
                ", term=" + term +
                '}';
    }

    public String toFormattedString(){
        String formattedTerm;
        if(term == 1)
            formattedTerm = "Spring";
        else
            formattedTerm = "Fall";

        return formattedTerm + " - " + year;
    }

    public static Semester parseFormattedString(String formattedString){
        int term = 1;
        int year = 0;
        if (formattedString.contains("Fall")){
            term = 2;
            year = Integer.parseInt(formattedString.substring(7, 11));
        }else{
            year = Integer.parseInt(formattedString.substring(9, 13));
        }

        Semester semester = new Semester(year, term);
        return semester;
    }
}
