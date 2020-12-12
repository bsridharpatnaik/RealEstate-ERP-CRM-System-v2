package com.ec.common.Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> userName;
	public static volatile SingularAttribute<User, Boolean> passwordExpired;
	public static volatile SingularAttribute<User, Long> userId;
	public static volatile SingularAttribute<User, Boolean> status;

	public static final String PASSWORD = "password";
	public static final String ROLES = "roles";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD_EXPIRED = "passwordExpired";
	public static final String USER_ID = "userId";
	public static final String STATUS = "status";

}

