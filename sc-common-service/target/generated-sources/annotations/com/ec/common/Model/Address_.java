package com.ec.common.Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Address.class)
public abstract class Address_ extends com.ec.ReusableClasses.ReusableFields_ {

	public static volatile SingularAttribute<Address, String> zip;
	public static volatile SingularAttribute<Address, String> addr_line1;
	public static volatile SingularAttribute<Address, String> city;
	public static volatile SingularAttribute<Address, Long> addrId;
	public static volatile SingularAttribute<Address, String> addr_line2;
	public static volatile SingularAttribute<Address, String> state;

	public static final String ZIP = "zip";
	public static final String ADDR_LINE1 = "addr_line1";
	public static final String CITY = "city";
	public static final String ADDR_ID = "addrId";
	public static final String ADDR_LINE2 = "addr_line2";
	public static final String STATE = "state";

}

