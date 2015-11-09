package model;

/**
 * Created by neikila on 07.11.15.
 */
public class EdsJ {
    private Double omega;
    private Double Emax;

    public EdsJ(Double omega, Double emax) {
        this.omega = omega;
        Emax = emax;
    }

    public Double E(Double time) {
        return Emax * Math.sin(omega * time);
    }
}
