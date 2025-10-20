/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.ruler.intf;

import com.jiuqi.va.biz.ruler.common.consts.CheckState;
import com.jiuqi.va.biz.ruler.common.consts.FormulaType;
import java.util.UUID;

public interface Formula {
    public UUID getId();

    public String getName();

    public String getTitle();

    public String getExpression();

    public Object getCompiledExpression();

    public FormulaType getFormulaType();

    public String getCheckMessage();

    public Object getCompiledCheckMessage();

    public boolean isUsed();

    public String getGroup();

    public CheckState getCheckState();

    public String getObjectType();

    public UUID getObjectId();

    public String getPropertyType();

    default public boolean isExtend() {
        return false;
    }
}

