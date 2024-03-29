CREATE TABLE boqupload (
   id int NOT NULL AUTO_INCREMENT,
   createdBy varchar(255) DEFAULT NULL,
   creationDate datetime DEFAULT NULL,
   is_deleted tinyint(1) DEFAULT '1',
   lastModifiedBy varchar(255) DEFAULT NULL,
   lastModifiedDate datetime DEFAULT NULL,
   changes varchar(255) DEFAULT NULL,
   quantity double NOT NULL,
   sno int NOT NULL,
   buildingTypeId bigint NOT NULL,
   locationId bigint DEFAULT NULL,
   productId bigint DEFAULT NULL,
   usageLocationId bigint DEFAULT NULL,
   PRIMARY KEY (id),
   KEY FKbow7o7l4vmpturl5xeuuf2u7s (buildingTypeId),
   KEY FK5il96sotrly6t1yl541u5pcep (locationId),
   KEY FKhxsmk5h2aha23tt63uxciiwdm (productId),
   KEY FKc9w98ukkv3vff1cls2gqfpgjr (usageLocationId),
   CONSTRAINT FK5il96sotrly6t1yl541u5pcep FOREIGN KEY (locationId) REFERENCES usage_area (usageAreaId),
   CONSTRAINT FKbow7o7l4vmpturl5xeuuf2u7s FOREIGN KEY (buildingTypeId) REFERENCES building_type (typeId),
   CONSTRAINT FKc9w98ukkv3vff1cls2gqfpgjr FOREIGN KEY (usageLocationId) REFERENCES usage_location (locationId),
   CONSTRAINT FKhxsmk5h2aha23tt63uxciiwdm FOREIGN KEY (productId) REFERENCES product (productId)
 ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
Gariox Arti, [Aug 1, 2022 at 9:19 PM]
CREATE TABLE boqupload_aud (
   id int NOT NULL,
   REV int NOT NULL,
   REVTYPE tinyint DEFAULT NULL,
   changes varchar(255) DEFAULT NULL,
   quantity double DEFAULT NULL,
   sno int DEFAULT NULL,
   buildingTypeId bigint DEFAULT NULL,
   locationId bigint DEFAULT NULL,
   productId bigint DEFAULT NULL,
   usageLocationId bigint DEFAULT NULL,
   PRIMARY KEY (id,REV),
   KEY FKfkx4aharjkp0awrb0auh3m7br (REV),
   CONSTRAINT FKfkx4aharjkp0awrb0auh3m7br FOREIGN KEY (REV) REFERENCES revinfo (REV)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci