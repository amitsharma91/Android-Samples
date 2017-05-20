package tk.developeramit.scoreapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public int teamAScore = 0, teamBScore = 0;

    //for Team A
    public void addThreeForA(View view) {
        teamAScore += 3;
        displayTeamAScore(teamAScore);
    }

    public void addTwoForA(View view) {
        teamAScore += 2;
        displayTeamAScore(teamAScore);
    }

    public void addOneForA(View view) {
        teamAScore += 1;
        displayTeamAScore(teamAScore);
    }

    public void displayTeamAScore(int score) {
        TextView textView = (TextView) findViewById(R.id.scoreA_text);
        textView.setText("" + score);
    }


    //For Team B
    public void addThreeForB(View view) {
        teamBScore += 3;
        displayTeamBScore(teamBScore);
    }

    public void addTwoForB(View view) {
        teamBScore += 2;
        displayTeamBScore(teamBScore);
    }

    public void addOneForB(View view) {
        teamBScore += 1;
        displayTeamBScore(teamBScore);
    }

    public void displayTeamBScore(int score) {
        TextView textView = (TextView) findViewById(R.id.scoreB_text);
        textView.setText("" + score);
    }


    //For RESET

    public void resetScore(View view) {
        teamAScore = 0;
        teamBScore = 0;
        displayTeamAScore(teamAScore);
        displayTeamBScore(teamBScore);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
