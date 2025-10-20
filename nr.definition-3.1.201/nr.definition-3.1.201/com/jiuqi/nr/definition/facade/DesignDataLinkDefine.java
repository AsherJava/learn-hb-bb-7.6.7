/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.definition.common.DataLinkEditMode;
import com.jiuqi.nr.definition.common.DataLinkType;
import com.jiuqi.nr.definition.common.EnumDisplayMode;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import java.util.Date;
import java.util.List;

public interface DesignDataLinkDefine
extends DataLinkDefine {
    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setRegionKey(String var1);

    public void setLinkExpression(String var1);

    @Deprecated
    public void setBindingExpression(String var1);

    public void setPosX(int var1);

    public void setPosY(int var1);

    public void setColNum(int var1);

    public void setRowNum(int var1);

    public void setEditMode(DataLinkEditMode var1);

    public void setDisplayMode(EnumDisplayMode var1);

    public void setDataValidation(List<String> var1);

    public void setCaptionFieldsString(String var1);

    public void setDropDownFieldsString(String var1);

    public void setAllowUndefinedCode(Boolean var1);

    public void setAllowNullAble(Boolean var1);

    public void setAllowNotLeafNodeRefer(boolean var1);

    public void setFormatProperties(FormatProperties var1);

    public void setUniqueCode(String var1);

    public void setEnumShowFullPath(String var1);

    public void setEnumTitleField(String var1);

    public void setType(DataLinkType var1);

    public void setEnumLinkage(String var1);

    public void setEnumCount(int var1);

    public void setEnumPos(String var1);

    public void setEnumLinkageStatus(boolean var1);

    public void setFilterExpression(String var1);

    public void setIgnorePermissions(boolean var1);

    public String setFilterTemplate(String var1);

    public void setMeasureUnit(String var1);
}

