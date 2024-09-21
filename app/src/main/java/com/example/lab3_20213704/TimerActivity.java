package com.example.lab3_20213704;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import androidx.appcompat.widget.ActivityChooserView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.CountDownTimer;


import com.example.lab3_20213704.ConsumoWebServices.Interfaces.Usuario;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.Time;

public class TimerActivity extends AppCompatActivity {
    private TextView timerTextView;
    private ImageView startButton;
    private CountDownTimer timer;
    private final long startTimeInMillis = 25 * 60 * 1000;

    private final long startTimeBreakInMillis = 5 * 60 * 1000;

    private String idUsuario;

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
        idUsuario = idUser;
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
        if (timer != null) {
            timer.cancel();
        }
        startButton.setImageResource(R.drawable.restart_alt_24px);
        startButton.setOnClickListener(v -> restartCountdown());
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
                timerTextView.setText("05:00");
                //Empieza el break o se decide que vaya a otra vista a marcar el spinner
                startBreak();
            }
        }.start();
    }

    private void restartCountdown() {
        // Cancela cualquier cronómetro anterior si existe
        if (timer != null) {
            timer.cancel();
        }
        //Cambia el icono
        startButton.setImageResource(R.drawable.restart_alt_24px);
        startButton.setOnClickListener(v -> restartCountdown());
        // Configura un nuevo cronómetro
        timer = new CountDownTimer(startTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }
            @Override
            public void onFinish() {
                timerTextView.setText("05:00");
                startBreak();
            }
        }.start();
    }


    public void mostrarDialog(){
        new MaterialAlertDialogBuilder(this)
                .setTitle("!Felicidades¡")
                .setMessage("Empezó el tiempo de descanso")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Cierra el diálogo
                    }
                })
                .show();
    }


    public void startBreak(){
        boolean validarSinTareas =true ;

        //Invocamos la c



        if(validarSinTareas){
            if (timer != null) {
                timer.cancel();
            }
            //Cambia el icono
            mostrarDialog();
            startButton.setVisibility(View.INVISIBLE);
            startButton.setClickable(false);
            //startButton.setOnClickListener(v -> restarCountdown());
            // Configura un nuevo cronómetro
            timer = new CountDownTimer(startTimeBreakInMillis, 1000) {
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
                    timerTextView.setText("25:00");
                    prepararInicio();
                }
            }.start();
        }else{
            //Caso tenga tareas pendientes irá al otro activity
            Intent intent = new Intent(TimerActivity.this , FinalActivity.class);
            startActivity(intent);
        }
    }


    public void prepararInicio(){
        timerTextView.setText("25:00");
        startButton.setVisibility(View.VISIBLE);
        startButton.setClickable(true);
        startButton.setImageResource(R.drawable.resume_24px);
        startButton.setOnClickListener(v -> startCountdown());
    }



}