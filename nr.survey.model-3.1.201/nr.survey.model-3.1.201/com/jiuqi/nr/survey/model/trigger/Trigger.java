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
package com.jiuqi.nr.survey.model.trigger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.nr.survey.model.trigger.TriggerComplete;
import com.jiuqi.nr.survey.model.trigger.TriggerCopyValue;
import com.jiuqi.nr.survey.model.trigger.TriggerRunExpression;
import com.jiuqi.nr.survey.model.trigger.TriggerSetValue;
import com.jiuqi.nr.survey.model.trigger.TriggerSkip;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.EXISTING_PROPERTY, property="type", visible=true)
@JsonSubTypes(value={@JsonSubTypes.Type(value=TriggerRunExpression.class, name="runexpression"), @JsonSubTypes.Type(value=TriggerComplete.class, name="complete"), @JsonSubTypes.Type(value=TriggerSetValue.class, name="setvalue"), @JsonSubTypes.Type(value=TriggerCopyValue.class, name="copyvalue"), @JsonSubTypes.Type(value=TriggerSkip.class, name="skip")})
public class Trigger {
    private String type;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

