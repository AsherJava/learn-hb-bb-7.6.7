/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent
 */
package com.jiuqi.nr.workflow2.engine.common.definition.model;

import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserActionEvent;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;

public class UserActionEvent
implements IUserActionEvent {
    private String alias;
    private String defnitionId;
    private String settings;

    public UserActionEvent(String alias, String defnitionId, String settings) {
        if (StringUtils.isEmpty(defnitionId)) {
            throw new ProcessDefinitionException("jiuqi.nr.default", null, "UserActionEvent definitionId must not be empty.");
        }
        this.alias = alias;
        this.defnitionId = defnitionId;
        this.settings = settings;
    }

    public String getDefinitionId() {
        return this.defnitionId;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getSettings() {
        return this.settings;
    }
}

