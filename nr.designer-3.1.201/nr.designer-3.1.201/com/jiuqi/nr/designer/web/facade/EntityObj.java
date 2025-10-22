/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import com.jiuqi.nr.designer.web.facade.EnumData;
import com.jiuqi.nr.designer.web.treebean.TableObject;

public class EntityObj {
    private TableObject tableObj;
    private EnumData[] datas;

    public TableObject getTableObj() {
        return this.tableObj;
    }

    public void setTableObj(TableObject tableObj) {
        this.tableObj = tableObj;
    }

    public EnumData[] getDatas() {
        return this.datas;
    }

    public void setDatas(EnumData[] datas) {
        this.datas = datas;
    }
}

