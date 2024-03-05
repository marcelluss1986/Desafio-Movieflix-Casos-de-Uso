package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.projections.MovieProjection;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import com.devsuperior.movieflix.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	@Autowired
	private GenreRepository genreRepository;


	@Transactional(readOnly = true)
	public MovieDetailsDTO findById(Long id) {
		Optional<Movie> obj = repository.findById(id);
		Movie entity = obj.orElseThrow(() -> new ResourceNotFoundException("Not found! " + id));
		return new MovieDetailsDTO(entity);
	}

	@Transactional(readOnly = true)
	public Page<MovieCardDTO> findByGenre(String genreId, String title,
										  Pageable pageable) {
		List<Long> genreIds = Arrays.asList();
		if (!"0".equals(genreId))
			genreIds = Arrays.asList(genreId.split(",")).stream().map(x -> Long.parseLong(x)).collect(Collectors.toList());

			Page<MovieProjection> page = repository.searchMovies(genreIds, title, pageable);
			List<Long> movieIds = page.map(x -> x.getId()).stream().toList();

			List<Movie> entities = repository.searchGenreWithMovies(genreIds);

			List<MovieCardDTO> dto = entities.stream().map(x -> new MovieCardDTO(x)).toList();
			Page<MovieCardDTO> pageDto = new PageImpl(dto, page.getPageable(), page.getTotalElements());
			return pageDto;


	}
}







