/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;

public interface TaskLinkOrgMappingDefine {
    public String getTaskLinkKey();

    public String getTargetEntity();

    public String getSourceEntity();

    public TaskLinkMatchingType getMatchingType();

    public String getTargetFormula();

    public String getSourceFormula();

    public TaskLinkExpressionType getExpressionType();

    public String getOrder();
}

