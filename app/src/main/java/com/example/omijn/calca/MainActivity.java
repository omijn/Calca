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

    int top = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void displayButtonValue(String identifier) {

        TextView mtv = (TextView) findViewById(R.id.mathTextView);

        //clear
        if (identifier.equals("clear"))
            mtv.setText("");

            //backspace
        else if (identifier.equals("backspace")) {
            String currentText = mtv.getText().toString();

            if (currentText.length() > 0) {
                //Remove last character
                String newText = currentText.substring(0, currentText.length() - 1);
                mtv.setText(newText);
            }
        }

        //display a character
        else
            mtv.setText(mtv.getText() + identifier);

    }

    public void passToTextView(View view) {

        switch (view.getId()) {
            /*numeric*/
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
            case R.id.button_decimal:
                displayButtonValue(".");
                break;

            /*clear and backspace*/
            case R.id.clear_button:
                displayButtonValue("clear");
                break;
            case R.id.backspace_button:
                displayButtonValue("backspace");
                break;

            /*operations*/
            case R.id.button_add:
                displayButtonValue("+");
                break;
            case R.id.button_subtract:
                displayButtonValue("-");
                break;
            case R.id.button_multiply:
                displayButtonValue("*");
                break;
            case R.id.button_divide:
                displayButtonValue("/");
                break;

            /*special*/
            case R.id.button_equal:
                parseEquation();
                break;
        }
    }


    private void parseEquation() {
        TextView tv = (TextView) findViewById(R.id.mathTextView);
        String equation = tv.getText().toString();

        //Split the equation at operators to get operands, store them in operand array (string format)
        String[] operandArray = equation.split("\\*|\\/|\\-|\\+");

        //Operator array (String format)
        String[] operatorArray = new String[operandArray.length - 1];

        int operatorArrayCounter = 0, j = 0;

        //Get operator array by scanning equation from L2R. If operator is encountered, add it to the operator array
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
        int postfixCounter = 0;

        String[] operatorStack = new String[operatorArray.length];
        operatorStack[0] = "";


        //Deadly Function For Infix To Postfix
        for (j = 0; j <= operandArray.length; ++j) {
            //J VALUE DUE TO LESS RANGE SPECIFIED, MISSED PUSHING INTO STACK THE LAST OPERAND
            postfix[postfixCounter++] = operandArray[j];

            if (top != -1) {

                if (j == operatorArray.length) {
                    //PUSHING OPERATORS IF REQUIRED AT THE END OF THE INFIX EXPRESSION
                    while (top != -1)
                        postfix[postfixCounter++] = pop(operatorStack);
                    break;
                }

                while ((top != -1) && (precedence(operatorArray[j]) <= precedence(operatorStack[top]))) {
                    postfix[postfixCounter++] = pop(operatorStack);
                }
            }

            push(operatorStack, operatorArray[j]);
        }


        tv.setText("");
        for (j = 0; j < postfix.length; ++j) {
            tv.setText(tv.getText().toString() + postfix[j]);
        }


        /*Long[] numericArray = new Long[stringArray.length];

        for (int i = 0; i < stringArray.length; ++i) {
            numericArray[i] = Long.parseLong(stringArray[i]);
        }
        */

    }

    private void push(String[] stack, String item) {
        stack[++top] = item;
    }

    private String pop(String[] stack) {
        return stack[top--];
    }

    private int precedence(String operator) {
        switch (operator) {
            case "*":
            case "/":
                return 3;

            case "+":
            case "-":
                return 2;

            default:
                return 0;
        }
    }

}
