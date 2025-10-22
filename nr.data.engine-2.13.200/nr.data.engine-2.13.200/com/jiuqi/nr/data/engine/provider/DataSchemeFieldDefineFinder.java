/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFieldDefineFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 *  com.jiuqi.nr.datascheme.internal.service.DataFieldService
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.data.engine.provider;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFieldDefineFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.service.IDesignDataSchemeService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.datascheme.internal.service.DataFieldService;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataSchemeFieldDefineFinder
implements IFieldDefineFinder {
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataFieldService dataFieldService;
    @Autowired
    private IDesignDataSchemeService designDataSchemeService;
    @Autowired
    private IDataDefinitionDesignTimeController npDesignController;
    @Autowired
    private IDataDefinitionRuntimeController npRuntimeController;

    public FieldDefine findFieldDefine(ExecutorContext context, String fieldCode) throws Exception {
        IFmlExecEnvironment env = context.getEnv();
        FieldDefine fieldDefine = null;
        if (env != null && env instanceof ReportFmlExecEnvironment) {
            ReportFmlExecEnvironment rptEnv = (ReportFmlExecEnvironment)env;
            String currentDataScheme = rptEnv.getDataScehmeKey();
            fieldDefine = this.findFieldDefine(context, currentDataScheme, fieldCode);
        }
        return fieldDefine;
    }

    public FieldDefine findFieldDefine(ExecutorContext context, String fieldCode, int periodType) throws Exception {
        FieldDefine fieldDefine = this.findFieldDefine(context, fieldCode);
        if (periodType == 0) {
            return fieldDefine;
        }
        IFmlExecEnvironment env = context.getEnv();
        if (env != null) {
            int currentPeriodType = env.getPeriodType();
            if (currentPeriodType == periodType && fieldDefine != null) {
                return fieldDefine;
            }
            List dataSchcmes = this.runtimeDataSchemeService.getDataSchemeByPeriodType(PeriodType.fromType((int)periodType));
            ArrayList<FieldDefine> findedFields = new ArrayList<FieldDefine>();
            for (DataScheme dataScheme : dataSchcmes) {
                FieldDefine field = this.findFieldDefine(context, dataScheme.getCode(), fieldCode);
                if (field != null) {
                    findedFields.add(field);
                }
                if (findedFields.size() <= 1) continue;
                throw new Exception("\u6307\u6807\u4ee3\u7801" + fieldCode + "\u5b58\u5728\u6b67\u4e49\uff0c\u627e\u5230\u591a\u4e2a\u76f8\u540c\u4ee3\u7801\u7684\u6307\u6807");
            }
            if (findedFields.size() == 1) {
                fieldDefine = (FieldDefine)findedFields.get(0);
            }
        }
        return fieldDefine;
    }

    public FieldDefine findFieldDefine(ExecutorContext context, String dataScheme, String fieldCode) throws Exception {
        if (context.isDesignTimeData()) {
            DesignDataField dataField = this.designDataSchemeService.getZbKindDataFieldBySchemeKeyAndCode(dataScheme, fieldCode);
            if (dataField == null) {
                return null;
            }
            return this.npDesignController.queryFieldDefine(dataField.getKey());
        }
        DataFieldDTO dataField = this.dataFieldService.getZbKindDataFieldBySchemeKeyAndCode(dataScheme, fieldCode);
        if (dataField == null) {
            return null;
        }
        return this.npRuntimeController.queryFieldDefine(dataField.getKey());
    }
}

