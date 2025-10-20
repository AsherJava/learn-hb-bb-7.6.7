/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelDO
 */
package com.jiuqi.va.datamodel.exchange.nvwa.base;

import com.jiuqi.va.datamodel.exchange.nvwa.base.TableType;
import com.jiuqi.va.domain.datamodel.DataModelDO;
import java.util.List;
import java.util.Map;

public interface FDataModelEditEntiy {
    public DataModelDO getVdataModel();

    public TableType getVaTableType();

    public Map<String, String> getFieldMaps();

    public String getTableName();

    public List<String> getKeys();

    public String find(String var1);
}

