package com.ec.crm.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ec.crm.Model.PaymentSchedule;
import com.ec.crm.ReusableClasses.BaseRepository;

@Repository
public interface PaymentScheduleRepo extends BaseRepository<PaymentSchedule, Long>
{
	@Query("select ps from PaymentSchedule ps where ps.ds.dealId=:dealId")
	List<PaymentSchedule> getSchedulesForDeal(@Param("dealId") Long dealId);
}
