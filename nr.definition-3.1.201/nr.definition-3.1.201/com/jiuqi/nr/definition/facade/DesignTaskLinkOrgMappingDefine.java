/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingDefine;

public interface DesignTaskLinkOrgMappingDefine
extends TaskLinkOrgMappingDefine {
    public void setTaskLinkKey(String var1);

    public void setSourceEntity(String var1);

    public void setTargetEntity(String var1);

    public void setMatchingType(TaskLinkMatchingType var1);

    public void setTargetFormula(String var1);

    public void setSourceFormula(String var1);

    public void setExpressionType(TaskLinkExpressionType var1);

    public void setOrder(String var1);
}

