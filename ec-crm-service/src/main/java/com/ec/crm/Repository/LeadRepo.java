package com.ec.crm.Repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ec.crm.Model.Lead;
import com.ec.crm.ReusableClasses.BaseRepository;

@Repository
public interface LeadRepo extends BaseRepository<Lead, Long>, JpaSpecificationExecutor<Lead> {
	@Query(value="SELECT count(*) from Lead m where primaryMobile=:mobileNo")
	int findCountByPMobileNo(@Param("mobileNo")String mobileNo);
	
	@Query(value="SELECT count(*) from Lead m where secondaryMobile=:mobileNo")
	int findCountBySMobileNo(@Param("mobileNo")String mobileNo);
}
