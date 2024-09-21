package com.example.lab3_20213704.ConsumoWebServices.Interfaces;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListaUsuarios implements Serializable {
    @SerializedName("todos")
    private AuxiliarTarea[] todos;

    @SerializedName("total")
    private Integer total;

    @SerializedName("skip")
    private Integer skip;

    @SerializedName("limit")
    private Integer limit;

    public AuxiliarTarea[] getTodos() {
        return todos;
    }

    public void setTodos(AuxiliarTarea[] todos) {
        this.todos = todos;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
