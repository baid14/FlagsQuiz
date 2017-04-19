package dev.edmt.flagsquizapp;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

import dev.edmt.flagsquizapp.DbHelper.DbHelper;
import dev.edmt.flagsquizapp.Model.Ranking;

public class Done extends AppCompatActivity {

    Button btnTryAgain;
    TextView txtResultScore, txtResultQuestion;
    ProgressBar progressBarResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        DbHelper db = new DbHelper(this);


        txtResultScore = (TextView) findViewById(R.id.txtTotalScore);
        txtResultQuestion = (TextView) findViewById(R.id.txtTotalQuestion);
        progressBarResult = (ProgressBar) findViewById(R.id.doneProgressBar);
        btnTryAgain = (Button) findViewById(R.id.btnTryAgain);
        btnTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Bundle extra = getIntent().getExtras();
        if (extra != null) {
			
            int score = extra.getInt("SCORE");
            
			   int correctAnswer = extra.getInt("CORRECT");
			
			int totalQuestion = extra.getInt("TOTAL");
         

          
            int playCount = 0;
            if(totalQuestion == 30) 
            {
                playCount = db.getPlayCount(0);
                playCount++;
                db.updatePlayCount(0,playCount); 
            }
            else if(totalQuestion == 50) 
            {
                playCount = db.getPlayCount(1);
                playCount++;
                db.updatePlayCount(1,playCount); 
            }
            else if(totalQuestion == 100)
            {
                playCount = db.getPlayCount(2);
                playCount++;
                db.updatePlayCount(2,playCount); 
            }
            else if(totalQuestion == 200) 
            {
                playCount = db.getPlayCount(3);
                playCount++;
                db.updatePlayCount(3,playCount); 
            }

            double subtract = ((5.0/(float)score)*100)*(playCount-1);
            double finalScore = score - subtract;

            txtResultScore.setText(String.format("SCORE : %.1f (-%d)%%", finalScore,5*(playCount-1)));
            txtResultQuestion.setText(String.format("PASSED : %d/%d", correctAnswer, totalQuestion));

            progressBarResult.setMax(totalQuestion);
			
            progressBarResult.setProgress(correctAnswer);

        
            db.insertScore(finalScore);
        }
    }
}
