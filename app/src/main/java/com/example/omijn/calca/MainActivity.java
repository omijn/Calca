package com.example.omijn.calca;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    int top = -1, operatorCount = 0, decimalPointCount = 0;
    boolean equalsAllowed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void displayButtonValue(String identifier) {

        TextView mtv = (TextView) findViewById(R.id.mathTextView);

        //clear
        if (identifier.equals("clear")) {
            mtv.setText("");
            decimalPointCount = 0;
            operatorCount = 0;
        }


        //backspace
        else if (identifier.equals("backspace")) {
            operatorCount = 0;
            String currentText = mtv.getText().toString();

            //reset count of decimal points if we're backspacing a '.'
            char lastCharacter = 'c';

            if(currentText.length() > 0) {
                lastCharacter = currentText.charAt(currentText.length() - 1);
                mtv.setText(mtv.getText().toString() + Character.toString(lastCharacter));
            }



            if (lastCharacter == '.')
                decimalPointCount = 0;

            if(lastCharacter == 'y' || lastCharacter == 'd') {
                //mtv.setText("");
                displayButtonValue("clear");
                //Avoid resetting text again in the next if
                return;
            }

            if (currentText.length() > 0) {
                //Remove last character
                String newText = currentText.substring(0, currentText.length() - 1);
                mtv.setText(newText);
            }
        }

        //display a character
        else {

            //Error handling - Forbid multiple consecutive operators
            switch (identifier) {
                case "+":
                case "x":
                case "-":
                case "/":
                    //Avoid initial operator problem
                    if(mtv.getText().toString().equals(""))
                        return;
                    operatorCount++;
                    //To Avoid 9.+.. error
                    //if(mtv.getText().toString().charAt(mtv.length()-1) != '.')
                    if(operatorCount<=1)
                    decimalPointCount = 0;
                    break;

                case ".":
                    decimalPointCount++;
                    //operatorCount++;
                    break;

                default:
                    operatorCount = 0;
            }

            if (decimalPointCount <= 1 && identifier == ".") {
                mtv.setText(mtv.getText() + identifier);
                return;
            }

            if (operatorCount <= 1 && identifier != ".") {
                mtv.setText(mtv.getText() + identifier);
            }

        }

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
                displayButtonValue("x");
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

        //Equal Operator Error Handler Code
        int equalOperatorErrorCounter=0;
        if(equation.equals("")) {
            return;
        }
        for(int j = 0; j<equation.length(); ++j) {
            switch (equation.charAt(j)) {
                case 'x':
                case '/':
                case '+':
                case '-':
                if(j==equation.length()-1) {
                    displayButtonValue("clear");
                    return;

                }
                    break;
                case '.':
                if((j==0 && equation.charAt(1)=='\0') || ((j==equation.length()-1))) {
                    if(j==equation.length()-1){
                        switch (equation.charAt(equation.length()-2)) {
                            case '1': case '2': case '3': case '4': case '5': case '6':
                            case '7': case '8': case '9': case '0':
                                continue;
                        }
                    }
                    displayButtonValue("clear");
                    return;
                }
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                case '0':
                ++equalOperatorErrorCounter;
                if(j==equation.length()-1 && equalOperatorErrorCounter==equation.length()) {
                    displayButtonValue("clear");
                    return;
                }


            }

        }

        //Split the equation at operators to get operands, store them in operand array (string format)
        String[] operandArray = equation.split("x|\\/|\\-|\\+");

        //Operator array (String format)
        String[] operatorArray = new String[operandArray.length - 1];

        int operatorArrayCounter = 0, j = 0;

        //Get operator array by scanning equation from L2R. If operator is encountered, add it to the operator array
        for (j = 0; j < equation.length(); ++j) {

            switch (equation.charAt(j)) {
                case '+':
                case '-':
                case 'x':
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
        for (j = 0; j < operandArray.length; ++j) {
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


        //Evaluation of postfix expression
        Double operand1, operand2;
        double[] evaluationStack = new double[operandArray.length];
        int evaluationStackTop = -1;

        for (j = 0; j < postfix.length; ++j) {
            switch (postfix[j]) {
                case "x":
                    operand2 = evaluationStack[evaluationStackTop--];
                    operand1 = evaluationStack[evaluationStackTop];
                    evaluationStack[evaluationStackTop] = operand1 * operand2;
                    break;

                case "/":
                    operand2 = evaluationStack[evaluationStackTop--];
                    operand1 = evaluationStack[evaluationStackTop];
                    evaluationStack[evaluationStackTop] = operand1 / operand2;
                    break;

                case "+":
                    operand2 = evaluationStack[evaluationStackTop--];
                    operand1 = evaluationStack[evaluationStackTop];
                    evaluationStack[evaluationStackTop] = operand1 + operand2;
                    break;

                case "-":
                    operand2 = evaluationStack[evaluationStackTop--];
                    operand1 = evaluationStack[evaluationStackTop];
                    evaluationStack[evaluationStackTop] = operand1 - operand2;
                    break;

                default:
                    evaluationStack[++evaluationStackTop] = Double.parseDouble(postfix[j]);

            }
        }

        //Result
        if ((long) evaluationStack[evaluationStackTop] == evaluationStack[evaluationStackTop]) {
            tv.setText(Long.toString((long) evaluationStack[evaluationStackTop]));
        } else {
            tv.setText(Double.toString(evaluationStack[evaluationStackTop]));
        }

        //Divide by zero error = Undefined
        if (tv.getText().toString().equals("NaN")) {
            tv.setText("Undefined");
        }
        for (int i = 0; i < tv.length(); ++i) {
            if (tv.getText().charAt(i) == '.'){
                decimalPointCount++;
            }

        }

    }

    private void push(String[] stack, String item) {
        stack[++top] = item;
    }

    private String pop(String[] stack) {
        return stack[top--];
    }

    private int precedence(String operator) {
        switch (operator) {
            case "x":
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
