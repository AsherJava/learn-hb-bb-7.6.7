/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonTypeInfo
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$As
 *  com.fasterxml.jackson.annotation.JsonTypeInfo$Id
 *  com.jiuqi.np.definition.facade.IMetaItem
 */
package com.jiuqi.nr.definition.facade;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import com.jiuqi.nr.definition.facade.RegionTabSettingDefine;
import com.jiuqi.nr.definition.facade.RowNumberSetting;
import com.jiuqi.nr.definition.util.EntityDefaultValue;
import com.jiuqi.nr.definition.util.RecordCard;
import java.util.List;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface RegionSettingDefine
extends IMetaItem {
    public String getCollapseCellIndex();

    public String getDictionaryFillLinks();

    public String getDefaultRowSetting();

    public List<RegionTabSettingDefine> getRegionTabSetting();

    public List<RowNumberSetting> getRowNumberSetting();

    public List<RegionEdgeStyleDefine> getLastRowStyles();

    public List<RegionEdgeStyleDefine> getLastColumnStyle();

    public RecordCard getCardRecord();

    public List<EntityDefaultValue> getEntityDefaultValue();

    default public String getRegionSurvey() {
        return null;
    }
}

