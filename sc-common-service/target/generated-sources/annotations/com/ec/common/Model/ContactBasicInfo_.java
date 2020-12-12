package com.ec.common.Model;

import com.ec.common.Data.CustomerTypeEnum;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContactBasicInfo.class)
public abstract class ContactBasicInfo_ extends com.ec.ReusableClasses.ReusableFields_ {

	public static volatile SingularAttribute<ContactBasicInfo, Address> address;
	public static volatile SingularAttribute<ContactBasicInfo, Long> contactId;
	public static volatile SingularAttribute<ContactBasicInfo, String> name;
	public static volatile SingularAttribute<ContactBasicInfo, String> emailId;
	public static volatile SingularAttribute<ContactBasicInfo, CustomerTypeEnum> contactType;
	public static volatile SingularAttribute<ContactBasicInfo, String> mobileNo;

	public static final String ADDRESS = "address";
	public static final String CONTACT_ID = "contactId";
	public static final String NAME = "name";
	public static final String EMAIL_ID = "emailId";
	public static final String CONTACT_TYPE = "contactType";
	public static final String MOBILE_NO = "mobileNo";

}

