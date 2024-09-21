package com.example.lab3_20213704.ConsumoWebServices.Interfaces;

import com.google.gson.annotations.SerializedName;

public class AuxiliarTarea {

    @SerializedName("id")
    private Integer id;
    @SerializedName("todo")
    private String todo;
    @SerializedName("completed")
    private String completed;
    @SerializedName("userId")
    private Integer userId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
