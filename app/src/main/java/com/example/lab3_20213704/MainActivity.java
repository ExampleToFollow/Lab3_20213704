package com.example.lab3_20213704;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.AnimatedStateListDrawableCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab3_20213704.ConsumoWebServices.Interfaces.AuthUsuario;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.RespuestaAuth;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.Usuario;
import com.example.lab3_20213704.ConsumoWebServices.Interfaces.UsuarioService;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }





    //Llevará a la vista principal en caso se válide correctamente
    public void IniciarSesion(View view){
        AuthUsuario authUsuario = (AuthUsuario) new Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(AuthUsuario.class);

        String username = ((EditText) findViewById(R.id.userInput)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordInput)).getText().toString();
        authUsuario.login(username, password).enqueue(new Callback<RespuestaAuth>() {
            @Override
            public void onResponse(Call<RespuestaAuth> call, Response<RespuestaAuth> response) {
                //Pasar a la vista timer
                if(response.isSuccessful()){
                    Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                    startActivity(intent);
                }else{
                    showMaterialDialog();
                }
            }
            @Override
            public void onFailure(Call<RespuestaAuth> call, Throwable t) {
                Log.e("Error", "Ayuda");
            }
        });
    }


    private void showMaterialDialog() {
        // Crea y configura el diálogo
        new MaterialAlertDialogBuilder(this)
                .setTitle("Credenciales incorrectas")
                .setMessage("Ingreso mal sus credenciales")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Cierra el diálogo
                    }
                })
                .show();
    }
}