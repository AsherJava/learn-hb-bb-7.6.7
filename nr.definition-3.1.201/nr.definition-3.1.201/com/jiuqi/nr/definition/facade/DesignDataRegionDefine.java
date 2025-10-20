/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.util.LevelSetting;
import java.util.Date;

public interface DesignDataRegionDefine
extends DataRegionDefine {
    public void setUpdateTime(Date var1);

    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setCode(String var1);

    public void setFormKey(String var1);

    public void setRegionLeft(int var1);

    public void setRegionRight(int var1);

    public void setRegionTop(int var1);

    public void setRegionBottom(int var1);

    public void setRegionKind(DataRegionKind var1);

    @Deprecated
    public void setBindingExpression(String var1);

    public void setInputOrderFieldKey(String var1);

    public void setSortFieldsList(String var1);

    public void setRowsInFloatRegion(int var1);

    public void setGatherFields(String var1);

    @Deprecated
    public void setGatherSetting(String var1);

    public void setLevelSetting(LevelSetting var1);

    public void setShowGatherDetailRows(boolean var1);

    public void setShowGatherDetailRowByOne(boolean var1);

    public void setShowGatherSummaryRow(boolean var1);

    public void setShowAddress(String var1);

    public void setRegionSettingKey(String var1);

    @Deprecated
    public void setCardInputFormKey(String var1);

    public void setFilterCondition(String var1);

    public void setMaxRowCount(int var1);

    public void setCanDeleteRow(boolean var1);

    public void setCanInsertRow(boolean var1);

    public void setPageSize(int var1);

    @Deprecated
    public void setMasterEntitiesKey(String var1);

    public void setReadOnlyCondition(String var1);

    public void setAllowDuplicateKey(boolean var1);

    public void setIsCanFold(boolean var1);

    public void setHideZeroGatherFields(String var1);

    @Deprecated
    public void setBizKeyFields(String var1);

    public void setRegionEnterNext(RegionEnterNext var1);

    public void setDisplayLevel(String var1);
}

