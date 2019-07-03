package com.kgate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kgate.model.Country;
@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{

}
