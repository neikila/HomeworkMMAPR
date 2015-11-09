package model;

import main.Gaus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neikila on 07.11.15.
 */
public class SolverJ {
    private Settings settings = new Settings();
    private MathModel model = new MathModel(settings);

    public List<XVectorJ> resultSolution;
    public List<Double> times;

    public SolverJ() {
        resultSolution = new ArrayList<>();
        resultSolution.add(new XVectorJ());

        times = new ArrayList<>();
        times.add(0.0);
    }
    private Double time = 0.0;
    private Double dt = Math.pow(10, -8) * 5;

    public void solve() {
        int i = 0;
        while (time < settings.deadline()) {
            XVectorJ temp = new Step(resultSolution.get(resultSolution.size() - 1)).calculate();
            resultSolution.add(temp);
            time += dt;
            times.add(time);
            i += 1;
//            if (i % 100000 == 0) {
                System.out.println(i);
                System.out.println(time);
//            }
        }
    }

    class Step {
        private int iterationNum = 0;
        private XVectorJ iterationApproximation;
        private XVectorJ previousStep;

        public Step(XVectorJ previous) {
            previousStep = previous;
            iterationApproximation = new XVectorJ(new ArrayList<>(previous.list));
        }

        public XVectorJ calculate() {
            List<Double> B;
            do {
                List <List<Double>> A = model.getAMatrix(dt, iterationApproximation.getDeltaU());
                B = model.getBMatrix(iterationApproximation, previousStep, time, dt);
                for (List <Double> temp: A) {
                    System.out.println(temp);
                }
                System.out.println("B = ");
                System.out.println(B);
                Gaus.solve(A, B);
                System.out.println("Result");
                System.out.println(B);
                iterationApproximation.add(B);
            } while (!checkIfEnd(B));
            return iterationApproximation;
        }

        private boolean checkIfEnd(List<Double> delta) {
            boolean result = true;
            for (int i = 0; i < delta.size() && result; ++i) {
                if (Math.abs(delta.get(i)) >= 0.001)
                    result = false;
            }
            if (!result) {
                if (iterationNum < 7) {
                    iterationNum += 1;
                } else {
                    dt /= 2;
                    System.out.println(dt);
                    iterationApproximation = new XVectorJ(new ArrayList<>(previousStep.list));
                    iterationNum = 0;
                }
            }
            return result;
        }
    }
}
