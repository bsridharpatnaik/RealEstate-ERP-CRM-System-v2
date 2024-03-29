package com.ec.application.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ec.application.ReusableClasses.BaseRepository;
import com.ec.application.ReusableClasses.IdNameProjections;
import com.ec.application.model.Machinery;
import com.ec.application.model.MachineryOnRent;

@Repository
public interface MachineryRepo extends BaseRepository<Machinery, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Machinery save(Machinery entity);

    boolean existsByMachineryName(String machineryName);

    ArrayList<Machinery> findBymachineryName(String machineryName);

    @Query(value = "SELECT m from Machinery m where machineryName LIKE %:name%")
    ArrayList<Machinery> findByPartialName(@Param("name") String name);

    @Query(value = "SELECT machineryId as id,machineryName as name from Machinery m order by name")
    List<IdNameProjections> findIdAndNames();

    @Query(value = "SELECT machineryName from Machinery m order by machineryName")
    List<String> getNames();

}
