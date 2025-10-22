/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.BIDataSet
 *  com.jiuqi.bi.dataset.DSContext
 *  com.jiuqi.bi.dataset.IDSContext
 *  com.jiuqi.bi.dataset.manager.DataSetManagerFactory
 *  com.jiuqi.bi.dataset.manager.IDataSetManager
 *  com.jiuqi.bi.dataset.model.DSModel
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.definitions.ColumnModelAdapter
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.impl.internal.FieldDefineImpl
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider
 *  com.jiuqi.nvwa.dataextract.DataExtractRequestManager
 *  com.jiuqi.nvwa.dataextract.DataExtractResult
 *  com.jiuqi.nvwa.dataextract.IDataExtractRequest
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.framework.parameter.IParameterEnv
 *  com.jiuqi.nvwa.framework.parameter.ParameterEnvFactory
 *  com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager
 *  com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterModel
 *  com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode
 *  com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.function.func.extract;

import com.jiuqi.bi.dataset.BIDataSet;
import com.jiuqi.bi.dataset.DSContext;
import com.jiuqi.bi.dataset.IDSContext;
import com.jiuqi.bi.dataset.manager.DataSetManagerFactory;
import com.jiuqi.bi.dataset.manager.IDataSetManager;
import com.jiuqi.bi.dataset.model.DSModel;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.definitions.ColumnModelAdapter;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.impl.internal.FieldDefineImpl;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.function.func.extract.DataSetQueryResult;
import com.jiuqi.nvwa.dataanalysis.dataset.storage.DataSetStorageProvider;
import com.jiuqi.nvwa.dataextract.DataExtractRequestManager;
import com.jiuqi.nvwa.dataextract.DataExtractResult;
import com.jiuqi.nvwa.dataextract.IDataExtractRequest;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.framework.parameter.IParameterEnv;
import com.jiuqi.nvwa.framework.parameter.ParameterEnvFactory;
import com.jiuqi.nvwa.framework.parameter.datasource.ParameterDataSourceManager;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterModel;
import com.jiuqi.nvwa.framework.parameter.model.ParameterSelectMode;
import com.jiuqi.nvwa.framework.parameter.model.config.AbstractParameterValueConfig;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryUtils {
    public static DataExtractResult getResult(QueryContext qContext, String type, String queryName, List<IASTNode> parameters, IReportFunction function) throws SyntaxException, Exception {
        IDataExtractRequest request = DataExtractRequestManager.getInstance().findRequest(type);
        ArrayList<String> argValues = new ArrayList<String>();
        StringBuilder cacheKeyBuff = new StringBuilder(queryName);
        for (IASTNode para : parameters) {
            cacheKeyBuff.append("_");
            Object argValue = para.evaluate((IContext)qContext);
            cacheKeyBuff.append(argValue);
            argValues.add(argValue == null ? "" : argValue.toString());
        }
        String cacheKey = cacheKeyBuff.toString();
        DataExtractResult result = (DataExtractResult)qContext.getCache().get(cacheKey);
        IMonitor monitor = qContext.getMonitor();
        if (result == null) {
            result = request.getResult(qContext, queryName, argValues, function);
            if (result == null) {
                throw new SyntaxException("\u6ca1\u6709\u53d6\u5230" + queryName + "\u7ed3\u679c\u96c6\uff01");
            }
            qContext.getCache().put(cacheKey, result);
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u516c\u5f0f\u6267\u884c\u4e0a\u4e0b\u6587\u4e2d\u6839\u636ecacheKey\u3010" + cacheKey + "\u3011\u672a\u53d1\u73b0\u7f13\u5b58\u7ed3\u679c\uff0c\u5df2\u6267\u884c\u6570\u636e\u96c6\u53d6\u6570\u5e76\u628a\u7ed3\u679c\u7f13\u5b58\u5165\u4e0a\u4e0b\u6587", (Object)function);
            }
        } else if (monitor != null && monitor.isDebug()) {
            monitor.message("\u516c\u5f0f\u6267\u884c\u4e0a\u4e0b\u6587\u4e2d\u6839\u636ecacheKey\u3010" + cacheKey + "\u3011\u627e\u5230\u7f13\u5b58\u7ed3\u679c\uff0c\u8bb0\u5f55\u6570\uff1a" + result.size(), (Object)function);
        }
        return result;
    }

    public static boolean checkDataExtract(QueryContext qContext, String type, String queryName, List<Integer> columnIndexes, List<Integer> argParaTypes, IReportFunction function) throws Exception {
        IDataExtractRequest request = DataExtractRequestManager.getInstance().findRequest(type);
        return request.checkDataExtract(qContext, queryName, columnIndexes, argParaTypes);
    }

    public static DataSetQueryResult OpenDataSet(QueryContext qContext, String dataSetName, String paraValues, IReportFunction function) throws SyntaxException, Exception {
        DataSetStorageProvider dataSetStorageProvider;
        String cacheKey = "DS_" + dataSetName + "_" + paraValues;
        DataSetQueryResult result = (DataSetQueryResult)qContext.getCache().get(cacheKey);
        DSModel dsModel = null;
        IMonitor monitor = qContext.getMonitor();
        if (monitor != null && monitor.isDebug()) {
            dataSetStorageProvider = (DataSetStorageProvider)SpringBeanUtils.getBean(DataSetStorageProvider.class);
            dsModel = dataSetStorageProvider.findModel(dataSetName);
            if (dsModel == null) {
                throw new SyntaxException("\u6570\u636e\u96c6" + dataSetName + "\u4e0d\u5b58\u5728\uff01");
            }
            monitor.message("\u6570\u636e\u96c6\uff1a" + dataSetName, (Object)function);
            monitor.message("\u53c2\u6570\u5b9a\u4e49\uff1a", (Object)function);
            List parameterModels = dsModel.getParameterModels();
            for (ParameterModel parameterModel : parameterModels) {
                StringBuilder msg = new StringBuilder();
                msg.append(parameterModel.getName()).append("|").append(parameterModel.getTitle()).append(", ");
                AbstractParameterDataSourceModel datasource = parameterModel.getDatasource();
                msg.append("\u6570\u636e\u6765\u6e90\uff1a").append(datasource == null ? "\u65e0" : ParameterDataSourceManager.getInstance().getFactory(datasource.getType()).title()).append(", ");
                AbstractParameterValueConfig valueConfig = parameterModel.getValueConfig();
                if (valueConfig != null) {
                    msg.append("\u9ed8\u8ba4\u503c\u6a21\u5f0f\uff1a").append(QueryUtils.getDefaultModeTitle(valueConfig));
                    msg.append("\u9ed8\u8ba4\u503c\uff1a").append(valueConfig.getDefaultValue());
                }
                monitor.message(msg.toString(), (Object)function);
            }
        }
        if (result == null) {
            if (dsModel == null) {
                dataSetStorageProvider = (DataSetStorageProvider)SpringBeanUtils.getBean(DataSetStorageProvider.class);
                dsModel = dataSetStorageProvider.findModel(dataSetName);
            }
            if (dsModel == null) {
                throw new SyntaxException("\u6570\u636e\u96c6" + dataSetName + "\u4e0d\u5b58\u5728\uff01");
            }
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u516c\u5f0f\u6267\u884c\u4e0a\u4e0b\u6587\u4e2d\u6839\u636ecacheKey\u3010" + cacheKey + "\u3011\u672a\u53d1\u73b0\u7f13\u5b58\u7ed3\u679c\uff0c\u5c06\u6267\u884c\u6570\u636e\u96c6\u53d6\u6570\u5e76\u628a\u7ed3\u679c\u7f13\u5b58\u5165\u4e0a\u4e0b\u6587", (Object)function);
            }
            DSContext context = QueryUtils.createDSContext(qContext, dsModel, paraValues, function);
            IDataSetManager dataSetManager = DataSetManagerFactory.create();
            BIDataSet biDataSet = dataSetManager.open((IDSContext)context, dsModel);
            if (biDataSet == null) {
                throw new SyntaxException("\u6ca1\u6709\u53d6\u5230" + dataSetName + "\u7ed3\u679c\u96c6\uff01");
            }
            if (monitor != null && monitor.isDebug()) {
                monitor.message("\u6570\u636e\u96c6\u8bb0\u5f55\u6570\uff1a" + biDataSet.getRecordCount(), (Object)function);
            }
            result = new DataSetQueryResult(dataSetName, biDataSet, paraValues, function);
            qContext.getCache().put(cacheKey, result);
        } else if (monitor != null && monitor.isDebug()) {
            monitor.message("\u516c\u5f0f\u6267\u884c\u4e0a\u4e0b\u6587\u4e2d\u6839\u636ecacheKey\u3010" + cacheKey + "\u3011\u627e\u5230\u7f13\u5b58\u7ed3\u679c\uff0c\u8bb0\u5f55\u6570\uff1a" + result.size(), (Object)function);
        }
        return result;
    }

    public static boolean checkDataSetQuery(String dataSetName, List<String> columnNames, String paraValues, IReportFunction function) throws Exception {
        DataSetStorageProvider dataSetStorageProvider = (DataSetStorageProvider)SpringBeanUtils.getBean(DataSetStorageProvider.class);
        DSModel dsModel = dataSetStorageProvider.findModel(dataSetName);
        if (dsModel == null) {
            throw new SyntaxException("\u6570\u636e\u96c6" + dataSetName + "\u4e0d\u5b58\u5728");
        }
        for (String fieldName : columnNames) {
            if (dsModel.findField(fieldName) != null) continue;
            throw new SyntaxException("\u6765\u6e90\u6570\u636e\u96c6\u5217" + fieldName + "\u4e0d\u5b58\u5728");
        }
        if (StringUtils.isNotEmpty((String)paraValues)) {
            String[] paraArray;
            for (String para : paraArray = paraValues.split(";")) {
                String[] paraStrs = para.split("=");
                if (paraStrs.length < 1) {
                    throw new SyntaxException("\u65e0\u6548\u7684\u6570\u636e\u96c6\u53c2\u6570\u683c\u5f0f:" + para);
                }
                String parameterName = paraStrs[0].trim();
                ParameterModel paramModel = dsModel.findParamemterModel(parameterName);
                if (paramModel != null) continue;
                throw new SyntaxException("\u6570\u636e\u96c6\u53c2\u6570" + parameterName + "\u4e0d\u5b58\u5728");
            }
        }
        return true;
    }

    private static DSContext createDSContext(QueryContext qContext, DSModel dsModel, String paraValues, IReportFunction function) throws Exception {
        String userGuid = NpContextHolder.getContext().getUserId();
        List parameterModels = dsModel.getParameterModels();
        IParameterEnv parameterEnv = ParameterEnvFactory.createParameterEnv((List)parameterModels, (String)userGuid);
        dsModel.prepareParameterEnv(parameterEnv);
        IMonitor monitor = qContext.getMonitor();
        monitor.message("\u7ed9\u6570\u636e\u96c6\u4f20\u9012\u7684\u53c2\u6570\u503c\uff1a", (Object)function);
        if (StringUtils.isNotEmpty((String)paraValues)) {
            String[] paraArray;
            for (String para : paraArray = paraValues.split(";")) {
                ParameterModel paramModel;
                String[] paraStrs = para.split("=");
                if (paraStrs.length != 2) {
                    throw new SyntaxException("\u65e0\u6548\u7684\u6570\u636e\u96c6\u53c2\u6570\u683c\u5f0f:" + paraStrs);
                }
                String parameterName = paraStrs[0].trim();
                String paraValue = paraStrs[1].trim();
                if (paraValue.startsWith("'") && paraValue.endsWith("'") || paraValue.startsWith("\"") && paraValue.endsWith("\"")) {
                    paraValue = paraValue.substring(1, paraValue.length() - 1);
                }
                if ((paramModel = parameterEnv.getParameterModelByName(parameterName)) == null) {
                    throw new SyntaxException("\u53c2\u6570\u540d" + parameterName + "\u65e0\u6548");
                }
                ArrayList<String> values = new ArrayList<String>();
                if (paramModel.getSelectMode() == ParameterSelectMode.SINGLE) {
                    values.add(paraValue);
                } else if (!paraValue.startsWith("{") || !paraValue.startsWith("}")) {
                    values.add(paraValue);
                } else {
                    String[] valueArray;
                    for (String value : valueArray = paraValue.substring(1, paraValue.length() - 1).split(",")) {
                        values.add(value);
                    }
                }
                if (monitor != null && monitor.isDebug()) {
                    StringBuilder msg = new StringBuilder();
                    msg.append(paramModel.getName()).append("=").append(values);
                    monitor.message(msg.toString(), (Object)function);
                }
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
        }
        DSContext context = new DSContext(dsModel, userGuid, parameterEnv);
        return context;
    }

    public static void checkDestNotNullColumn(QueryContext qContext, TableModelRunInfo destTableInfo, Map<String, ColumnModelDefine> destColumns) throws SyntaxException {
        for (ColumnModelDefine destColumnModel : destTableInfo.getAllFields()) {
            if (destColumns.containsKey(destColumnModel.getID()) || destColumnModel.isNullAble() || !StringUtils.isEmpty((String)destColumnModel.getDefaultValue())) continue;
            if (destTableInfo.isKeyField(destColumnModel.getCode())) {
                DataFieldKind dataFieldKind;
                DesignFieldDefine fieldDefine;
                String dimName = destTableInfo.getDimensionName(destColumnModel.getCode());
                DataField dataField = null;
                if (destColumnModel instanceof ColumnModelAdapter) {
                    fieldDefine = ((ColumnModelAdapter)destColumnModel).getFieldDefine();
                    dataField = ((FieldDefineImpl)fieldDefine).getDataField();
                } else {
                    try {
                        fieldDefine = qContext.getColumnModelFinder().findFieldDefine(destColumnModel);
                        if (fieldDefine instanceof DataField) {
                            dataField = (DataField)fieldDefine;
                        } else if (fieldDefine instanceof FieldDefineImpl) {
                            dataField = ((FieldDefineImpl)fieldDefine).getDataField();
                        }
                    }
                    catch (Exception e) {
                        qContext.getMonitor().exception(e);
                    }
                }
                if ((dataFieldKind = dataField.getDataFieldKind()) == DataFieldKind.PUBLIC_FIELD_DIM || dataFieldKind == DataFieldKind.BUILT_IN_FIELD || qContext.getMasterKeys().hasValue(dimName) || qContext.is1v1RelationDim(dimName, null)) continue;
            }
            throw new SyntaxException("\u76ee\u6807\u8868" + destColumnModel.getCode() + "\u4e0d\u53ef\u4e3a\u7a7a");
        }
    }

    private static String getDefaultModeTitle(AbstractParameterValueConfig valueConfig) {
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
}

