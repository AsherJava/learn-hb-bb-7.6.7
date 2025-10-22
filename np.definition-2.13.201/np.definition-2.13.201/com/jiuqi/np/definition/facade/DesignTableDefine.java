/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.np.definition.facade.DesignUniversalTableDefine;

public interface DesignTableDefine
extends DesignUniversalTableDefine {
    public void setDataAreaSetting(String var1);

    public void setHugeRecord(boolean var1);

    public void setSupportDatedVersion(boolean var1);

    public void setGatherType(TableGatherType var1);

    public void setPeriodFieldID(String var1);

    public void setDictTreeStruct(String var1);

    public void setDimensionName(String var1);

    public void setDimensionList(String var1);

    public void setIndexKeys(String var1);

    public void setIndexKeys(String[] var1);

    public void setEntityMasterKeys(String var1);

    public void setTitleAbbreviation(String var1);

    public void setTableType(String var1);

    public void setGatherFields(String var1);
}

