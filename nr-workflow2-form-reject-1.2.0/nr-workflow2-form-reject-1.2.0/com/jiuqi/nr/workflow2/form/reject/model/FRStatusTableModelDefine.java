/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.workflow2.form.reject.model;

import com.jiuqi.nr.workflow2.form.reject.model.FRStatusTable;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class FRStatusTableModelDefine {
    private final FRStatusTable statusTable;
    private final TableModelDefine tableModelDefine;
    private final List<ColumnModelDefine> allColumnDefines;
    private final List<ColumnModelDefine> bizKeyColumnDefines;
    private final List<ColumnModelDefine> combinationColumnDefines;
    private final ColumnModelDefine formColumnDefine;
    private final ColumnModelDefine statusColumnDefine;

    public FRStatusTableModelDefine(FRStatusTable statusTable, DataModelService dataModelService) {
        this.statusTable = statusTable;
        this.tableModelDefine = dataModelService.getTableModelDefineByName(statusTable.getTableName());
        this.allColumnDefines = dataModelService.getColumnModelDefinesByTable(this.tableModelDefine.getID());
        this.bizKeyColumnDefines = this.getBizKeyColumnDefines(this.allColumnDefines);
        this.combinationColumnDefines = this.getCombinationColumnDefines(this.allColumnDefines);
        this.formColumnDefine = this.getFormColumnDefine(this.allColumnDefines);
        this.statusColumnDefine = this.getStatusColumnDefine(this.allColumnDefines);
    }

    public String getName() {
        return this.tableModelDefine.getName();
    }

    public FRStatusTable getStatusTable() {
        return this.statusTable;
    }

    public List<ColumnModelDefine> getBizKeyColumnDefines() {
        return this.bizKeyColumnDefines;
    }

    public List<ColumnModelDefine> getCombinationColumnDefines() {
        return this.combinationColumnDefines;
    }

    public ColumnModelDefine getFormColumnDefine() {
        return this.formColumnDefine;
    }

    public ColumnModelDefine getStatusColumnDefine() {
        return this.statusColumnDefine;
    }

    public List<ColumnModelDefine> getAllColumnDefines() {
        return this.allColumnDefines;
    }

    private List<ColumnModelDefine> getCombinationColumnDefines(List<ColumnModelDefine> allColumnDefines) {
        String[] bizKeys;
        ArrayList<ColumnModelDefine> bizKeyColumns = new ArrayList<ColumnModelDefine>();
        for (String bizKey : bizKeys = this.tableModelDefine.getBizKeys().split(";")) {
            ColumnModelDefine columnModelDefine = allColumnDefines.stream().filter(c -> c.getID().equals(bizKey)).findFirst().orElse(null);
            if (columnModelDefine == null) {
                throw new RuntimeException("\u65e0\u6548\u7684[" + this.statusTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
            }
            if (columnModelDefine.getName().equalsIgnoreCase(this.statusTable.getFormIdColumnName())) continue;
            bizKeyColumns.add(columnModelDefine);
        }
        return bizKeyColumns;
    }

    private List<ColumnModelDefine> getBizKeyColumnDefines(List<ColumnModelDefine> allColumnDefines) {
        String[] bizKeys;
        ArrayList<ColumnModelDefine> bizKeyColumns = new ArrayList<ColumnModelDefine>();
        for (String bizKey : bizKeys = this.tableModelDefine.getBizKeys().split(";")) {
            ColumnModelDefine columnModelDefine = allColumnDefines.stream().filter(c -> c.getID().equals(bizKey)).findFirst().orElse(null);
            if (columnModelDefine == null) {
                throw new RuntimeException("\u65e0\u6548\u7684[" + this.statusTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
            }
            bizKeyColumns.add(columnModelDefine);
        }
        return bizKeyColumns;
    }

    private ColumnModelDefine getFormColumnDefine(List<ColumnModelDefine> allColumnDefines) {
        Optional<ColumnModelDefine> optional = allColumnDefines.stream().filter(c -> c.getName().equalsIgnoreCase(this.statusTable.getFormIdColumnName())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("\u65e0\u6548\u7684[" + this.statusTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c[" + this.statusTable.getFormIdColumnName() + "]\u4e0d\u5b58\u5728\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
    }

    private ColumnModelDefine getStatusColumnDefine(List<ColumnModelDefine> allColumnDefines) {
        Optional<ColumnModelDefine> optional = allColumnDefines.stream().filter(c -> c.getName().equalsIgnoreCase(this.statusTable.getStatusColumnName())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("\u65e0\u6548\u7684[" + this.statusTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c[" + this.statusTable.getStatusColumnName() + "]\u4e0d\u5b58\u5728\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
    }
}

