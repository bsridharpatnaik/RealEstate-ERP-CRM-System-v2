package com.ec.common.Model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NotificationHistory.class)
public abstract class NotificationHistory_ {

	public static volatile SingularAttribute<NotificationHistory, Long> historyId;
	public static volatile SingularAttribute<NotificationHistory, String> body;
	public static volatile SingularAttribute<NotificationHistory, String> title;
	public static volatile SingularAttribute<NotificationHistory, Long> userId;

	public static final String HISTORY_ID = "historyId";
	public static final String BODY = "body";
	public static final String TITLE = "title";
	public static final String USER_ID = "userId";

}

