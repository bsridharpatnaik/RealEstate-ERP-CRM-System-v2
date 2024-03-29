package com.ec.application.model;

import lombok.Data;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Subselect("select * from inward_stats")
@Immutable
@Audited
@Data
public class InwardInventoryStatsForDashboardV2 {
    @Id
    @Column(name="supplier_name")
    String supplierName;

    @Column(name="inward_count")
    Double inwardCount;

    @Column(name="avg_lead_time")
    Double avgLeadTime;

    @Column(name="rejectCount")
    Double rejectCount;
}
