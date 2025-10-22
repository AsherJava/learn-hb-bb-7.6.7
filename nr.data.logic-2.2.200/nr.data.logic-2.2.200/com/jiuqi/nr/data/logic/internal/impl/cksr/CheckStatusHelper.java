/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.logic.internal.impl.cksr;

import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.api.param.cksr.CheckStatusQueryPar;
import com.jiuqi.nr.data.logic.internal.impl.cksr.obj.CheckStatusTableInfo;
import com.jiuqi.nr.data.logic.internal.util.SplitCheckTableHelper;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class CheckStatusHelper {
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private SplitCheckTableHelper splitCheckTableHelper;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    private static final Logger logger = LoggerFactory.getLogger(CheckStatusHelper.class);

    @Nullable
    public CheckStatusTableInfo checkTableModel(@NonNull FormSchemeDefine formScheme) {
        String splitCKSTableName = this.splitCheckTableHelper.getSplitCKSTableName(formScheme);
        TableModelDefine cksTable = this.dataModelService.getTableModelDefineByName(splitCKSTableName);
        if (cksTable == null) {
            logger.error("CKS\u8868\u4e0d\u5b58\u5728{}", (Object)splitCKSTableName);
            return null;
        }
        String splitCKSSubTableName = this.splitCheckTableHelper.getSplitCKSSubTableName(formScheme);
        TableModelDefine cksSubTable = this.dataModelService.getTableModelDefineByName(splitCKSSubTableName);
        if (cksSubTable == null) {
            logger.error("CKS_S\u5b50\u8868\u4e0d\u5b58\u5728{}", (Object)splitCKSSubTableName);
            return null;
        }
        return new CheckStatusTableInfo(cksTable, cksSubTable, formScheme);
    }

    public NvwaQueryModel getDetailQueryModel(CheckStatusQueryPar par, @NonNull CheckStatusTableInfo tableInfo) {
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        HashMap<String, ColumnModelDefine> colMap = new HashMap<String, ColumnModelDefine>();
        List cksCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksTable().getID());
        List cksSubCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksSubTable().getID());
        cksCols.forEach(cksCol -> {
            colMap.put(cksCol.getCode(), (ColumnModelDefine)cksCol);
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(cksCol));
        });
        cksSubCols.forEach(cksSubCol -> {
            colMap.put(cksSubCol.getCode(), (ColumnModelDefine)cksSubCol);
            nvwaQueryModel.getColumns().add(new NvwaQueryColumn(cksSubCol));
        });
        nvwaQueryModel.setMainTableName(tableInfo.getCksTable().getName());
        CheckStatusHelper.appendQueryFilter(par, nvwaQueryModel, colMap);
        this.appendDimFilter(par, tableInfo, nvwaQueryModel);
        this.appendSplitKeyFilter(tableInfo, colMap, nvwaQueryModel);
        return nvwaQueryModel;
    }

    public NvwaQueryModel getFormQueryModel(CheckStatusQueryPar par, @NonNull CheckStatusTableInfo tableInfo) {
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        HashMap<String, ColumnModelDefine> colMap = new HashMap<String, ColumnModelDefine>();
        List cksCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksTable().getID());
        List cksSubCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksSubTable().getID());
        cksCols.forEach(cksCol -> colMap.put(cksCol.getCode(), (ColumnModelDefine)cksCol));
        cksSubCols.forEach(cksSubCol -> colMap.put(cksSubCol.getCode(), (ColumnModelDefine)cksSubCol));
        NvwaQueryColumn formQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_FRM_KEY"));
        formQueryCol.setAggrType(AggrType.NONE);
        nvwaQueryModel.getColumns().add(formQueryCol);
        nvwaQueryModel.getGroupByColumns().add(nvwaQueryModel.getColumns().size() - 1);
        NvwaQueryColumn stateQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_CK_STATE"));
        stateQueryCol.setAggrType(AggrType.MAX);
        nvwaQueryModel.getColumns().add(stateQueryCol);
        NvwaQueryColumn checkTypeQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_S_CK_TYPE"));
        checkTypeQueryCol.setAggrType(AggrType.NONE);
        nvwaQueryModel.getColumns().add(checkTypeQueryCol);
        nvwaQueryModel.getGroupByColumns().add(nvwaQueryModel.getColumns().size() - 1);
        NvwaQueryColumn errorCountQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_S_ERR_COUNT"));
        errorCountQueryCol.setAggrType(AggrType.SUM);
        nvwaQueryModel.getColumns().add(errorCountQueryCol);
        nvwaQueryModel.setMainTableName(tableInfo.getCksTable().getName());
        CheckStatusHelper.appendQueryFilter(par, nvwaQueryModel, colMap);
        this.appendDimFilter(par, tableInfo, nvwaQueryModel);
        this.appendSplitKeyFilter(tableInfo, colMap, nvwaQueryModel);
        return nvwaQueryModel;
    }

    public NvwaQueryModel getUnitQueryModel(CheckStatusQueryPar par, @NonNull CheckStatusTableInfo tableInfo) {
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        HashMap<String, ColumnModelDefine> colMap = new HashMap<String, ColumnModelDefine>();
        List cksCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksTable().getID());
        List cksSubCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksSubTable().getID());
        cksCols.forEach(cksCol -> colMap.put(cksCol.getCode(), (ColumnModelDefine)cksCol));
        cksSubCols.forEach(cksSubCol -> colMap.put(cksSubCol.getCode(), (ColumnModelDefine)cksSubCol));
        NvwaQueryColumn unitQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("MDCODE"));
        unitQueryCol.setAggrType(AggrType.NONE);
        nvwaQueryModel.getColumns().add(unitQueryCol);
        nvwaQueryModel.getGroupByColumns().add(nvwaQueryModel.getColumns().size() - 1);
        NvwaQueryColumn stateQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_CK_STATE"));
        stateQueryCol.setAggrType(AggrType.MAX);
        nvwaQueryModel.getColumns().add(stateQueryCol);
        NvwaQueryColumn checkTypeQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_S_CK_TYPE"));
        checkTypeQueryCol.setAggrType(AggrType.NONE);
        nvwaQueryModel.getColumns().add(checkTypeQueryCol);
        nvwaQueryModel.getGroupByColumns().add(nvwaQueryModel.getColumns().size() - 1);
        NvwaQueryColumn errorCountQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_S_ERR_COUNT"));
        errorCountQueryCol.setAggrType(AggrType.SUM);
        nvwaQueryModel.getColumns().add(errorCountQueryCol);
        nvwaQueryModel.setMainTableName(tableInfo.getCksTable().getName());
        CheckStatusHelper.appendQueryFilter(par, nvwaQueryModel, colMap);
        this.appendDimFilter(par, tableInfo, nvwaQueryModel);
        this.appendSplitKeyFilter(tableInfo, colMap, nvwaQueryModel);
        return nvwaQueryModel;
    }

    public NvwaQueryModel getUnitFormQueryModel(CheckStatusQueryPar par, @NonNull CheckStatusTableInfo tableInfo) {
        NvwaQueryModel nvwaQueryModel = new NvwaQueryModel();
        HashMap<String, ColumnModelDefine> colMap = new HashMap<String, ColumnModelDefine>();
        List cksCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksTable().getID());
        List cksSubCols = this.dataModelService.getColumnModelDefinesByTable(tableInfo.getCksSubTable().getID());
        cksCols.forEach(cksCol -> colMap.put(cksCol.getCode(), (ColumnModelDefine)cksCol));
        cksSubCols.forEach(cksSubCol -> colMap.put(cksSubCol.getCode(), (ColumnModelDefine)cksSubCol));
        NvwaQueryColumn unitQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("MDCODE"));
        unitQueryCol.setAggrType(AggrType.NONE);
        nvwaQueryModel.getColumns().add(unitQueryCol);
        nvwaQueryModel.getGroupByColumns().add(nvwaQueryModel.getColumns().size() - 1);
        NvwaQueryColumn formQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_FRM_KEY"));
        formQueryCol.setAggrType(AggrType.NONE);
        nvwaQueryModel.getColumns().add(formQueryCol);
        nvwaQueryModel.getGroupByColumns().add(nvwaQueryModel.getColumns().size() - 1);
        NvwaQueryColumn stateQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_CK_STATE"));
        stateQueryCol.setAggrType(AggrType.MAX);
        nvwaQueryModel.getColumns().add(stateQueryCol);
        NvwaQueryColumn checkTypeQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_S_CK_TYPE"));
        checkTypeQueryCol.setAggrType(AggrType.NONE);
        nvwaQueryModel.getColumns().add(checkTypeQueryCol);
        nvwaQueryModel.getGroupByColumns().add(nvwaQueryModel.getColumns().size() - 1);
        NvwaQueryColumn errorCountQueryCol = new NvwaQueryColumn((ColumnModelDefine)colMap.get("CKS_S_ERR_COUNT"));
        errorCountQueryCol.setAggrType(AggrType.SUM);
        nvwaQueryModel.getColumns().add(errorCountQueryCol);
        nvwaQueryModel.setMainTableName(tableInfo.getCksTable().getName());
        CheckStatusHelper.appendQueryFilter(par, nvwaQueryModel, colMap);
        this.appendDimFilter(par, tableInfo, nvwaQueryModel);
        this.appendSplitKeyFilter(tableInfo, colMap, nvwaQueryModel);
        return nvwaQueryModel;
    }

    private static void appendQueryFilter(CheckStatusQueryPar par, NvwaQueryModel nvwaQueryModel, Map<String, ColumnModelDefine> colMap) {
        if (!CollectionUtils.isEmpty(par.getFormulaSchemeKeys())) {
            nvwaQueryModel.getColumnFilters().put(colMap.get("CKS_FLS_KEY"), par.getFormulaSchemeKeys());
        }
        if (!CollectionUtils.isEmpty(par.getFormKeys())) {
            nvwaQueryModel.getColumnFilters().put(colMap.get("CKS_FRM_KEY"), par.getFormKeys());
        }
    }

    private void appendDimFilter(CheckStatusQueryPar par, CheckStatusTableInfo tableInfo, NvwaQueryModel nvwaQueryModel) {
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableInfo.getCksTable().getName());
        if (par.getDimensionValueSet() != null) {
            for (int i = 0; i < par.getDimensionValueSet().size(); ++i) {
                String dimName = par.getDimensionValueSet().getName(i);
                Object dimValue = par.getDimensionValueSet().getValue(i);
                ColumnModelDefine column = dimensionChanger.getColumn(dimName);
                if (column == null) continue;
                nvwaQueryModel.getColumnFilters().put(column, dimValue);
            }
        }
    }

    private void appendSplitKeyFilter(CheckStatusTableInfo tableInfo, Map<String, ColumnModelDefine> colMap, NvwaQueryModel nvwaQueryModel) {
        Map<String, String> cksSplitKeyValue = this.splitCheckTableHelper.getSplitKeyValue(tableInfo.getFormScheme(), tableInfo.getCksTable().getName());
        cksSplitKeyValue.forEach((key, value) -> {
            ColumnModelDefine keyCol = (ColumnModelDefine)colMap.get(key);
            if (keyCol != null) {
                nvwaQueryModel.getColumnFilters().put(keyCol, value);
            }
        });
        Map<String, String> cksSubSplitKeyValue = this.splitCheckTableHelper.getSplitKeyValue(tableInfo.getFormScheme(), tableInfo.getCksSubTable().getName());
        cksSubSplitKeyValue.forEach((key, value) -> {
            ColumnModelDefine keyCol = (ColumnModelDefine)colMap.get(key);
            if (keyCol != null) {
                nvwaQueryModel.getColumnFilters().put(keyCol, value);
            }
        });
    }
}

