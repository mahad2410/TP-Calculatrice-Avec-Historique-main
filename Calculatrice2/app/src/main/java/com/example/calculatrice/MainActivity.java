package com.example.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //declaration des variables
    TextView outputText, inputText;
    Button _btnDiv, _btnMutl, _btnSom, _btnSous, _btnEqual;
    Button _btn0, _btn1, _btn2, _btn3, _btn4, _btn5, _btn6, _btn7, _btn8, _btn9;
    Button _btnAC, _btnPoint, _btnBack;
    ListView lvHistorique;
    String data;
    DatabaseAdapter db;
    SQLiteDatabase bd;
    Cursor c, d;
    ArrayList<HashMap<String, String>> OperationList = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //liaison des controleurs java au controleurs xml
        _btn0 = findViewById(R.id.btn0);
        _btn1 = findViewById(R.id.btn1);
        _btn2 = findViewById(R.id.btn2);
        _btn3 = findViewById(R.id.btn3);
        _btn4 = findViewById(R.id.btn4);
        _btn5 = findViewById(R.id.btn5);
        _btn6 = findViewById(R.id.btn6);
        _btn7 = findViewById(R.id.btn7);
        _btn8 = findViewById(R.id.btn8);
        _btn9 = findViewById(R.id.btn9);
        _btnDiv = findViewById(R.id.btnDiv);
        _btnMutl = findViewById(R.id.btnMult);
        _btnSous = findViewById(R.id.btnSous);
        _btnSom = findViewById(R.id.btnSom);
        _btnEqual = findViewById(R.id.btnEqual);

        _btnAC = findViewById(R.id.btnAC);
        _btnPoint = findViewById(R.id.btnPoint);
        _btnBack = findViewById(R.id.btnBack);
        outputText = findViewById(R.id.txtResult);
        inputText = findViewById(R.id.txtOperation);

        lvHistorique = findViewById(R.id.lstViewHistoriques);
        _btn0.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "0");
            }
        });
        _btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "1");
            }
        });
        _btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "2");
            }
        });
        _btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "3");
            }
        });
        _btn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "4");
            }
        });
        _btn5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "5");
            }
        });
        _btn6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "6");
            }
        });
        _btn7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "7");
            }
        });
        _btn8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "8");
            }
        });
        _btn9.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "9");
            }
        });
        _btnPoint.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + ".");
            }
        });
        _btnAC.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                inputText.setText("");
                outputText.setText("");
            }
        });
        _btnSom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "+");
            }
        });
        _btnSous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "-");
            }
        });
        _btnMutl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "×");
            }
        });
        _btnDiv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                inputText.setText(data + "/");
            }
        });
        _btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expressionASupprimer = inputText.getText().toString();
                if (expressionASupprimer.length() == 0) {
                    Toast.makeText(MainActivity.this, "rien à supprimer", Toast.LENGTH_SHORT).show();
                } else {
                    expressionASupprimer = expressionASupprimer.substring(0, expressionASupprimer.length() - 1);
                    inputText.setText(expressionASupprimer);
                }
            }
        });

        db = new DatabaseAdapter(this);

        _btnEqual.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data = inputText.getText().toString();
                if (data.length() != 0) {
                    data = data.replaceAll("×", "*");
                    Context rhino = Context.enter();
                    rhino.setOptimizationLevel(-1);

                    String finalResult = "";
                    Scriptable scriptable = rhino.initSafeStandardObjects();
                    finalResult = rhino.evaluateString(scriptable, data, "Javascript", 1, null).toString();

                    outputText.setText(finalResult);
                    String expressionOp = inputText.getText().toString();
                    String resultatOp = outputText.getText().toString();
                    if (!expressionOp.equals("") && db.insertData(expressionOp, resultatOp) && !resultatOp.equals("")) {

                        Toast.makeText(MainActivity.this, "insertion reussit", Toast.LENGTH_SHORT).show();
                        DatabaseAdapter db = new DatabaseAdapter(MainActivity.this);
                        OperationList = db.GetUsers();
                        ListAdapter adapter = new SimpleAdapter(MainActivity.this, OperationList, R.layout.lsv_element_historiques, new String[]{"_id", "_expression", "_resultat"}, new int[]{R.id.lsv_historique_id, R.id.lsv_history_expression, R.id.lsv_history_result});
                        lvHistorique.setAdapter(adapter);

                    } else {
                        Toast.makeText(MainActivity.this, "insertion non echoué", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });


        DatabaseAdapter db = new DatabaseAdapter(this);
        OperationList = db.GetUsers();
        ListAdapter adapter = new SimpleAdapter(MainActivity.this, OperationList, R.layout.lsv_element_historiques, new String[]{"_id", "_expression", "_resultat"}, new int[]{R.id.lsv_historique_id, R.id.lsv_history_expression, R.id.lsv_history_result});
        lvHistorique.setAdapter(adapter);

    }

}