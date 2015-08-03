package com.example.omijn.calca;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends ActionBarActivity {

    int top = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void displayButtonValue(String identifier) {

        long num;

        TextView mtv = (TextView) findViewById(R.id.mathTextView);

        //clear
        if (identifier.equals("clear"))
            mtv.setText("");

            //backspace
        else if (identifier.equals("backspace")) {
            String currentText = mtv.getText().toString();

            if (currentText.length() > 0) {
                //Remove last character//
                String newText = currentText.substring(0, currentText.length() - 1);
                mtv.setText(newText);
            }
        }
        else
            mtv.setText(mtv.getText() + identifier);

    }

    public void passToTextView(View view) {
        switch (view.getId()) {
            case R.id.button_0:
                displayButtonValue("0");
                break;
            case R.id.button_1:
                displayButtonValue("1");
                break;
            case R.id.button_2:
                displayButtonValue("2");
                break;
            case R.id.button_3:
                displayButtonValue("3");
                break;
            case R.id.button_4:
                displayButtonValue("4");
                break;
            case R.id.button_5:
                displayButtonValue("5");
                break;
            case R.id.button_6:
                displayButtonValue("6");
                break;
            case R.id.button_7:
                displayButtonValue("7");
                break;
            case R.id.button_8:
                displayButtonValue("8");
                break;
            case R.id.button_9:
                displayButtonValue("9");
                break;

            case R.id.clear_button:
                displayButtonValue("clear");
                break;
            case R.id.backspace_button:
                displayButtonValue("backspace");
                break;

            case R.id.button_add:
                displayButtonValue("+");
                //add();
                break;

            case R.id.button_subtract:
                displayButtonValue("-");
                //add();
                break;

            case R.id.button_multiply:
                displayButtonValue("*");
                //add();
                break;

            case R.id.button_divide:
                displayButtonValue("/");
                //add();
                break;

            case R.id.button_decimal:
                displayButtonValue(".");
                //add();
                break;

            case R.id.button_equal:
                parseEquation();
                //equal();
                break;
        }
    }

    //LOGIC TO BE IMPLEMENTED
    private void parseEquation() {
        TextView tv = (TextView) findViewById(R.id.mathTextView);
        String equation = tv.getText().toString();

        //Operand Array (String format)
        String[] operandArray = equation.split("\\*|\\/|\\-|\\+");

        //Operator Array (String format)
        String[] operatorArray = new String[operandArray.length - 1];

        int operatorArrayCounter = 0, j = 0;

        //Get Operator Array
        for (j = 0; j < equation.length(); ++j) {

            switch (equation.charAt(j)) {
                case '+':
                case '-':
                case '*':
                case '/':
                    operatorArray[operatorArrayCounter++] = Character.toString(equation.charAt(j));
            }
        }

        j = 0;

        String[] postfix = new String[operandArray.length + operatorArray.length];

        String[] operatorStack = new String[operatorArray.length];
        int postfixCounter = 0;
        operatorStack[top] = "";


        for(j = 0; j < operandArray.length; ++j) {
            postfix[postfixCounter++] = operandArray[j];

            while(precedence(operatorArray[j]) <= precedence(operatorStack[top])) {
                postfix[postfixCounter++] = pop(operatorStack, top);
            }

            push(operatorStack, operatorArray[j], top);
        }

        for(j = 0; j < postfix.length; ++j) {
            tv.setText(tv.getText().toString() + postfix[j]);
        }


        /*Long[] numericArray = new Long[stringArray.length];

        for (int i = 0; i < stringArray.length; ++i) {
            numericArray[i] = Long.parseLong(stringArray[i]);
        }
        */

    }

    private void push(String[] stack, String item, int top) {
        stack[top++] = item;

    }

    private String pop(String[] stack, int top) {
        return stack[top--];
    }

    private int precedence(String operator) {
        if(operator.equals("+") || operator.equals("-"))
            return 2;
        else if(operator.equals("*") || operator.equals("/"))
            return 3;
        else
            return 0;

//        switch(operator) {
//            case "*":
//            case "/":
//                return 3;
//
//            case "+":
//            case "-":
//                return 2;
//
//            default:
//                return 0;
//        }
    }

}
