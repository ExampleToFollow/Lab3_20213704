package com.example.lab3_20213704;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20213704.ConsumoWebServices.Interfaces.AuxiliarTarea;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.ListaUsuarios;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.Usuario;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.UsuarioService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinalActivity extends AppCompatActivity {

    private ListaUsuarios lista;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String id = intent.getStringExtra("idUser");
        String name = intent.getStringExtra("name");

        UsuarioService usuarioService = (UsuarioService) new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsuarioService.class);
        usuarioService.obtenerUsuarios(id).enqueue(new Callback<ListaUsuarios>() {
            @Override
            public void onResponse(Call<ListaUsuarios> call, Response<ListaUsuarios> response) {
                if (response.isSuccessful()) {
                    lista = response.body();
                    TextView text = (TextView) findViewById(R.id.text);
                    text.setText("Ver tareas de " + name + ":");
                    ArrayList<String> options = new ArrayList<>();
                    for (AuxiliarTarea tarea : lista.getTodos()) {
                        if(!tarea.getCompleted()){
                            options.add(tarea.getTodo() + " - No completado" );
                        }else{
                            options.add(tarea.getTodo() + " - Completado" );
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>( FinalActivity.this, android.R.layout.simple_spinner_dropdown_item, options);
                    Spinner spinnerDatos = findViewById(R.id.comboBox);
                    spinnerDatos.setAdapter(adapter);
                    Button boton = findViewById(R.id.changeState);

                    boton.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            UsuarioService usuarioService = (UsuarioService) new Retrofit.Builder()
                                    .baseUrl("https://dummyjson.com")
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                    .create(UsuarioService.class);
                            int idx = spinnerDatos.getSelectedItemPosition();
                            boolean estadoACambiar = !lista.getTodos()[idx].getCompleted();
                            usuarioService.cambiarEstado(id,estadoACambiar ).enqueue(new Callback<AuxiliarTarea>() {
                                @Override
                                public void onResponse(Call<AuxiliarTarea> call, Response<AuxiliarTarea> response) {
                                    if(response.isSuccessful()){
                                        Toast.makeText(FinalActivity.this,"Cambio exitoso" ,Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<AuxiliarTarea> call, Throwable t) {

                                }
                            });


                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ListaUsuarios> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_settings){
            Intent intent = new Intent(FinalActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        if (item.getItemId()==android.R.id.home){
            //Para regresar al activity anterior borramos el actual de la pila
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}