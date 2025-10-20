/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.datamodel.exchange.nvwa.task;

import com.jiuqi.va.datamodel.exchange.nvwa.base.FDataModelEditEntiy;
import com.jiuqi.va.datamodel.exchange.nvwa.base.TableType;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelFieldDefine;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelTableDefine;
import com.jiuqi.va.datamodel.exchange.nvwa.impl.DataModelViewDefine;

public interface FModelDataViewTask {
    public boolean accept(TableType var1);

    default public boolean initViewInfo(DataModelTableDefine table, DataModelViewDefine view, FDataModelEditEntiy entiy) {
        return false;
    }

    default public boolean initDefaultView(DataModelTableDefine table, DataModelViewDefine view, FDataModelEditEntiy entiy) {
        return false;
    }

    default public boolean initTableDefine(DataModelTableDefine table, FDataModelEditEntiy entiy) {
        return false;
    }

    default public boolean initFieldDefine(DataModelFieldDefine field, FDataModelEditEntiy entiy) {
        return false;
    }

    default public boolean canDeleteFieldDefine(String fieldName, FDataModelEditEntiy entiy) {
        return true;
    }

    default public int sortOrder() {
        return 1;
    }
}

