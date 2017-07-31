package app.Model.Exams;

import app.Model.Exam;

import java.util.Vector;

/**
 * Created by husam on 7/19/17.
 */
public class GSCExam extends Exam {

    public final static String title = "GSC Examination";

    private static final String firstSection = "Occlusal surface:";
    private static final String secondSection = "Axial surface(s):";
    private static final String thirdSection = "Finish line:";
    private static final String fourthSection = "Ajacent teeth:";

    private static final String questionOne = "Q1: Functional cusps reduction is ";
    private static final String questionTwo = "Q2: Non-functional cusps reduction is ";
    private static final String questionThree = "Q3: Bucco-lingual/plattal occlusal convergence is ";
    private static final String questionFour = "Q4: Proximal occlusal convergence is ";
    private static final String questionFive = "Q5: Axial surfaces reduction is ";
    private static final String questionSix = "Q6: Type of finish line is ";
    private static final String questionSeven = "Q7: Depth of finish line is ";
    private static final String questionEight = "Q8: Finish line location is ";
    private static final String questionNine = "Q9: Contact area with adjacent teeth is ";
    private static final String questionTen = "Q10: Adjacent teeth have ";

    public static final String q1a1 = "adequate with symmetrical bevel (6.00 - 6.50 mm)";
    public static final String q1a2 = "acceptable without symmetrical bevel (6.00 - 6.50 mm)";
    public static final String q1a3 = "underprepared (> 6.50 mm)";
    public static final String q1a4 = "overprepared (< 6.00 mm)";
    public static final String q2a1 = "adequate (6.50 - 7.00 mm)";
    public static final String q2a2 = "underprepared (> 7.00 mm)";
    public static final String q2a3 = "overprepared (< 6.50 mm)";
    public static final String q3a1 = "adequate 0° - 20° (7.00 - 9.00 mm)";
    public static final String q3a2 = "underprepared < 0° (> 9.00 mm)";
    public static final String q3a3 = "overprepared > 20° (< 7.00 mm)";
    public static final String q4a1 = "adequate 0° - 20° (7.00 - 9.00 mm)";
    public static final String q4a2 = "underprepared < 0° (> 9.00 mm)";
    public static final String q4a3 = "overprepared > 20° (< 7.00 mm)";
    public static final String q5a1 = "adequate (0.50 - 1.50 mm)";
    public static final String q5a2 = "underprepared (< 0.50 mm)";
    public static final String q5a3 = "overprepared (> 1.50 mm)";
    public static final String q6a1 = "Chamfer";
    public static final String q6a2 = "Chamfer with small lip";
    public static final String q6a3 = "Knife edge";
    public static final String q6a4 = "Shoulder";
    public static final String q7a1 = "adequate (0.50 - 1.00 mm)";
    public static final String q7a2 = "underprepared (< 0.50 mm)";
    public static final String q7a3 = "overprepared (> 1.00 mm)";
    public static final String q8a1 = "adequate (supragingival) (0.50 - 1.00 mm)";
    public static final String q8a2 = "acceptable (at to supragingival) (0 - < 0.50 mm)";
    public static final String q8a3 = "underprepared (supragingival) (> 1.0 mm)";
    public static final String q8a4 = "overprepared (subgingival) (< 0 mm)";
    public static final String q9a1 = "Cleared on both sides (0.50 mm)";
    public static final String q9a2 = "Not clear on one or both sides";
    public static final String q9a3 = "cleared on one or both sides (> 0.50 mm)";
    public static final String q10a1 = "no damage";
    public static final String q10a2 = "minor damage for one or both teeth";
    public static final String q10a3 = "moderate or severe damage for one or both teeth";

    public static final int IDEAL = 1;
    public static final int ACCEPTABLE = 0;
    public static final int NEEDS_MOD = -4;
    public static final int NO_MOD_CAN_APPLY = -46;

    public static final int FATAL_MISTAKE = -461;

    private Vector<String> questionOneAnswers = null,
            questionTwoAnswers = null,
            questionThreeAnswers = null,
            questionFourAnswers = null,
            questionFiveAnswers = null,
            questionSixAnswers = null,
            questionSevenAnswers = null,
            questionEightAnswers = null,
            questionNineAnswers = null,
            questionTenAnswers = null;

    public GSCExam() {
    }

    public GSCExam(int type, int examinerId, int semesterId, int studentId, int tooth, int grade, String date) {
        super(type, examinerId, semesterId, studentId, tooth, grade, date);
    }

    public GSCExam(int id, int type, int examinerId, int semesterId, int studentId, int tooth, int grade, String date) {
        super(id, type, examinerId, semesterId, studentId, tooth, grade, date);
    }
}
