package com.devsuperior.movieflix.projections;

public interface MovieProjection extends IdProjection<Long> {

    @Override
    Long getId();
}
