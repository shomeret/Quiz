package com.example.quizapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag extends Fragment {
    TextView fragText;

    private Quiz quiz;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_one, null);

            quiz = (Quiz) getArguments().getSerializable("QUIZ");


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        fragText = (TextView) getView().findViewById(R.id.frg1);
        //fragText.setText(question);

        fragText.setText(quiz.getQuestion());
        fragText.setBackgroundColor(requireContext().getColor(quiz.getColor()));

    }

    public void findAnswer(Quiz quiz) {
        this.quiz =quiz;
        fragText.setText(quiz.getQuestion());
        fragText.setBackgroundColor(requireContext().getColor(quiz.getColor()));
    }

    /*public static Fragment newInstance(int someInt) {
        Fragment frag = new Fragment();
        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        frag.setArguments(args);
        return frag;
    }*/

}


