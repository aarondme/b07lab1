import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

public class Polynomial{

    double[] coefficients;
    int[] exponents;

    Polynomial(){
        coefficients = null;
        exponents = null;
    }

    Polynomial(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] splitLine = reader.readLine()
                            .replace("-", "+-")
                            .split("\\+");
        if(splitLine[0].equals("")){
            String[] tempLine = new String[splitLine.length - 1];
            for(int i = 0; i < tempLine.length; i++){
                tempLine[i] = splitLine[i+1];
            }
            splitLine = tempLine;
        }
             
        exponents = new int[splitLine.length];
        coefficients = new double[splitLine.length];

        for(int i = 0; i < splitLine.length; i++){
            String[] coefXexp = splitLine[i].split("x");
            if(coefXexp.length == 1){
                coefficients[i] = Double.parseDouble(coefXexp[0]);
                exponents[i] = 0;
            }
            else{
                coefficients[i] = Double.parseDouble(coefXexp[0]);
                exponents[i] = Integer.parseInt(coefXexp[1]);
            }
        }

        reader.close();
    }

    Polynomial(double[] nonzeroCoefficients, int[] exponents){
        this.coefficients = Arrays.copyOf(nonzeroCoefficients, nonzeroCoefficients.length); 
        this.exponents = Arrays.copyOf(exponents, exponents.length); 
    }

    Polynomial add(Polynomial other){
        HashMap<Integer, Double> outMap = new HashMap<>();
        Polynomial p = this;
        for(int i = 0; i < p.coefficients.length; i++){
            double sum = outMap.getOrDefault(p.exponents[i], 0.0) + p.coefficients[i];                
            outMap.put(p.exponents[i], sum);
        }
        p = other;
        for(int i = 0; i < p.coefficients.length; i++){
            double sum = outMap.getOrDefault(p.exponents[i], 0.0) + p.coefficients[i];                
            outMap.put(p.exponents[i], sum);
        }
        return polynomialFromHashMap(outMap);
    }


    private Polynomial polynomialFromHashMap(HashMap<Integer, Double> outMap){
        int n = outMap.size();
        if(n == 0) 
            return new Polynomial();

        double[] outCoefficients = new double[n];
        int[] outExponents = new int[n];

        int i = 0;
        for (Entry<Integer, Double> e : outMap.entrySet()) {
            outExponents[i] = e.getKey();
            outCoefficients[i] = e.getValue();
            i++;
        }
        return new Polynomial(outCoefficients, outExponents);
    }

    Polynomial multiply(Polynomial other){
        HashMap<Integer, Double> outMap = new HashMap<>(); 
        
        for (int i = 0; i < exponents.length; i++) {
            for (int j = 0; j < other.exponents.length; j++) {
                int newExp = exponents[i] + other.exponents[j];
                double sum = outMap.getOrDefault(newExp, 0.0) + 
                    coefficients[i] * other.coefficients[j];                
                outMap.put(newExp, sum);
            }
        }

        return polynomialFromHashMap(outMap);
    }

    double evaluate(double x){
        double total = 0;
        for(int i = 0; i < coefficients.length; i++){
            total += Math.pow(x, exponents[i]) * coefficients[i];
        }
        return total;
    }

    //NOTE: this is a misnomer, should be isRoot, but lab notes says it must be named this.
    boolean hasRoot(double x){
        double out = evaluate(x);
        return out == 0;
    }

    void saveToFile(String fileName) throws IOException{
        File file = new File(fileName);
        PrintWriter writer = new PrintWriter(file);
        writer.println(toString());
        writer.close();
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
         for(int i = 0; i < exponents.length; i++){
            if(coefficients[i] >= 0 && i > 0)
                builder.append("+");
            builder.append(coefficients[i]);
            if(exponents[i] != 0){
                builder.append("x");
                builder.append(exponents[i]);
            }
        }
        return builder.toString();
    }
}