package com.example.lab3_20213704.ConsumoWebServices.Interfaces;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListaUsuarios {
    @SerializedName("todos")
    private List<AuxiliarTarea> todos;

    @SerializedName("total")
    private Integer total;

    @SerializedName("skip")
    private Integer skip;

    @SerializedName("limit")
    private Integer limit;

    public List<AuxiliarTarea> getTodos() {
        return todos;
    }

    public void setTodos(List<AuxiliarTarea> todos) {
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
