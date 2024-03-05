package com.devsuperior.movieflix.controllers;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.services.GenreService;
import com.devsuperior.movieflix.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

    @Autowired
    private MovieService service;


    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
        MovieDetailsDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @GetMapping
    public ResponseEntity<Page<MovieCardDTO>> findByGenre(
            @RequestParam(value = "genreId", defaultValue = "0") String genreId,
            @RequestParam(value = "title", defaultValue = "") String title,
            Pageable pageable) {
        Page<MovieCardDTO> page = service.findByGenre(genreId, title, pageable);
        return ResponseEntity.ok().body(page);
    }

//    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
//    @GetMapping(value = "/test")
//    public ResponseEntity<Page<MovieCardDTO>> findAll(
//            @RequestParam(value = "genreId", defaultValue = "0") Long genreId,
//            @RequestParam(value = "title", defaultValue = "") String title,
//            Pageable pageable) {
//        Page<MovieCardDTO> page = service.findByGenre(genreId, title, pageable);
//        return ResponseEntity.ok().body(page);
//    }
}