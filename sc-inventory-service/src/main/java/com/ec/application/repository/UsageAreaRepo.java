package com.ec.application.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ec.application.ReusableClasses.BaseRepository;
import com.ec.application.ReusableClasses.IdNameProjections;
import com.ec.application.model.OutwardInventory;
import com.ec.application.model.UsageArea;

@Repository
public interface UsageAreaRepo extends BaseRepository<UsageArea, Long>
{

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	UsageArea save(UsageArea entity);
	
	boolean existsByUsageAreaName(String usageAreaName);

	ArrayList<UsageArea> findByusageAreaName(String usageAreaName);

	@Query(value="SELECT m from UsageArea m where usageAreaName LIKE %:name%")
	ArrayList<UsageArea> findByPartialName(@Param("name") String name);

	@Query(value="SELECT usageAreaId as id,usageAreaName as name from UsageArea m  order by name")
	List<IdNameProjections> findIdAndNames();

	@Query(value="SELECT usageAreaName as name from UsageArea m order by usageAreaName")
	List<String> getNames();

	@Query(value="SELECT count(m) from UsageArea m where m.usageAreaId=:id")
	int usageAreaUsageCount(@Param("id")Long id);

	UsageArea findByUsageAreaName(String location);

	UsageArea findByUsageAreaId(long usageAreaId);

	boolean existsByUsageAreaNameAndIsDeleted(String location, boolean b);

	
}
