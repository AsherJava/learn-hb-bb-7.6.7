/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.data.engine.provider;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.data.engine.provider.EngineFieldDefine;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.definition.common.RuntimeDefinitionTransfer;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class DataSchemeColumnModelFinder
implements IColumnModelFinder {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String fieldCode) throws Exception {
        IFmlExecEnvironment env = context.getEnv();
        ColumnModelDefine columnModel = null;
        if (env instanceof ReportFmlExecEnvironment) {
            ReportFmlExecEnvironment rptEnv = (ReportFmlExecEnvironment)env;
            String currentDataScheme = rptEnv.getDataScehmeKey();
            columnModel = this.findColumnModelDefine(context, currentDataScheme, fieldCode);
        }
        return columnModel;
    }

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String fieldCode, int periodType) throws Exception {
        ColumnModelDefine columnModel = this.findColumnModelDefine(context, fieldCode);
        if (periodType == 0) {
            return columnModel;
        }
        IFmlExecEnvironment env = context.getEnv();
        if (env != null) {
            int currentPeriodType = env.getPeriodType();
            if (currentPeriodType == periodType && columnModel != null) {
                return columnModel;
            }
            List dataSchcmes = this.runtimeDataSchemeService.getDataSchemeByPeriodType(PeriodType.fromType((int)periodType));
            ArrayList<ColumnModelDefine> columnModels = new ArrayList<ColumnModelDefine>();
            for (DataScheme dataScheme : dataSchcmes) {
                ColumnModelDefine field = this.findColumnModelDefine(context, dataScheme.getCode(), fieldCode);
                if (field != null) {
                    columnModels.add(field);
                }
                if (columnModels.size() <= 1) continue;
                throw new Exception("\u6307\u6807\u4ee3\u7801" + fieldCode + "\u5b58\u5728\u6b67\u4e49\uff0c\u627e\u5230\u591a\u4e2a\u76f8\u540c\u4ee3\u7801\u7684\u6307\u6807");
            }
            if (columnModels.size() == 1) {
                columnModel = (ColumnModelDefine)columnModels.get(0);
            }
        }
        return columnModel;
    }

    public ColumnModelDefine findColumnModelDefine(ExecutorContext context, String dataScheme, String fieldCode) throws Exception {
        if (context.isDesignTimeData()) {
            return null;
        }
        DataField fieldDefine = this.runtimeDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(dataScheme, fieldCode);
        if (fieldDefine == null) {
            return null;
        }
        return this.findColumnModelDefine(RuntimeDefinitionTransfer.toFieldDefine((DataField)fieldDefine));
    }

    public ColumnModelDefine findColumnModelDefine(FieldDefine fieldDefine) throws Exception {
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldDefine.getKey()});
        if (null == deployInfos || deployInfos.isEmpty()) {
            return this.dataModelService.getColumnModelDefineByID(fieldDefine.getKey());
        }
        if (deployInfos.size() > 1) {
            Optional<DataFieldDeployInfo> fieldDeploy = deployInfos.stream().filter(e -> !e.getTableName().endsWith("_RPT")).findFirst();
            if (fieldDeploy.isPresent()) {
                return this.dataModelService.getColumnModelDefineByID(fieldDeploy.get().getColumnModelKey());
            }
            return this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
        }
        return this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
    }

    public FieldDefine findFieldDefine(ColumnModelDefine columnModelDefine) throws Exception {
        DataFieldDeployInfo deployInfo = this.runtimeDataSchemeService.getDeployInfoByColumnKey(columnModelDefine.getID());
        if (null == deployInfo) {
            return new EngineFieldDefine(columnModelDefine);
        }
        String dataFieldKey = deployInfo.getDataFieldKey();
        return RuntimeDefinitionTransfer.toFieldDefine((DataField)this.runtimeDataSchemeService.getDataField(dataFieldKey));
    }

    public FieldDefine findFieldDefineByColumnId(String columnId) {
        DataFieldDeployInfo deployInfo = this.runtimeDataSchemeService.getDeployInfoByColumnKey(columnId);
        if (null == deployInfo) {
            return null;
        }
        String dataFieldKey = deployInfo.getDataFieldKey();
        return RuntimeDefinitionTransfer.toFieldDefine((DataField)this.runtimeDataSchemeService.getDataField(dataFieldKey));
    }

    public List<ColumnModelDefine> getAllColumnModelsByTableKey(ExecutorContext context, String tableKey) throws Exception {
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
        if (CollectionUtils.isEmpty(deployInfos)) {
            return Collections.emptyList();
        }
        return deployInfos.stream().map(e -> this.dataModelService.getColumnModelDefineByID(e.getColumnModelKey())).collect(Collectors.toList());
    }

    public TableModelDefine getTableModelByTableKey(ExecutorContext context, String tableKey) throws Exception {
        List tableDeployInfo = this.runtimeDataSchemeService.getDeployInfoByDataTableKey(tableKey);
        if (CollectionUtils.isEmpty(tableDeployInfo)) {
            return null;
        }
        return this.dataModelService.getTableModelDefineById(((DataFieldDeployInfo)tableDeployInfo.stream().findFirst().get()).getTableModelKey());
    }

    public PeriodType getPeriodType(TableModelDefine tableModelDefine) {
        List deployInfos = this.runtimeDataSchemeService.getDeployInfoByTableModelKey(tableModelDefine.getID());
        if (CollectionUtils.isEmpty(deployInfos)) {
            return null;
        }
        List dimensions = this.runtimeDataSchemeService.getDataSchemeDimension(((DataFieldDeployInfo)deployInfos.get(0)).getDataSchemeKey());
        Optional<DataDimension> optional = dimensions.stream().filter(d -> d.getDimensionType() == DimensionType.PERIOD).findFirst();
        if (optional.isPresent()) {
            return optional.get().getPeriodType();
        }
        return null;
    }
}

