package com.ec.application.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ec.application.SoftDelete.BaseRepository;
import com.ec.application.model.Machinery;

@Repository
public interface MachineryRepo extends BaseRepository<Machinery, Long>
{

	boolean existsByMachineryName(String machineryName);

	ArrayList<Machinery> findBymachineryName(String machineryName);

	@Query(value="SELECT m from Machinery m where machineryName LIKE %:name%")
	ArrayList<Machinery> findByPartialName(@Param("name") String name);
}
