package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.Role;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.projections.ReviewProjection;
import com.devsuperior.movieflix.projections.UserDetailsProjection;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;



//	@Transactional(readOnly = true)
//	public ReviewDTO findById(Long id) {
//		Optional<Review> obj = repository.findById(id);
//		Review entity = obj.orElseThrow(() -> new ResourceNotFoundException("Not found! " + id));
//		return new ReviewDetailsDTO(entity);
//	}

	@Transactional
	public ReviewDTO insert( ReviewDTO dto){
		Review entity = new Review();
			entity.setText(dto.getText());
			Movie movie = movieRepository.getReferenceById(dto.getMovieId());
			entity.setMovie(movie);

			UserDTO result = userService.getProfile();
			User user = new User();
			user.setId(result.getId());
			user.setName(result.getName());
			user.setEmail(result.getEmail());
			user = userRepository.save(user);
			entity.setUser(user);
			entity = repository.save(entity);


			return new ReviewDTO(entity);

	}
}







