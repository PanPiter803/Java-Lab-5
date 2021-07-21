package obliczenia;

import java.math.*;

public class Sprawdzacz {

    private BigInteger ar1, ar2, wyn;

    public Sprawdzacz(BigInteger a, BigInteger b, BigInteger c) {
        ar1 = a;
        ar2 = b;
        wyn = c;
    }

    //Kalkulator sprawdza czy 2 wymagane wartości są ustawione
    public boolean Argumenty() {
        if(!Pierwsza() && Druga()) return false;
        else if(Pierwsza() && !Druga()) return false;
        else if(!Pierwsza() && !Druga()) return false;
        else return true;
    }
    public boolean Pierwsza() {
        if(ar1 == null) return false;
        else return true;
    }

    public boolean Druga() {
        if(ar2 == null) return false;
        else return true;
    }
}
