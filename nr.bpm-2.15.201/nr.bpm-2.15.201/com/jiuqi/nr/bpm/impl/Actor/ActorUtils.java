/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.Actor;

import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.Actor.ActorStrategy;
import com.jiuqi.nr.bpm.Actor.ActorStrategyInstance;
import com.jiuqi.nr.bpm.Actor.ActorStrategyProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeyInfo;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActorUtils {
    static boolean isTesting = false;

    public static Set<String> getTaskActors(UserTask userTask, BusinessKey businessKey, ActorStrategyProvider actorStrategyProvider, Task task) {
        if (isTesting) {
            return Collections.emptySet();
        }
        HashSet<String> actorIds = new HashSet<String>();
        for (ActorStrategyInstance actorStrategyInstance : userTask.getActorStrategies()) {
            ActorStrategy<?> actorStrategy = actorStrategyProvider.getActorStrategyByType(actorStrategyInstance.getType());
            actorIds.addAll(actorStrategy.getActors((BusinessKeyInfo)businessKey, actorStrategyInstance.getParameterJson(), task));
        }
        return actorIds;
    }

    public static boolean isTaskActor(UserTask userTask, BusinessKey businessKey, Actor actor, ActorStrategyProvider actorStrategyProvider, Task task) {
        if (isTesting) {
            return true;
        }
        for (ActorStrategyInstance actorStrategyInstance : userTask.getActorStrategies()) {
            ActorStrategy<?> actorStrategy = actorStrategyProvider.getActorStrategyByType(actorStrategyInstance.getType());
            if (!actorStrategy.isUserMatch((BusinessKeyInfo)businessKey, actorStrategyInstance.getParameterJson(), actor, task)) continue;
            return true;
        }
        return false;
    }

    public static Set<String> setToLow(Set<String> elements) {
        HashSet<String> result = new HashSet<String>();
        for (String element : elements) {
            result.add(element.toLowerCase());
        }
        return result;
    }

    public static Collection<String> listToLow(Collection<String> elements) {
        ArrayList<String> result = new ArrayList<String>();
        for (String element : elements) {
            result.add(element.toLowerCase());
        }
        return result;
    }

    public static Map<String, Boolean> isBatchTaskActor(UserTask userTask, List<BusinessKey> businessKeys, Actor actor, ActorStrategyProvider actorStrategyProvider, Task task, boolean isDefault) {
        HashMap<String, Boolean> result = new HashMap<String, Boolean>();
        if (isTesting) {
            return new HashMap<String, Boolean>();
        }
        ArrayList batchTaskActors = new ArrayList();
        for (ActorStrategyInstance actorStrategyInstance : userTask.getActorStrategies()) {
            ActorStrategy<?> actorStrategy = actorStrategyProvider.getActorStrategyByType(actorStrategyInstance.getType());
            if (actorStrategy.isBatch()) {
                Map<BusinessKey, Boolean> batchUserMatch = actorStrategy.isBatchUserMatch(businessKeys, actorStrategyInstance.getParameterJson(), actor, task);
                for (Map.Entry<BusinessKey, Boolean> entry : batchUserMatch.entrySet()) {
                    String businessStr = BusinessKeyFormatter.formatToString(entry.getKey());
                    result.put(businessStr, entry.getValue());
                }
                return result;
            }
            for (BusinessKey businessKey : businessKeys) {
                if (!actorStrategy.isUserMatch((BusinessKeyInfo)businessKey, actorStrategyInstance.getParameterJson(), actor, task)) continue;
                String businessStr = BusinessKeyFormatter.formatToString(businessKey);
                result.put(businessStr, true);
            }
        }
        return result;
    }
}

