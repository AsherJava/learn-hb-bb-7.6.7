/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.List;

public class DataEntityReturnInfo {
    private String entityCode;
    private String entityTitle;
    private List<DataEntityReturnInfo> childrenEntityList;

    public DataEntityReturnInfo() {
    }

    public DataEntityReturnInfo(IEntityRow iEntityRow) {
        this.entityCode = iEntityRow.getCode();
        this.entityTitle = iEntityRow.getTitle();
    }

    public String getEntityCode() {
        return this.entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public String getEntityTitle() {
        return this.entityTitle;
    }

    public void setEntityTitle(String entityTitle) {
        this.entityTitle = entityTitle;
    }

    public List<DataEntityReturnInfo> getChildrenEntityList() {
        return this.childrenEntityList;
    }

    public void setChildrenEntityList(List<DataEntityReturnInfo> childrenEntityList) {
        this.childrenEntityList = childrenEntityList;
    }
}

