package com.example.lab3_20213704;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.CountDownTimer;


import com.example.lab3_20213704.ConsumoWebServices.Interfaces.Usuario;

import java.sql.Time;

public class TimerActivity extends AppCompatActivity {
    private TextView timerTextView;
    private ImageView startButton;
    private CountDownTimer timer;
    private final long startTimeInMillis = 25 * 60 * 1000;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.timer);
        //Ahora cambiamos de acuerdo a los datos
        Usuario user = new Usuario();
        Intent intent = getIntent();
        String name  = intent.getStringExtra("name");
        String username  = intent.getStringExtra("username");
        String idUser  = intent.getStringExtra("idUser");
        String gender  = intent.getStringExtra("gender");
        String firstName  = intent.getStringExtra("firstName");
        String lastName  = intent.getStringExtra("lastName");
        String email  = intent.getStringExtra("email");

        TextView nameView = findViewById(R.id.name);
        nameView.setText(firstName + " " + lastName);

        TextView emailView = findViewById(R.id.email);
        emailView.setText(email);

        ImageView image = findViewById(R.id.gender_image);
        if(gender.equals("female")){
            image.setImageResource(R.drawable.woman_24px);
        }else{
            image.setImageResource(R.drawable.man_24px);
        }


        timerTextView = findViewById(R.id.timer);
        startButton = findViewById(R.id.start_button);

        startButton.setOnClickListener(v -> startCountdown());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timer_menu, menu);
        return true;
    }

    public void volverInicioSesion(MenuItem item ){
        Intent intent = new Intent(TimerActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void startCountdown() {
        // Cancela cualquier cronómetro anterior si existe
        if (timer != null) {
            timer.cancel();
        }

        // Configura un nuevo cronómetro
        timer = new CountDownTimer(startTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Actualiza el texto del TextView con el tiempo restante
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                // Acciones a realizar cuando el cronómetro finaliza
                timerTextView.setText("00:00");
            }
        }.start();
    }




}