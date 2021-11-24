package com.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class userAdd extends AppCompatActivity {
    Button usrSubmit;
    Spinner spinner;
    RadioButton radTrue, radFalse, radSelectedBut;
    RadioGroup radGroup;
    String[] usrQuesHolder = {};
    EditText usrAddOnQues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usr_input);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.colors_spinner);
        usrAddOnQues = findViewById(R.id.usrInputTxt);
        usrSubmit = findViewById(R.id.usrInputBtn);
        radTrue = findViewById(R.id.radButTru);
        radFalse = findViewById(R.id.radButFal);
        radGroup = findViewById(R.id.radioGroup);
        populateSpinner();


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Quiz> quizArr = (ArrayList<Quiz>) args.getSerializable("Arraylist");


        usrSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinResult = spinner.getSelectedItem().toString();
                String usrQst = usrAddOnQues.getText().toString();
                if (usrQst.isEmpty()) {
                    AlertDialog alertD = new AlertDialog.Builder(userAdd.this)
                            .setTitle("Error")
                            .setMessage("Invalid question, please do not leave this field blank").show();

                } else {
                    String usrAns;
                    //spinner.setOnClickListener(this);
                    int selectedAnswer = radGroup.getCheckedRadioButtonId();
                    radSelectedBut = (RadioButton) findViewById(selectedAnswer);
                    usrAns = radSelectedBut.getText().toString();
                    Boolean res = Boolean.valueOf(usrAns);

                    switch (spinResult) {
                        case "Yellow":
                            quizArr.add(new Quiz(usrQst, res, R.color.yellow));
                            break;
                        case "Green":
                            quizArr.add(new Quiz(usrQst, res, R.color.green));
                            break;
                        case "Blue":
                            quizArr.add(new Quiz(usrQst, res, R.color.blue));
                            break;
                        case "Red":
                            quizArr.add(new Quiz(usrQst, res, R.color.red));
                            break;
                        case "Orange":
                            quizArr.add(new Quiz(usrQst, res, R.color.orange));
                            break;
                        case "Purple":
                            quizArr.add(new Quiz(usrQst, res, R.color.purple_500));
                            break;
                        case "Pink":
                            quizArr.add(new Quiz(usrQst, res, R.color.pink));
                            break;
                        default:
                            quizArr.add(new Quiz(usrQst, res, R.color.white));
                            break;
                    }

                    Intent intent1 = getIntent();
                    Bundle args = new Bundle();
                    args.putSerializable("Arraylist", quizArr);
                    intent1.putExtra("BUNDLE", args);
                    setResult(RESULT_OK,intent1);
                    finish();
                }

            }
        });


    }

    private void populateSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.Color_usrInput_array));
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
}
