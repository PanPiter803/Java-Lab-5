package obliczenia;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.math.*;

public class Okno extends JFrame {

    //stan kalkulatora
    private int podst = 10;
    private int stan = 0;
    private String op;
    private BigInteger operand = null;
    private BigInteger operand2 = null;
    private BigInteger wynik = null;
    Sprawdzacz sprawdź;

    //argument i wynik
    private JTextField arg = new JTextField("0");
    private JTextField wyn = new JTextField();
    {
        arg.setFocusable(false);
        arg.setEditable(false);
        wyn.setEditable(false);
    }

    //Słuchacze przycisków
    private ActionListener sluchaczCyfr = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            JButton b = (JButton)ev.getSource();
            if(podst != 10) {
                stan = Integer.parseInt(arg.getText(), podst);
                if(arg.getText().startsWith("-") && stan == 0) arg.setText("-");
                else if(stan == 0) arg.setText("");
                if(arg.getText().length() - 1 <= 16) arg.setText(arg.getText() + b.getText()); stan = Integer.parseInt(arg.getText(), podst);
            }
            else {
                stan = Integer.parseInt(arg.getText());
                if(arg.getText().startsWith("-") && stan == 0) arg.setText("-");
                else if(stan == 0) arg.setText("");
                if(arg.getText().length() - 1 <= 16) arg.setText(arg.getText() + b.getText()); stan = Integer.parseInt(arg.getText());
            }
            if(operand != null) ustawoperand2();
        }
    };

    private ActionListener sluchaczZnak = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(arg.getText().startsWith("-")) arg.setText(arg.getText().substring(1));
            else arg.setText("-" + arg.getText());
        }
    };

    private ActionListener sluchaczClear = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            operand = null;
            operand2 = null;
            wynik = null;
            wyczyśćarg();
            wyczyśćwyn();
        }
    };

    private ActionListener sluchaczDelete = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(arg.getText().length() - 1 > 0)arg.setText(arg.getText().substring(0, arg.getText().length() - 1));
            else arg.setText("0");
        }
    };

    private ActionListener sluchaczDodawanie = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(stan == 0) operand2 = null;
            sprawdź = new Sprawdzacz(operand, operand2, wynik);
            if(op != "+") {
                działanie();
                op = "+";
            }
            else
            {
                op = "+";
                działanie();
                operand2 = null;
            }
        }
    };

    private ActionListener sluchaczOdejmowanie = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(stan == 0) operand2 = null;
            sprawdź = new Sprawdzacz(operand, operand2, wynik);
            if(op != "-") {
                działanie();
                op = "-";
            }
            else {
                op = "-";
                działanie();
                operand2 = null;
            }
        }
    };

    private ActionListener sluchaczMnozenie = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(stan == 0) operand2 = null;
            sprawdź = new Sprawdzacz(operand, operand2, wynik);
            if(op != "*") {
                działanie();
                op = "*";
            }
            else {
                op = "*";
                działanie();
                operand2 = null;
            }
        }
    };

    private ActionListener sluchaczDzielenie = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(stan == 0) operand2 = null;
            sprawdź = new Sprawdzacz(operand, operand2, wynik);
            if(op != "/") {
                działanie();
                op = "/";
            }
            else {
                op = "/";
                działanie();
                operand2 = null;
            }
        }
    };

    private ActionListener sluchaczPotęga = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(stan == 0) operand2 = null;
            sprawdź = new Sprawdzacz(operand, operand2, wynik);
            if(op != "^") {
                działanie();
                op = "^";
            }
            else {
                op = "^";
                działanie();
                operand2 = null;
            }
        }
    };

    private ActionListener sluchaczModulo = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            if(stan == 0) operand2 = null;
            sprawdź = new Sprawdzacz(operand, operand2, wynik);
            if(op != "%") {
                działanie();
                op = "%";
            }
            else {
                op = "%";
                działanie();
                operand2 = null;
            }
        }
    };

    private ActionListener sluchaczRówne = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            sprawdź = new Sprawdzacz(operand, operand2, wynik);
            if(!sprawdź.Pierwsza()) pierwszaliczba();
            else if(!sprawdź.Druga() && stan == 0) drugaliczba();
            else działanie();
        }
    };

    private ActionListener sluchaczSilnia = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            ustawoperand();
            if(operand.intValue() < 0) silniaujemna();
            else {
                Obliczenia oblicz = new Obliczenia(operand, operand2, op);
                wynik = oblicz.silnia(operand.intValue());
                wyn.setText("Wynik silnii: " + wynik);
            }
        }
    };

    private ActionListener sluchaczPierwiastek = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ev) {
            ustawoperand();
            if(operand.intValue() < 0) pierwiastekujemny();
            else wyn.setText("Wynik pierwiastka: " + Math.sqrt(operand.intValue()));
        }
    };

    //Wiadomości zwrotne kiedy użytkownik nie wprowadzi liczby albo dokona nieprawidłowej operacji
    public void pierwszaliczba() {
        wyn.setText("Wprowadź pierwszą liczbę.");
    }
    public void drugaliczba() {
        wyn.setText("Wprowadź drugą liczbę.");
    }
    public void dzielzero() {
        wyn.setText("Nie można dzielić przez 0.");
    }
    public void silniaujemna() { wyn.setText("Silnia nie może być ujemna."); }
    public void pierwiastekujemny() { wyn.setText("Kalkulator nie obsługuje ujemnych pierwiastków."); }

    //Reset górnego i dolnego pola tekstowego
    private void wyczyśćarg() { arg.setText("0"); }
    private void wyczyśćwyn() { wyn.setText(""); }

    private void ustawoperand() {
        if(podst == 2 || podst == 16)
        {
            String a = arg.getText();
            BigInteger x = new BigInteger(a, podst);
            operand = new BigInteger(x.toString(10));
        }
        else operand = new BigInteger(arg.getText());
    }

    private void ustawoperand2() {
        if(podst == 2 || podst == 16)
        {
            String a = arg.getText();
            BigInteger x = new BigInteger(a, podst);
            operand2 = new BigInteger(x.toString(10));
        }
        else operand2 = new BigInteger(arg.getText());
    }

    //Liczenie wyniku po przycisku = albo gdy użytkownik będzie ciągle wpisywał np. 5+5+5+5
    private void działanie() {
        if(!sprawdź.Argumenty())
        {
            if(!sprawdź.Pierwsza()) {
                ustawoperand();
                wyczyśćarg();
            }
        }
        else {
            wyczyśćarg();
            Obliczenia oblicz = new Obliczenia(operand, operand2, op);

            //Zabezpieczenie przed dzieleniem przez zero
            if(op == "/" || op == "mod") {
                if(operand2.equals(0))
                {
                    dzielzero();
                    operand = null;
                    operand2 = null;
                }
                wynik = oblicz.równaj();
                if(podst == 16 || podst == 2) wyn.setText("Wynik: " + wynik.toString(podst));
                else wyn.setText("Wynik: " + operand + " " + op + " " + operand2 + " = " + wynik.toString());
                operand = wynik;
                stan = 0;
            }
            else {
                wynik = oblicz.równaj();
                if(podst == 16 || podst == 2) wyn.setText("Wynik: " + wynik.toString(podst));
                else wyn.setText("Wynik: " + operand + " " + op + " " + operand2 + " = " + wynik.toString());
                operand = wynik;
                stan = 0;
            }
        }
    }

    //Przyciski oraz ich ustawienie
    private JButton c0 = new JButton("0");
    private JButton c1 = new JButton("1");
    private JButton c2 = new JButton("2");
    private JButton c3 = new JButton("3");
    private JButton c4 = new JButton("4");
    private JButton c5 = new JButton("5");
    private JButton c6 = new JButton("6");
    private JButton c7 = new JButton("7");
    private JButton c8 = new JButton("8");
    private JButton c9 = new JButton("9");
    private JButton cA = new JButton("A");
    private JButton cB = new JButton("B");
    private JButton cC = new JButton("C");
    private JButton cD = new JButton("D");
    private JButton cE = new JButton("E");
    private JButton cF = new JButton("F");
    private JButton clear = new JButton("Clear");
    private JButton delete = new JButton("Delete");
    private JButton dodaj = new JButton("+");
    private JButton odejmij = new JButton("-");
    private JButton mnóż = new JButton("*");
    private JButton dziel = new JButton("/");
    private JButton modulo = new JButton("mod");
    private JButton potęga = new JButton("^");
    private JButton znak = new JButton("+/-");
    private JButton silnia = new JButton("!");
    private JButton równe = new JButton("=");
    private JButton pierwiastek = new JButton("V");

    private JPanel centralny = new JPanel(new GridLayout(4, 7));
    {
        centralny.add(cC);
        cC.setEnabled(false);
        cC.addActionListener(sluchaczCyfr);
        centralny.add(cD);
        cD.setEnabled(false);
        cD.addActionListener(sluchaczCyfr);
        centralny.add(cE);
        cE.setEnabled(false);
        cE.addActionListener(sluchaczCyfr);
        centralny.add(cF);
        cF.setEnabled(false);
        cF.addActionListener(sluchaczCyfr);
        centralny.add(dodaj);
        dodaj.addActionListener(sluchaczDodawanie);
        centralny.add(odejmij);
        odejmij.addActionListener(sluchaczOdejmowanie);
        centralny.add(clear);
        clear.addActionListener(sluchaczClear);
        //...
        centralny.add(c8);
        c8.addActionListener(sluchaczCyfr);
        centralny.add(c9);
        c9.addActionListener(sluchaczCyfr);
        centralny.add(cA);
        cA.setEnabled(false);
        cA.addActionListener(sluchaczCyfr);
        centralny.add(cB);
        cB.setEnabled(false);
        cB.addActionListener(sluchaczCyfr);
        centralny.add(mnóż);
        mnóż.addActionListener(sluchaczMnozenie);
        centralny.add(dziel);
        dziel.addActionListener(sluchaczDzielenie);
        centralny.add(delete);
        delete.addActionListener(sluchaczDelete);
        //...
        centralny.add(c4);
        c4.addActionListener(sluchaczCyfr);
        centralny.add(c5);
        c5.addActionListener(sluchaczCyfr);
        centralny.add(c6);
        c6.addActionListener(sluchaczCyfr);
        centralny.add(c7);
        c7.addActionListener(sluchaczCyfr);
        centralny.add(potęga);
        potęga.addActionListener(sluchaczPotęga);
        centralny.add(modulo);
        modulo.addActionListener(sluchaczModulo);
        centralny.add(równe);
        równe.addActionListener(sluchaczRówne);
        //...
        centralny.add(c0);
        c0.addActionListener(sluchaczCyfr);
        centralny.add(c1);
        c1.addActionListener(sluchaczCyfr);
        centralny.add(c2);
        c2.addActionListener(sluchaczCyfr);
        centralny.add(c3);
        c3.addActionListener(sluchaczCyfr);
        centralny.add(znak);
        znak.addActionListener(sluchaczZnak);
        centralny.add(silnia);
        silnia.addActionListener(sluchaczSilnia);
        centralny.add(pierwiastek);
        pierwiastek.addActionListener(sluchaczPierwiastek);
        //...
    }

    //Konwerter na liczby binarne
    private ItemListener sluchaczSyst2 = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            int p = podst;
            if (p == 2) return;
            c2.setEnabled(false);
            c3.setEnabled(false);
            c4.setEnabled(false);
            c5.setEnabled(false);
            c6.setEnabled(false);
            c7.setEnabled(false);
            c8.setEnabled(false);
            c9.setEnabled(false);
            cA.setEnabled(false);
            cB.setEnabled(false);
            cC.setEnabled(false);
            cD.setEnabled(false);
            cE.setEnabled(false);
            cF.setEnabled(false);
            pierwiastek.setEnabled(false);
            silnia.setEnabled(false);
            podst = 2;
            String a = arg.getText();
            if (a.length() > 0) {
                BigInteger x = new BigInteger(a, p);
                arg.setText(x.toString(2));
            }
            if(wynik != null) {
                String w = wynik.toString();
                if (w.length() > 0) {
                    BigInteger x = new BigInteger(w, p);
                    wyn.setText("Wynik: " + x.toString(2));
                }
            }
            else wyn.setText("");
        }
    };

    //Konwerter na liczby dziesiętne
    private ItemListener sluchaczSyst10 = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            int p = podst;
            if (p == 10) return;
            c2.setEnabled(true);
            c3.setEnabled(true);
            c4.setEnabled(true);
            c5.setEnabled(true);
            c6.setEnabled(true);
            c7.setEnabled(true);
            c8.setEnabled(true);
            c9.setEnabled(true);
            cA.setEnabled(false);
            cB.setEnabled(false);
            cC.setEnabled(false);
            cD.setEnabled(false);
            cE.setEnabled(false);
            cF.setEnabled(false);
            silnia.setEnabled(true);
            pierwiastek.setEnabled(true);
            podst = 10;
            String a = arg.getText();
            if (a.length() > 0) {
                BigInteger x = new BigInteger(a, p);
                arg.setText(x.toString(10));
            }
            if(wynik != null) {
                wyn.setText("Wynik: " + wynik.toString());
            }
            else wyn.setText("");
        }
    };

    //Konwerter na liczby szesnastkowe
    private ItemListener sluchaczSyst16 = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent ev) {
            int p = podst;
            if (p == 16) return;
            c2.setEnabled(true);
            c3.setEnabled(true);
            c4.setEnabled(true);
            c5.setEnabled(true);
            c6.setEnabled(true);
            c7.setEnabled(true);
            c8.setEnabled(true);
            c9.setEnabled(true);
            cA.setEnabled(true);
            cB.setEnabled(true);
            cC.setEnabled(true);
            cD.setEnabled(true);
            cE.setEnabled(true);
            cF.setEnabled(true);
            silnia.setEnabled(false);
            pierwiastek.setEnabled(false);
            podst = 16;
            String a = arg.getText();
            if (a.length() > 0) {
                BigInteger x = new BigInteger(a, p);
                arg.setText(x.toString(16));
            }
            if(wynik != null) {
                String w = wynik.toString();
                if (w.length() > 0) {
                    BigInteger x = new BigInteger(w, p);
                    wyn.setText("Wynik: " + x.toString(16));
                }
            }
            else wyn.setText("");
        }
    };

    private JLabel sys = new JLabel("System:");
    private JRadioButton s2 = new JRadioButton("dwójkowy", false);
    private JRadioButton s10 = new JRadioButton("dziesiętny", true);
    private JRadioButton s16 = new JRadioButton("szesnaskowy", false);
    private ButtonGroup bg = new ButtonGroup();
    private JPanel prawy = new JPanel(new GridLayout(4, 1));
    {
        prawy.add(sys);
        prawy.add(s2);
        s2.addItemListener(sluchaczSyst2);
        prawy.add(s10);
        s10.addItemListener(sluchaczSyst10);
        prawy.add(s16);
        s16.addItemListener(sluchaczSyst16);
        bg.add(s2);
        bg.add(s10);
        bg.add(s16);
    }
    
    public Okno() {
        super("Kalkulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 240);
        setLocation(120, 120);
        setResizable(false);
        getContentPane().add(arg, BorderLayout.NORTH);
        getContentPane().add(wyn, BorderLayout.SOUTH);
        getContentPane().add(centralny, BorderLayout.CENTER);
        getContentPane().add(prawy, BorderLayout.EAST);
        setVisible(true);
    }
}
