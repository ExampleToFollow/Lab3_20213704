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


import com.example.lab3_20213704.ConsumoWebServices.Interfaces.AuthUsuario;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.AuxiliarTarea;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.ListaUsuarios;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.Usuario;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.UsuarioService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.sql.Time;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimerActivity extends AppCompatActivity {
    private TextView timerTextView;
    private ImageView startButton;
    private CountDownTimer timer;
    private final long startTimeInMillis = 2 * 60 * 1000;
    private final long startTimeBreakInMillis = 1 * 60 * 1000;
    private String idUsuario;
    private ListaUsuarios lista;
private String nameSupreme ;

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
        //String idUser  = intent.getStringExtra("idUser");
        String idUser = String.valueOf(intent.getIntExtra("idUser", 0));
        String gender  = intent.getStringExtra("gender");
        String firstName  = intent.getStringExtra("firstName");
        String lastName  = intent.getStringExtra("lastName");
        String email  = intent.getStringExtra("email");
        idUsuario = "" +  idUser;
        nameSupreme= firstName;
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
        UsuarioService usuarioService = (UsuarioService) new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsuarioService.class);
        usuarioService.obtenerUsuarios(idUsuario).enqueue(new Callback<ListaUsuarios>() {
            @Override
            public void onResponse(Call<ListaUsuarios> call, Response<ListaUsuarios> response) {
                if(response.isSuccessful()){
                    lista = response.body();
                }
            }

            @Override
            public void onFailure(Call<ListaUsuarios> call, Throwable t) {

            }
        });
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
                // Cambia el texto como cronometro
                int minutes = (int) (millisUntilFinished / 1000) / 60;
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
            }
            @Override
            public void onFinish() {
                // Acciones a realizar cuando el cronómetro finaliza por start NO por restart
                startBreak();
            }
        }.start();
    }
    private void restartCountdown() {
        // Cancela cualquier cronómetro anterior si existe
        TextView textText = findViewById(R.id.textUp);
        textText.setText("Descanso 01:00");
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
        //Se muestra un aviso de que termino el tiempo
        //En caso haya tareas se redirige a otra vista
        TextView textText = findViewById(R.id.textUp);
        textText.setText("En descanso");
        if (timer != null) {
            timer.cancel();
        }
        //Cambia el icono
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
                mostrarDialogFinBreak();
                prepararInicio();
            }
        }.start();

        if(lista.getTotal()>0){
            Intent intent = new Intent(TimerActivity.this, FinalActivity.class);
            intent.putExtra("idUser" , idUsuario);
            intent.putExtra("name" ,nameSupreme );
            startActivity(intent);
        }else{
            mostrarDialog();
        }
    }
    public void prepararInicio(){
        timerTextView.setText("00:00");
        startButton.setVisibility(View.VISIBLE);
        startButton.setClickable(true);
        startButton.setImageResource(R.drawable.restart_alt_24px);
        startButton.setOnClickListener(v -> restartCountdown());
        TextView textText = findViewById(R.id.textUp);
        textText.setText("Fin del descanso");

    }
    public void mostrarDialogFinBreak(){
        new MaterialAlertDialogBuilder(this)
                .setTitle("!Atención¡")
                .setMessage("Terminó el tiempo de descanso , dale al botón de reinicio para empezar otro ciclo")
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Cierra el diálogo
                    }
                })
                .show();
    }
    public void verSize(View view){
        Log.e("Lista", "" + lista.getTotal());
    }

}