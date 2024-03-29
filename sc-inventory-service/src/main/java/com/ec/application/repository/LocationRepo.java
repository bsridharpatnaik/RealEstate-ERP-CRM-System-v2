package com.ec.application.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ec.application.ReusableClasses.BaseRepository;
import com.ec.application.ReusableClasses.IdNameProjections;
import com.ec.application.model.UsageLocation;

@Repository
public interface LocationRepo extends BaseRepository<UsageLocation, Long>
{
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	UsageLocation save(UsageLocation entity);

	boolean existsByLocationName(String locationName);

	ArrayList<UsageLocation> findByLocationName(String locationName);

	@Query(value = "SELECT m from UsageLocation m where locationName LIKE %:name%")
	ArrayList<UsageLocation> findByPartialName(@Param("name") String name);

	@Query(value = "SELECT locationId as id,locationName as name from UsageLocation m  order by name")
	List<IdNameProjections> findIdAndNames();

	@Query(value = "SELECT locationName from UsageLocation m order by locationName")
	List<String> getNames();

	@Query(value = "SELECT locationName from UsageLocation m where locationName like %:name% order by locationName")
	List<String> getNamesForTypeAhead(@Param("name") String name);

	@Query(value = "SELECT count(m) from UsageLocation m where m.buildingType.typeId=:typeId")
	int getBuildingTypeUsageCount(@Param("typeId") Long typeId);

	@Query(value = "SELECT locationId as id,locationName as name from UsageLocation m where m.buildingType.typeId=:typeId order by name")
	List<IdNameProjections> getByBuildingType(@Param("typeId") Long typeId);

	UsageLocation findByLocationId(long buildingUnit);

	List<UsageLocation> findByBuildingTypeTypeId(long businessTypeId);

}
