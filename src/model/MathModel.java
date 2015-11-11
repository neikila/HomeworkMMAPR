package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 01.11.15.
 */
public class MathModel {
    private double C4;
    private double L3;
    private double R4;
    private Diod diod;
    private Eds eds;

    public MathModel(double c4, double l3, double r4, Diod diod, Eds eds) {
        C4 = c4;
        L3 = l3;
        R4 = r4;
        this.diod = diod;
        this.eds = eds;
    }

    public MathModel(Settings settings) {
        C4 = settings.c4();
        L3 = settings.l3();
        R4 = settings.r4();
        diod = settings.diod();
        eds = settings.eds();
    }

    public List <List <Double>> getAMatrix(double dt, double deltaU) {
        int size = 19;
        List <List <Double>> result = new ArrayList<>(size);
        List <Double> zero = initZero(size);

        double a = -diod.getMultiplier(deltaU);
        double b = -1.0 / dt;

        List <Double> duc4dt = new ArrayList<>(zero);
        duc4dt.set(0, 1.0);
        duc4dt.set(16, b);
        result.add(duc4dt);

        List <Double> dIl3dt = new ArrayList<>(zero);
        dIl3dt.set(1, 1.0);
        dIl3dt.set(11, b);
        result.add(dIl3dt);

        List <Double> dUcddt = new ArrayList<>(zero);
        dUcddt.set(2, 1.0);
        dUcddt.set(17, b);
        result.add(dUcddt);

        List <Double> Ul3 = new ArrayList<>(zero);
        Ul3.set(3, 1.0);
        Ul3.set(16, -1.0);
        result.add(Ul3);

        List <Double> Ury = new ArrayList<>(zero);
        Ury.set(4, 1.0);
        Ury.set(17, -1.0);
        result.add(Ury);

        List <Double> Uid = new ArrayList<>(zero);
        Uid.set(5, 1.0);
        Uid.set(17, -1.0);
        result.add(Uid);

        List <Double> Urd = new ArrayList<>(zero);
        Urd.set( 6,  1.0);
        Urd.set(15,  1.0);
        Urd.set(16, -1.0);
        Urd.set(17,  1.0);
        Urd.set(18, -1.0);
        result.add(Urd);

        List <Double> Ie = new ArrayList<>(zero);
        Ie.set(7, 1.0);
        Ie.set(14, -1.0);
        result.add(Ie);

        List <Double> Ic4 = new ArrayList<>(zero);
        Ic4.set(8, 1.0);
        Ic4.set(11, 1.0);
        Ic4.set(14, 1.0);
        result.add(Ic4);

        List <Double> Icd = new ArrayList<>(zero);
        Icd.set(9, 1.0);
        Icd.set(12, 1.0);
        Icd.set(13, 1.0);
        Icd.set(14, -1.0);
        result.add(Icd);

        List <Double> Ir4 = new ArrayList<>(zero);
        Ir4.set(10, 1.0);
        Ir4.set(14, 1.0);
        result.add(Ir4);

        List <Double> Il3 = new ArrayList<>(zero);
        Il3.set(1, L3);
        Il3.set(3, -1.0);
        result.add(Il3);

        List <Double> Iry = new ArrayList<>(zero);
        Iry.set(4, 1.0);
        Iry.set(12, -diod.Ry());
        result.add(Iry);

        List <Double> Iid = new ArrayList<>(zero);
        Iid.set(4, a);
        Iid.set(13, 1.0);
        result.add(Iid);

        List <Double> Ird = new ArrayList<>(zero);
        Ird.set(14, -diod.Rr());
        Ird.set(6, 1.0);
        result.add(Ird);

        List <Double> Ue = new ArrayList<>(zero);
        Ue.set(15, 1.0);
        result.add(Ue);

        List <Double> Uc4 = new ArrayList<>(zero);
        Uc4.set(0, C4);
        Uc4.set(8, -1.0);
        result.add(Uc4);

        List <Double> Ucd = new ArrayList<>(zero);
        Ucd.set(2, diod.C());
        Ucd.set(9, -1.0);
        result.add(Ucd);

        List <Double> Ur4 = new ArrayList<>(zero);
        Ur4.set(10, -R4);
        Ur4.set(18, 1.0);
        result.add(Ur4);

        return result;
    }

    private List <Double> initZero(int size) {
        List <Double> initList = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            initList.add(0.0);
        }
        return initList;
    }

    public List <Double> getBMatrix(XVector approximate, XVector previous, double time, double dt) {
        List <Double> result = new ArrayList<>();

        result.add(approximate.dUc4dt() - (approximate.Uc4() - previous.Uc4()) / dt);
        result.add(approximate.dIl3dt() - (approximate.Il3() - previous.Il3()) / dt);
        result.add(approximate.dUcddt() - (approximate.Ucd() - previous.Ucd()) / dt);

        result.add(approximate.Ul3() - approximate.Uc4());
        result.add(approximate.Ury() - approximate.Ucd());
        result.add(approximate.Uid() - approximate.Ucd());
        result.add(approximate.Urd() + approximate.Ue() -
                approximate.Uc4() + approximate.Ucd() - approximate.Ur4());
        result.add(approximate.Ie() - approximate.Ird());
        result.add(approximate.Ic4() + approximate.Il3() + approximate.Ird());
        result.add(approximate.Icd() + approximate.Iry() +
                approximate.Iid() - approximate.Ird());
        result.add(approximate.Ir4() + approximate.Ird());

        result.add(L3 * approximate.dIl3dt() - approximate.Ul3());
        result.add(approximate.Ury() - approximate.Iry() * diod.Ry());
        result.add(approximate.Iid() - diod.getI(approximate.getDeltaU()));
        result.add(approximate.Urd() - approximate.Ird() * diod.Rr());
        result.add(approximate.Ue() - eds.E(time + dt));
        result.add(approximate.dUc4dt() * C4 - approximate.Ic4());
        result.add(approximate.dUcddt() * diod.C() - approximate.Icd());
        result.add(approximate.Ur4() - approximate.Ir4() * R4);

        for (int i = 0; i < result.size(); ++i) {
            result.set(i, -1 * result.get(i));
        }

        return result;
    }
}
