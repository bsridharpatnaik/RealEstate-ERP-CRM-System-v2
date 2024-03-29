use businesspark; -- suncitynx,kalpavrish,riddhisiddhi,smartcity,businesspark;
set @dbname='common';

INSERT IGNORE INTO `source`
(
`source_id`,
`source_name`)
VALUES
(111,'Broker');

-- Fetch lead with latest activity by date --

SELECT cl.*,la.* from customer_lead cl
INNER  JOIN LeadActivity la on la.lead_id=cl.lead_id
LEFT OUTER JOIN LeadActivity la2 on
	(cl.lead_id=la2.lead_id AND
		(la.created_at < la2.created_at OR
        (la.created_at = la2.created_at AND la.leadactivity_id<la2.leadactivity_id)))
WHERE la2.leadactivity_id IS NULL
AND cl.is_deleted=false;

-- User Information
set @q=concat('CREATE OR REPLACE view userdetails AS SELECT su.user_id,su.user_name,group_concat(ur.role_name SEPARATOR \',\') as roles FROM ',@dbname,'.security_user su
INNER JOIN ',@dbname,'.user_role ur on ur.user_id = su.user_id
INNER JOIN ',@dbname,'.role r on r.name = ur.role_name WHERE su.status=true
GROUP BY su.user_id,su.user_name;');
PREPARE stmt FROM @q;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

--       FETCH Stagnant Leads --
set @q=concat('CREATE OR REPLACE VIEW stangnantdetails AS
SELECT
assigneename,
       Sum(CASE
             WHEN stagnantdays = \'<10 Days\' THEN c
             ELSE 0
           END) AS \'lessThan10Days\',
       Sum(CASE
             WHEN stagnantdays = \'10-20 Days\' THEN c
             ELSE 0
           END) AS \'tenTo20Days\',
       Sum(CASE
             WHEN stagnantdays = \'20-30 Days\' THEN c
             ELSE 0
           END) AS \'twentyTo30Days\',
       Sum(CASE
             WHEN stagnantdays = \'>30 Days\' THEN c
             ELSE 0
           END) AS \'greaterThan30Days\'
FROM   (SELECT u.user_name       AS assigneeName,
               Count(cl.lead_id) AS c, CASE WHEN Datediff(Now(), la1.updated_at)
       >=10
       AND Datediff(Now(), la1.updated_at)<20 THEN \'10-20 Days\' WHEN Datediff(
       Now(),
       la1.updated_at) >=20 AND Datediff(Now(), la1.updated_at)<30 THEN
       \'20-30 Days\'
       WHEN Datediff(Now(), la1.updated_at) >30 THEN \'>30 Days\' WHEN Datediff(
       Now(),
       la1.updated_at) <10 THEN \'<10 Days\' END AS \'StagnantDays\'
        FROM   customer_lead cl
               INNER JOIN LeadActivity la1
                       ON cl.lead_id = la1.lead_id
               LEFT OUTER JOIN LeadActivity la2
                            ON ( cl.lead_id = la2.lead_id
                                 AND ( la1.updated_at < la2.updated_at
                                        OR ( la1.updated_at = la2.updated_at
                                             AND
                                       la1.leadactivity_id < la2.leadactivity_id
                                           ) )
                               )
               INNER JOIN ',@dbname,'.security_user u
                       ON cl.user_id = u.user_id
        WHERE  cl.is_deleted=0 AND cl.status NOT IN (\'Deal_Lost\',\'Deal_Closed\') AND la2.leadactivity_id IS NULL GROUP BY
       assigneename,
       stagnantdays) AS tx
GROUP  BY assigneename;');

PREPARE stmt FROM @q;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ----- Conversion Ratio  -------


set @q=concat('CREATE OR REPLACE view convertion_ratio AS
SELECT y.user_id AS \'user_id\', su.user_name AS \'asigneeName\',
       totalcount,
       CASE
		WHEN convertedcount IS NULL THEN 0
         ELSE convertedcount END AS convertedcount,
       CASE
         WHEN convertedcount / totalcount * 100 IS NULL THEN 0
         ELSE TRUNCATE(convertedcount / totalcount * 100,2)
       END          AS ratio
FROM   (SELECT cl.user_id,
               Count(DISTINCT cl.lead_id) AS totalCount
        FROM   LeadActivity la
               INNER JOIN customer_lead cl
                       ON cl.lead_id = la.lead_id  AND cl.created_at>=DATE_SUB(LAST_DAY(NOW()),INTERVAL DAY(LAST_DAY(NOW()))-
1 DAY) AND cl.is_deleted != 1 AND la.is_deleted !=1 and la.created_at>=DATE_SUB(LAST_DAY(NOW()),INTERVAL DAY(LAST_DAY(NOW()))-
1 DAY)
        GROUP  BY cl.user_id) AS y
       LEFT JOIN (SELECT cl.user_id,
                         Count(DISTINCT cl.lead_id) AS convertedCount
                  FROM   LeadActivity la
                         INNER JOIN customer_lead cl
                                 ON cl.lead_id = la.lead_id AND cl.is_deleted != 1  AND la.is_deleted !=1
                  WHERE  la.activityType = \'Deal_Close\' AND cl.status=\'Deal_Closed\' AND la.created_at>=DATE_SUB(LAST_DAY(NOW()),INTERVAL DAY(LAST_DAY(NOW()))-
1 DAY)
                  GROUP  BY cl.user_id) AS tx
              ON tx.user_id = y.user_id
       INNER JOIN ',@dbname, '.security_user su
               ON su.user_id = y.user_id;');
PREPARE stmt FROM @q;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;


--       FETCH Stagnant Leads BY Property Type--
CREATE OR REPLACE VIEW stangnant_details_proptype AS
SELECT CASE WHEN propertyType IS NULL THEN 'OTHERS' ELSE propertyType END as 'property_type',
       Sum(CASE
             WHEN stagnantdays = '<10 Days' THEN c
             ELSE 0
           END) AS 'lessThan10Days',
       Sum(CASE
             WHEN stagnantdays = '10-20 Days' THEN c
             ELSE 0
           END) AS 'tenTo20Days',
       Sum(CASE
             WHEN stagnantdays = '20-30 Days' THEN c
             ELSE 0
           END) AS 'twentyTo30Days',
       Sum(CASE
             WHEN stagnantdays = '>30 Days' THEN c
             ELSE 0
           END) AS 'greaterThan30Days'
FROM   (SELECT cl.propertytype   AS propertyType,
               Count(cl.lead_id) AS c,
               CASE WHEN Datediff(Now(), la1.updated_at)>=10
       AND Datediff(Now(), la1.updated_at)<20 THEN '10-20 Days' WHEN Datediff(
       Now(),
       la1.updated_at) >=20 AND Datediff(Now(), la1.updated_at)<30 THEN
       '20-30 Days'
       WHEN Datediff(Now(), la1.updated_at) >30 THEN '>30 Days' WHEN Datediff(
       Now(),
       la1.updated_at) <10 THEN '<10 Days' END AS 'StagnantDays'
        FROM   customer_lead cl
               INNER JOIN LeadActivity la1
                       ON cl.lead_id = la1.lead_id
               LEFT OUTER JOIN LeadActivity la2
                            ON ( cl.lead_id = la2.lead_id
                                 AND ( la1.updated_at < la2.updated_at
                                        OR ( la1.updated_at = la2.updated_at
                                             AND la1.leadactivity_id <
                                                 la2.leadactivity_id ) )
                               )
        WHERE  cl.is_deleted=0 AND cl.status NOT IN ('Deal_Lost', 'Deal_Closed')
       AND
       la2.leadactivity_id IS NULL GROUP BY propertyType, stagnantdays) AS tx
GROUP  BY propertyType;

--    Fetch Conversion Ratio Property Type

CREATE OR REPLACE view convertion_ratio_prop_type AS
SELECT
		y.propertyType AS 'propertyType',
       totalcount,
       CASE
		WHEN convertedcount IS NULL THEN 0
         ELSE convertedcount END AS convertedcount,
       CASE
         WHEN convertedcount / totalcount * 100 IS NULL THEN 0
         ELSE TRUNCATE(convertedcount / totalcount * 100,2)
       END          AS ratio
FROM   (SELECT CASE WHEN cl.propertytype IS NULL THEN 'OTHERS' ELSE  cl.propertytype END           AS propertyType,
       Count(DISTINCT cl.lead_id) AS totalCount
		FROM   LeadActivity la
			INNER JOIN customer_lead cl
               ON cl.lead_id = la.lead_id
                  AND cl.created_at >= Date_sub(Last_day(Now()),
                                       INTERVAL Day(Last_day(Now()))- 1
                                       day)
                  AND cl.is_deleted != 1
                  AND la.is_deleted != 1
                  AND la.created_at >= Date_sub(Last_day(Now()),
                                       INTERVAL Day(Last_day(Now()))- 1 day)
		GROUP  BY cl.propertytype
        ) AS y
       LEFT JOIN (SELECT cl.propertyType as propertyType,
                         Count(DISTINCT cl.lead_id) AS convertedCount
                  FROM   LeadActivity la
                         INNER JOIN customer_lead cl
                                 ON cl.lead_id = la.lead_id AND cl.is_deleted != 1  AND la.is_deleted !=1
                  WHERE  la.activityType = 'Deal_Close' AND cl.status='Deal_Closed' AND la.created_at>=DATE_SUB(LAST_DAY(NOW()),INTERVAL DAY(LAST_DAY(NOW()))-
1 DAY)
                  GROUP  BY cl.propertyType) AS tx
              ON tx.propertyType = y.propertyType;


-- Activities for dashboard
CREATE OR REPLACE VIEW activities_for_dashboard AS
SELECT UUID() as id,'today' as type, su.user_name,COUNT(leadactivity_id) as count FROM LeadActivity la
INNER JOIN customer_lead l on l.lead_id=la.lead_id
INNER JOIN security_user su on su.user_id=l.user_id
WHERE la.is_deleted=0
	AND DATE(la.activity_date_time)=DATE(sysdate())
GROUP BY su.user_name
UNION ALL
-- Tomorrow's Activity
SELECT UUID() as id,'tomorrow' as type, su.user_name,COUNT(leadactivity_id) as count FROM LeadActivity la
INNER JOIN customer_lead l on l.lead_id=la.lead_id
INNER JOIN security_user su on su.user_id=l.user_id
WHERE la.is_deleted=0
	AND DATE(la.activity_date_time)=DATE(DATE_ADD(sysdate(),INTERVAL 1 DAY))
GROUP BY su.user_name
UNION ALL
-- Pending Activity
SELECT UUID() as id,'pending', su.user_name,COUNT(leadactivity_id) as count FROM LeadActivity la
INNER JOIN customer_lead l on l.lead_id=la.lead_id
INNER JOIN security_user su on su.user_id=l.user_id
WHERE la.is_deleted=0
	AND DATE(la.activity_date_time)<DATE(sysdate())
    AND la.isOpen=true
GROUP BY su.user_name
UNION ALL
-- Upcoming
SELECT UUID() as id,'upcoming', su.user_name,COUNT(leadactivity_id) as count FROM LeadActivity la
INNER JOIN customer_lead l on l.lead_id=la.lead_id
INNER JOIN security_user su on su.user_id=l.user_id
WHERE la.is_deleted=0
	AND DATE(la.activity_date_time)>DATE(sysdate())
GROUP BY su.user_name
UNION ALL
-- Live Leads
SELECT UUID() as id,'live', su.user_name,COUNT(l.lead_id) as count FROM customer_lead l
INNER JOIN security_user su on su.user_id=l.user_id
WHERE l.is_deleted=0
	AND l.status NOT IN ('Deal_Closed','Deal_Lost')
GROUP BY su.user_name
UNION ALL
SELECT UUID() as id,'prospect', su.user_name,COUNT(l.lead_id) as count FROM customer_lead l
INNER JOIN security_user su on su.user_id=l.user_id
WHERE l.is_deleted=0
	AND l.status NOT IN ('Deal_Closed','Deal_Lost') AND l.is_prospect_lead=true
GROUP BY su.user_name;

-- Lead stage mapping

CREATE OR REPLACE VIEW lead_stage_agent_mapping AS
SELECT
	user_name,
    SUM(CASE WHEN l.status='New_Lead' THEN 1 ELSE 0 END) as new_lead ,
    SUM(CASE WHEN l.status='Visit_Scheduled' THEN 1 ELSE 0 END) as visit_scheduled,
    SUM(CASE WHEN l.status='Visit_Completed' THEN 1 ELSE 0 END) as visit_completed,
    SUM(CASE WHEN l.status='Negotiation' THEN 1 ELSE 0 END) as negotiation,
    SUM(CASE WHEN l.status='Deal_Lost' THEN 1 ELSE 0 END) as deal_lost,
    SUM(CASE WHEN l.status='Deal_Closed' THEN 1 ELSE 0 END) as deal_closed
FROM customer_lead l
INNER JOIN security_user su on su.user_id=l.user_id
WHERE l.is_deleted=0
GROUP BY su.user_name;