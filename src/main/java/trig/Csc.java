package trig;

public class Csc implements FunctionInterface {
    private double eps;
    private Sin sin;
    public Csc(double eps){
        this.eps = eps;
        this.sin = new Sin(eps);
    }
    public Csc(double eps, Sin sin){
        this.eps = eps;
        this.sin = sin;
    }
    public double calculate(double x){
        if (x % Math.PI != 0){
            return 1 / sin.calculate(x);
        }
        else {
            return Double.NaN;
        }
    }
    public double stubCalculate(double x){
        if (x % Math.PI != 0){
            return 1 / Math.sin(x);
        }
        else {
            return Double.NaN;
        }
    }

    public void setEps(double eps) {
        this.eps = eps;
        this.sin.setEps(eps);
    }
}
