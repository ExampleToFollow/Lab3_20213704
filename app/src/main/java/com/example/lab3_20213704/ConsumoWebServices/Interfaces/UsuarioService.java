package com.example.lab3_20213704.ConsumoWebServices.Interfaces;

import retrofit2.Call;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuarioService {


    @GET("/todos/user/{userId}")
    Call<ListaUsuarios> obtenerUsuarios(@Path("userId") String userId);


    @FormUrlEncoded
    @PUT("/todos/{todoId}")
    Call<AuxiliarTarea> cambiarEstado(@Path("todoId") String todoId , @Field("completed") boolean completed);

}
