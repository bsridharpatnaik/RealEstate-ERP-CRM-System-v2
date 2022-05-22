use common;
INSERT INTO `tenant` VALUES ('suncitynx',true,true,'SUNCITY NX');
INSERT INTO `tenant` VALUES ('kalpavrish',false,true,'KALPAVRISH');
INSERT INTO `tenant` VALUES ('riddhisiddhi',false,true,'RIDDHI SIDDHI');
INSERT INTO `tenant` VALUES ('smartcity',false,true,'SMART CITY');
INSERT INTO `tenant` VALUES ('businesspark',false,true,'BUSINESS PARK');
INSERT INTO `tenant` VALUES ('drgtrdcntr',false,true,'DURG TRADE CENTER');

INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-101,'ReadOnly','suncitynx');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-102,'ReadOnly','kalpavrish');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-103,'ReadOnly','riddhisiddhi');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-104,'ReadOnly','businesspark');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-105,'ReadOnly','smartcity');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-106,'ReadOnly','drgtrdcntr');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-107,'FullAccess','suncitynx');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-108,'FullAccess','kalpavrish');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-109,'FullAccess','riddhisiddhi');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-110,'FullAccess','businesspark');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-111,'FullAccess','smartcity');
INSERT INTO `common`.`tenant_authorization_mapping` VALUES (-112,'FullAccess','drgtrdcntr');

INSERT INTO `common`.`user_tenant_mapping` VALUES (4,-104);
INSERT INTO `common`.`user_tenant_mapping` VALUES (4,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (4,-108);
INSERT INTO `common`.`user_tenant_mapping` VALUES (4,-105);
INSERT INTO `common`.`user_tenant_mapping` VALUES (4,-101);
INSERT INTO `common`.`user_tenant_mapping` VALUES (2,-107);
INSERT INTO `common`.`user_tenant_mapping` VALUES (2,-108);
INSERT INTO `common`.`user_tenant_mapping` VALUES (2,-111);
INSERT INTO `common`.`user_tenant_mapping` VALUES (2,-110);
INSERT INTO `common`.`user_tenant_mapping` VALUES (2,-112);
INSERT INTO `common`.`user_tenant_mapping` VALUES (19,-104);
INSERT INTO `common`.`user_tenant_mapping` VALUES (19,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (19,-102);
INSERT INTO `common`.`user_tenant_mapping` VALUES (19,-105);
INSERT INTO `common`.`user_tenant_mapping` VALUES (19,-101);
INSERT INTO `common`.`user_tenant_mapping` VALUES (3,-107);
INSERT INTO `common`.`user_tenant_mapping` VALUES (3,-108);
INSERT INTO `common`.`user_tenant_mapping` VALUES (3,-111);
INSERT INTO `common`.`user_tenant_mapping` VALUES (3,-110);
INSERT INTO `common`.`user_tenant_mapping` VALUES (3,-112);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10,-104);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10,-102);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10,-105);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10,-101);
INSERT INTO `common`.`user_tenant_mapping` VALUES (6,-110);
INSERT INTO `common`.`user_tenant_mapping` VALUES (6,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (6,-102);
INSERT INTO `common`.`user_tenant_mapping` VALUES (6,-105);
INSERT INTO `common`.`user_tenant_mapping` VALUES (6,-101);
INSERT INTO `common`.`user_tenant_mapping` VALUES (5,-104);
INSERT INTO `common`.`user_tenant_mapping` VALUES (5,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (5,-109);
INSERT INTO `common`.`user_tenant_mapping` VALUES (5,-105);
INSERT INTO `common`.`user_tenant_mapping` VALUES (5,-101);
INSERT INTO `common`.`user_tenant_mapping` VALUES (1,-107);
INSERT INTO `common`.`user_tenant_mapping` VALUES (1,-108);
INSERT INTO `common`.`user_tenant_mapping` VALUES (1,-111);
INSERT INTO `common`.`user_tenant_mapping` VALUES (1,-110);
INSERT INTO `common`.`user_tenant_mapping` VALUES (1,-112);
INSERT INTO `common`.`user_tenant_mapping` VALUES (11,-104);
INSERT INTO `common`.`user_tenant_mapping` VALUES (11,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (11,-102);
INSERT INTO `common`.`user_tenant_mapping` VALUES (11,-111);
INSERT INTO `common`.`user_tenant_mapping` VALUES (11,-101);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10001,-107);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10001,-108);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10001,-111);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10001,-110);
INSERT INTO `common`.`user_tenant_mapping` VALUES (10001,-112);
INSERT INTO `common`.`user_tenant_mapping` VALUES (8,-105);
INSERT INTO `common`.`user_tenant_mapping` VALUES (8,-101);
INSERT INTO `common`.`user_tenant_mapping` VALUES (18,-104);
INSERT INTO `common`.`user_tenant_mapping` VALUES (18,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (17,-104);
INSERT INTO `common`.`user_tenant_mapping` VALUES (17,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (17,-102);
INSERT INTO `common`.`user_tenant_mapping` VALUES (17,-105);
INSERT INTO `common`.`user_tenant_mapping` VALUES (17,-107);
INSERT INTO `common`.`user_tenant_mapping` VALUES (13,-104);
INSERT INTO `common`.`user_tenant_mapping` VALUES (13,-106);
INSERT INTO `common`.`user_tenant_mapping` VALUES (13,-102);
INSERT INTO `common`.`user_tenant_mapping` VALUES (13,-105);
INSERT INTO `common`.`user_tenant_mapping` VALUES (13,-107);
