/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.front;

import com.jiuqi.va.biz.front.FrontDataTableDefine;
import com.jiuqi.va.biz.front.FrontModelDefine;
import com.jiuqi.va.biz.front.FrontPluginDefine;
import com.jiuqi.va.biz.impl.data.DataFieldDefineImpl;
import com.jiuqi.va.biz.intf.data.DataDefine;
import com.jiuqi.va.biz.intf.data.DataFieldDefine;
import com.jiuqi.va.biz.intf.data.DataTableDefine;
import com.jiuqi.va.biz.intf.model.ModelDefine;
import com.jiuqi.va.biz.intf.model.PluginDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FrontDataDefine
extends FrontPluginDefine {
    private List<FrontDataTableDefine> tables = new ArrayList<FrontDataTableDefine>();
    private Map<String, Map<String, Integer>> fieldIndexs = new HashMap<String, Map<String, Integer>>();
    private final transient Map<String, FrontDataTableDefine> nameMap = new HashMap<String, FrontDataTableDefine>();
    private final transient DataDefine dataDefine;

    public FrontDataDefine() {
        this.dataDefine = null;
    }

    public FrontDataDefine(FrontModelDefine frontModelDefine, PluginDefine pluginDefine) {
        super(frontModelDefine, pluginDefine);
        this.dataDefine = (DataDefine)((Object)pluginDefine);
        if (!frontModelDefine.ENABLE_SERVER_DATA_BUFFER) {
            this.dataDefine.getTables().forEach((table_i, table_o) -> {
                this.addTable((DataTableDefine)table_o);
                FrontDataTableDefine frontTable = this.nameMap.get(table_o.getName());
                table_o.getFields().forEachIndex((field_i, field_o) -> frontTable.addField((DataFieldDefineImpl)field_o));
            });
        }
    }

    public DataDefine getDataDefine() {
        return this.dataDefine;
    }

    boolean addTable(DataTableDefine table) {
        boolean result;
        boolean bl = result = !this.nameMap.containsKey(table.getName());
        if (result) {
            FrontDataTableDefine frontTable = FrontDataTableDefine.create(table);
            this.nameMap.put(table.getName(), frontTable);
            this.tables.add(frontTable);
        }
        return result;
    }

    public void addFrontTable(String tableName) {
        DataTableDefine table;
        if (this.frontModelDefine.ENABLE_SERVER_DATA_BUFFER && this.addTable(table = this.dataDefine.getTables().get(tableName))) {
            FrontDataTableDefine frontTable = this.nameMap.get(tableName);
            frontTable.addField((DataFieldDefineImpl)table.getFields().get("ID"));
            DataFieldDefine f_ver = table.getFields().get("VER");
            if (f_ver != null) {
                frontTable.addField((DataFieldDefineImpl)f_ver);
            }
        }
    }

    public void addFrontField(String tableName, String fieldName) {
        if (this.frontModelDefine.ENABLE_SERVER_DATA_BUFFER) {
            this.addFrontTable(tableName);
            FrontDataTableDefine frontTable = this.nameMap.get(tableName);
            frontTable.addField((DataFieldDefineImpl)this.dataDefine.getTables().get(tableName).getFields().get(fieldName));
        }
    }

    @Override
    protected Map<String, Set<String>> getTableFields(ModelDefine modelDefine) {
        HashMap<String, Set<String>> tableFields = new HashMap<String, Set<String>>();
        this.getDataDefine().getTables().forEach((i, t) -> {
            if (t.getName().endsWith("_M")) {
                tableFields.put(t.getName(), t.getFields().stream().map(DataFieldDefine::getName).collect(Collectors.toSet()));
            }
        });
        return tableFields;
    }

    public void setFieldIndexs(Map<String, Map<String, Integer>> fieldIndexs) {
        this.fieldIndexs = fieldIndexs;
    }

    public List<FrontDataTableDefine> getTableList() {
        return this.tables;
    }
}

