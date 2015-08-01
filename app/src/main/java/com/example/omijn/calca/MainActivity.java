package com.example.omijn.calca;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

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

        /*addition
        else if (value == -3) {

            //convert string to long
            num = Long.parseLong(mtv.getText().toString());


            //convert long to string
            mtv.setText(Long.toString(num));
        }*/

        //put numeric value to screen
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
        char[] equation = tv.getText().toString().toCharArray();
        StringBuilder x = new StringBuilder();

        for (int i = 0; i < tv.length(); ++i) {
           if(Character.isDigit(equation[i])){

           }
        }
    }


    /////////////////////////////////////////////////////////////////////////
//////////////////////////////OPERATOR-MODULES///////////////////////////
/////////////////////////////////////////////////////////////////////////
    /*private void add() {

        /*Update edit text
                mtv.setText(newText);
            }
    }*/


/////////////////////////////////////////////////////////////////////////



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
