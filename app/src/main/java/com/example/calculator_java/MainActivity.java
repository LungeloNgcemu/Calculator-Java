package com.example.calculator_java;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    private Button b0;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;

    private Button bD;
    private Button bM;
    private Button bS;
    private Button bA;
    private Button bE;
    private Button bAC;
    private Button bC;
    private Button bNegitive;
    private Button bPercentage;

    private TextView display;

    private String strContainer = "";

    private String sign = "none";

    private BigDecimal answer;

    private boolean isPressed = false;


    private List<BigDecimal> memory = new ArrayList<BigDecimal>();

    private int count = 0;
    private int equalCount = 0;

    //function to change ac to c


    // function for sighns


    void calculateThread(BigDecimal digit) {

        switch (sign) {
            case "-":

                answer = memory.get(0).subtract(digit);

                memory.clear();

                memory.add(answer);

                answer = BigDecimal.valueOf(0);

                updateList();

                display.setText(String.valueOf(memory.get(0).toPlainString()));

                break;

            case "+":

                answer = memory.get(0).add(digit);

                System.out.println("check 2 : " + answer);

                memory.clear();


                memory.add(answer);

                System.out.println("check 3 : " + memory);

                answer = BigDecimal.valueOf(0);

                updateList();

                display.setText(String.valueOf(memory.get(0).toPlainString()));

                break;
            case "*":

                answer = memory.get(0).multiply(digit, MathContext.DECIMAL128);



                memory.clear();

                memory.add(answer);

                answer = BigDecimal.valueOf(0);

                updateList();

                display.setText(String.valueOf(memory.get(0).toPlainString()));

                break;

            case "/":


                answer = memory.get(0).divide(digit, 10, RoundingMode.HALF_UP );

                System.out.println("anwser : " + answer);

                memory.clear();

                memory.add(answer);

                answer = BigDecimal.valueOf(0);


                updateList();
                display.setText(String.valueOf(memory.get(0).toPlainString()));

                break;
            default:
                // Handle unrecognized operation
                System.out.println("Error: Unrecognized operation");
                break;
        }

    }

    //Just incase
//    void calculate() {
//
//        switch (sign) {
//            case "-":
//
//                answer = memory.get(0) - memory.get(1);
//
//                memory.clear();
//
//                memory.add(answer);
//
//                answer = 0;
//
//                display.setText(String.valueOf(memory.get(0)));
//
//                break;
//
//            case "+":
//
//                answer = memory.get(0) + memory.get(1);
//
//                memory.clear();
//
//                memory.add(answer);
//
//                answer = 0;
//
//                display.setText(String.valueOf(memory.get(0)));
//
//                break;
//            case "*":
//
//                answer = memory.get(0) * memory.get(1);
//
//                memory.clear();
//
//                memory.add(answer);
//
//                answer = 0;
//
//                display.setText(String.valueOf(memory.get(0)));
//
//                break;
//
//            case "/":
//
//
//                answer = memory.get(0) / memory.get(1);
//
//                memory.clear();
//
//                memory.add(answer);
//
//                answer = 0;
//
//                display.setText(String.valueOf(memory.get(0)));
//
//                break;
//            default:
//                // Handle unrecognized operation
//                System.out.println("Error: Unrecognized operation");
//                break;
//        }
//
//    }


    void check(String type) {


        if (type != "none") {


            //Case Devide
            sign = type;

            display.setText(String.valueOf(0));

            //Check list amount(Calculate)


            try {
                //Push into List
                BigDecimal number = BigDecimal.valueOf(Double.parseDouble(strContainer));

                memory.add(number);

                if (count > 0) {

                    calculateThread(number);

                }
                ;

            } catch (NumberFormatException e) {

                display.setText(String.valueOf(0));

            }


            strContainer = "";

        }


    }


    void equalCheck(String type) {

        System.out.println("equal 1");
        //Case Devide
        sign = type;

        //Check if sign wa pressed
        if (sign != "none") {

            System.out.println("equal 2");
            try {

                System.out.println("equal 3");
                // simple right way
                BigDecimal number = new BigDecimal(strContainer);

                System.out.println(number);

                System.out.println("check : " + number);


                calculateThread(number);

            } catch (NumberFormatException e) {

                //Do Nothing
            }


        }


        strContainer = "";
    }


    void clearAll() {

        strContainer = "";
        isPressed = false;
        count = 0;
        display.setText(String.valueOf(0));
        memory.clear();

    }


    void toggle(String type) {

        //Check whats diplayed

        String value = display.getText().toString();

        System.out.println("value : " + value);

        if (value.equals("0") || value.equals("0.00")) {

            System.out.println("1");
            //Nothing to do but it needs to be empty

            display.setText(String.valueOf(0));

        } else {
            // in the memory

            System.out.println("2");

            if (memory.size() > 0 && value.equals(String.valueOf(memory.get(0).toPlainString()))) {

                System.out.println("3");
                BigDecimal num = BigDecimal.valueOf(Double.parseDouble(value));

                if (Objects.equals(type, "negitive")) {

                    num = num.negate() ;

                    System.out.println("here :"+num);

                } else {

                   num = num.divide(BigDecimal.valueOf(100));
                }


                memory.set(0, num);
                updateList();

                display.setText(String.valueOf(memory.get(0).toPlainString()));


            } else {
                //not in the memory
                System.out.println("4");
                BigDecimal num = BigDecimal.valueOf(Double.parseDouble(value));

                if (Objects.equals(type, "negitive")) {

                    num = num.negate() ;

                    System.out.println("here : "+ num);

                } else {

                  num =  num.divide(BigDecimal.valueOf(100));
                }

                BigDecimal newValue =  removeTrailingZeros(num);

                strContainer = String.valueOf(newValue);

                System.out.println("negitive : " + newValue);

                display.setText(strContainer);


            }
        }
    }


    void commaDuplicate() {

        //Check whats diplayed

        String value = display.getText().toString();

        System.out.println("value : " + value);

        if (!value.contains(".") && !value.equals("0")) {
            String newValue = value + ".";

            display.setText(String.valueOf(newValue));
        }
    }


    void addComma() {

        //Check whats diplayed

        String value = display.getText().toString();

        System.out.println("value : " + value);

        if (!value.contains(".")) {


            if (memory.size() > 0 && value.equals(String.valueOf(memory.get(0)))) {

                System.out.println("3");
                String newValue = value + ".";

//                double num = Double.parseDouble(newValue);
//
//                memory.set(0, num);
//
//                display.setText(String.valueOf(memory.get(0)));

                strContainer = String.valueOf(newValue);

                display.setText(strContainer);


            } else {
                //not in the memory
                System.out.println("4");

                String newValue = value + ".";

                // double num = Double.parseDouble(newValue);

                strContainer = String.valueOf(newValue);

                display.setText(strContainer);


            }
        }
    }


    void updateList() {


        if (!memory.isEmpty()) {


            for (int i = 0; i < memory.size(); i++) {

                BigDecimal originalValue = memory.get(i);

                BigDecimal formattedValue = removeTrailingZeros(originalValue);


                memory.set(i, formattedValue);


            }


        }

        hope();

        System.out.println(memory);


    }

    void hope() {


        if (!memory.isEmpty()) {


            for (int i = 0; i < memory.size(); i++) {

                BigDecimal originalValue = memory.get(i);



                BigDecimal bigDecimalValue = new BigDecimal(String.valueOf(originalValue));


                memory.set(i, bigDecimalValue);


            }


        }

        System.out.println(memory);


    }



    public static BigDecimal removeTrailingZeros(BigDecimal value) {
        // Use BigDecimal to remove trailing zeros and convert back to double

        return value.stripTrailingZeros();

    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b0 = findViewById(R.id.b0);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);

        bD = findViewById(R.id.bD);
        bM = findViewById(R.id.bM);
        bS = findViewById(R.id.bS);
        bA = findViewById(R.id.bA);
        bE = findViewById(R.id.bE);
        bC = findViewById(R.id.bC);

        bAC = findViewById(R.id.bAC);
        bNegitive = findViewById(R.id.bNegitive);
        bPercentage = findViewById(R.id.bPercentage);

        display = findViewById(R.id.display);


        b0.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "0";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });


        b1.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "1";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });

        b2.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "2";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });

        b3.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "3";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });

        b4.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "4";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });

        b5.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "5";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });

        b6.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "6";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });

        b7.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "7";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });

        b8.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "8";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });

        b9.setOnClickListener(view -> {

            isPressed = true;

            String strNumber = "9";

            strContainer = strContainer + strNumber;

            display.setText(String.valueOf(strContainer));


        });


        //cant press this like a fool
        bC.setOnClickListener(view -> {

            addComma();


        });

        bD.setOnClickListener(view -> {

            if (isPressed == true) {

                check("/");

                ++count;
            }


        });

        bM.setOnClickListener(view -> {


            if (isPressed == true) {

                check("*");

                ++count;
            }


        });

        bS.setOnClickListener(view -> {

            if (isPressed == true) {

                check("-");

                ++count;
            }


        });


        bA.setOnClickListener(view -> {

            if (isPressed == true) {

                check("+");

                ++count;

            }


        });

        bE.setOnClickListener(view -> {

            // enter Switch case here

            equalCheck(sign);


        });

        bAC.setOnClickListener(view -> {

            clearAll();


        });

        bNegitive.setOnClickListener(view -> {

            toggle("negitive");


        });

        bPercentage.setOnClickListener(view -> {

            toggle("Percentage");


        });


    }
}