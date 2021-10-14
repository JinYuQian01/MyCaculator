package com.example.mycaculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mycaculator.adapter.MyCalculatorAdapter;
import com.example.mycaculator.views.ConfirmDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyCalculatorAdapter.OnCalculatorItemClickListener {

    private TextView tvFormula;
    private RecyclerView rvCalculatorButton;
    private ArrayList<String> buttonStrings;
    private String[] buttons={"CE","÷","×","C","7","8","9","+","4","5","6","-","1","2","3","√","0",".","="};
    private String calculatorText="";
//    private Double results="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initEvent();
    }



    //数据先于View进行设置
    private void initData() {
        buttonStrings = new ArrayList<>();
        for (int i = 0; i < buttons.length; i++) {
            buttonStrings.add(buttons[i]);
        }
    }

    //找到控件
    private void initView() {
        tvFormula = findViewById(R.id.tv_formula);
        rvCalculatorButton = findViewById(R.id.rv_button);


    }

    //设置点击相关事件
    private void initEvent() {
        if (rvCalculatorButton != null) {
            rvCalculatorButton.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
        }
        MyCalculatorAdapter myCalculatorAdapter = new MyCalculatorAdapter(buttonStrings);
        if (myCalculatorAdapter != null) {
            myCalculatorAdapter.setOnCalculatorItemClickListener(this);
            rvCalculatorButton.setAdapter(myCalculatorAdapter);
        }
    }

    public void refreshTvCalculator(){
        if (tvFormula != null ) {
            tvFormula.setText(calculatorText);
        }
    }

    public void addEmptySpace(){
        if (calculatorText != null) {
            calculatorText+=" ";
        }
    }

    public void setCharacter(String c){
        addEmptySpace();
        calculatorText+=c;
        addEmptySpace();
    }

    @Override
    public void ItemClick(int position) {
        switch (position){
            case 0:
                calculatorText="";
                refreshTvCalculator();
                break;
            case 1:
                //TODO:除号
                setCharacter("÷");
                refreshTvCalculator();
                break;
            case 2:
                //TODO:乘号
                setCharacter("×");
                refreshTvCalculator();
                break;
            case 3:
                //TODO:回退一步
                if (calculatorText != null) {
                    char lastWord=calculatorText.charAt(calculatorText.length()-1);
                    if (lastWord==' '){
                        //符号类型，回退三格
                        calculatorText=calculatorText.substring(0,calculatorText.length()-3);//利用字符串的方式去除最后一项
                    }else{
                        //数字类型，回退一格
                        calculatorText=calculatorText.substring(0,calculatorText.length()-1);//利用字符串的方式去除最后一项
                    }
                    refreshTvCalculator();
                }
                break;
            case 4:
                //TODO:7
                calculatorText+="7";
                refreshTvCalculator();
                break;
            case 5:
                //TODO:8
                calculatorText+="8";
                refreshTvCalculator();
                break;
            case 6:
                //TODO:9
                calculatorText+="9";
                refreshTvCalculator();
                break;
            case 7:
                //TODO:+
                setCharacter("+");
                refreshTvCalculator();
                break;
            case 8:
                //TODO:4
                calculatorText+="4";
                refreshTvCalculator();
                break;
            case 9:
                //TODO:5
                calculatorText+="5";
                refreshTvCalculator();
                break;
            case 10:
                //TODO:6
                calculatorText+="6";
                refreshTvCalculator();
                break;
            case 11:
                //TODO:-
                setCharacter("-");
                refreshTvCalculator();
                break;
            case 12:
                //TODO:1
                calculatorText+="1";
                refreshTvCalculator();
                break;
            case 13:
                //TODO:2
                calculatorText+="2";
                refreshTvCalculator();
                break;
            case 14:
                //TODO:3
                calculatorText+="3";
                refreshTvCalculator();
                break;
            case 15:
                //TODO:√
                setCharacter("√");
                refreshTvCalculator();
                break;
            case 16:
                //TODO:0
                calculatorText+="0";
                refreshTvCalculator();
                break;
            case 17:
                //TODO:.
                calculatorText+=".";
                refreshTvCalculator();
                break;
            case 18:
                //TODO:=
                getResults();
                break;
        }
    }

    private void getResults() {
        String calculatorFormulaInstance= (String) tvFormula.getText();
        if (calculatorFormulaInstance == null || calculatorFormulaInstance.equals("")) {
            ConfirmDialog confirmDialog = new ConfirmDialog(MainActivity.this);
            confirmDialog.show();
            return;
        }else {
            //运算符前的数字
            String s1=calculatorFormulaInstance.substring(0,calculatorFormulaInstance.indexOf(" "));
            //运算符
            String op=calculatorFormulaInstance.substring(calculatorFormulaInstance.indexOf(" ")+1,calculatorFormulaInstance.indexOf(" ")+2);
            //运算符后的数字
            String s2=calculatorFormulaInstance.substring(calculatorFormulaInstance.indexOf(" ")+3);

            double results=0;
            if(!s1.equals("")&&!s2.equals("") && op!=null){
                //如果包含小数点的运算
                double d1=Double.parseDouble(s1);//则数字都是double类型
                double d2=Double.parseDouble(s2);


                if(op.equals("+")){
                    //如果是+
                    results=d1+d2;
                }else if(op.equals("-")){
                    //如果是-
                    results=d1-d2;
                }else if(op.equals("×")){
                    //如果是*
                    results=d1*d2;
                }else if(op.equals("÷")){
                    if(d2==0){
                        //如果被除数是0,出现提示
                        ConfirmDialog confirmDialog = new ConfirmDialog(MainActivity.this);
                        confirmDialog.show();
                    }
                    else {
                        //否则执行正常运算
                        results=d1/d2;
                    }
                }
                if(!s1.contains(".") &&!s2.contains(".")&&!op.equals("÷")){
                    //如果是整数类型
                    int r=(int)results;//都是整形
                    calculatorText=r+"";
                    refreshTvCalculator();
                }else {
                    calculatorText=results+"";
                    refreshTvCalculator();
                }
            }else  if(!s1.equals("")&& s2.equals("")){
                if (op.equals("√")){
                    results=Math.sqrt(Double.parseDouble(s1));
                    calculatorText=results+"";
                    refreshTvCalculator();
                }else {
                    //如果只输入运算符前的数字
                    tvFormula.setText(s1);//直接返回当前输入内容
                }
            }else if (s1.equals("")&& !s2.equals("")){
                //如果是只输入运算符后面的数
                double d2 =Double.parseDouble(s2);

                //运算符当前没有输入数字
                if(op.equals("+")){
                    results= 0 + d2;
                }else  if(op.equals("-")){
                    results= 0 - d2;
                }else if (op.equals("*")){
                    results= 0;
                }else  if(op.equals("/")){
                    results= 0;
                }
                if(!s1.contains(".")&&!s2.contains(".")){
                    int r=(int) results;
                    calculatorText=r+"";
                    refreshTvCalculator();
                }else {
                    calculatorText=results+"";
                    refreshTvCalculator();
                }
            }else {
                calculatorFormulaInstance="";
                refreshTvCalculator();
            }
        }
    }
}