package university.enums;

public enum Grade {
    A(90, 100, 4.0),
    B(80, 89, 3.0),
    C(70, 79, 2.0),
    D(60, 69, 1.0),
    F(0, 59, 0.0),
    NA(-1, -1, 0.0);

    private final int minScore;
    private final int maxScore;
    private final double gpaValue;

    Grade(int minScore, int maxScore, double gpaValue) {
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.gpaValue = gpaValue;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public double getGpaValue() {
        return gpaValue;
    }

    public static Grade fromScore(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }

        for (Grade grade : Grade.values()) {
            if (grade != NA && score >= grade.minScore && score <= grade.maxScore) {
                return grade;
            }
        }

        return NA;
    }
}