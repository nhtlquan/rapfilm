package com.example.lequan.film.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ListFilm implements Serializable {
    private ArrayList<Film>  films = new ArrayList<>();

    public ListFilm(ArrayList<Film> films) {
        this.films = films;
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    public void setFilms(ArrayList<Film> films) {
        this.films = films;
    }
}
