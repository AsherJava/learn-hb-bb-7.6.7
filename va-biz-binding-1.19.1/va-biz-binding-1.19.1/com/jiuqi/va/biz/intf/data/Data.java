/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.intf.data;

import com.jiuqi.va.biz.inc.intf.DataRecord;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataListener;
import com.jiuqi.va.biz.intf.data.DataState;
import com.jiuqi.va.biz.intf.data.DataTable;
import com.jiuqi.va.biz.intf.data.DataTableNodeContainer;
import com.jiuqi.va.biz.intf.data.DataUpdate;
import com.jiuqi.va.biz.intf.model.Model;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Data {
    public Model getModel();

    public DataDefine getDefine();

    public DataState getState();

    public DataTable getMasterTable();

    public DataTableNodeContainer<? extends DataTable> getTables();

    public void reset();

    public void create();

    public void delete();

    public void deleteWithLock(Set<String> var1);

    public void load(Map<String, Object> var1);

    public void edit();

    public void save();

    public void saveWithLock(Set<String> var1);

    public Map<String, DataUpdate> getUpdate();

    public void applyChange();

    public void cancelChange();

    public void beginUpdate();

    public void endUpdate();

    public void startRecord();

    public DataRecord getRecordUpdate();

    public void stopRecord();

    public Map<String, List<Map<String, Object>>> getTablesData();

    public Map<String, List<Map<String, Object>>> getFilterTablesData();

    public Map<String, List<Map<String, Object>>> getTablesData(boolean var1);

    public Map<String, List<List<Object>>> getFrontTablesData();

    public void setTablesData(Map<String, List<Map<String, Object>>> var1);

    public void setIncTablesData(Map<String, List<List<Object>>> var1);

    public void setUpdate(Map<String, ? extends DataUpdate> var1);

    public void addListener(DataListener var1);

    public void removeListener(DataListener var1);

    public boolean isEnableRule();

    public void setEnableRule(boolean var1);
}

