package com.morawicz.backendcodingchallenge.repositories;

import org.springframework.data.repository.CrudRepository;

import com.morawicz.backendcodingchallenge.entities.CityEntity;

public interface CityRepository extends CrudRepository<CityEntity, Long> {

}
