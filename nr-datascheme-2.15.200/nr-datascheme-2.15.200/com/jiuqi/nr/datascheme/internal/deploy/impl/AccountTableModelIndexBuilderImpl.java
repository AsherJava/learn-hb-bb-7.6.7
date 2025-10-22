/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.datascheme.api.type.ErrorType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.IModelDefineItem
 *  com.jiuqi.nvwa.definition.facade.IndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.datascheme.api.type.ErrorType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.deploy.IDataTableDeployObjGetter;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataColumnModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.deploy.common.DesignTableDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.RuntimeDataTableDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AbstractTableModelIndexBuilder;
import com.jiuqi.nr.datascheme.internal.deploy.impl.AccountTableModelDeployObjBuilderImpl;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.IModelDefineItem;
import com.jiuqi.nvwa.definition.facade.IndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModelDefine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountTableModelIndexBuilderImpl
extends AbstractTableModelIndexBuilder {
    @Autowired
    private IDataTableDeployObjGetter iDataTableDeployObjGetter;

    @Override
    public DataTableType[] getDoForTableTypes() {
        return new DataTableType[]{DataTableType.ACCOUNT};
    }

    @Override
    public void build(DataTableDeployObj dataTable, List<TableModelDeployObj> tableModels) throws ModelValidateException, JQException {
        TableModelDeployObj tableModel = AccountTableModelDeployObjBuilderImpl.getTableModelDeployObj(tableModels);
        TableModelDeployObj hisTableModel = null;
        TableModelDeployObj rptTableModel = null;
        for (TableModelDeployObj obj : tableModels) {
            if (AccountTableModelDeployObjBuilderImpl.getRptTableName(tableModel.getTableModelCode()).equals(obj.getTableModelCode())) {
                rptTableModel = obj;
                continue;
            }
            if (!AccountTableModelDeployObjBuilderImpl.getHisTableName(tableModel.getTableModelCode()).equals(obj.getTableModelCode())) continue;
            hisTableModel = obj;
        }
        if (null == tableModel || null == hisTableModel || null == rptTableModel) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DEPLOY, "\u53f0\u8d26\u8868\u7f3a\u5c11\u5b58\u50a8\u8868\u6a21\u578b");
        }
        if (DeployType.ADD != dataTable.getState()) {
            this.removeAllIndexV1(dataTable.getDataTable(), rptTableModel, tableModel, hisTableModel);
        }
        this.updateIndex(dataTable, rptTableModel);
        this.updateIndex(dataTable, tableModel, hisTableModel);
    }

    private void updateIndex(DataTableDeployObj dataTable, TableModelDeployObj rptTableModel) throws ModelValidateException {
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTable, rptTableModel);
        DesignTableModel designTableModel = rptTableModel.getDesignTableModel();
        Map<String, DesignIndexModelDefine> indexes = designTableModel.getIndexs().stream().collect(Collectors.toMap(IndexModelDefine::getID, v -> v));
        designTableModel.setKeys(requireColumns.bizKeyOrder.getID());
        this.updateBizkeys(indexes, designTableModel, AccountTableModelIndexBuilderImpl.getRptBizKeys(requireColumns));
        DesignIndexModelDefine indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.SDT, designTableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getRptSDTIndexFields(requireColumns));
        this.updateIndexModel(designTableModel, indexes, indexModel);
    }

    private static String getRptSDTIndexFields(AbstractTableModelIndexBuilder.RequireColumns requireColumns) {
        return requireColumns.sbid.getID() + ";" + requireColumns.datatime.getID();
    }

    private static String getRptBizKeys(AbstractTableModelIndexBuilder.RequireColumns requireColumns) {
        ArrayList<DesignColumnModelDefine> bizkeys = new ArrayList<DesignColumnModelDefine>();
        bizkeys.add(requireColumns.mdcode);
        bizkeys.add(requireColumns.datatime);
        bizkeys.addAll(requireColumns.dims);
        bizkeys.add(requireColumns.sbid);
        return bizkeys.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
    }

    private void updateIndex(DataTableDeployObj dataTable, TableModelDeployObj tableModel, TableModelDeployObj hisTableModel) throws ModelValidateException {
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTable, tableModel);
        this.updateIndex(dataTable, tableModel, requireColumns);
        Map<String, DesignColumnModelDefine> codeColumns = hisTableModel.getAllColumns().entrySet().stream().collect(Collectors.toMap(e -> ((DesignColumnModelDefine)e.getValue()).getCode(), Map.Entry::getValue));
        DesignTableModel designTableModel = hisTableModel.getDesignTableModel();
        Map<String, DesignIndexModelDefine> indexes = designTableModel.getIndexs().stream().collect(Collectors.toMap(IndexModelDefine::getID, v -> v));
        designTableModel.setKeys(codeColumns.get("BIZKEYORDER").getID());
        designTableModel.setBizKeys(AccountTableModelIndexBuilderImpl.getHisBizKeys(codeColumns));
        DesignIndexModelDefine indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.BIZKEYS, designTableModel.getID());
        indexModel.setFieldIDs(designTableModel.getBizKeys());
        this.updateIndexModel(designTableModel, indexes, indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.VT, designTableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getHisVTIndexFields(codeColumns));
        this.updateIndexModel(designTableModel, indexes, indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.MVIDT, designTableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getHisMVIDTIndexFields(requireColumns, codeColumns));
        this.updateIndexModel(designTableModel, indexes, indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.MIDT, designTableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getHisMIDTIndexFields(requireColumns, codeColumns));
        this.updateIndexModel(designTableModel, indexes, indexModel);
    }

    private static String getHisMIDTIndexFields(AbstractTableModelIndexBuilder.RequireColumns requireColumns, Map<String, DesignColumnModelDefine> codeColumns) {
        ArrayList<DesignColumnModelDefine> fields = new ArrayList<DesignColumnModelDefine>();
        fields.add(codeColumns.get(requireColumns.mdcode.getCode()));
        for (DesignColumnModelDefine dim : requireColumns.dims) {
            fields.add(codeColumns.get(dim.getCode()));
        }
        fields.add(codeColumns.get("INVALIDDATATIME"));
        return fields.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
    }

    private static String getHisMVIDTIndexFields(AbstractTableModelIndexBuilder.RequireColumns requireColumns, Map<String, DesignColumnModelDefine> codeColumns) {
        ArrayList<DesignColumnModelDefine> fields = new ArrayList<DesignColumnModelDefine>();
        fields.add(codeColumns.get(requireColumns.mdcode.getCode()));
        for (DesignColumnModelDefine dim : requireColumns.dims) {
            fields.add(codeColumns.get(dim.getCode()));
        }
        fields.add(codeColumns.get("VALIDDATATIME"));
        fields.add(codeColumns.get("INVALIDDATATIME"));
        return fields.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
    }

    private static String getHisVTIndexFields(Map<String, DesignColumnModelDefine> codeColumns) {
        return codeColumns.get("VALIDTIME").getID();
    }

    private static String getHisBizKeys(Map<String, DesignColumnModelDefine> codeColumns) {
        return codeColumns.get("SBID").getID() + ";" + codeColumns.get("VALIDDATATIME").getID();
    }

    private void updateIndex(DataTableDeployObj dataTable, TableModelDeployObj tableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns) throws ModelValidateException {
        Map<String, DesignColumnModelDefine> codeColumns = tableModel.getAllColumns().entrySet().stream().collect(Collectors.toMap(e -> ((DesignColumnModelDefine)e.getValue()).getCode(), Map.Entry::getValue));
        DesignTableModel designTableModel = tableModel.getDesignTableModel();
        Map<String, DesignIndexModelDefine> indexes = designTableModel.getIndexs().stream().collect(Collectors.toMap(IndexModelDefine::getID, v -> v));
        designTableModel.setKeys(requireColumns.bizKeyOrder.getID());
        this.updateBizkeys(indexes, designTableModel, AccountTableModelIndexBuilderImpl.getBizKeys(dataTable.getDataTable(), requireColumns));
        DesignIndexModelDefine indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.SBID, designTableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getSBIDIndexFields(requireColumns));
        this.updateIndexModel(designTableModel, indexes, indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.MVDT, designTableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getMVDTIndexFields(requireColumns, codeColumns));
        this.updateIndexModel(designTableModel, indexes, indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.MT, designTableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getMTIndexFields(codeColumns));
        this.updateIndexModel(designTableModel, indexes, indexModel);
    }

    private static String getSBIDIndexFields(AbstractTableModelIndexBuilder.RequireColumns requireColumns) {
        return requireColumns.sbid.getID();
    }

    private static String getMTIndexFields(Map<String, DesignColumnModelDefine> codeColumns) {
        return codeColumns.get("MODIFYTIME").getID();
    }

    private static String getMVDTIndexFields(AbstractTableModelIndexBuilder.RequireColumns requireColumns, Map<String, DesignColumnModelDefine> codeColumns) {
        ArrayList<DesignColumnModelDefine> fields = new ArrayList<DesignColumnModelDefine>();
        fields.add(requireColumns.mdcode);
        fields.addAll(requireColumns.dims);
        fields.add(codeColumns.get("VALIDDATATIME"));
        return fields.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
    }

    private static String getBizKeys(DataTable dataTable, AbstractTableModelIndexBuilder.RequireColumns requireColumns) {
        ArrayList<DesignColumnModelDefine> bizkeys = new ArrayList<DesignColumnModelDefine>();
        bizkeys.add(requireColumns.mdcode);
        bizkeys.addAll(requireColumns.dims);
        bizkeys.addAll(requireColumns.dimsInTable);
        if (dataTable.isRepeatCode()) {
            bizkeys.add(requireColumns.sbid);
        }
        return bizkeys.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
    }

    private void updateBizkeys(Map<String, DesignIndexModelDefine> indexes, DesignTableModel designTableModel, String bizkeys) throws ModelValidateException {
        designTableModel.setBizKeys(bizkeys);
        DesignIndexModelDefine indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.BIZKEYS, designTableModel.getID());
        indexModel.setFieldIDs(bizkeys);
        this.updateIndexModel(designTableModel, indexes, indexModel);
    }

    private void removeAllIndexV1(DataTable dataTable, TableModelDeployObj rptTableModel, TableModelDeployObj tableModel, TableModelDeployObj hisTableModel) {
        String indexId = AbstractTableModelIndexBuilder.IndexType.BIZKEYS.getIndexId(rptTableModel.getTableModelKey());
        List indexes = rptTableModel.getDesignTableModel().getIndexs();
        for (DesignIndexModelDefine index2 : indexes) {
            if (!index2.getID().equals(indexId)) continue;
            return;
        }
        RuntimeDataTableDTO runtimeDataTable = this.iDataTableDeployObjGetter.getRuntimeDataTable(Collections.singletonList(dataTable.getKey())).stream().findFirst().orElse(null);
        if (null == runtimeDataTable) {
            return;
        }
        this.getIndexV1(runtimeDataTable, index -> rptTableModel.getDesignTableModel().removeIndex(index), index -> tableModel.getDesignTableModel().removeIndex(index), index -> hisTableModel.getDesignTableModel().removeIndex(index));
    }

    private void getIndexV1(RuntimeDataTableDTO runtimeDataTable, Consumer<DesignIndexModelDefine> rptIndexConsumer, Consumer<DesignIndexModelDefine> indexConsumer, Consumer<DesignIndexModelDefine> hisIndexConsumer) {
        List<DataTableModelDTO> dataTableModels = runtimeDataTable.getDataTableModels();
        for (DataTableModelDTO dataTableModel : dataTableModels) {
            if (dataTableModel.getExtendTableModels().isEmpty()) {
                this.getRptIndexV1(dataTableModel, rptIndexConsumer);
                continue;
            }
            this.getIndexV1(dataTableModel, indexConsumer, hisIndexConsumer);
        }
    }

    private void getRptIndexV1(DataTableModelDTO dataTableModel, Consumer<DesignIndexModelDefine> rptIndexConsumer) {
        String tableId = dataTableModel.getTableModel().getID();
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
        String index1 = requireColumns.sbid.getID() + ";" + requireColumns.datatime.getID();
        String index2 = requireColumns.mdcode.getID() + ";" + requireColumns.datatime.getID() + requireColumns.dims.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";")) + ";" + requireColumns.sbid.getID();
        List<DesignIndexModelDefine> indexModels = dataTableModel.getIndexModels();
        for (DesignIndexModelDefine indexModel : indexModels) {
            if (AbstractTableModelIndexBuilder.IndexType.containId(tableId, indexModel.getID()) || !index1.equals(indexModel.getFieldIDs()) && !index2.equals(indexModel.getFieldIDs())) continue;
            rptIndexConsumer.accept(indexModel);
        }
    }

    private void getIndexV1(DataTableModelDTO dataTableModel, Consumer<DesignIndexModelDefine> indexConsumer, Consumer<DesignIndexModelDefine> hisIndexConsumer) {
        String tableId = dataTableModel.getTableModel().getID();
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
        String index1 = requireColumns.sbid.getID();
        ArrayList<DesignColumnModelDefine> columns = new ArrayList<DesignColumnModelDefine>();
        columns.add(requireColumns.mdcode);
        columns.addAll(requireColumns.dims);
        columns.addAll(requireColumns.dimsInTable);
        if (dataTableModel.getDataTable().isRepeatCode()) {
            columns.add(requireColumns.sbid);
        }
        String index2 = columns.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
        columns.clear();
        columns.add(requireColumns.mdcode);
        columns.addAll(requireColumns.dims);
        columns.addAll(requireColumns.dimsInTable);
        columns.add(requireColumns.floatOrder);
        columns.add(requireColumns.sbid);
        String index3 = columns.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
        List<DesignIndexModelDefine> indexModels = dataTableModel.getIndexModels();
        for (DesignIndexModelDefine indexModel : indexModels) {
            if (AbstractTableModelIndexBuilder.IndexType.containId(tableId, indexModel.getID()) || !index1.equals(indexModel.getFieldIDs()) && !index2.equals(indexModel.getFieldIDs()) && !index3.equals(indexModel.getFieldIDs())) continue;
            indexConsumer.accept(indexModel);
        }
        this.getHisIndexV1(dataTableModel, requireColumns, hisIndexConsumer);
    }

    private void getHisIndexV1(DataTableModelDTO dataTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns, Consumer<DesignIndexModelDefine> hisIndexConsumer) {
        DesignTableDTO extendTableModel = dataTableModel.getExtendTableModels().values().stream().findFirst().orElse(null);
        if (null == extendTableModel) {
            return;
        }
        String tableId = extendTableModel.getTableModel().getID();
        ArrayList<DesignColumnModelDefine> columns = new ArrayList<DesignColumnModelDefine>();
        Map<String, DesignColumnModelDefine> columnCodeMap = extendTableModel.getColumnCodeMap();
        String index4 = columnCodeMap.get("SBID").getID() + ";" + columnCodeMap.get("VALIDDATATIME").getID();
        columns.add(columnCodeMap.get("MDCODE"));
        for (DesignColumnModelDefine c : requireColumns.dims) {
            columns.add(columnCodeMap.get(c.getCode()));
        }
        columns.add(columnCodeMap.get("VALIDDATATIME"));
        columns.add(columnCodeMap.get("INVALIDDATATIME"));
        String index5 = columns.stream().map(IModelDefineItem::getID).collect(Collectors.joining(";"));
        for (DesignIndexModelDefine indexModel : extendTableModel.getIndexModels()) {
            if (AbstractTableModelIndexBuilder.IndexType.containId(tableId, indexModel.getID()) || !index4.equals(indexModel.getFieldIDs()) && !index5.equals(indexModel.getFieldIDs())) continue;
            hisIndexConsumer.accept(indexModel);
        }
    }

    @Override
    protected int check(DataTableModelDTO dataTableModel) throws Exception {
        Map<String, DesignTableDTO> extendTableModels = dataTableModel.getExtendTableModels();
        if (extendTableModels.isEmpty()) {
            return this.checkRptTableIndex(dataTableModel);
        }
        return this.checkTableIndex(dataTableModel);
    }

    private int checkTableIndex(DataTableModelDTO dataTableModel) throws Exception {
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
        Map<String, DesignIndexModelDefine> indexIdMap = dataTableModel.getIndexIdMap();
        DesignTableDTO extendTableModel = dataTableModel.getExtendTableModels().values().stream().findFirst().orElse(null);
        if (null == extendTableModel) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DEPLOY, "\u53f0\u8d26\u8868\u7f3a\u5c11\u5b58\u50a8\u8868\u6a21\u578b");
        }
        Map<String, DesignIndexModelDefine> hisIndexIdMap = extendTableModel.getIndexIdMap();
        int errorType = this.checkTableIndexError(dataTableModel, requireColumns, indexIdMap);
        errorType |= this.checkIndex(dataTableModel.getTableModel().getID());
        errorType |= this.checkHisTableIndex(extendTableModel, requireColumns, hisIndexIdMap);
        int indexSize = indexIdMap.size() + hisIndexIdMap.size();
        this.getIndexV1(dataTableModel, index -> {
            DesignIndexModelDefine cfr_ignored_0 = (DesignIndexModelDefine)indexIdMap.remove(index.getID());
        }, index -> {
            DesignIndexModelDefine cfr_ignored_0 = (DesignIndexModelDefine)hisIndexIdMap.remove(index.getID());
        });
        if (indexSize != indexIdMap.size() + hisIndexIdMap.size()) {
            errorType |= ErrorType.WARNING.getValue();
        }
        if (!indexIdMap.isEmpty() || !hisIndexIdMap.isEmpty()) {
            errorType |= ErrorType.TIP.getValue();
        }
        return errorType;
    }

    private int checkTableIndexError(DataTableModelDTO dataTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns, Map<String, DesignIndexModelDefine> indexes) {
        int errorType = ErrorType.NONE.getValue();
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        String bizkeys = AccountTableModelIndexBuilderImpl.getBizKeys(dataTableModel.getDataTable(), requireColumns);
        if (!requireColumns.bizKeyOrder.getID().equals(tableModel.getKeys()) || !bizkeys.equals(tableModel.getBizKeys())) {
            errorType |= ErrorType.ERROR.getValue();
        }
        Map<String, DesignColumnModelDefine> codeColumns = dataTableModel.getDataColumnModels().stream().map(DataColumnModelDTO::getColumnModel).collect(Collectors.toMap(IModelDefineItem::getCode, v -> v));
        DesignIndexModelDefine index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.BIZKEYS.getIndexId(tableModel.getID()));
        errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, bizkeys).getValue();
        index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.MVDT.getIndexId(tableModel.getID()));
        errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, AccountTableModelIndexBuilderImpl.getMVDTIndexFields(requireColumns, codeColumns)).getValue();
        index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.SBID.getIndexId(tableModel.getID()));
        errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, AccountTableModelIndexBuilderImpl.getSBIDIndexFields(requireColumns)).getValue();
        index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.MT.getIndexId(tableModel.getID()));
        return errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, AccountTableModelIndexBuilderImpl.getMTIndexFields(codeColumns)).getValue();
    }

    private int checkHisTableIndex(DesignTableDTO extendTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns, Map<String, DesignIndexModelDefine> indexes) throws Exception {
        return this.checkHisTableIndexError(extendTableModel, requireColumns, indexes) | this.checkIndex(extendTableModel.getTableModel().getID());
    }

    private int checkHisTableIndexError(DesignTableDTO extendTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns, Map<String, DesignIndexModelDefine> indexes) {
        int errorType = ErrorType.NONE.getValue();
        DesignTableModelDefine tableModel = extendTableModel.getTableModel();
        Map<String, DesignColumnModelDefine> codeColumns = extendTableModel.getColumnCodeMap();
        String bizkeys = AccountTableModelIndexBuilderImpl.getHisBizKeys(codeColumns);
        if (!codeColumns.get("BIZKEYORDER").getID().equals(tableModel.getKeys()) || !bizkeys.equals(tableModel.getBizKeys())) {
            errorType |= ErrorType.ERROR.getValue();
        }
        DesignIndexModelDefine index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.BIZKEYS.getIndexId(tableModel.getID()));
        errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, bizkeys).getValue();
        index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.MVIDT.getIndexId(tableModel.getID()));
        errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, AccountTableModelIndexBuilderImpl.getHisMVIDTIndexFields(requireColumns, codeColumns)).getValue();
        index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.MIDT.getIndexId(tableModel.getID()));
        errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, AccountTableModelIndexBuilderImpl.getHisMIDTIndexFields(requireColumns, codeColumns)).getValue();
        index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.VT.getIndexId(tableModel.getID()));
        return errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, AccountTableModelIndexBuilderImpl.getHisVTIndexFields(codeColumns)).getValue();
    }

    private int checkRptTableIndex(DataTableModelDTO dataTableModel) throws Exception {
        Map<String, DesignIndexModelDefine> indexIdMap = dataTableModel.getIndexIdMap();
        int errorType = this.checkRptTableIndexError(dataTableModel, indexIdMap) | this.checkIndex(dataTableModel.getTableModel().getID());
        int indexSize = indexIdMap.size();
        this.getRptIndexV1(dataTableModel, index -> {
            DesignIndexModelDefine cfr_ignored_0 = (DesignIndexModelDefine)indexIdMap.remove(index.getID());
        });
        if (indexSize != indexIdMap.size()) {
            errorType |= ErrorType.WARNING.getValue();
        }
        if (!indexIdMap.isEmpty()) {
            errorType |= ErrorType.TIP.getValue();
        }
        return errorType;
    }

    private int checkRptTableIndexError(DataTableModelDTO dataTableModel, Map<String, DesignIndexModelDefine> indexes) throws Exception {
        int errorType = ErrorType.NONE.getValue();
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
        String bizkeys = AccountTableModelIndexBuilderImpl.getRptBizKeys(requireColumns);
        if (!requireColumns.bizKeyOrder.getID().equals(tableModel.getKeys()) || !bizkeys.equals(tableModel.getBizKeys())) {
            errorType |= ErrorType.ERROR.getValue();
        }
        DesignIndexModelDefine index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.BIZKEYS.getIndexId(tableModel.getID()));
        errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, bizkeys).getValue();
        index = indexes.remove(AbstractTableModelIndexBuilder.IndexType.SDT.getIndexId(tableModel.getID()));
        return errorType |= AccountTableModelIndexBuilderImpl.getIndexErrorType(index, AccountTableModelIndexBuilderImpl.getRptSDTIndexFields(requireColumns)).getValue();
    }

    private static ErrorType getIndexErrorType(DesignIndexModelDefine index, String fields) {
        if (null == index) {
            return ErrorType.ERROR;
        }
        if (!fields.equals(index.getFieldIDs())) {
            return ErrorType.ERROR;
        }
        return ErrorType.NONE;
    }

    @Override
    protected void rebuild(DataTableModelDTO dataTableModel, int errorTypes) throws Exception {
        Map<String, DesignTableDTO> extendTableModels = dataTableModel.getExtendTableModels();
        if (extendTableModels.isEmpty()) {
            this.rebuildRptTableIndex(dataTableModel, errorTypes);
        } else {
            this.rebuildTableIndex(dataTableModel, errorTypes);
        }
    }

    private void rebuildTableIndex(DataTableModelDTO dataTableModel, int errorTypes) throws Exception {
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        DesignTableDTO extendTableModel = dataTableModel.getExtendTableModels().values().stream().findFirst().orElse(null);
        if (null == extendTableModel) {
            throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DEPLOY, "\u53f0\u8d26\u8868\u7f3a\u5c11\u5b58\u50a8\u8868\u6a21\u578b");
        }
        Map<String, DesignIndexModelDefine> indexIdMap = dataTableModel.getIndexIdMap();
        Map<String, DesignIndexModelDefine> hisIndexIdMap = extendTableModel.getIndexIdMap();
        this.getIndexV1(dataTableModel, index -> {
            if (0 != (ErrorType.WARNING.getValue() & errorTypes)) {
                this.designDataModelService.deleteIndexModelDefine(index.getID());
            }
            if (0 != (ErrorType.ERROR.getValue() & errorTypes)) {
                this.designDataModelService.deleteIndexModelDefine(index.getID());
            }
            indexIdMap.remove(index.getID());
        }, index -> {
            if (0 != (ErrorType.WARNING.getValue() & errorTypes)) {
                this.designDataModelService.deleteIndexModelDefine(index.getID());
            }
            if (0 != (ErrorType.ERROR.getValue() & errorTypes)) {
                this.designDataModelService.deleteIndexModelDefine(index.getID());
            }
            hisIndexIdMap.remove(index.getID());
        });
        this.removeErrorOrTipIndex(tableModel, indexIdMap, errorTypes);
        this.removeErrorOrTipIndex(extendTableModel.getTableModel(), hisIndexIdMap, errorTypes);
        if (0 != (ErrorType.ERROR.getValue() & errorTypes)) {
            AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
            this.addAllIndex(dataTableModel, requireColumns);
            this.addAllHisIndex(extendTableModel, requireColumns);
        }
        this.rebuildIndexes(tableModel.getID());
        this.rebuildIndexes(extendTableModel.getTableModel().getID());
    }

    private void addAllIndex(DataTableModelDTO dataTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns) throws ModelValidateException {
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        String bizkeys = AccountTableModelIndexBuilderImpl.getBizKeys(dataTableModel.getDataTable(), requireColumns);
        tableModel.setKeys(requireColumns.bizKeyOrder.getID());
        tableModel.setBizKeys(bizkeys);
        this.designDataModelService.updateTableModelDefine(tableModel);
        DesignIndexModelDefine indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.BIZKEYS, tableModel.getID());
        indexModel.setFieldIDs(tableModel.getBizKeys());
        this.designDataModelService.addIndexModelDefine(indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.SBID, tableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getSBIDIndexFields(requireColumns));
        this.designDataModelService.addIndexModelDefine(indexModel);
        Map<String, DesignColumnModelDefine> codeColumns = dataTableModel.getDataColumnModels().stream().map(DataColumnModelDTO::getColumnModel).collect(Collectors.toMap(IModelDefineItem::getCode, v -> v));
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.MT, tableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getMTIndexFields(codeColumns));
        this.designDataModelService.addIndexModelDefine(indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.MVDT, tableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getMVDTIndexFields(requireColumns, codeColumns));
        this.designDataModelService.addIndexModelDefine(indexModel);
    }

    private void addAllHisIndex(DesignTableDTO extendTableModel, AbstractTableModelIndexBuilder.RequireColumns requireColumns) throws Exception {
        DesignTableModelDefine tableModel = extendTableModel.getTableModel();
        Map<String, DesignColumnModelDefine> codeColumns = extendTableModel.getColumnCodeMap();
        String bizkeys = AccountTableModelIndexBuilderImpl.getHisBizKeys(codeColumns);
        tableModel.setKeys(codeColumns.get("BIZKEYORDER").getID());
        tableModel.setBizKeys(bizkeys);
        this.designDataModelService.updateTableModelDefine(tableModel);
        DesignIndexModelDefine indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.BIZKEYS, tableModel.getID());
        indexModel.setFieldIDs(tableModel.getBizKeys());
        this.designDataModelService.addIndexModelDefine(indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.VT, tableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getHisVTIndexFields(codeColumns));
        this.designDataModelService.addIndexModelDefine(indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.MIDT, tableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getHisMIDTIndexFields(requireColumns, codeColumns));
        this.designDataModelService.addIndexModelDefine(indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.MVIDT, tableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getHisMVIDTIndexFields(requireColumns, codeColumns));
        this.designDataModelService.addIndexModelDefine(indexModel);
    }

    private void rebuildRptTableIndex(DataTableModelDTO dataTableModel, int errorTypes) throws Exception {
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        Map<String, DesignIndexModelDefine> indexIdMap = dataTableModel.getIndexIdMap();
        this.getRptIndexV1(dataTableModel, index -> {
            if (0 != (ErrorType.WARNING.getValue() & errorTypes)) {
                this.designDataModelService.deleteIndexModelDefine(index.getID());
            }
            if (0 != (ErrorType.ERROR.getValue() & errorTypes)) {
                this.designDataModelService.deleteIndexModelDefine(index.getID());
            }
            indexIdMap.remove(index.getID());
        });
        this.removeErrorOrTipIndex(tableModel, indexIdMap, errorTypes);
        if (0 != (ErrorType.ERROR.getValue() & errorTypes)) {
            this.addAllRptIndex(dataTableModel);
        }
        this.rebuildIndexes(tableModel.getID());
    }

    private void addAllRptIndex(DataTableModelDTO dataTableModel) throws ModelValidateException {
        DesignTableModelDefine tableModel = dataTableModel.getTableModel();
        AbstractTableModelIndexBuilder.RequireColumns requireColumns = this.getRequireColumns(dataTableModel);
        String bizkeys = AccountTableModelIndexBuilderImpl.getRptBizKeys(requireColumns);
        tableModel.setKeys(requireColumns.bizKeyOrder.getID());
        tableModel.setBizKeys(bizkeys);
        this.designDataModelService.updateTableModelDefine(tableModel);
        DesignIndexModelDefine indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.BIZKEYS, tableModel.getID());
        indexModel.setFieldIDs(tableModel.getBizKeys());
        this.designDataModelService.addIndexModelDefine(indexModel);
        indexModel = this.createIndexModel(AbstractTableModelIndexBuilder.IndexType.SDT, tableModel.getID());
        indexModel.setFieldIDs(AccountTableModelIndexBuilderImpl.getRptSDTIndexFields(requireColumns));
        this.designDataModelService.addIndexModelDefine(indexModel);
    }

    private void removeErrorOrTipIndex(DesignTableModelDefine tableModel, Map<String, DesignIndexModelDefine> indexIdMap, int errorTypes) {
        for (String indexId : indexIdMap.keySet()) {
            if (!AbstractTableModelIndexBuilder.IndexType.containId(tableModel.getID(), indexId)) {
                if (0 == (ErrorType.TIP.getValue() & errorTypes)) continue;
                this.designDataModelService.deleteIndexModelDefine(indexId);
                continue;
            }
            if (0 == (ErrorType.ERROR.getValue() & errorTypes)) continue;
            this.designDataModelService.deleteIndexModelDefine(indexId);
        }
    }
}

