package com.example.lab3_20213704.ConsumoWebServices.Interfaces;

import retrofit2.Call;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UsuarioService {

    @POST("/auth/login")
    Call<RespuestaAuth> login(@Query("username") String username, @Query("password") String password);

    @GET("/users")
    Call<List<Usuario>> obtenerUsuarios();

}
