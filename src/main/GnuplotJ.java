package main;

import model.XVectorJ;

import java.io.PrintWriter;
import java.util.List;

/**
 * Created by neikila on 07.11.15.
 */
public class GnuplotJ {
    private String directory = "out/";
    private List<Double> times;
    private List<XVectorJ> result;

    public GnuplotJ(List<Double> times, List<XVectorJ> result) {
        this.times = times;
        this.result = result;
    }

    public void printAll() {
        printToFile("Il3");
    }

    public void printToFile(String fileName) {
        try {
            PrintWriter out = new PrintWriter(directory + fileName);
            for (int i = 0; i < result.size(); ++i){
                out.println(times.get(i) + " " + result.get(i).Il3());
            }
            out.close();
        } catch (Exception e) {

        }
    }
}
