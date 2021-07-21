package obliczenia;

import java.math.*;

public class Obliczenia {

    private BigInteger ar1, ar2, wyn;
    private String operator;

    public Obliczenia(BigInteger operand, BigInteger operand2, String op) {
        ar1 = operand;
        ar2 = operand2;
        operator = op;
    }

    public BigInteger dodaj() {
        return ar1.add(ar2);
    }

    public BigInteger odejmij() {
        return ar1.subtract(ar2);
    }

    public BigInteger pomnóż() {
        return ar1.multiply(ar2);
    }

    public BigInteger podziel() {
        return ar1.divide(ar2);
    }

    public BigInteger potęga() {
        return ar1.pow(ar2.intValue());
    }

    public BigInteger modulo() {
        return ar1.mod(ar2);
    }

    public BigInteger równaj() {
        switch(operator) {
            case "+":
                wyn = dodaj();
                break;
            case "-":
                wyn = odejmij();
                break;
            case "/":
                wyn = podziel();
                break;
            case "*":
                wyn = pomnóż();
                break;
            case "^":
                wyn = potęga();
                break;
            case "%":
                wyn = modulo();
                break;
        }
        return wyn;
    }

    public BigInteger silnia(int n) {
        if(n == 0) return BigInteger.valueOf(1);
        else return silnia(n - 1).multiply(BigInteger.valueOf(n));
    }
}
