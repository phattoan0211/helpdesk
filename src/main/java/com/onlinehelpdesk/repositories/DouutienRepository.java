package com.onlinehelpdesk.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.onlinehelpdesk.entities.Douutien;

@Repository
public interface DouutienRepository extends CrudRepository<Douutien, Integer> {
	@Query("from Douutien where madouutien = :madouutien")
	public Douutien findmadouutien(@Param("madouutien") int madouutien);


}
