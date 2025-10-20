/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.impl;

import java.util.Date;

public interface DataModelViewDefine {
    public String getKey();

    public String getTitle();

    public String getOrder();

    public String getVersion();

    public String getOwnerLevelAndId();

    public Date getUpdateTime();

    public String getTableKey();

    public boolean getIsDefault();

    public String[] getCaptionFieldsKeys();

    public String getSortFieldsList();

    public String getRowFilterExpression();

    public int getCacheModeDB();

    public String getCaptionFieldsString();

    public String getDropDownFieldsString();

    public boolean getFilterRowByAuthority();

    public int getInheritModeDB();

    public boolean getShowCode();

    public String getParentField();

    public Date getVersionDate();

    public String getICONField();

    public String getParentView();

    public String getCascadeViewParentField();

    public boolean getIsDisplayOnSingleTree();

    public String getStopedField();

    public String getRecoveryField();

    public String getParentsField();

    public void setKey(String var1);

    public void setOrder(String var1);

    public void setVersion(String var1);

    public void setOwnerLevelAndId(String var1);

    public void setTitle(String var1);

    public void setUpdateTime(Date var1);

    public void setTableKey(String var1);

    public void setIsDefault(boolean var1);

    public void setCaptionFieldsKeys(String[] var1);

    public void setSortFieldsList(String var1);

    public void setRowFilterExpression(String var1);

    public void setCacheModeDB(Integer var1);

    public void setCaptionFieldsString(String var1);

    public void setDropDownFieldsString(String var1);

    public void setFilterRowByAuthority(boolean var1);

    public void setInheritModeDB(Integer var1);

    public void setParentField(String var1);

    public void setVersionDate(Date var1);

    public void setICONField(String var1);

    public void setParentView(String var1);

    public void setCascadeViewParentField(String var1);

    public void setIsDisplayOnSingleTree(boolean var1);

    public void setStopedField(String var1);

    public void setRecoveryField(String var1);

    public void setParentsField(String var1);
}

