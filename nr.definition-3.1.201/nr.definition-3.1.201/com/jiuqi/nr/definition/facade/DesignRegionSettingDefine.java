/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionSettingDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.util.RecordCard;
import java.util.Date;
import java.util.List;

public interface DesignRegionSettingDefine
extends RegionSettingDefine {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setCollapseCellIndex(String var1);

    public void setDictionaryFillLinks(String var1);

    public void setDefaultRowSetting(String var1);

    public void setRegionTabSetting(List<RegionTabSettingDefine> var1);

    public void setLastRowStyle(List<RegionEdgeStyleDefine> var1);

    public void setLastColumnStyle(List<RegionEdgeStyleDefine> var1);

    public void setRowNumberSetting(List<RowNumberSetting> var1);

    public void setCardRecord(RecordCard var1);

    public void setEntityDefaultValue(String var1);

    public void setRegionSurvey(String var1);
}

