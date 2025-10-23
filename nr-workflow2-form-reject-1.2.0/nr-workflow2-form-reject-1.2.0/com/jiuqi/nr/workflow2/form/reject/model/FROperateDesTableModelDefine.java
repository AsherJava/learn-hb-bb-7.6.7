/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 */
package com.jiuqi.nr.workflow2.form.reject.model;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.workflow2.form.reject.model.FROperateTable;
import com.jiuqi.nr.workflow2.form.reject.model.FRStatusDesTableModelDefine;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FROperateDesTableModelDefine
extends FRStatusDesTableModelDefine {
    public FROperateDesTableModelDefine(FROperateTable operateTable, DesignDataModelService designDataModelService) {
        this.tableModelDefine = this.initTableModelDefine(operateTable, designDataModelService);
        this.bizKeyColumnModelDefines = this.initBizKeyColumnModelDefines(operateTable, designDataModelService);
        this.indexModelDefine = this.initIndexModelDefines(this.bizKeyColumnModelDefines, designDataModelService);
        this.columnModelDefines = this.intColumnModelDefines(operateTable, designDataModelService);
        this.tableModelDefine.setKeys(this.bizKeyColumnModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";")));
        this.tableModelDefine.setBizKeys(this.bizKeyColumnModelDefines.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";")));
    }

    protected DesignTableModelDefine initTableModelDefine(FROperateTable operateTable, DesignDataModelService designDataModelService) {
        FormSchemeDefine formSchemeDefine = operateTable.getFormSchemeDefine();
        DesignTableModelDefine tableModelDefine = designDataModelService.createTableModelDefine();
        tableModelDefine.setCode(operateTable.getTableName());
        tableModelDefine.setName(operateTable.getTableName());
        tableModelDefine.setTitle(formSchemeDefine.getTitle() + "\u3010" + formSchemeDefine.getFormSchemeCode() + "\u3011\u7684\u5355\u8868\u9000\u56de\u8f68\u8ff9\u5b58\u50a8\u8868");
        return tableModelDefine;
    }

    protected List<DesignColumnModelDefine> initBizKeyColumnModelDefines(FROperateTable operateTable, DesignDataModelService designDataModelService) {
        ArrayList<DesignColumnModelDefine> bizKeyColumnModelDefines = new ArrayList<DesignColumnModelDefine>();
        bizKeyColumnModelDefines.add(this.createOptIdColumnModelDefine(operateTable, designDataModelService));
        bizKeyColumnModelDefines.addAll(super.initBizKeyColumnModelDefines(operateTable, designDataModelService));
        return bizKeyColumnModelDefines;
    }

    protected List<DesignColumnModelDefine> intColumnModelDefines(FROperateTable operateTable, DesignDataModelService designDataModelService) {
        ArrayList<DesignColumnModelDefine> columnModelDefines = new ArrayList<DesignColumnModelDefine>();
        columnModelDefines.add(this.createOptUserColumnModelDefine(operateTable, designDataModelService));
        columnModelDefines.add(this.createOptTimeColumnModelDefine(operateTable, designDataModelService));
        columnModelDefines.add(this.createOptCommentColumnModelDefine(operateTable, designDataModelService));
        return columnModelDefines;
    }

    private DesignColumnModelDefine createOptIdColumnModelDefine(FROperateTable operateTable, DesignDataModelService designDataModelService) {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setTableID(this.tableModelDefine.getID());
        columnModelDefine.setCode(operateTable.getOptIdColumnName());
        columnModelDefine.setName(operateTable.getOptIdColumnName());
        columnModelDefine.setTitle("\u64cd\u4f5cID");
        columnModelDefine.setColumnType(ColumnModelType.STRING);
        columnModelDefine.setPrecision(60);
        columnModelDefine.setNullAble(false);
        return columnModelDefine;
    }

    private DesignColumnModelDefine createOptUserColumnModelDefine(FROperateTable operateTable, DesignDataModelService designDataModelService) {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setTableID(this.tableModelDefine.getID());
        columnModelDefine.setCode(operateTable.getOptUserColumnName());
        columnModelDefine.setName(operateTable.getOptUserColumnName());
        columnModelDefine.setTitle("\u64cd\u4f5c\u4eba");
        columnModelDefine.setColumnType(ColumnModelType.STRING);
        columnModelDefine.setPrecision(60);
        columnModelDefine.setNullAble(true);
        return columnModelDefine;
    }

    private DesignColumnModelDefine createOptTimeColumnModelDefine(FROperateTable operateTable, DesignDataModelService designDataModelService) {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setTableID(this.tableModelDefine.getID());
        columnModelDefine.setCode(operateTable.getOptTimeColumnName());
        columnModelDefine.setName(operateTable.getOptTimeColumnName());
        columnModelDefine.setTitle("\u64cd\u4f5c\u65f6\u95f4");
        columnModelDefine.setColumnType(ColumnModelType.DATETIME);
        columnModelDefine.setPrecision(6);
        columnModelDefine.setNullAble(true);
        return columnModelDefine;
    }

    private DesignColumnModelDefine createOptCommentColumnModelDefine(FROperateTable operateTable, DesignDataModelService designDataModelService) {
        DesignColumnModelDefine columnModelDefine = designDataModelService.createColumnModelDefine();
        columnModelDefine.setTableID(this.tableModelDefine.getID());
        columnModelDefine.setCode(operateTable.getOptCommentColumnName());
        columnModelDefine.setName(operateTable.getOptCommentColumnName());
        columnModelDefine.setTitle("\u64cd\u4f5c\u8bf4\u660e\uff08\u6279\u6ce8\uff09");
        columnModelDefine.setColumnType(ColumnModelType.STRING);
        columnModelDefine.setPrecision(2000);
        columnModelDefine.setNullAble(true);
        return columnModelDefine;
    }
}

