/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.ErrorType
 *  com.jiuqi.nvwa.definition.common.IndexModelType
 *  com.jiuqi.nvwa.definition.common.exception.ModelValidateException
 *  com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine
 *  com.jiuqi.nvwa.definition.facade.design.DesignTableModel
 *  com.jiuqi.nvwa.definition.service.DataModelDeployService
 *  com.jiuqi.nvwa.definition.service.DesignDataModelService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.datascheme.internal.deploy.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.ErrorType;
import com.jiuqi.nr.datascheme.common.DataSchemeEnum;
import com.jiuqi.nr.datascheme.internal.deploy.ITableModelIndexBuilder;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataColumnModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableDeployObj;
import com.jiuqi.nr.datascheme.internal.deploy.common.DataTableModelDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.DeployType;
import com.jiuqi.nr.datascheme.internal.deploy.common.RuntimeDataTableDTO;
import com.jiuqi.nr.datascheme.internal.deploy.common.TableModelDeployObj;
import com.jiuqi.nr.datascheme.internal.entity.DataFieldDeployInfoDO;
import com.jiuqi.nvwa.definition.common.IndexModelType;
import com.jiuqi.nvwa.definition.common.exception.ModelValidateException;
import com.jiuqi.nvwa.definition.facade.design.DesignColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignIndexModelDefine;
import com.jiuqi.nvwa.definition.facade.design.DesignTableModel;
import com.jiuqi.nvwa.definition.service.DataModelDeployService;
import com.jiuqi.nvwa.definition.service.DesignDataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractTableModelIndexBuilder
implements ITableModelIndexBuilder {
    @Autowired
    protected DesignDataModelService designDataModelService;
    @Autowired
    protected DataModelDeployService dataModelDeployService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTableModelIndexBuilder.class);

    protected RequireDataFields getRequireDataFields(DataTableDeployObj dataTable) {
        RequireDataFields obj = new RequireDataFields();
        List<DataField> requireDataFields = dataTable.getRequireDataFields();
        for (DataField field : requireDataFields) {
            if (DataFieldKind.PUBLIC_FIELD_DIM == field.getDataFieldKind()) {
                if ("MDCODE".equals(field.getCode())) {
                    obj.mdcode = field;
                    continue;
                }
                if ("DATATIME".equals(field.getCode())) {
                    obj.datatime = field;
                    continue;
                }
                obj.dims.add(field);
                continue;
            }
            if (DataFieldKind.TABLE_FIELD_DIM == field.getDataFieldKind()) {
                obj.dimsInTable.add(field);
                continue;
            }
            if (DataFieldKind.BUILT_IN_FIELD != field.getDataFieldKind()) continue;
            if ("SBID".equals(field.getCode())) {
                obj.sbid = field;
                continue;
            }
            if ("BIZKEYORDER".equals(field.getCode())) {
                obj.bizKeyOrder = field;
                continue;
            }
            if (!"FLOATORDER".equals(field.getCode())) continue;
            obj.floatOrder = field;
        }
        return obj;
    }

    protected RequireColumns getRequireColumns(DataTableDeployObj dataTable, TableModelDeployObj tableModel) {
        DesignColumnModelDefine column;
        HashMap<String, String> dataFieldToColumn = new HashMap<String, String>();
        List<DataFieldDeployInfoDO> deployInfos = dataTable.getDeployInfoByTableModelKey(tableModel.getTableModelKey());
        for (DataFieldDeployInfoDO dataFieldDeployInfoDO : deployInfos) {
            dataFieldToColumn.put(dataFieldDeployInfoDO.getDataFieldKey(), dataFieldDeployInfoDO.getColumnModelKey());
        }
        Collection<DataFieldDeployInfoDO> addDeployInfos = tableModel.getAddDeployInfos();
        for (DataFieldDeployInfoDO dataFieldDeployInfoDO : addDeployInfos) {
            dataFieldToColumn.put(dataFieldDeployInfoDO.getDataFieldKey(), dataFieldDeployInfoDO.getColumnModelKey());
        }
        Collection<DataFieldDeployInfoDO> collection = tableModel.getUpdateDeployInfos();
        for (DataFieldDeployInfoDO info : collection) {
            dataFieldToColumn.put(info.getDataFieldKey(), info.getColumnModelKey());
        }
        RequireDataFields requireDataFields = this.getRequireDataFields(dataTable);
        RequireColumns obj = new RequireColumns();
        obj.sbid = null == requireDataFields.sbid ? null : tableModel.getAllColumns().get(dataFieldToColumn.get(requireDataFields.sbid.getKey()));
        obj.bizKeyOrder = null == requireDataFields.bizKeyOrder ? null : tableModel.getAllColumns().get(dataFieldToColumn.get(requireDataFields.bizKeyOrder.getKey()));
        obj.floatOrder = null == requireDataFields.floatOrder ? null : tableModel.getAllColumns().get(dataFieldToColumn.get(requireDataFields.floatOrder.getKey()));
        obj.mdcode = null == requireDataFields.mdcode ? null : tableModel.getAllColumns().get(dataFieldToColumn.get(requireDataFields.mdcode.getKey()));
        obj.datatime = null == requireDataFields.datatime ? null : tableModel.getAllColumns().get(dataFieldToColumn.get(requireDataFields.datatime.getKey()));
        for (DataField dim : requireDataFields.dims) {
            column = tableModel.getAllColumns().get(dataFieldToColumn.get(dim.getKey()));
            if (null == column) continue;
            obj.dims.add(column);
        }
        for (DataField dim : requireDataFields.dimsInTable) {
            column = tableModel.getAllColumns().get(dataFieldToColumn.get(dim.getKey()));
            if (null == column) continue;
            obj.dimsInTable.add(column);
        }
        return obj;
    }

    protected DesignIndexModelDefine createIndexModel(IndexType type, String tableId) {
        DesignIndexModelDefine indexModel = this.designDataModelService.createIndexModel();
        indexModel.setID(type.getIndexId(tableId));
        indexModel.setTableID(tableId);
        indexModel.setType(type.type);
        indexModel.setName(type.type.name() + "_" + type.name() + "_" + OrderGenerator.newOrder());
        return indexModel;
    }

    protected void updateIndexModel(DesignTableModel tableModel, Map<String, DesignIndexModelDefine> indexes, DesignIndexModelDefine indexModel) throws ModelValidateException {
        DesignIndexModelDefine define = indexes.get(indexModel.getID());
        if (null != define) {
            define.setFieldIDs(indexModel.getFieldIDs());
            tableModel.modifyIndex(define);
        } else {
            for (DesignIndexModelDefine index : indexes.values()) {
                String[] newFields;
                String[] oldFields = index.getFieldIDs().split(";");
                if (oldFields.length != (newFields = indexModel.getFieldIDs().split(";")).length) continue;
                HashSet<String> fields = new HashSet<String>();
                fields.addAll(Arrays.asList(oldFields));
                fields.addAll(Arrays.asList(newFields));
                if (oldFields.length != fields.size()) continue;
                tableModel.removeIndex(index);
            }
            tableModel.addIndex(indexModel);
        }
    }

    @Override
    public void doBuild(DataTableDeployObj dataTable, List<TableModelDeployObj> tableModels) throws ModelValidateException, JQException {
        if (DeployType.DELETE == dataTable.getState()) {
            return;
        }
        this.build(dataTable, tableModels);
    }

    protected abstract void build(DataTableDeployObj var1, List<TableModelDeployObj> var2) throws ModelValidateException, JQException;

    protected RequireColumns getRequireColumns(DataTableModelDTO dataTableModel) {
        RequireColumns requireColumns = new RequireColumns();
        List<DataColumnModelDTO> dataColumnModels = dataTableModel.getDataColumnModels();
        ArrayList<DataColumnModelDTO> dims = new ArrayList<DataColumnModelDTO>();
        ArrayList<DataColumnModelDTO> dimsInTable = new ArrayList<DataColumnModelDTO>();
        for (DataColumnModelDTO dataColumnModel : dataColumnModels) {
            DataField dataField = dataColumnModel.getDataField();
            if (null == dataField) continue;
            if (DataFieldKind.PUBLIC_FIELD_DIM == dataField.getDataFieldKind()) {
                if ("MDCODE".equals(dataField.getCode())) {
                    requireColumns.mdcode = dataColumnModel.getColumnModel();
                    continue;
                }
                if ("DATATIME".equals(dataField.getCode())) {
                    requireColumns.datatime = dataColumnModel.getColumnModel();
                    continue;
                }
                dims.add(dataColumnModel);
                continue;
            }
            if (DataFieldKind.TABLE_FIELD_DIM == dataField.getDataFieldKind()) {
                dimsInTable.add(dataColumnModel);
                continue;
            }
            if (DataFieldKind.BUILT_IN_FIELD != dataField.getDataFieldKind()) continue;
            if ("SBID".equals(dataColumnModel.getColumnModel().getCode())) {
                requireColumns.sbid = dataColumnModel.getColumnModel();
                continue;
            }
            if ("BIZKEYORDER".equals(dataColumnModel.getColumnModel().getCode())) {
                requireColumns.bizKeyOrder = dataColumnModel.getColumnModel();
                continue;
            }
            if (!"FLOATORDER".equals(dataColumnModel.getColumnModel().getCode())) continue;
            requireColumns.floatOrder = dataColumnModel.getColumnModel();
        }
        requireColumns.dims = dims.stream().sorted().map(DataColumnModelDTO::getColumnModel).collect(Collectors.toList());
        requireColumns.dimsInTable = dimsInTable.stream().sorted().map(DataColumnModelDTO::getColumnModel).collect(Collectors.toList());
        return requireColumns;
    }

    protected int checkIndex(String tableId) throws Exception {
        return ErrorType.NONE.getValue();
    }

    @Override
    public int doCheck(RuntimeDataTableDTO dataTable) throws JQException {
        LOGGER.info("\u6570\u636e\u65b9\u6848\uff1a\u68c0\u67e5\u6570\u636e\u8868{}[{}]\u7684\u7d22\u5f15", (Object)dataTable.getDataTable().getTitle(), (Object)dataTable.getDataTable().getCode());
        int errorType = ErrorType.NONE.getValue();
        List<DataTableModelDTO> dataTableModels = dataTable.getDataTableModels();
        for (DataTableModelDTO dataTableModel : dataTableModels) {
            try {
                errorType |= this.check(dataTableModel);
            }
            catch (Exception e) {
                LOGGER.error("\u6570\u636e\u65b9\u6848\uff1a\u68c0\u67e5\u6570\u636e\u8868{}[{}]\u7684\u7d22\u5f15\u5f02\u5e38", dataTable.getDataTable().getTitle(), dataTable.getDataTable().getCode(), e);
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DEPLOY, "\u7d22\u5f15\u68c0\u67e5\u5931\u8d25", (Throwable)e);
            }
        }
        return errorType;
    }

    protected abstract int check(DataTableModelDTO var1) throws Exception;

    @Override
    @Transactional
    public void doRebuild(RuntimeDataTableDTO dataTable, int errorTypes) throws JQException {
        if (ErrorType.NONE.getValue() == errorTypes) {
            return;
        }
        LOGGER.info("\u6570\u636e\u65b9\u6848\uff1a\u91cd\u6784\u6570\u636e\u8868{}[{}]\u7684\u7d22\u5f15", (Object)dataTable.getDataTable().getTitle(), (Object)dataTable.getDataTable().getCode());
        List<DataTableModelDTO> dataTableModels = dataTable.getDataTableModels();
        for (DataTableModelDTO dataTableModel : dataTableModels) {
            try {
                this.rebuild(dataTableModel, errorTypes);
            }
            catch (Exception e) {
                LOGGER.error("\u6570\u636e\u65b9\u6848\uff1a\u91cd\u6784\u6570\u636e\u8868{}[{}]\u7684\u7d22\u5f15\u5931\u8d25", dataTable.getDataTable().getTitle(), dataTable.getDataTable().getCode(), e);
                throw new JQException((ErrorEnum)DataSchemeEnum.DATA_SCHEME_DEPLOY, "\u7d22\u5f15\u91cd\u6784\u5931\u8d25", (Throwable)e);
            }
        }
    }

    protected abstract void rebuild(DataTableModelDTO var1, int var2) throws Exception;

    protected void rebuildIndexes(String tableId) throws Exception {
        this.dataModelDeployService.deployTable(tableId);
    }

    protected static enum IndexType {
        BIZKEYS("_bizkeys", IndexModelType.UNIQUE, "\u4e1a\u52a1\u4e3b\u952e\u7684\u552f\u4e00\u7d22\u5f15"),
        FLOATORDER("_order", IndexModelType.NORMAL, "\u6392\u5e8f\u5b57\u6bb5\u7d22\u5f15"),
        SBID("_sbid", IndexModelType.UNIQUE, "\u53f0\u8d26\u8bb0\u5f55ID\u7684\u552f\u4e00\u7d22\u5f15"),
        MVDT("_mvdt", IndexModelType.NORMAL, "\u5f15\u64ce\u8fde\u8868\u67e5\u8be2\u4f7f\u7528"),
        MT("_mt", IndexModelType.NORMAL, "\u5ba1\u6838\u4f7f\u7528"),
        MVIDT("_mvidt", IndexModelType.NORMAL, "\u5f15\u64ce\u8fde\u8868\u67e5\u8be2\u4f7f\u7528"),
        MIDT("_midt", IndexModelType.NORMAL, "\u6743\u9650\u5224\u65ad\u4f7f\u7528"),
        VT("_vt", IndexModelType.NORMAL, "\u4e34\u65f6\u8868\u4f7f\u7528"),
        SDT("_sdt", IndexModelType.NORMAL, "");

        final String suffix;
        final IndexModelType type;
        final String desc;

        private IndexType(String suffix, IndexModelType type, String desc) {
            this.suffix = suffix;
            this.desc = desc;
            this.type = type;
        }

        String getIndexId(String tableId) {
            return tableId.concat(this.suffix);
        }

        static boolean containId(String tableId, String indexId) {
            String replaceFirst = indexId.replaceFirst(tableId, "");
            if (indexId.equals(replaceFirst)) {
                return false;
            }
            for (IndexType t : IndexType.values()) {
                if (!t.suffix.equals(replaceFirst)) continue;
                return true;
            }
            return false;
        }
    }

    protected static class RequireColumns {
        DesignColumnModelDefine sbid = null;
        DesignColumnModelDefine bizKeyOrder = null;
        DesignColumnModelDefine floatOrder = null;
        DesignColumnModelDefine mdcode = null;
        DesignColumnModelDefine datatime = null;
        List<DesignColumnModelDefine> dims = new ArrayList<DesignColumnModelDefine>();
        List<DesignColumnModelDefine> dimsInTable = new ArrayList<DesignColumnModelDefine>();

        protected RequireColumns() {
        }
    }

    protected static class RequireDataFields {
        DataField sbid = null;
        DataField bizKeyOrder = null;
        DataField floatOrder = null;
        DataField mdcode = null;
        DataField datatime = null;
        List<DataField> dims = new ArrayList<DataField>();
        List<DataField> dimsInTable = new ArrayList<DataField>();

        protected RequireDataFields() {
        }
    }
}

