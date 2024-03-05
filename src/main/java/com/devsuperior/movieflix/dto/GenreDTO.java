package com.devsuperior.movieflix.dto;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;

import java.util.HashSet;
import java.util.Set;

public class GenreDTO {

    private Long id;
    private String name;

    private Set<MovieCardDTO> movies = new HashSet<>();

    public GenreDTO() {
    }

    public GenreDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreDTO(Genre entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public GenreDTO(Set<Movie> movies) {
        movies.forEach(mov -> this.movies.add(new MovieCardDTO(mov)));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
