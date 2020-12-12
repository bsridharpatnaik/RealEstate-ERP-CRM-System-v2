package com.ec.common.Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AndroidTokenDetails.class)
public abstract class AndroidTokenDetails_ extends com.ec.ReusableClasses.ReusableFields_ {

	public static volatile SingularAttribute<AndroidTokenDetails, Long> tokenId;
	public static volatile SingularAttribute<AndroidTokenDetails, User> user;
	public static volatile SingularAttribute<AndroidTokenDetails, String> token;

	public static final String TOKEN_ID = "tokenId";
	public static final String USER = "user";
	public static final String TOKEN = "token";

}

