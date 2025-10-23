/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataRow
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DSContext
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.Metadata
 *  com.jiuqi.bi.dataset.manager.DataSetManagerFactory
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.dataset.sql.SQLModel
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider
 *  com.jiuqi.nvwa.dataextract.DataExtractResult
 *  com.jiuqi.nvwa.dataextract.ExtractDataRow
 *  com.jiuqi.nvwa.dataextract.IDataExtractRequest
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnvFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 */
package com.jiuqi.nvwa.dataextract.impl;

import com.jiuqi.bi.dataset.BIDataRow;
import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.Metadata;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.dataset.sql.SQLModel;
import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider;
import com.jiuqi.nvwa.dataextract.DataExtractResult;
import com.jiuqi.nvwa.dataextract.ExtractDataRow;
import com.jiuqi.nvwa.dataextract.IDataExtractRequest;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnvFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SQLDataSetExtractRequest
implements IDataExtractRequest {
    @Autowired
    private DataSetStorageProvider dataSetStorageProvider;

    public String getType() {
        return "SQLDS";
    }

    public String getTitle() {
        return "SQL\u6570\u636e\u96c6";
    }

    public DataExtractResult getResult(QueryContext qContext, String queryName, List<String> argValues, IReportFunction function) throws Exception {
        DataExtractResult result = null;
        IMonitor monitor = qContext.getMonitor();
        SQLModel sqlModel = (SQLModel)this.dataSetStorageProvider.findModel(queryName);
        if (monitor != null && monitor.isDebug()) {
            monitor.message("\u6570\u636e\u96c6\uff1a" + queryName, (Object)function);
            monitor.message("\u53c2\u6570\u5b9a\u4e49\uff1a", (Object)function);
            List parameterModels = sqlModel.getParameterModels();
            for (ParameterModel parameterModel : parameterModels) {
                StringBuilder msg = new StringBuilder();
                msg.append(parameterModel.getName()).append("|").append(parameterModel.getTitle()).append("\uff0c");
                AbstractParameterDataSourceModel datasource = parameterModel.getDatasource();
                msg.append("\u6570\u636e\u6765\u6e90\uff1a").append(datasource == null ? "\u65e0" : ParameterDataSourceManager.getInstance().getFactory(datasource.getType()).title()).append("\uff0c");
                AbstractParameterValueConfig valueConfig = parameterModel.getValueConfig();
                if (valueConfig != null) {
                    msg.append("\u9ed8\u8ba4\u503c\u6a21\u5f0f\uff1a").append(this.getDefaultModeTitle(valueConfig)).append("\uff0c");
                    msg.append("\u9ed8\u8ba4\u503c\uff1a").append(valueConfig.getDefaultValue());
                }
                monitor.message(msg.toString(), (Object)function);
            }
        }
        DSContext context = this.createDSContext(qContext, (DSModel)sqlModel, argValues, function);
        IDataSetManager dataSetManager = DataSetManagerFactory.create();
        BIDataSet biDataSet = dataSetManager.open((IDSContext)context, (DSModel)sqlModel);
        if (monitor != null && monitor.isDebug()) {
            monitor.message("\u6570\u636e\u96c6\u8bb0\u5f55\u6570\uff1a" + biDataSet.getRecordCount(), (Object)function);
        }
        Metadata metadata = biDataSet.getMetadata();
        result = new DataExtractResult(metadata.getColumnCount());
        for (int i = 0; i < metadata.getColumnCount(); ++i) {
            Column column = metadata.getColumn(i);
            result.setDataType(i, column.getDataType());
            result.setColumnName(i, column.getName());
        }
        for (BIDataRow row : biDataSet) {
            ExtractDataRow resultRow = result.addRow();
            for (int i = 0; i < metadata.getColumnCount(); ++i) {
                resultRow.setFieldValue(i, row.getValue(i));
            }
        }
        return result;
    }

    private String getDefaultModeTitle(AbstractParameterValueConfig valueConfig) {
        String defaultValueMode = valueConfig.getDefaultValueMode();
        if ("appoint".equals(defaultValueMode)) {
            return "\u6307\u5b9a\u6210\u5458";
        }
        if ("first".equals(defaultValueMode)) {
            return "\u7b2c\u4e00\u4e2a\u6210\u5458";
        }
        if ("expr".equals(defaultValueMode)) {
            return "\u8868\u8fbe\u5f0f";
        }
        if ("none".equals(defaultValueMode)) {
            return "\u65e0";
        }
        if ("currPeriod".equals(defaultValueMode)) {
            return "\u5f53\u524d\u671f";
        }
        if ("lastPeriod".equals(defaultValueMode)) {
            return "\u6700\u672b\u671f";
        }
        if ("firstChild".equals(defaultValueMode)) {
            return "\u672c\u7ea7+\u76f4\u63a5\u4e0b\u7ea7 ";
        }
        if ("firstAllChild".equals(defaultValueMode)) {
            return "\u672c\u7ea7+\u6240\u6709\u4e0b\u7ea7 ";
        }
        return defaultValueMode;
    }

    public boolean checkDataExtract(QueryContext qContext, String queryName, List<Integer> columnIndexes, List<Integer> argParaTypes) throws Exception {
        SQLModel sqlModel = (SQLModel)this.dataSetStorageProvider.findModel(queryName);
        if (sqlModel == null) {
            throw new Exception("\u672a\u627e\u5230\u6807\u8bc6\u4e3a" + queryName + "\u7684SQL\u6570\u636e\u96c6");
        }
        for (int index : columnIndexes) {
            if (index >= 1 && index <= sqlModel.getFields().size()) continue;
            throw new Exception("\u65e0\u6548\u7684\u6570\u636e\u96c6\u5217\u53f7\uff1a" + index);
        }
        List parameterModels = sqlModel.getParameterModels();
        if (parameterModels.size() != argParaTypes.size()) {
            throw new Exception("\u53c2\u6570\u4e2a\u6570\u4e0d\u5339\u914d");
        }
        for (int i = 0; i < parameterModels.size(); ++i) {
            ParameterModel paramModel = (ParameterModel)parameterModels.get(i);
            int dataType = paramModel.getDataType();
            if (DataType.isCompatible((int)dataType, (int)argParaTypes.get(i))) continue;
            throw new Exception("\u7b2c" + (i + 1) + "\u4e2a\u6570\u636e\u96c6\u53c2\u6570\u7c7b\u578b\u4e0d\u5339\u914d");
        }
        return true;
    }

    private DSContext createDSContext(QueryContext qContext, DSModel dsModel, List<String> argValues, IReportFunction function) throws Exception {
        String userGuid = NpContextHolder.getContext().getUserId();
        List parameterModels = dsModel.getParameterModels();
        IParameterEnv parameterEnv = ParameterEnvFactory.createParameterEnv((List)parameterModels, (String)userGuid);
        dsModel.prepareParameterEnv(parameterEnv);
        IMonitor monitor = qContext.getMonitor();
        monitor.message("\u7ed9\u6570\u636e\u96c6\u4f20\u9012\u7684\u53c2\u6570\u503c\uff1a", (Object)function);
        for (int i = 0; i < parameterModels.size(); ++i) {
            ParameterModel paramModel = (ParameterModel)parameterModels.get(i);
            String paraValue = argValues.get(i);
            ArrayList<String> values = new ArrayList<String>();
            if (paramModel.getSelectMode() == ParameterSelectMode.SINGLE) {
                values.add(paraValue);
            } else {
                String[] valueArray;
                for (String value : valueArray = paraValue.split(",|;")) {
                    values.add(value);
                }
            }
            if (monitor != null && monitor.isDebug()) {
                StringBuilder msg = new StringBuilder();
                msg.append(paramModel.getName()).append("=").append(values);
                monitor.message(msg.toString(), (Object)function);
            }
            if (paramModel == null) continue;
            if (paramModel.isRangeParameter()) {
                if (values.size() > 0) {
                    ArrayList minValues = new ArrayList();
                    minValues.add(values.get(0));
                    parameterEnv.setValue(paramModel.getName() + ".MIN", minValues);
                }
                if (values.size() <= 1) continue;
                ArrayList maxValues = new ArrayList();
                maxValues.add(values.get(1));
                parameterEnv.setValue(paramModel.getName() + ".MAX", maxValues);
                continue;
            }
            parameterEnv.setValue(paramModel.getName(), values);
        }
        DSContext context = new DSContext(dsModel, userGuid, parameterEnv);
        return context;
    }
}

