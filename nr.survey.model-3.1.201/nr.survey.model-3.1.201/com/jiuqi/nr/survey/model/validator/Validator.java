/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonSubTypes
 *  com.fasterxml.jackson.annotation.JsonSubTypes$Type
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 */
package com.jiuqi.nr.survey.model.validator;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.survey.model.validator.ValidatorEmail;
import com.jiuqi.nr.survey.model.validator.ValidatorExpression;
import com.jiuqi.nr.survey.model.validator.ValidatorNumeric;
import com.jiuqi.nr.survey.model.validator.ValidatorRegex;
import com.jiuqi.nr.survey.model.validator.ValidatorText;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="type", visible=true)
@JsonSubTypes(value={@JsonSubTypes.Type(value=ValidatorExpression.class, name="expression"), @JsonSubTypes.Type(value=ValidatorNumeric.class, name="numeric"), @JsonSubTypes.Type(value=ValidatorText.class, name="text"), @JsonSubTypes.Type(value=ValidatorRegex.class, name="regex"), @JsonSubTypes.Type(value=ValidatorEmail.class, name="email")})
public class Validator {
    private String type;
    private String text;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

