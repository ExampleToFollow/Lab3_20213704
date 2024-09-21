package com.example.lab3_20213704.ConsumoWebServices.Interfaces;

import com.google.gson.annotations.SerializedName;

public class UsuarioAuth {
    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
