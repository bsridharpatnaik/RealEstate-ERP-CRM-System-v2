package com.ec.application.model;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TransactionTypeForAuthorization
{
	Inward, Outward, MOR, LostDamaged;

	public TransactionTypeForAuthorization setFromString(String name)
	{
		return TransactionTypeForAuthorization.valueOf(name);
	}

	public static List<String> getValidModes()
	{
		List<String> validModes = new ArrayList<String>();
		EnumSet.allOf(TransactionTypeForAuthorization.class).forEach(type -> validModes.add(type.toString()));
		return validModes;
	}
}
