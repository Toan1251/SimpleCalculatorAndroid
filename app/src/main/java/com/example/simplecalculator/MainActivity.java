package com.example.simplecalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultView, solutionView;
    private Button bC, bCE, bBS ,bDivide, bMultiply, bPlus, bMinus, bEqual, bDot, bNegativePositive;
    private Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;

    private String solutionString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultView = findViewById(R.id.result_view);
        solutionView = findViewById(R.id.solution_view);

        assignId(bC, R.id.button_c);
        assignId(bCE, R.id.button_ce);
        assignId(bBS, R.id.button_bs);
        assignId(bDivide, R.id.button_divide);
        assignId(bMultiply, R.id.button_multiply);
        assignId(bPlus, R.id.button_plus);
        assignId(bMinus, R.id.button_minus);
        assignId(bEqual, R.id.button_equal);
        assignId(bDot, R.id.button_dot);
        assignId(bNegativePositive, R.id.button_negative_positive);
        assignId(button0, R.id.button_0);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
    }

    void assignId(Button btn, int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String dataToCalculate = solutionString;

        if(btnText.equals("+/-")||btnText.equals("CE")||btnText.equals("BS")){
            solutionString = "";
            solutionView.setText(solutionString);
            resultView.setText("0");
            return;
        }

        if(btnText.equals("=")){
            String result = calculate(dataToCalculate);
            if(!result.equals("Error")){
                solutionString = result;
                solutionView.setText(dataToCalculate);
            }
            resultView.setText(result);
            return;
        }

        if(btnText.equals("C")){
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length()-1);
        } else{
            dataToCalculate += btnText;
        }
        solutionString = dataToCalculate;
        solutionView.setText(solutionString);
    }

    public String calculate(String dataToCalculate){
        try{
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String result = context.evaluateString(scriptable, dataToCalculate, "Javascript", 1, null).toString();

            if(result.endsWith(".0")){
                result = result.replace(".0", "");
            }

            if(result.contains(".")&& result.length() > result.indexOf(".") + 3){
                result = result.substring(0, result.indexOf(".") + 3);
            }
            return result;
        }catch(Exception e){
            return "Error";
        }

    }
}