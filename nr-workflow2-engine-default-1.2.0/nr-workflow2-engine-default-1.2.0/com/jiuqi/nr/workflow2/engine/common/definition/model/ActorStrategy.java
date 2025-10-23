/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IActorStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;

public class ActorStrategy
implements IActorStrategy {
    private String actorStrategyDefnitionId;
    private String parameter;

    public ActorStrategy(String actorStrategyDefnitionId, String parameter) {
        if (StringUtils.isEmpty(actorStrategyDefnitionId)) {
            throw new ProcessDefinitionException("jiuqi.nr.default", null, ErrorCode.UNKNOW, "ActorStrategy definitionId must not be empty.");
        }
        this.actorStrategyDefnitionId = actorStrategyDefnitionId;
        this.parameter = parameter;
    }

    public String getDefinitionId() {
        return this.actorStrategyDefnitionId;
    }

    public String getParameter() {
        return this.parameter;
    }
}

