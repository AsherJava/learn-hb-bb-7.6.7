/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.workflow2.form.reject.model;

import com.jiuqi.nr.workflow2.form.reject.model.FROperateTable;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class FROperateTableModelDefine {
    private final FROperateTable operateTable;
    private final TableModelDefine tableModelDefine;
    private final List<ColumnModelDefine> allColumnDefines;
    private final List<ColumnModelDefine> bizKeyColumnDefines;
    private final List<ColumnModelDefine> combinationColumnDefines;
    private final ColumnModelDefine optIdColumnDefine;
    private final ColumnModelDefine optUserColumnDefine;
    private final ColumnModelDefine optTimeColumnDefine;
    private final ColumnModelDefine optCommentColumnDefine;
    private final ColumnModelDefine formIdColumnDefine;

    public FROperateTableModelDefine(FROperateTable operateTable, DataModelService dataModelService) {
        this.operateTable = operateTable;
        this.tableModelDefine = dataModelService.getTableModelDefineByName(operateTable.getTableName());
        this.allColumnDefines = dataModelService.getColumnModelDefinesByTable(this.tableModelDefine.getID());
        this.bizKeyColumnDefines = this.getBizKeyColumnDefines(this.allColumnDefines);
        this.combinationColumnDefines = this.getCombinationColumnDefines(this.allColumnDefines);
        this.optIdColumnDefine = this.getOptIdColumnDefine(this.allColumnDefines);
        this.optUserColumnDefine = this.getOptUserColumnDefine(this.allColumnDefines);
        this.optTimeColumnDefine = this.getOptTimeColumnDefine(this.allColumnDefines);
        this.optCommentColumnDefine = this.getOptCommentColumnDefine(this.allColumnDefines);
        this.formIdColumnDefine = this.getFormIdColumnDefine(this.allColumnDefines);
    }

    public String getName() {
        return this.tableModelDefine.getName();
    }

    public FROperateTable getOperateTable() {
        return this.operateTable;
    }

    public List<ColumnModelDefine> getAllColumnDefines() {
        return this.allColumnDefines;
    }

    public List<ColumnModelDefine> getBizKeyColumnDefines() {
        return this.bizKeyColumnDefines;
    }

    public List<ColumnModelDefine> getCombinationColumnDefines() {
        return this.combinationColumnDefines;
    }

    public ColumnModelDefine getOptIdColumnDefine() {
        return this.optIdColumnDefine;
    }

    public ColumnModelDefine getOptUserColumnDefine() {
        return this.optUserColumnDefine;
    }

    public ColumnModelDefine getOptTimeColumnDefine() {
        return this.optTimeColumnDefine;
    }

    public ColumnModelDefine getOptCommentColumnDefine() {
        return this.optCommentColumnDefine;
    }

    public ColumnModelDefine getFormIdColumnDefine() {
        return this.formIdColumnDefine;
    }

    private List<ColumnModelDefine> getBizKeyColumnDefines(List<ColumnModelDefine> allColumnDefines) {
        String[] bizKeys;
        ArrayList<ColumnModelDefine> bizKeyColumns = new ArrayList<ColumnModelDefine>();
        for (String bizKey : bizKeys = this.tableModelDefine.getBizKeys().split(";")) {
            ColumnModelDefine columnModelDefine = allColumnDefines.stream().filter(c -> c.getID().equals(bizKey)).findFirst().orElse(null);
            if (columnModelDefine == null) {
                throw new RuntimeException("\u65e0\u6548\u7684[" + this.operateTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
            }
            bizKeyColumns.add(columnModelDefine);
        }
        return bizKeyColumns;
    }

    private List<ColumnModelDefine> getCombinationColumnDefines(List<ColumnModelDefine> allColumnDefines) {
        String[] bizKeys;
        ArrayList<ColumnModelDefine> bizKeyColumns = new ArrayList<ColumnModelDefine>();
        List<String> excludeColumnNames = Arrays.asList(this.operateTable.getOptIdColumnName(), this.operateTable.getFormIdColumnName());
        for (String bizKey : bizKeys = this.tableModelDefine.getBizKeys().split(";")) {
            ColumnModelDefine columnModelDefine = allColumnDefines.stream().filter(c -> c.getID().equals(bizKey)).findFirst().orElse(null);
            if (columnModelDefine == null) {
                throw new RuntimeException("\u65e0\u6548\u7684[" + this.operateTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
            }
            if (excludeColumnNames.contains(columnModelDefine.getName())) continue;
            bizKeyColumns.add(columnModelDefine);
        }
        return bizKeyColumns;
    }

    private ColumnModelDefine getOptIdColumnDefine(List<ColumnModelDefine> allColumnDefines) {
        Optional<ColumnModelDefine> optional = allColumnDefines.stream().filter(c -> c.getName().equals(this.operateTable.getOptIdColumnName())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("\u65e0\u6548\u7684[" + this.operateTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c[" + this.operateTable.getOptIdColumnName() + "]\u4e0d\u5b58\u5728\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
    }

    private ColumnModelDefine getOptUserColumnDefine(List<ColumnModelDefine> allColumnDefines) {
        Optional<ColumnModelDefine> optional = allColumnDefines.stream().filter(c -> c.getName().equals(this.operateTable.getOptUserColumnName())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("\u65e0\u6548\u7684[" + this.operateTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c[" + this.operateTable.getOptUserColumnName() + "]\u4e0d\u5b58\u5728\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
    }

    private ColumnModelDefine getOptTimeColumnDefine(List<ColumnModelDefine> allColumnDefines) {
        Optional<ColumnModelDefine> optional = allColumnDefines.stream().filter(c -> c.getName().equals(this.operateTable.getOptTimeColumnName())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("\u65e0\u6548\u7684[" + this.operateTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c[" + this.operateTable.getOptTimeColumnName() + "]\u4e0d\u5b58\u5728\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
    }

    private ColumnModelDefine getOptCommentColumnDefine(List<ColumnModelDefine> allColumnDefines) {
        Optional<ColumnModelDefine> optional = allColumnDefines.stream().filter(c -> c.getName().equals(this.operateTable.getOptCommentColumnName())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("\u65e0\u6548\u7684[" + this.operateTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c[" + this.operateTable.getOptCommentColumnName() + "]\u4e0d\u5b58\u5728\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
    }

    private ColumnModelDefine getFormIdColumnDefine(List<ColumnModelDefine> allColumnDefines) {
        Optional<ColumnModelDefine> optional = allColumnDefines.stream().filter(c -> c.getName().equals(this.operateTable.getFormIdColumnName())).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new RuntimeException("\u65e0\u6548\u7684[" + this.operateTable.getTableName() + "]\u8868\u6a21\u578b\u5b9a\u4e49\uff0c[" + this.operateTable.getFormIdColumnName() + "]\u4e0d\u5b58\u5728\uff0c\u9700\u68c0\u67e5\u53c2\u6570\u662f\u5426\u5b8c\u6574\uff01");
    }
}

