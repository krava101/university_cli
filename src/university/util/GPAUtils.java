package university.util;

import university.entities.Enrollment;
import university.enums.Grade;

import java.util.List;

public class GPAUtils {
    public static double calculateGPA(List<Enrollment> enrollments) {
        double total = 0;
        int count = 0;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != Grade.NA) {
                total += enrollment.getGrade().getGpaValue();
                count++;
            }
        }

        if (count == 0) {
            return 0.0;
        }

        return total / count;
    }
}