import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Driver {
    public static void main(String [] args) throws IOException {
        addTest();
        multiplyTest();
        readFileTest();
        toStringTest();
        System.out.println("ALL TESTS PASSED");
    }

    static void equalsHelper(Polynomial p, HashMap<Integer, Double> terms){
        assert p.exponents.length == terms.size();
        assert p.coefficients.length == terms.size();

        for (int i = 0; i < p.exponents.length; i++) {
            int c = p.exponents[i];
            assert terms.containsKey(c);
            assert terms.get(c) == p.coefficients[i];
            terms.remove(c);
        }
        
    }

    static void addTest(){
        Polynomial p = new Polynomial(new double[]{3, 4, 1, 2}, new int[]{3, 5, 1, 0});
        Polynomial q = new Polynomial(new double[]{1, 4, 2}, new int[]{0, 2, 1});

        Polynomial sum = p.add(q);

        HashMap<Integer, Double> terms = new HashMap<>();
        terms.put(0, 3.0);
        terms.put(1, 3.0);
        terms.put(2, 4.0);
        terms.put(3,3.0);
        terms.put(5, 4.0);

        equalsHelper(sum, terms);
    }

    static void multiplyTest(){
        Polynomial p = new Polynomial(new double[]{2, 3, 4}, new int[]{3, 1, 0});
        Polynomial q = new Polynomial(new double[]{1, 2}, new int[]{1, 2});

        Polynomial product = p.multiply(q);

        HashMap<Integer, Double> terms = new HashMap<>();
        terms.put(1, 4.0);
        terms.put(2, 11.0);
        terms.put(3, 6.0);
        terms.put(4,2.0);
        terms.put(5, 4.0);

        equalsHelper(product, terms);
    }

    static void readFileTest() throws IOException{
        Polynomial p = new Polynomial(new File("testone.txt"));
        
        HashMap<Integer, Double> terms = new HashMap<>();
        terms.put(1, -2.0);
        terms.put(0,4.0);
        terms.put(2, 6.0);

        equalsHelper(p, terms);
    }

    static void toStringTest(){
        Polynomial p = new Polynomial(new double[]{-2, 3, 4}, new int[]{3, 1, 0});
        assert p.toString().equals("-2.0x3+3.0x+4.0");

    }

}
 
    