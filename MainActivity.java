package com.example.quizapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.CharSequenceTransformation;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearProgressIndicator progBar;
    FragmentManager fm;
    Button tru, fal, usrSubmit;
    ArrayList<Quiz> quizArrayList = new ArrayList<>();
    int index = 0;
    int score = 0;
    Frag fragment;
    StorageManager storageManager;
    String resultText;
    Spinner spinner;
    String[] usrQuesHolder = {};
    EditText usrAddOnQues;
    RadioButton radTrue, radFalse, radSelectedBut;
    RadioGroup radGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*mTextView = (TextView) findViewById(R.id.frg1);
        if (savedInstanceState != null) {
            CharSequence savedText = savedInstanceState.getCharSequence(KEY_TEXT_VALUE);
            mTextView.setText(savedText);
        }*/
        setContentView(R.layout.activity_main);
        progBar = findViewById(R.id.progressLine);
        tru = findViewById(R.id.butTrue);
        fal = findViewById(R.id.butFalse);
        usrSubmit = findViewById(R.id.usrInputBtn);
        spinner = findViewById(R.id.colors_spinner);
        usrAddOnQues = findViewById(R.id.usrInputTxt);
        radTrue = findViewById(R.id.radButTru);
        radFalse = findViewById(R.id.radButFal);
        radGroup = findViewById(R.id.radioGroup);

        Resources res = getResources();
        storageManager = ((myApp) getApplication()).getStorageManager();



        String[] tmpQuesHolder = res.getStringArray(R.array.quesArr);

        quizArrayList.add(new Quiz(tmpQuesHolder[0], false, R.color.purple_200));
        quizArrayList.add(new Quiz(tmpQuesHolder[1], false, R.color.pink));
        quizArrayList.add(new Quiz(tmpQuesHolder[2], true, R.color.green));
        quizArrayList.add(new Quiz(tmpQuesHolder[3], true, R.color.red));
        quizArrayList.add(new Quiz(tmpQuesHolder[4], true, R.color.yellow));
        quizArrayList.add(new Quiz(tmpQuesHolder[5], false, R.color.green));
        quizArrayList.add(new Quiz(tmpQuesHolder[6], true, R.color.purple_500));
        progBar.setMax(quizArrayList.size());

        //ArrayAdapter<String> adapter = ArrayAdapter.createFromResource(this, R.array.Color_usrInput_array, R.layout.usr_input);
        //fragAdapter = new FragmentAdapter(this.getSupportFragmentManager(), this.getLifecycle(), listOfFrags, colors);

        if(savedInstanceState !=null){
            index = savedInstanceState.getInt("index");
            score = savedInstanceState.getInt("score");
            progBar.setProgress(index);
            quizArrayList =(ArrayList<Quiz>) savedInstanceState.getSerializable("LIST");
        }
            fragment = new Frag();
            Bundle bundle = new Bundle();
            bundle.putSerializable("QUIZ", quizArrayList.get(index));
            fragment.setArguments(bundle);
            fm = getSupportFragmentManager();
            FragmentTransaction fragTran = fm.beginTransaction();
            fragTran.replace(R.id.frameLay, fragment);
            fragTran.commit();




        tru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluateAnswer(true);

            }
        });

        fal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                evaluateAnswer(false);

            }
        });
        //addFragment();
    }

    private void populateSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.Color_usrInput_array));
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.average:
                //String resultText = "Your correct answers are " + storageManager.total + " in " + storageManager.attempt + " attempts";
                AlertDialog alertD = new AlertDialog.Builder(this)
                        .setMessage(storageManager.getStringFromPrivateExternal(getApplicationContext()))
                        .setPositiveButton(getResources().getString(R.string.sav), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                storageManager.saveNewPrivateExternal(getApplicationContext(), resultText);
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.save_ans),Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                storageManager.attempt = 0;
                                storageManager.total = 0;
                                resultText = getResources().getString(R.string.cor_ans)
                                        + " " + storageManager.total + " " + getResources().getString(R.string.cor_ans_in)
                                        + " " + (storageManager.attempt+1)
                                        + " " + getResources().getString(R.string.cor_ans_attempt);
                                storageManager.saveNewPrivateExternal(getApplicationContext(), resultText);
                                //index = 0;
                                //score = 0;
                                Toast.makeText(getApplicationContext(),getResources().getString(R.string.reset_save),Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                return true;
            case R.id.resetSave:
                storageManager.attempt = 0;
                storageManager.total = 0;
                resultText = getResources().getString(R.string.cor_ans)
                        + " " + storageManager.total + " " + getResources().getString(R.string.cor_ans_in)
                        + " " + (storageManager.attempt+1)
                        + " " + getResources().getString(R.string.cor_ans_attempt);
                storageManager.saveNewPrivateExternal(getApplicationContext(), resultText);
                //index = 0;
                //score = 0;
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.reset_save),Toast.LENGTH_SHORT).show();
                return true;
            case R.id.addQues:
                Intent myint = new Intent(this, userAdd.class);
                Bundle args = new Bundle();
                args.putSerializable("Arraylist", quizArrayList);
                myint.putExtra("BUNDLE", args);
                startActivityForResult(myint,101);
                return true;
                /*usrSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String spinResult = spinner.getSelectedItem().toString();
                        String usrQst = usrAddOnQues.getText().toString();
                        if (usrQst.isEmpty()) {
                            AlertDialog alertD = new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("Error")
                                    .setMessage("Invalid question, please do not leave this field blank").show();

                        } else {
                            String usrAns;
                            //spinner.setOnClickListener(this);
                            int selectedAnswer = radGroup.getCheckedRadioButtonId();
                            radSelectedBut = (RadioButton) findViewById(selectedAnswer);
                            usrAns = radSelectedBut.getText().toString();
                            Boolean res = Boolean.valueOf(usrAns);

                        }

                    }
                });*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void evaluateAnswer(boolean value) {
        Quiz quiz = quizArrayList.get(index);
        if (value == quiz.isAnswer()) {
            score++;
            Toast.makeText(this,getResources().getString(R.string.correct),Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,getResources().getString(R.string.incorrect),Toast.LENGTH_SHORT).show();
        }

        if (index == quizArrayList.size()-1) {
            index++;
            progBar.setProgress(index);
            storageManager.attempt = storageManager.attempt + 1;
            storageManager.total = storageManager.total + score;
            resultText = getResources().getString(R.string.cor_ans)
                    + " " + storageManager.total + " " + getResources().getString(R.string.cor_ans_in)
                    + " " + (storageManager.attempt)
                    + " " + getResources().getString(R.string.cor_ans_attempt);
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.res_title))
                    .setMessage(getResources().getString(R.string.totScore)
                            + " " + score
                            + " " + getResources().getString(R.string.totOutOf)
                            + " " + quizArrayList.size())
                    .setPositiveButton(getResources().getString(R.string.sav), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            storageManager.saveNewPrivateExternal(getApplicationContext(), resultText);
                            Toast.makeText(getApplicationContext(),getResources().getString(R.string.save_ans),Toast.LENGTH_SHORT).show();
                            score = 0;
                            index = 0;
                            progBar.setProgress(index);
                            Collections.shuffle(quizArrayList);
                            fragment.findAnswer(quizArrayList.get(0));
                        }
                    }).setNegativeButton(getResources().getString(R.string.ign), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            score = 0;
                            index = 0;
                            progBar.setProgress(index);
                            Collections.shuffle(quizArrayList);
                            fragment.findAnswer(quizArrayList.get(0));
                        }
                    })
                    .show();

            return;
        }
        index++;
        progBar.setProgress(index);
        fragment.findAnswer(quizArrayList.get(index));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index",index);
        outState.putInt("score",score);
        outState.putSerializable("LIST", quizArrayList);
    }


/* private void addFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment frg = new Fragment();
        fragmentTransaction.add(R.id.frameLay, frg);
        fragmentTransaction.commit();

    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            Bundle args = data.getBundleExtra("BUNDLE");
            quizArrayList= (ArrayList<Quiz>) args.getSerializable("Arraylist");

        }
    }
}