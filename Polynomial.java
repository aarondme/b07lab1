import java.util.Arrays;

public class Polynomial{

    double[] coefficients;

    Polynomial(){
        coefficients = new double[]{0};
    }

    Polynomial(double[] coefficients){
        this.coefficients = Arrays.copyOf(coefficients, coefficients.length); 
    }

    Polynomial add(Polynomial other){
        int n = Math.max(coefficients.length, other.coefficients.length);
        double[] outCoefficients = new double[n];
        
        for(int i = 0; i < n; i++){
            double sum = 0;
            if(i < coefficients.length)
                sum += coefficients[i];
            if(i < other.coefficients.length)
                sum += other.coefficients[i];
            outCoefficients[i] = sum;
        }

        return new Polynomial(outCoefficients);
    }

    double evaluate(double x){
        double xi = 1;
        double total = 0;
        for(int i = 0; i < coefficients.length; i++){
            total += xi * coefficients[i];
            xi *= x;
        }
        return total;
    }

    //NOTE: this is a misnomer, should be isRoot, but lab notes says it must be named this.
    boolean hasRoot(double x){
        double out = evaluate(x);
        return out == 0;
    }
}