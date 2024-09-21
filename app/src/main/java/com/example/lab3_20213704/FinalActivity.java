package com.example.lab3_20213704;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20213704.ConsumoWebServices.Interfaces.ListaUsuarios;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.Usuario;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FinalActivity extends AppCompatActivity {


    private ListaUsuarios lista;

    @Override
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
        UsuarioService usuarioService = (UsuarioService) new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsuarioService.class);
        usuarioService.obtenerUsuarios(id).enqueue(new Callback<ListaUsuarios>() {
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

    public void volverInicioSesion(MenuItem item ){
        Intent intent = new Intent(FinalActivity.this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.action_settings){
            Intent intent = new Intent(FinalActivity.this,MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        if (item.getItemId()==android.R.id.home){
            Intent intent = new Intent();
            setResult(RESULT_OK,intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.timer_menu, menu);
        return true;
    }


}