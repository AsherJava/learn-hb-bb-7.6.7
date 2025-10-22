/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.ErrorType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 *  com.jiuqi.nvwa.definition.interval.deploy.DeployTableType
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.ErrorType;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AbstractTableModelIndexBuilder;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import com.jiuqi.nvwa.definition.interval.deploy.DeployTableType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class DetailTableModelIndexBuilderImpl
extends AbstractTableModelIndexBuilder {
    @Override
    public DataTableType[] getDoForTableTypes() {
        return new DataTableType[]{DataTableType.DETAIL, DataTableType.SUB_TABLE, DataTableType.MULTI_DIM};
    }

    @Override
    protected void build(DataTableDeployObj dataTable, List<TableModelDeployObj> tableModels) throws ModelValidateException {
        for (TableModelDeployObj tableModel : tableModels) {
            String oldKeys;
            AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTable, tableModel);
            String bizkeys = DetailTableModelIndexBuilderImpl.getBizKeys(dataTable.getDataTable(), requireColumns);
            DesignTableModel designTableModel = tableModel.getDesignTableModel();
            String oldBizKeys = designTableModel.getBizKeys();
            if (DeployTableType.ADD != tableModel.getState() && StringUtils.hasText(oldKeys = tableModel.getDesignTableModel().getKeys()) && oldKeys.equals(bizkeys)) {
                return;
            }
            designTableModel.setKeys(requireColumns.bizKeyOrder.getID());
            designTableModel.setBizKeys(bizkeys);
            if (DeployTableType.ADD == tableModel.getState()) {
                this.addAllIndex(designTableModel, requireColumns);
                continue;
            }
            this.removeAllIndexV1(designTableModel, oldBizKeys, requireColumns.floatOrder.getID());
            this.updateIndex(designTableModel, requireColumns);
        }
    }

    private static String getBizKeys(DataTable dataTable, AbstractTableModelIndexBuilder.RequireColumns requireColumns) {
        ArrayList<DesignColumnModelDefine> bizkeys = new ArrayList<DesignColumnModelDefine>();
        if (null != requireColumns.mdcode) {
            bizkeys.add(requireColumns.mdcode);
        }
        if (null != requireColumns.datatime) {
            bizkeys.add(requireColumns.datatime);
        }
        bizkeys.addAll(requireColumns.dims);
        bizkeys.addAll(requireColumns.dimsInTable);
        if (dataTable.isRepeatCode()) {
            bizkeys.add(requireColumns.bizKeyOrder);
        }
        return bizkeys.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
    }

    private void addAllIndex(DesignTableModel designTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns) throws ModelValidateException {
        DesignIndexModelDefine bizkeysIndex = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.BIZKEYS, designTableModel.getID());
        bizkeysIndex.setFieldIDs(designTableModel.getBizKeys());
        designTableModel.addIndex(bizkeysIndex);
        DesignIndexModelDefine floatOrderIndex = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.FLOATORDER, designTableModel.getID());
        floatOrderIndex.setFieldIDs(requireColumns.floatOrder.getID());
        designTableModel.addIndex(floatOrderIndex);
    }

    private void updateIndex(DesignTableModel designTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns) throws ModelValidateException {
        String bizkeysIndexId = AbstractTableModelIndexBuilder.IndexType.BIZKEYS.getIndexId(designTableModel.getID());
        String floatOrderIndexId = AbstractTableModelIndexBuilder.IndexType.FLOATORDER.getIndexId(designTableModel.getID());
        DesignIndexModelDefine bizkeysIndex = null;
        DesignIndexModelDefine floatOrderIndex = null;
        List indexes = designTableModel.getIndexs();
        for (DesignIndexModelDefine index : indexes) {
            if (index.getID().equals(bizkeysIndexId)) {
                bizkeysIndex = index;
                continue;
            }
            if (!index.getID().equals(floatOrderIndexId)) continue;
            floatOrderIndex = index;
        }
        if (null == bizkeysIndex) {
            bizkeysIndex = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.BIZKEYS, designTableModel.getID());
            bizkeysIndex.setFieldIDs(designTableModel.getBizKeys());
            designTableModel.addIndex(bizkeysIndex);
        } else {
            bizkeysIndex.setFieldIDs(designTableModel.getBizKeys());
            designTableModel.modifyIndex(bizkeysIndex);
        }
        if (null == floatOrderIndex) {
            floatOrderIndex = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.FLOATORDER, designTableModel.getID());
            floatOrderIndex.setFieldIDs(requireColumns.floatOrder.getID());
            designTableModel.addIndex(floatOrderIndex);
        }
    }

    private void removeAllIndexV1(DesignTableModel designTableModel, String oldBizkeys, String floatOrderId) {
        List indexes = designTableModel.getIndexs();
        for (DesignIndexModelDefine index : indexes) {
            if (index.getID().equals(AbstractTableModelIndexBuilder.IndexType.BIZKEYS.getIndexId(designTableModel.getID())) || index.getID().equals(AbstractTableModelIndexBuilder.IndexType.FLOATORDER.getIndexId(designTableModel.getID())) || !index.getFieldIDs().equals(oldBizkeys) && !index.getFieldIDs().equals(floatOrderId)) continue;
            designTableModel.removeIndex(index);
        }
    }

    @Override
    protected int check(DataTableModelDTO dataTableModel) throws Exception {
        int errorType = ErrorType.NONE.getValue();
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
        String bizkeys = DetailTableModelIndexBuilderImpl.getBizKeys(dataTableModel.getDataTable(), requireColumns);
        if (!requireColumns.bizKeyOrder.getID().equals(tableModel.getKeys()) || !bizkeys.equals(tableModel.getBizKeys())) {
            errorType |= ErrorType.ERROR.getValue();
        }
        Map<String, DesignIndexModelDefine> indexIdMap = dataTableModel.getIndexIdMap();
        DesignIndexModelDefine bizkeysIndex = indexIdMap.remove(AbstractTableModelIndexBuilder.IndexType.BIZKEYS.getIndexId(tableModel.getID()));
        DesignIndexModelDefine floatOrderIndex = indexIdMap.remove(AbstractTableModelIndexBuilder.IndexType.FLOATORDER.getIndexId(tableModel.getID()));
        for (DesignIndexModelDefine index : indexIdMap.values()) {
            if (bizkeys.equals(index.getFieldIDs())) {
                if (null == bizkeysIndex) {
                    bizkeysIndex = index;
                    continue;
                }
                errorType |= ErrorType.WARNING.getValue();
                continue;
            }
            if (requireColumns.floatOrder.getID().equals(index.getFieldIDs())) {
                if (null == floatOrderIndex) {
                    floatOrderIndex = index;
                    continue;
                }
                errorType |= ErrorType.WARNING.getValue();
                continue;
            }
            errorType |= ErrorType.TIP.getValue();
        }
        if (null == bizkeysIndex || null == floatOrderIndex || !bizkeysIndex.getFieldIDs().equals(bizkeys) || !floatOrderIndex.getFieldIDs().equals(requireColumns.floatOrder.getID())) {
            errorType |= ErrorType.ERROR.getValue();
        }
        return errorType |= this.checkIndex(tableModel.getID());
    }

    @Override
    protected void rebuild(DataTableModelDTO dataTableModel, int errorTypes) throws Exception {
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
        String bizkeys = DetailTableModelIndexBuilderImpl.getBizKeys(dataTableModel.getDataTable(), requireColumns);
        for (DesignIndexModelDefine index : dataTableModel.getIndexModels()) {
            if (index.getID().equals(AbstractTableModelIndexBuilder.IndexType.BIZKEYS.getIndexId(dataTableModel.getTableModel().getID())) || index.getID().equals(AbstractTableModelIndexBuilder.IndexType.FLOATORDER.getIndexId(dataTableModel.getTableModel().getID()))) {
                if (0 == (ErrorType.ERROR.getValue() & errorTypes)) continue;
                this.designDataModelService.deleteIndexModelDefine(index.getID());
                continue;
            }
            if (bizkeys.equals(index.getFieldIDs()) || requireColumns.floatOrder.getID().equals(index.getFieldIDs())) {
                if (0 == (ErrorType.WARNING.getValue() + ErrorType.ERROR.getValue() & errorTypes)) continue;
                this.designDataModelService.deleteIndexModelDefine(index.getID());
                continue;
            }
            if (0 == (ErrorType.TIP.getValue() & errorTypes)) continue;
            this.designDataModelService.deleteIndexModelDefine(index.getID());
        }
        if (0 != (ErrorType.ERROR.getValue() & errorTypes)) {
            tableModel.setKeys(requireColumns.bizKeyOrder.getID());
            tableModel.setBizKeys(bizkeys);
            this.designDataModelService.updateTableModelDefine(tableModel);
            DesignIndexModelDefine bizkeysIndex = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.BIZKEYS, tableModel.getID());
            bizkeysIndex.setFieldIDs(tableModel.getBizKeys());
            this.designDataModelService.addIndexModelDefine(bizkeysIndex);
            DesignIndexModelDefine floatOrderIndex = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.FLOATORDER, tableModel.getID());
            floatOrderIndex.setFieldIDs(requireColumns.floatOrder.getID());
            this.designDataModelService.addIndexModelDefine(floatOrderIndex);
        }
        this.rebuildIndexes(tableModel.getID());
    }
}

