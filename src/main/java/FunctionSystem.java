import trig.*;
import log.*;

public class FunctionSystem implements FunctionInterface {
    private double eps;
    private Sin sin;
    private Cos cos;
    private Sec sec;
    private Cot cot;
    private Csc csc;
    private Ln ln;
    private Log log2;
    private Log log10;

    public FunctionSystem (Sin sin, Cos cos, Sec sec, Csc csc, Cot cot, Ln ln, Log log2, Log log10) {
        this.sin = sin;
        this.cos = cos;
        this.sec = sec;
        this.csc = csc;
        this.cot = cot;
        this.ln = ln;
        this.log2 = log2;
        this.log10 = log10;
    }

    public double calculate (double x) {
        if (x <= 0) return (((((csc.calculate(x) + sec.calculate(x)) / sec.calculate(x)) / (cos.calculate(x) / cos.calculate(x))) * (Math.pow(cot.calculate(x) - cot.calculate(x), 3))) + sin.calculate(x));
        return Math.pow(Math.pow((ln.calculate(x) * log2.calculate(x) + log10.calculate(x)) - ln.calculate(x), 2), 3);
    }
    @Override
    public void setEps(double eps) {
        this.eps = eps;
    }
}
