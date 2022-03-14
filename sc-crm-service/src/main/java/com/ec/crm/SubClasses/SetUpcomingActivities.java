package com.ec.crm.SubClasses;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

import com.ec.crm.Data.ActivitiesForDashboard;
import com.ec.crm.Enums.InstanceEnum;
import com.ec.crm.Model.ActivitiesStatsForDashboard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ec.crm.Data.MapForPipelineAndActivities;
import com.ec.crm.Data.PipelineAndActivitiesForDashboard;
import com.ec.crm.Model.LeadActivity;
import com.ec.crm.ReusableClasses.ReusableMethods;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SetUpcomingActivities implements Runnable {
    private CyclicBarrier barrier;
    private ActivitiesForDashboard dashboardPipelineReturnData;
    private List<ActivitiesStatsForDashboard> data;
    Logger log = LoggerFactory.getLogger(SetUpcomingActivities.class);

    public SetUpcomingActivities(CyclicBarrier barrier, ActivitiesForDashboard dashboardPipelineReturnData,
                                 List<ActivitiesStatsForDashboard> data) {
        this.dashboardPipelineReturnData = dashboardPipelineReturnData;
        this.barrier = barrier;
        this.data = data;
    }

    @Override
    public void run() {

        log.info("Fetching stats for SetUpcomingActivities");
        dashboardPipelineReturnData
                .setUpcomingActivities(new MapForPipelineAndActivities(data.stream().filter(e -> e.getType().equals("upcoming")).mapToLong(i -> i.getCount()).sum()
                        , data.stream().filter(e -> e.getType().equals("upcoming")).collect(Collectors.toList())));
        log.info("Completed stats for SetUpcomingActivities");
        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
