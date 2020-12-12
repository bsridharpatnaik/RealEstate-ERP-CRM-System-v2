package com.ec.ReusableClasses;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ReusableFields.class)
public abstract class ReusableFields_ {

	public static volatile SingularAttribute<ReusableFields, Boolean> isDeleted;
	public static volatile SingularAttribute<ReusableFields, Date> created;
	public static volatile SingularAttribute<ReusableFields, Date> modified;

	public static final String IS_DELETED = "isDeleted";
	public static final String CREATED = "created";
	public static final String MODIFIED = "modified";

}

