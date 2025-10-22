/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.Basic
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.Basic;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DataModelLinkFinder
implements IDataModelLinkFinder {
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    private DataModelService dataModelService;

    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public void setDataModelService(DataModelService dataModelService) {
        this.dataModelService = dataModelService;
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, int rowIndex, int colIndex, boolean isGridPosition) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private DataModelLinkColumn getDataModelLinkColumn(ReportInfo reportInfo, DataLinkDefine dataLink) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DataModelLinkColumn findDataColumn(ReportInfo reportInfo, String dataLinkCode) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public DataModelLinkColumn findDataColumnByFieldName(ReportInfo reportInfo, String fieldName) {
        String dataTableKey = reportInfo.getReportKey();
        DataField dataField = this.runtimeDataSchemeService.getDataFieldByTableKeyAndCode(dataTableKey, fieldName);
        if (dataField == null) {
            return null;
        }
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKey(dataField.getDataSchemeKey(), dataField.getKey());
        if (deployInfos == null || deployInfos.isEmpty()) {
            return null;
        }
        String columnModelKey = ((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey();
        ColumnModelDefine columnModelDefine = this.dataModelService.getColumnModelDefineByID(columnModelKey);
        if (columnModelDefine == null) {
            return null;
        }
        return new DataModelLinkColumn(columnModelDefine);
    }

    public ReportInfo findReportInfo(String reportName) {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(reportName);
        if (dataTable == null) {
            return null;
        }
        return new ReportInfo(dataTable.getKey(), dataTable.getCode(), dataTable.getTitle(), 0, 0, 0, 0);
    }

    public ReportInfo findReportInfo(String linkAlias, String reportName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ReportInfo> findAllRelatedReportInfo(String reportName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map<Object, List<Object>> findRelatedUnitKeyMap(ExecutorContext context, String linkAlias, String dimensionName, List<Object> unitKeys) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Object> findRelatedUnitKey(ExecutorContext context, String linkAlias, String dimensionName, Object unitKey) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRelatedUnitDimName(ExecutorContext context, String linkAlias, String dimensionName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map<String, List<Object>> expandByDimensions(ExecutorContext context, DataModelLinkColumn dataModelLinkColumn) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean is1V1Related(ExecutorContext context, String linkAlias) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean hasRegionCondition(ExecutorContext context, String region) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getRegionCondition(ExecutorContext context, String region) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getTableInnerKeys(ExecutorContext context, String tableCode) {
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByTableName(tableCode);
        List dataFields = this.runtimeDataSchemeService.getDataFieldByTableCodeAndKind(tableCode, new DataFieldKind[]{DataFieldKind.TABLE_FIELD_DIM});
        Map fieldDeployInfoMap = deployInfos.stream().collect(Collectors.toMap(DataFieldDeployInfo::getDataFieldKey, Function.identity(), (o, n) -> o));
        return dataFields.stream().map(Basic::getKey).map(fieldDeployInfoMap::get).filter(Objects::nonNull).map(DataFieldDeployInfo::getColumnModelKey).collect(Collectors.toList());
    }
}

