package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 07.11.15.
 */
public class XVectorJ {
    List <Double> list;

    public XVectorJ(List<Double> list) {
        this.list = list;
    }

    public XVectorJ() {
        list = new ArrayList<>();
        for (int i = 0; i < 19; ++i) {
            list.add(0.0);
        }
    }

    public Double dUc4dt()  { return list.get(0); }
    public Double dIl3dt()  { return list.get(1); }
    public Double dUcddt()  { return list.get(2); }
    public Double Ul3()     { return list.get(3); }
    public Double Ury()     { return list.get(4); }
    public Double Uid()     { return list.get(5); }
    public Double Urd()     { return list.get(6); }
    public Double Ie()      { return list.get(7); }
    public Double Ic4()     { return list.get(8); }
    public Double Icd()     { return list.get(9); }
    public Double Ir4()     { return list.get(10); }
    public Double Il3()     { return list.get(11); }
    public Double Iry()     { return list.get(12); }
    public Double Iid()     { return list.get(13); }
    public Double Ird()     { return list.get(14); }
    public Double Ue()      { return list.get(15); }
    public Double Uc4()     { return list.get(16); }
    public Double Ucd()     { return list.get(17); }
    public Double Ur4()     { return list.get(18); }

    public Double getDeltaU() { return Ury(); }
    public Double get(int i) {
        return list.get(i);
    }

    public XVectorJ sum (XVectorJ vector) {
        List <Double> sum = new ArrayList<>();
        List <Double> addList = vector.list;
        for (int i = 0; i < list.size(); ++i) {
            sum.add(list.get(i) + addList.get(i));
        }
        return new XVectorJ(sum);
    }

    public void add(List<Double> vector) {
        for (int i = 0; i < list.size(); ++i) {
            list.set(i, vector.get(i) + list.get(i));
        }
    }

    public void add(XVectorJ vector) {
        this.add(vector.list);
    }
}