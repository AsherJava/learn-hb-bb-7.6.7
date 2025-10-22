/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.ErrorType
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.ErrorType;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AbstractTableModelIndexBuilder;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DefaultTableModelIndexBuilderImpl
extends AbstractTableModelIndexBuilder {
    @Override
    public DataTableType[] getDoForTableTypes() {
        return new DataTableType[]{DataTableType.TABLE, DataTableType.MD_INFO};
    }

    @Override
    protected void build(DataTableDeployObj dataTable, List<TableModelDeployObj> tableModels) {
        for (TableModelDeployObj tableModel : tableModels) {
            DesignTableModel designTableModel = tableModel.getDesignTableModel();
            AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTable, tableModel);
            this.update((DesignTableModelDefine)designTableModel, requireColumns);
        }
    }

    private void update(DesignTableModelDefine designTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns) {
        String bizkeys = DefaultTableModelIndexBuilderImpl.getBizkeys(requireColumns);
        designTableModel.setKeys(bizkeys);
        designTableModel.setBizKeys(bizkeys);
    }

    private static String getBizkeys(AbstractTableModelIndexBuilder.RequireColumns requireColumns) {
        ArrayList<DesignColumnModelDefine> bizkeys = new ArrayList<DesignColumnModelDefine>();
        if (null != requireColumns.mdcode) {
            bizkeys.add(requireColumns.mdcode);
        }
        if (null != requireColumns.datatime) {
            bizkeys.add(requireColumns.datatime);
        }
        bizkeys.addAll(requireColumns.dims);
        return bizkeys.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
    }

    @Override
    protected int check(DataTableModelDTO dataTableModel) throws Exception {
        AbstractTableModelIndexBuilder.RequireColumns requireColumns;
        String bizkeys;
        int errorType = 0;
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        String keys = tableModel.getKeys();
        if (!StringUtils.hasText(keys)) {
            errorType |= ErrorType.ERROR.getValue();
        }
        if (!keys.equals(bizkeys = DefaultTableModelIndexBuilderImpl.getBizkeys(requireColumns = this.getRequireColumns(dataTableModel))) || !keys.equals(tableModel.getBizKeys())) {
            errorType |= ErrorType.ERROR.getValue();
        }
        if (!dataTableModel.getIndexModels().isEmpty()) {
            errorType |= ErrorType.TIP.getValue();
        }
        return errorType |= this.checkIndex(tableModel.getID());
    }

    @Override
    protected void rebuild(DataTableModelDTO dataTableModel, int errorTypes) throws Exception {
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        if (0 != (ErrorType.TIP.getValue() & errorTypes)) {
            this.designDataModelService.deleteIndexsByTable(tableModel.getID());
        }
        if (0 != (ErrorType.ERROR.getValue() & errorTypes)) {
            AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
            this.update(tableModel, requireColumns);
            this.designDataModelService.updateTableModelDefine(tableModel);
        }
        this.rebuildIndexes(tableModel.getID());
    }
}

