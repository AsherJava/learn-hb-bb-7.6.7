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
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.common.RegionEnterNext;
import com.jiuqi.nr.definition.util.LevelSetting;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public interface DataRegionDefine
extends IMetaItem {
    public String getCode();

    public String getFormKey();

    public int getRegionLeft();

    public int getRegionRight();

    public int getRegionTop();

    public int getRegionBottom();

    public DataRegionKind getRegionKind();

    @Deprecated
    public String getBindingExpression();

    public String getInputOrderFieldKey();

    public String getSortFieldsList();

    public int getRowsInFloatRegion();

    public String getGatherFields();

    @Deprecated
    public String getGatherSetting();

    public LevelSetting getLevelSetting();

    public boolean getShowGatherDetailRows();

    public boolean getShowGatherDetailRowByOne();

    public boolean getShowGatherSummaryRow();

    public String getShowAddress();

    public String getRegionSettingKey();

    @Deprecated
    public String getCardInputFormKey();

    public String getFilterCondition();

    public int getMaxRowCount();

    public boolean getCanDeleteRow();

    public boolean getCanInsertRow();

    public int getPageSize();

    @Deprecated
    public String getMasterEntitiesKey();

    public String getReadOnlyCondition();

    public boolean getAllowDuplicateKey();

    public boolean getIsCanFold();

    public String getHideZeroGatherFields();

    public String getBizKeyFields();

    public RegionEnterNext getRegionEnterNext();

    public String getDisplayLevel();
}

