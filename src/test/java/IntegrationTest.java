import log.Ln;
import log.Log;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import trig.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    final double eps = 0.0001;

    static Sin sinStub = Mockito.mock(Sin.class);
    static Cos cosStub = Mockito.mock(Cos.class);
    static Sec secStub = Mockito.mock(Sec.class);
    static Csc cscStub = Mockito.mock(Csc.class);
    static Cot cotStub = Mockito.mock(Cot.class);
    static Ln lnStub = Mockito.mock(Ln.class);
    static Log log2Stub = Mockito.mock(Log.class);
    static Log log10Stub = Mockito.mock(Log.class);

    @BeforeClass
    public static void setupStubs(){
        log2Stub.setBase(2);
        log10Stub.setBase(10);
        Mockito.when(sinStub.calculate(Mockito.anyDouble())).thenAnswer(i -> Math.sin((Double) i.getArguments()[0]));
        Mockito.when(cosStub.calculate(Mockito.anyDouble())).thenAnswer(i -> Math.cos((Double) i.getArguments()[0]));
        Mockito.when(secStub.calculate(Mockito.anyDouble())).thenAnswer(i -> {
            if (((Double) i.getArguments()[0] - Math.PI/2) % Math.PI != 0){
                return 1 / Math.cos((Double) i.getArguments()[0]);
            }
            else {
                return Double.NaN;
            }
        });
        Mockito.when(cscStub.calculate(Mockito.anyDouble())).thenAnswer(i -> {
            if ((Double) i.getArguments()[0] % Math.PI != 0){
                return 1 / Math.sin((Double) i.getArguments()[0]);
            }
            else {
                return Double.NaN;
            }
        });
        Mockito.when(cotStub.calculate(Mockito.anyDouble())).thenAnswer(i -> {
            if ((Double) i.getArguments()[0] % Math.PI != 0){
                return Math.cos((Double) i.getArguments()[0]) / Math.sin((Double) i.getArguments()[0]);
            }
            else {
                return Double.NaN;
            }
        });
        Mockito.when(lnStub.calculate(Mockito.anyDouble())).thenAnswer(i -> Math.log((Double) i.getArguments()[0]));
        Mockito.when(log2Stub.calculate(Mockito.anyDouble())).thenAnswer(i -> Math.log((Double) i.getArguments()[0]) / Math.log(2));
        Mockito.when(log10Stub.calculate(Mockito.anyDouble())).thenAnswer(i -> Math.log((Double) i.getArguments()[0]) / Math.log(10));
    }

    @Test
    public void testWithAllStubs(){
        FunctionSystem functionSystem = new FunctionSystem(sinStub, cosStub, secStub, cscStub, cotStub, lnStub, log2Stub, log10Stub);
        assertEquals(-0.8414709848078965, functionSystem.calculate(-1), eps);
        assertEquals(-0.997495, functionSystem.calculate(-1.5), eps);
        assertEquals(-0.778073, functionSystem.calculate(-2.25), eps);
        assertEquals(0.110687, functionSystem.calculate(2.5), eps);
        assertEquals(1.971373378588643, functionSystem.calculate(3), eps);
        assertEquals(61.7963331333652, functionSystem.calculate(4), eps);
    }
    @Test
    public void testWithSinCosLnLogStubs(){
        Sec sec = new Sec(eps, cosStub);
        Csc csc = new Csc(eps, sinStub);
        Cot cot = new Cot(eps, sinStub, cosStub);
        FunctionSystem functionSystem = new FunctionSystem(sinStub, cosStub, sec, csc, cot, lnStub, log2Stub, log10Stub);
        assertEquals(-0.8414709848078965, functionSystem.calculate(-1), eps);
        assertEquals(-0.997495, functionSystem.calculate(-1.5), eps);
        assertEquals(-0.778073, functionSystem.calculate(-2.25), eps);
        assertEquals(0.110687, functionSystem.calculate(2.5), eps);
        assertEquals(1.971373378588643, functionSystem.calculate(3), eps);
        assertEquals(61.7963331333652, functionSystem.calculate(4), eps);
    }
    @Test
    public void testWithSinLnLogStubs(){
        Cos cos = new Cos(eps, sinStub);
        Sec sec = new Sec(eps);
        Csc csc = new Csc(eps, sinStub);
        Cot cot = new Cot(eps, sinStub);
        FunctionSystem functionSystem = new FunctionSystem(sinStub, cos, sec, csc, cot, lnStub, log2Stub, log10Stub);
        assertEquals(-0.8414709848078965, functionSystem.calculate(-1), eps);
        assertEquals(-0.997495, functionSystem.calculate(-1.5), eps);
        assertEquals(-0.778073, functionSystem.calculate(-2.25), eps);
        assertEquals(0.110687, functionSystem.calculate(2.5), eps);
        assertEquals(1.971373378588643, functionSystem.calculate(3), eps);
        assertEquals(61.7963331333652, functionSystem.calculate(4), eps);
    }

    @Test
    public void testWithLnLogStubs(){
        Sin sin = new Sin(eps);
        Cos cos = new Cos(eps);
        Sec sec = new Sec(eps);
        Csc csc = new Csc(eps);
        Cot cot = new Cot(eps);
        FunctionSystem functionSystem = new FunctionSystem(sin, cos, sec, csc, cot, lnStub, log2Stub, log10Stub);
        assertEquals(-0.8414709848078965, functionSystem.calculate(-1), eps);
        assertEquals(-0.997495, functionSystem.calculate(-1.5), eps);
        assertEquals(-0.778073, functionSystem.calculate(-2.25), eps);
        assertEquals(0.110687, functionSystem.calculate(2.5), eps);
        assertEquals(1.971373378588643, functionSystem.calculate(3), eps);
        assertEquals(61.7963331333652, functionSystem.calculate(4), eps);
    }

    @Test
    public void testWithLnStubs(){
        Sin sin = new Sin(eps);
        Cos cos = new Cos(eps);
        Sec sec = new Sec(eps);
        Csc csc = new Csc(eps);
        Cot cot = new Cot(eps);
        Log log2 = new Log(2, eps, lnStub);
        Log log10 = new Log(10, eps, lnStub);
        FunctionSystem functionSystem = new FunctionSystem(sin, cos, sec, csc, cot, lnStub, log2, log10);
        assertEquals(-0.8414709848078965, functionSystem.calculate(-1), eps);
        assertEquals(-0.997495, functionSystem.calculate(-1.5), eps);
        assertEquals(-0.778073, functionSystem.calculate(-2.25), eps);
        assertEquals(0.110687, functionSystem.calculate(2.5), eps);
        assertEquals(1.971373378588643, functionSystem.calculate(3), eps);
        assertEquals(61.7963331333652, functionSystem.calculate(4), eps);
    }

    @Test
    public void testWithNoStubs(){
        //почему черт возьми не работает при x = 3, 4 без ln в конструкторе?
        Sin sin = new Sin(eps);
        Cos cos = new Cos(eps);
        Sec sec = new Sec(eps);
        Csc csc = new Csc(eps);
        Cot cot = new Cot(eps);
        Ln ln = new Ln(eps);
        Log log2 = new Log(2, eps, ln);
        Log log10 = new Log(10, eps, ln);
        FunctionSystem functionSystem = new FunctionSystem(sin, cos, sec, csc, cot, lnStub, log2Stub, log10Stub);
        assertEquals(-0.8414709848078965, functionSystem.calculate(-1), eps);
        assertEquals(-0.997495, functionSystem.calculate(-1.5), eps);
        assertEquals(-0.778073, functionSystem.calculate(-2.25), eps);
        assertEquals(0.110687, functionSystem.calculate(2.5), eps);
        assertEquals(1.971373378588643, functionSystem.calculate(3), eps);
        assertEquals(61.7963331333652, functionSystem.calculate(4), eps);

    }



    @Test
    public void testCos(){
        Cos cos = new Cos(eps, sinStub);
        assertEquals(cos.stubCalculate(Math.PI), cos.calculate(Math.PI),eps);
        assertEquals(cos.stubCalculate(Math.PI/2), cos.calculate(Math.PI/2),eps);
        assertEquals(cos.stubCalculate(Math.PI/4), cos.calculate(Math.PI/4),eps);
    }
    @Test
    public void testSec(){
        Sec sec = new Sec(eps, cosStub);
        assertEquals(sec.stubCalculate(Math.PI), sec.calculate(Math.PI),eps);
        assertEquals(sec.stubCalculate(Math.PI/2), sec.calculate(Math.PI/2),eps);
        assertEquals(sec.stubCalculate(Math.PI/4), sec.calculate(Math.PI/4),eps);
    }
    @Test
    public void testCscWithSin(){
        Csc csc = new Csc(eps, sinStub);
        assertEquals(csc.stubCalculate(Math.PI), csc.calculate(Math.PI),eps);
        assertEquals(csc.stubCalculate(Math.PI/2), csc.calculate(Math.PI/2),eps);
        assertEquals(csc.stubCalculate(Math.PI/4), csc.calculate(Math.PI/4),eps);
    }

    @Test
    public void testCotWithSin(){
        Cot cot = new Cot(eps, sinStub);
        assertEquals(cot.stubCalculate(Math.PI), cot.calculate(Math.PI),eps);
        assertEquals(cot.stubCalculate(Math.PI/2), cot.calculate(Math.PI/2),eps);
        assertEquals(cot.stubCalculate(Math.PI/4), cot.calculate(Math.PI/4),eps);
    }
    @Test
    public void testCotWithCos(){
        Cot cot = new Cot(eps, sinStub);
        assertEquals(cot.stubCalculate(Math.PI), cot.calculate(Math.PI),eps);
        assertEquals(cot.stubCalculate(Math.PI/2), cot.calculate(Math.PI/2),eps);
        assertEquals(cot.stubCalculate(Math.PI/4), cot.calculate(Math.PI/4),eps);
    }

    @Test
    public void testLogWithLn(){
        Log log2 = new Log(2, eps, lnStub);
        Log log10 = new Log(10, eps, lnStub);
        assertEquals(log2.stubCalculate(Math.PI), log2.calculate(Math.PI),eps);
        assertEquals(log2.stubCalculate(Math.PI/2), log2.calculate(Math.PI/2),eps);
        assertEquals(log2.stubCalculate(Math.PI/4), log2.calculate(Math.PI/4),eps);
        assertEquals(log10.stubCalculate(Math.PI), log10.calculate(Math.PI),eps);
        assertEquals(log10.stubCalculate(Math.PI/2), log10.calculate(Math.PI/2),eps);
        assertEquals(log10.stubCalculate(Math.PI/4), log10.calculate(Math.PI/4),eps);
    }

}