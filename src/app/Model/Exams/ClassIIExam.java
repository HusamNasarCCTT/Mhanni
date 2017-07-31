package app.Model.Exams;

import app.Model.Exam;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by husam on 7/17/17.
 */
public class ClassIIExam extends Exam {


    public final static String title = "Class II Examination";

    private static final String firstSection = "Occlusal Cavity Part:";
    private static final String secondSection = "Proximal Box Part:";
    private static final String thirdSection = "Adjacent Tooth:";

    private static final String questionOne = "Depth of Occlusal cavity and occlusal key are:";
    private static final String questionTwo = "Occlusal Width is:";
    private static final String questionThree = "Retention form of occlusal cavity is:";
    private static final String questionFour = "Marginal ridge is:";
    private static final String questionFive = "Level of box floor with contact point is:";
    private static final String questionSix = "Mesial and Distal depth of the box is:";
    private static final String questionSeven = "Buccal and platal/lingual width of the box is:";
    private static final String questionEight = "Retention form of proximal box is:";
    private static final String questionNine = "Position of proximal box is:";
    private static final String questionTen = "Adjacent tooth has:";

    public static final String q1a1 = "less than 1.5 mms (1/2) condenser length";
    public static final String q1a2 = "between (1.5 mms)(1/2) condenser length to less than (2.0 mms)(2/3) condenser length";
    public static final String q1a3 = "more than (2.0 mms)(2/3) condenser length";
    public static final String q2a1 = "less than 1.5 mms (1/2) condenser thickness";
    public static final String q2a2 = "between (1.5 mms) one and a half condenser thickness to less than (2.0 mms) two condensers' thickness";
    public static final String q2a3 = "more than (2.0 mms)(2/3) two condensers thickness";
    public static final String q3a1 = "One converge or parallel and other diverge";
    public static final String q3a2 = "One converge and other parallel or two parallel walls";
    public static final String q3a3 = "Two diverge walls";
    public static final String q3a4 = "Two converge walls";
    public static final String q4a1 = "more than one condenser thickness (1.0 mms)";
    public static final String q4a2 = "less than than one condenser thickness (1.0 mms)";
    public static final String q5a1 = "at or below contact point (from 2.5 to 4.0 mms)";
    public static final String q5a2 = "below the previous range (<2.5 mms)";
    public static final String q5a3 = "above the previous range (>4.0 mms)";
    public static final String q6a1 = "less than 1.0 mms (1/2) condenser thickness";
    public static final String q6a2 = "between (1.0 mms) one condenser thickness to less than (1.5 mms) one and a half condensers' thickness";
    public static final String q6a3 = "more than (1.5 mms)(2/3) one and a half condensers' thickness";
    public static final String q7a1 = "less than three condenser thickness (<3.0 mms)";
    public static final String q7a2 = "clear about three condensers thickness (3.0 < 4.0 mms)";
    public static final String q7a3 = "more than four condensers' thickness (>4.0 mms)";
    public static final String q8a1 = "Two converge walls";
    public static final String q8a2 = "One converge and other parallel or two parallel walls";
    public static final String q8a3 = "One converge or parallel and other diverge";
    public static final String q8a4 = "Two diverge walls";
    public static final String q9a1 = "in the middle third between tips of two cusps without unsupported enamel";
    public static final String q9a2 = "in the middle with unsupported enamel";
    public static final String q9a3 = "far buccally, lingually, or palatally";
    public static final String q10a1 = "not damaged";
    public static final String q10a2 = "minor damage";
    public static final String q10a3 = "moderate or severe damage";

    private static final int IDEAL = 1;
    private static final int ACCEPTABLE = 0;
    private static final int NEEDS_MOD = -3;
    private static final int NO_MOD_CAN_APPLY = -34;
    private static final int FATAL_MISTAKE = -341;


    public ClassIIExam() {
    }

    public ClassIIExam(int type, int examinerId, int semesterId, int studentId, int tooth, int grade, String date) {
        super(type, examinerId, semesterId, studentId, tooth, grade, date);
    }

    public ClassIIExam(int id, int type, int examinerId, int semesterId, int studentId, int tooth, int grade, String date) {
        super(id, type, examinerId, semesterId, studentId, tooth, grade, date);
    }
}
