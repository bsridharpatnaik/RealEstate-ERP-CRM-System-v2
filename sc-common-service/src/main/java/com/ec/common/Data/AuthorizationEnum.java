package com.ec.common.Data;

import com.ec.ReusableClasses.DynamicAuthorizationEnumJsonSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using= DynamicAuthorizationEnumJsonSerializer.class)
public enum AuthorizationEnum {
    ReadOnly,
    FullAccess;

    public AuthorizationEnum setFromString(String name) {
        return AuthorizationEnum.valueOf(name);
    }
}
