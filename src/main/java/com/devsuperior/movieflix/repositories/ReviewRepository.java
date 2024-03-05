package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.projections.ReviewProjection;
import com.devsuperior.movieflix.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.movieflix.entities.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(nativeQuery = true, value = """
			SELECT tb_user.id, tb_user.email AS username
			FROM tb_user
			WHERE tb_user.email = :email
		""")
    List<UserDetailsProjection> searchUser(String email);

}
