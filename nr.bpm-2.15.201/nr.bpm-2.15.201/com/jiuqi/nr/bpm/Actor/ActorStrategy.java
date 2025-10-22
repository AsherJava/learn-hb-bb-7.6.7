/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.Actor;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategyParameter;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.exception.ActorStrategyParameterMismatched;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ActorStrategy<T extends ActorStrategyParameter> {
    default public String getType() {
        return this.getClass().getName();
    }

    public String getTitle();

    public String getDescription();

    public String serializeParameter(T var1) throws Exception;

    public T readParameter(String var1) throws Exception;

    public Set<String> getActors(BusinessKeyInfo var1, T var2, Task var3);

    default public Set<String> getActors(BusinessKeyInfo businessKey, String parameterText, Task task) {
        T parameter;
        try {
            parameter = this.readParameter(parameterText);
        }
        catch (Exception e) {
            throw new ActorStrategyParameterMismatched(this.getType());
        }
        return this.getActors(businessKey, parameter, task);
    }

    public boolean isUserMatch(BusinessKeyInfo var1, T var2, Actor var3, Task var4);

    default public boolean isUserMatch(BusinessKeyInfo businessKey, String parameterText, Actor actor, Task task) {
        T parameter;
        try {
            parameter = this.readParameter(parameterText);
        }
        catch (Exception e) {
            throw new ActorStrategyParameterMismatched(this.getType());
        }
        return this.isUserMatch(businessKey, parameter, actor, task);
    }

    public boolean isDefault();

    public Class<? extends T> getParameterType();

    public Set<String> getActors(BusinessKeyInfo var1, T var2, UserTask var3);

    default public boolean isBatch() {
        return false;
    }

    default public Map<BusinessKey, Boolean> isBatchUserMatch(List<BusinessKey> businessKeys, String parameterText, Actor actor, Task task) {
        T parameter;
        try {
            parameter = this.readParameter(parameterText);
        }
        catch (Exception e) {
            throw new ActorStrategyParameterMismatched(this.getType());
        }
        return this.isBatchUserMatch(businessKeys, parameter, actor, task);
    }

    default public Map<BusinessKey, Boolean> isBatchUserMatch(List<BusinessKey> businessKeys, T strategyParameter, Actor actor, Task task) {
        return new HashMap<BusinessKey, Boolean>();
    }
}

