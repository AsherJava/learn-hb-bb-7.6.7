/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.syntax.function.IFunctionProvider
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.formula.GcAbstractData
 *  com.jiuqi.gcreport.common.formula.GcFunctionProvider
 *  com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.DefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.setting.IFieldsInfo
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.conversion.service.impl;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.syntax.function.IFunctionProvider;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.formula.GcAbstractData;
import com.jiuqi.gcreport.common.formula.GcFunctionProvider;
import com.jiuqi.gcreport.conversion.common.GcConversionOrgAndFormContextEnv;
import com.jiuqi.gcreport.conversion.function.RateValueFunctionContext;
import com.jiuqi.gcreport.conversion.service.ConversionFormulaEvalService;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.rate.impl.utils.CommonRateUtils;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.DefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.HashMap;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionFormulaEvalServiceImpl
implements ConversionFormulaEvalService {
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataAccessProvider provider;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Double evaluateConversionRate(@NotNull DimensionValueSet ds, String formula, GcConversionOrgAndFormContextEnv env, IDataRow dataRow) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)SpringContextUtils.getBean(IEntityViewRunTimeController.class);
        MemoryDataSet<FieldDefine> dataSet = this.getDataSet(env, dataRow);
        try (IDataSetExprEvaluator evaluator = this.provider.newDataSetExprEvaluator(dataSet);){
            ExecutorContext executorContext = new ExecutorContext(this.runtimeController);
            executorContext.registerFunctionProvider((IFunctionProvider)new GcFunctionProvider());
            ReportFmlExecEnvironment reportFmlExecEnvironment = new ReportFmlExecEnvironment(runTimeViewController, this.runtimeController, entityViewRunTimeController, env.getSchemeId());
            VariableManager variableManager = reportFmlExecEnvironment.getVariableManager();
            RateValueFunctionContext rateValueFunctionContext = new RateValueFunctionContext(env.getRateSchemeCode(), env.getSchemeId(), env.getBeforeCurrencyCode(), env.getAfterCurrencyCode(), env.getPeriodStr());
            variableManager.add(new Variable("RateValueFunctionContext", "\u6298\u7b97\u4e0a\u4e0b\u6587\u73af\u5883", 0, (Object)rateValueFunctionContext));
            executorContext.setEnv((IFmlExecEnvironment)reportFmlExecEnvironment);
            evaluator.prepare(executorContext, ds, formula);
            DataRow memoryDataRow = dataSet.get(0);
            AbstractData data = evaluator.evaluate(memoryDataRow);
            if (data.isNull) {
                Double d = null;
                return d;
            }
            int scale = CommonRateUtils.getRateValueFieldFractionDigits(null);
            Double d = GcAbstractData.getDoubleValue((AbstractData)data, (int)scale);
            return d;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(formula + GcI18nUtil.getMessage((String)"gc.coversion.formula.eval.error", (Object[])new Object[]{e.getMessage()}), (Throwable)e);
        }
    }

    private MemoryDataSet<FieldDefine> getDataSet(GcConversionOrgAndFormContextEnv env, IDataRow dataRow) {
        IFieldsInfo fieldsInfo = dataRow.getFieldsInfo();
        String tableName = env.getTableDefine().getName();
        MemoryDataSet memoryDataSet = new MemoryDataSet(FieldDefine.class);
        Metadata columns = memoryDataSet.getMetadata();
        TableModelRunInfo tableInfo = this.getDefinitionsCache().getDataModelDefinitionsCache().getTableInfo(tableName);
        try {
            HashMap nrFieldKey2ColumnNameMap = new HashMap();
            tableInfo.getColumnFieldMap().forEach((columnModelDefine, fieldDefine) -> {
                int dataType = DataTypesConvert.fieldTypeToDataType((FieldType)fieldDefine.getType());
                columns.addColumn(new Column(columnModelDefine.getName(), dataType, fieldDefine));
                nrFieldKey2ColumnNameMap.put(fieldDefine.getKey(), columnModelDefine.getName());
            });
            DataRow dataRow2 = memoryDataSet.add();
            int fieldCount = fieldsInfo.getFieldCount();
            for (int i = 0; i < fieldCount; ++i) {
                Column column;
                String columnName;
                Object value = null;
                FieldDefine fieldDefine2 = fieldsInfo.getFieldDefine(i);
                AbstractData data = dataRow.getValue(i);
                if (!data.isNull) {
                    value = data.getAsObject();
                }
                if (StringUtils.isEmpty((String)(columnName = (String)nrFieldKey2ColumnNameMap.get(fieldDefine2.getKey()))) || (column = memoryDataSet.getMetadata().find(columnName)) == null) continue;
                dataRow2.setValue(column.getIndex(), value);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.coversion.formula.eval.tablename.error", (Object[])new Object[]{tableName}), e);
        }
        return memoryDataSet;
    }

    private DefinitionsCache getDefinitionsCache() {
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        ExecutorContext context = new ExecutorContext(dataDefinitionRuntimeController);
        DefinitionsCache definitionsCache = null;
        try {
            definitionsCache = new DefinitionsCache(context);
        }
        catch (ParseException e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        return definitionsCache;
    }
}

