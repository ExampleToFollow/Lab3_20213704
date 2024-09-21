package com.example.lab3_20213704.ConsumoWebServices.Interfaces;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario  {
    //@SerializedName("id")
    private Integer idUser;

    //@SerializedName("username")
    private String username;


    //@SerializedName("email")
    private String email;

    //@SerializedName("firstName")
    private String firstName;

    //@SerializedName("lastName")
    private String lastName;

    //@SerializedName("gender")
    private String gender;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
