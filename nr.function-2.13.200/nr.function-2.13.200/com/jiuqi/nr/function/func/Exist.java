/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Function
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FuncReadWriteType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.common.TempAssistantTable
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.IGroupingTable
 *  com.jiuqi.np.dataengine.intf.IReadonlyTable
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.SpringBeanProvider
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.function.func;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Function;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.common.TempAssistantTable;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IGroupingTable;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.SpringBeanProvider;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Exist
extends Function
implements IReportFunction {
    private static final long serialVersionUID = -7451573911821094905L;

    public Exist() {
        this.parameters().add(new Parameter("reportName", 6, "\u8868\u5355\u6807\u8bc6"));
    }

    public String name() {
        return "Exist";
    }

    public String title() {
        return "\u8868\u5355\u662f\u5426\u6709\u6570\u636e";
    }

    protected void toExplain(IContext context, List<IASTNode> parameters, StringBuilder buffer, Object info) throws InterpretException {
        buffer.append("\u62a5\u8868");
        parameters.get(0).interpret(context, buffer, Language.EXPLAIN, info);
        buffer.append("\u4e0d\u4e3a\u7a7a");
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 1;
    }

    public String category() {
        return "\u5176\u5b83\u51fd\u6570";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = (QueryContext)context;
        try {
            ReportInfo reportInfo;
            IDataModelLinkFinder dataModelLinkFinder;
            String reportName = (String)parameters.get(0).evaluate(context);
            IFmlExecEnvironment env = qContext.getExeContext().getEnv();
            if (env != null && (dataModelLinkFinder = env.getDataModelLinkFinder()) != null && (reportInfo = dataModelLinkFinder.findReportInfo(reportName)) == null) {
                throw new SyntaxException("\u6839\u636e\u62a5\u8868\u540d'" + reportName + "\u6ca1\u6709\u627e\u5230\u62a5\u8868");
            }
        }
        catch (SyntaxException se) {
            throw se;
        }
        catch (Exception e) {
            throw new SyntaxException(e.getMessage(), (Throwable)e);
        }
        return this.getResultType((IContext)qContext, parameters);
    }

    public Object evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        QueryContext qContext = null;
        try {
            if (context instanceof QueryContext) {
                qContext = (QueryContext)context;
                String reportName = (String)parameters.get(0).evaluate(context);
                ReportFmlExecEnvironment env = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv();
                IRunTimeViewController controller = env.getController();
                FormDefine form = controller.queryFormByCodeInScheme(env.getFormSchemeKey(), reportName.toUpperCase());
                if (form == null) {
                    throw new SyntaxException("\u6839\u636e\u62a5\u8868\u540d'" + reportName + "' \u5728\u62a5\u8868\u65b9\u6848 '" + env.getFormSchemeKey() + "' \u4e0b\u6ca1\u6709\u627e\u5230\u62a5\u8868");
                }
                List allRegionsInForm = controller.getAllRegionsInForm(form.getKey());
                String cacheKey = this.name() + "_" + reportName;
                if (!qContext.isBatch()) {
                    Boolean result = (Boolean)qContext.getCache().get(cacheKey);
                    if (result == null) {
                        DataRegionDefine region;
                        result = false;
                        Iterator iterator = allRegionsInForm.iterator();
                        while (iterator.hasNext() && !(result = this.judgeExist(qContext, env, result, region = (DataRegionDefine)iterator.next())).booleanValue()) {
                        }
                        qContext.getCache().put(cacheKey, result);
                    }
                    return result;
                }
                Set<String> existReports = (Set<String>)qContext.getCache().get(cacheKey);
                if (existReports == null) {
                    existReports = this.initExistReportSet(qContext, env, allRegionsInForm, cacheKey);
                    qContext.getCache().put(cacheKey, existReports);
                }
                return existReports.contains(qContext.getCurrentMasterKey().toString());
            }
        }
        catch (Exception e) {
            qContext.getMonitor().exception(e);
        }
        return false;
    }

    private Set<String> initExistReportSet(QueryContext qContext, ReportFmlExecEnvironment env, List<DataRegionDefine> allRegionsInForm, String cacheKey) throws Exception {
        HashSet<String> existReports = new HashSet<String>();
        for (DataRegionDefine region : allRegionsInForm) {
            if (region.getInputOrderFieldKey() != null) {
                this.initFloatRegion(qContext, env, existReports, region);
                continue;
            }
            this.initFixRegion(qContext, env, existReports, region);
        }
        return existReports;
    }

    private void initFixRegion(QueryContext qContext, ReportFmlExecEnvironment env, Set<String> existReports, DataRegionDefine region) throws Exception {
        IDataQuery dataQuery = this.createDataQuery(qContext, env, region.getKey(), qContext.getMasterKeys());
        IReadonlyTable executeReader = dataQuery.executeReader(qContext.getExeContext());
        if (executeReader.getCount() > 0) {
            for (int i = 0; i < executeReader.getCount(); ++i) {
                IDataRow row = executeReader.getItem(i);
                boolean exist = false;
                for (int index = 0; index < row.getFieldsInfo().getFieldCount(); ++index) {
                    AbstractData value = row.getValue(index);
                    if (value == null || value.isNull) continue;
                    exist = true;
                    break;
                }
                if (!exist) continue;
                DimensionValueSet masterKey = this.getMasterKey(qContext, row);
                existReports.add(masterKey.toString());
            }
        }
    }

    private DimensionValueSet getMasterKey(QueryContext qContext, IDataRow row) {
        DimensionValueSet currentMasterKey = qContext.getCurrentMasterKey();
        DimensionValueSet masterKey = new DimensionValueSet(currentMasterKey);
        DimensionValueSet rowKeys = row.getRowKeys();
        for (int i = 0; i < rowKeys.size(); ++i) {
            String keyName = rowKeys.getName(i);
            if (!masterKey.hasValue(keyName)) continue;
            masterKey.setValue(keyName, rowKeys.getValue(i));
        }
        return masterKey;
    }

    private void initFloatRegion(QueryContext qContext, ReportFmlExecEnvironment env, Set<String> existReports, DataRegionDefine region) throws Exception {
        DimensionValueSet dimValueSet = qContext.getMasterKeys();
        FieldDefine orderField = env.getRuntimeController().queryFieldDefine(region.getInputOrderFieldKey());
        IGroupingQuery groupQuery = this.createGroupQuery(qContext, env, region.getKey(), orderField, dimValueSet);
        String tableName = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableName(orderField);
        TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(tableName);
        for (int i = 0; i < dimValueSet.size(); ++i) {
            String keyName = dimValueSet.getName(i);
            ColumnModelDefine dimensionColumn = tableInfo.getDimensionField(keyName);
            if (dimensionColumn == null) continue;
            FieldDefine dimensionField = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(dimensionColumn);
            groupQuery.addGroupColumn(dimensionField);
        }
        IGroupingTable table = groupQuery.executeReader(qContext.getExeContext());
        if (table.getCount() > 0) {
            for (int i = 0; i < table.getCount(); ++i) {
                IDataRow row = table.getItem(i);
                AbstractData value = table.getItem(i).getValue(0);
                if (value.getAsInt() <= 0) continue;
                DimensionValueSet masterKey = this.getMasterKey(qContext, row);
                existReports.add(masterKey.toString());
            }
        }
    }

    private Boolean judgeExist(QueryContext qContext, ReportFmlExecEnvironment env, Boolean result, DataRegionDefine region) throws Exception {
        block3: {
            block2: {
                AbstractData value;
                if (region.getInputOrderFieldKey() == null) break block2;
                FieldDefine orderField = env.getRuntimeController().queryFieldDefine(region.getInputOrderFieldKey());
                IGroupingQuery groupQuery = this.createGroupQuery(qContext, env, region.getKey(), orderField, qContext.getCurrentMasterKey());
                IGroupingTable table = groupQuery.executeReader(qContext.getExeContext());
                if (table.getCount() <= 0 || (value = table.getItem(0).getValue(0)).getAsInt() <= 0) break block3;
                result = true;
                break block3;
            }
            IDataQuery dataQuery = this.createDataQuery(qContext, env, region.getKey(), qContext.getCurrentMasterKey());
            IReadonlyTable executeReader = dataQuery.executeReader(qContext.getExeContext());
            if (executeReader.getCount() > 0) {
                IDataRow row = executeReader.getItem(0);
                for (int i = 0; i < row.getFieldsInfo().getFieldCount(); ++i) {
                    AbstractData value = row.getValue(i);
                    if (value == null || value.isNull) continue;
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    private IDataQuery createDataQuery(QueryContext qContext, ReportFmlExecEnvironment env, String regionKey, DimensionValueSet dimValueSet) throws Exception {
        DimensionValueSet masterKeys = new DimensionValueSet(dimValueSet);
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IDataQuery request = accessProvider.newDataQuery();
        IRunTimeViewController controller = env.getController();
        List fieldKeysInRegion = controller.getFieldKeysInRegion(regionKey);
        List fields = env.getRuntimeController().queryFieldDefinesInRange((Collection)fieldKeysInRegion);
        for (FieldDefine field : fields) {
            request.addColumn(field);
        }
        request.setQueryParam(qContext.getQueryParam());
        request.setMasterKeys(masterKeys);
        Map tempAssistantTables = qContext.getTempAssistantTables();
        if (tempAssistantTables.size() > 0) {
            for (String dimension : tempAssistantTables.keySet()) {
                TempAssistantTable tempTable = (TempAssistantTable)tempAssistantTables.get(dimension);
                request.setTempAssistantTable(dimension, tempTable);
            }
        }
        return request;
    }

    private IGroupingQuery createGroupQuery(QueryContext qContext, ReportFmlExecEnvironment env, String regionKey, FieldDefine orderField, DimensionValueSet dimValueSet) throws Exception {
        DimensionValueSet masterKeys = new DimensionValueSet(dimValueSet);
        IDataAccessProvider accessProvider = (IDataAccessProvider)SpringBeanProvider.getBean(IDataAccessProvider.class);
        IGroupingQuery request = accessProvider.newGroupingQuery();
        request.addColumn(orderField);
        request.setGatherType(0, FieldGatherType.FIELD_GATHER_COUNT);
        request.setWantDetail(false);
        request.setQueryParam(qContext.getQueryParam());
        request.setMasterKeys(masterKeys);
        Map tempAssistantTables = qContext.getTempAssistantTables();
        if (tempAssistantTables.size() > 0) {
            for (String dimension : tempAssistantTables.keySet()) {
                TempAssistantTable tempTable = (TempAssistantTable)tempAssistantTables.get(dimension);
                request.setTempAssistantTable(dimension, tempTable);
            }
        }
        return request;
    }

    public DataEngineConsts.FuncReadWriteType getReadWriteType() {
        return DataEngineConsts.FuncReadWriteType.UNKNOWN;
    }
}

