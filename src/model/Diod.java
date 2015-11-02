package model;

/**
 * Created by neikila on 01.11.15.
 */
public class Diod {
    private double Ry;
    private double r;
    private double C;
    private double It;
    private double mft;

    public Diod(double ry, double r, double c, double i, double mft) {
        Ry = ry;
        this.r = r;
        C = c;
        It = i;
        this.mft = mft;
    }

    public double getRy() {
        return Ry;
    }

    public double getC() {
        return C;
    }

    public double getIt(double fa, double fk) {
        return It * (Math.exp((fa - fk) / mft) - 1);
    }

    public double getRr() {
        return r;
    }

    public double getMultiplier() {
        return -It * Math.E / mft;
    }
}
