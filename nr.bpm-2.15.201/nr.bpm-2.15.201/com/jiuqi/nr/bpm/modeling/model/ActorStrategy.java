/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 */
package com.jiuqi.nr.bpm.modeling.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.Actor.JsonUtils;
import com.jiuqi.nr.bpm.modeling.model.ActivitiExtension;
import org.springframework.util.Assert;

public class ActorStrategy
extends ActivitiExtension {
    public ActorStrategy() {
        super("actorStrategy");
    }

    public void setType(Class<?> clazz) {
        Assert.notNull(clazz, "'strategyType' must not be null.");
        super.setProperty("type", clazz.getName());
    }

    public void setParameterText(String paramText) {
        super.setProperty("param", paramText);
    }

    public void setParameter(ActorStrategyParameter param) {
        try {
            this.setParameterText(JsonUtils.toString(param));
        }
        catch (JsonProcessingException e) {
            throw new BpmException("generate actor strategy error.", e);
        }
    }
}

