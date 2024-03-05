package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.movieflix.entities.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

//    @Query(nativeQuery = true, value = """
//                SELECT * FROM tb_movie
//                WHERE tb_movie.genre_id
//                IN :genreIds
//                ORDER BY tb_movie.title
//                """)
//    List<Movie> searchGenreWithMovies(List<Long> genreIds);


    @Query(value = """
            SELECT * FROM(
                      SELECT tb_movie.id, tb_movie.title, tb_movie.sub_title  FROM tb_movie
                      INNER JOIN tb_genre
                      ON tb_genre.id = tb_movie.genre_id
                      WHERE (:genreIds IS NULL OR tb_genre.id
                      IN :genreIds)
                      AND LOWER(tb_movie.title) LIKE LOWER(CONCAT('%', :title, '%'))
                      ORDER BY tb_movie.title)
                      AS tb_result
                      """,
            countQuery = """
            SELECT COUNT(*) FROM(
                      SELECT tb_movie.id, tb_movie.title, tb_movie.sub_title FROM tb_movie INNER JOIN tb_genre
                      ON tb_genre.id = tb_movie.genre_id
                      WHERE (:genreIds IS NULL OR tb_genre.id
                      IN :genreIds)
                      AND (LOWER(tb_movie.title) LIKE LOWER(CONCAT('%', :title, '%')))
                      ORDER BY tb_movie.title)
                      AS tb_result
            """, nativeQuery = true)
    Page<MovieProjection> searchMovies(List<Long> genreIds, String title, Pageable pageable);


    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_movie
            WHERE (:genreIds IS NULL OR tb_movie.genre_id
            IN :genreIds)
            ORDER BY tb_movie.title
            """)
    List<Movie> searchGenreWithMovies(List<Long> genreIds);



}
