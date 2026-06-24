package university.enums;

public enum Grade {
    A(95),
    B(85),
    C(75),
    D(65),
    E(60),
    F(0);

    private final int points;

    Grade(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
