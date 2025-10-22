/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.datasource.AdhocDataSourceException
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.ArrayData
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.SQLInfoDescr
 *  com.jiuqi.bi.syntax.operator.In
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DataEngineUtil
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.QueryTable
 *  com.jiuqi.np.dataengine.common.SqlQueryHelper
 *  com.jiuqi.np.dataengine.common.TempResource
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.Formula
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExprExecNetwork
 *  com.jiuqi.np.dataengine.executors.ExprExecRegionCreator
 *  com.jiuqi.np.dataengine.executors.FmlExecRegionCreator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.dataengine.node.CheckExpression
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.VariableDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nr.common.intf.ISecretLevelFilter
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ItemNode
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.data.AbstractData
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.exception.UnauthorizedEntityException
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.datasource;

import com.jiuqi.bi.adhoc.datasource.AdhocDataSourceException;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.ArrayData;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.SQLInfoDescr;
import com.jiuqi.bi.syntax.operator.In;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DataEngineUtil;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.QueryTable;
import com.jiuqi.np.dataengine.common.SqlQueryHelper;
import com.jiuqi.np.dataengine.common.TempResource;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.Formula;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExprExecNetwork;
import com.jiuqi.np.dataengine.executors.ExprExecRegionCreator;
import com.jiuqi.np.dataengine.executors.FmlExecRegionCreator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.node.CheckExpression;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.VariableDataNode;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.bql.dataengine.query.QuerySqlBuilder;
import com.jiuqi.nr.bql.datasource.QueryContext;
import com.jiuqi.nr.bql.datasource.UnitChekMonitor;
import com.jiuqi.nr.bql.datasource.UnitFilterExpInfo;
import com.jiuqi.nr.bql.datasource.reader.EntityDimTableReader;
import com.jiuqi.nr.bql.datasource.reader.PeriodDimTableReader;
import com.jiuqi.nr.common.intf.ISecretLevelFilter;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ItemNode;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.data.AbstractData;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.exception.UnauthorizedEntityException;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UnitFilter {
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionController;
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired
    private PeriodDimTableReader periodDimTableReader;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;
    @Autowired(required=false)
    private ISecretLevelFilter secretLevelFilter;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void judgeUnitCondition(QueryContext qContext, DimensionValueSet masterKeys, UnitChekMonitor condigionMonitor, UnitFilterExpInfo unitFilterExpInfo, EntityDimTableReader entityDimTableReader) throws Exception {
        DimensionValueSet filterMasterKeys = new DimensionValueSet(masterKeys);
        String period = this.getVersionPeriod(qContext, filterMasterKeys);
        filterMasterKeys.setValue("DATATIME", (Object)period);
        String filter = unitFilterExpInfo.getFilter();
        com.jiuqi.np.dataengine.query.QueryContext cContext = null;
        qContext.getLogger().debug("\u6267\u884c\u5355\u4f4d\u8fc7\u6ee4\uff1afilter=" + filter + " ; formSchemeKey=" + unitFilterExpInfo.getFormSchemeKey() + " ; period=" + period);
        try (TempResource tempResource = new TempResource();){
            cContext = this.createUnitCheckContext(qContext, null, tempResource, filterMasterKeys, period, unitFilterExpInfo.getFormSchemeKey(), condigionMonitor);
            ReportFormulaParser formulaParser = qContext.getExeContext().getCache().getFormulaParser(true);
            IExpression condition = formulaParser.parseEval(filter, (IContext)cContext);
            for (IASTNode node : condition) {
                for (int n = 0; n < node.childrenSize(); ++n) {
                    IASTNode child = node.getChild(n);
                    if (!(child instanceof ItemNode)) continue;
                    IASTNode newDataNode = formulaParser.parseEval(qContext.getUnitEntityDefine().getCode() + "[" + child.toString() + "]", (IContext)cContext).getChild(0);
                    node.setChild(n, newDataNode);
                }
            }
            String unitDimensionName = qContext.getUnitEntityDefine().getDimensionName();
            if (!this.filterBySql(qContext, filterMasterKeys, period, cContext, filter, condition, unitDimensionName)) {
                Formula formula = new Formula();
                formula.setFormula(filter);
                formula.setReportName("query");
                formula.setCode("unitFilter");
                CheckExpression srcMainDimCondigion = new CheckExpression(condition, formula);
                List<IEntityRow> authEntityRows = this.initAuthListToMasterKey(qContext, filterMasterKeys, entityDimTableReader);
                Object currency = filterMasterKeys.getValue("MD_CURRENCY");
                if (currency != null && currency.toString().contains("PROVIDER_BASECURRENCY")) {
                    HashMap<String, ArrayList<String>> unitsByCurrency = new HashMap<String, ArrayList<String>>();
                    for (IEntityRow row : authEntityRows) {
                        AbstractData bwb = row.getValue("CURRENCYID");
                        String bwbStr = bwb == null ? null : bwb.getAsString();
                        ArrayList<String> units = (ArrayList<String>)unitsByCurrency.get(bwbStr);
                        if (units == null) {
                            units = new ArrayList<String>();
                            unitsByCurrency.put(bwbStr, units);
                        }
                        units.add(row.getEntityKeyData());
                    }
                    ArrayList filteredUnits = new ArrayList();
                    for (Map.Entry entry : unitsByCurrency.entrySet()) {
                        filterMasterKeys.setValue("MD_CURRENCY", entry.getKey());
                        filterMasterKeys.setValue(unitDimensionName, entry.getValue());
                        condigionMonitor.setSrcDimension(filterMasterKeys);
                        cContext = this.createUnitCheckContext(qContext, cContext.getQueryParam(), tempResource, filterMasterKeys, period, unitFilterExpInfo.getFormSchemeKey(), condigionMonitor);
                        cContext.setMasterKeys(filterMasterKeys);
                        FmlExecRegionCreator regionCreator = new FmlExecRegionCreator(cContext, false, cContext.getQueryParam());
                        ExprExecNetwork execNetwork = new ExprExecNetwork(cContext, (ExprExecRegionCreator)regionCreator);
                        execNetwork.arrangeCheckExpression((IExpression)srcMainDimCondigion);
                        execNetwork.initialize((Object)condigionMonitor);
                        execNetwork.checkRunTask((Object)condigionMonitor);
                        condigionMonitor.finish();
                        filteredUnits.addAll((Collection)filterMasterKeys.getValue(unitDimensionName));
                    }
                    masterKeys.setValue(unitDimensionName, filteredUnits);
                    qContext.getLogger().debug("\u6309\u672c\u4f4d\u5e01\u6267\u884c\u5355\u4f4d\u5185\u5b58\u8fc7\u6ee4\uff1afilter=" + filter + " ; formSchemeKey=" + unitFilterExpInfo.getFormSchemeKey() + " ; period=" + period);
                } else {
                    qContext.getLogger().debug("\u6267\u884c\u5185\u5b58\u5355\u4f4d\u8fc7\u6ee4\uff1afilter=" + filter + " ; formSchemeKey=" + unitFilterExpInfo.getFormSchemeKey() + " ; period=" + period);
                    cContext.setMasterKeys(filterMasterKeys);
                    condigionMonitor.setSrcDimension(filterMasterKeys);
                    FmlExecRegionCreator regionCreator = new FmlExecRegionCreator(cContext, false, cContext.getQueryParam());
                    ExprExecNetwork execNetwork = new ExprExecNetwork(cContext, (ExprExecRegionCreator)regionCreator);
                    execNetwork.arrangeCheckExpression((IExpression)srcMainDimCondigion);
                    execNetwork.initialize((Object)condigionMonitor);
                    execNetwork.checkRunTask((Object)condigionMonitor);
                    condigionMonitor.finish();
                    masterKeys.setValue(unitDimensionName, filterMasterKeys.getValue(unitDimensionName));
                }
            } else {
                masterKeys.setValue(unitDimensionName, filterMasterKeys.getValue(unitDimensionName));
            }
        }
        finally {
            cContext.getQueryParam().closeConnection();
        }
    }

    public String getVersionPeriod(QueryContext qContext, DimensionValueSet masterKeys) throws AdhocDataSourceException {
        String period = qContext.getPeriod(masterKeys);
        if (period == null) {
            period = this.periodDimTableReader.getCurrentPeriod(qContext.getPeriodEntityId());
        }
        PeriodWrapper pw = new PeriodWrapper(period);
        IPeriodEntity periodEntity = this.periodDimTableReader.getPeriodEntityAdapter().getPeriodEntity(qContext.getPeriodEntityId());
        if (pw.getType() != periodEntity.getPeriodType().type()) {
            throw new AdhocDataSourceException("\u5355\u4f4d\u8fc7\u6ee4\u6761\u4ef6\u6240\u5c5e\u4efb\u52a1\u7684\u65f6\u671f\u7c7b\u578b\u4e0e\u5f53\u524d\u67e5\u8be2\u4e0d\u7b26");
        }
        if (qContext.getAdjustVersionPeriod() != null && qContext.getAdjustVersionPeriod().compareTo(period) < 0) {
            period = qContext.getAdjustVersionPeriod();
        }
        return period;
    }

    public boolean filterBySql(QueryContext qContext, DimensionValueSet masterKeys, String period, com.jiuqi.np.dataengine.query.QueryContext cContext, String formula, IExpression condition, String unitDimensionName) {
        block29: {
            try {
                boolean succ = this.tryTransToUnitDimValues(qContext, masterKeys, cContext, formula, condition, unitDimensionName);
                if (succ) {
                    return true;
                }
                if (!this.judgeSqlModel(qContext, masterKeys, condition, unitDimensionName)) break block29;
                String unitTableName = qContext.getUnitEntityDefine().getCode();
                HashMap<String, List<QueryField>> dataTableFields = new HashMap<String, List<QueryField>>();
                boolean support = this.collectQueryFields(qContext, condition, dataTableFields);
                if (!support) break block29;
                DataModelDefinitionsCache dataModelDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
                IEntityModel unitEntityModel = this.entityMetaService.getEntityModel(qContext.getUnitEntityDefine().getId());
                TableModelRunInfo unitTableInfo = dataModelDefinitionsCache.getTableInfo(unitTableName);
                String unitKeyFiledName = unitEntityModel.getBizKeyField().getName();
                StringBuilder sql = new StringBuilder();
                sql.append("select ").append(unitTableName).append(".").append(unitKeyFiledName);
                sql.append(" from ").append(this.getTableName(qContext, unitTableName)).append(" ").append(unitTableName);
                for (Map.Entry entry : dataTableFields.entrySet()) {
                    String tableName = (String)entry.getKey();
                    if (tableName.equals(unitTableName)) continue;
                    TableModelRunInfo tableInfo = dataModelDefinitionsCache.getTableInfo(tableName);
                    sql.append(" left join ").append(this.getTableName(qContext, tableName)).append(" ").append(tableName).append(" on ");
                    sql.append(unitTableName).append(".").append(unitKeyFiledName).append("=").append(tableName).append(".").append(tableInfo.getDimensionField(unitDimensionName).getName());
                    sql.append(" and ");
                    sql.append(tableName).append(".").append("DATATIME").append("='").append(period).append("'");
                }
                sql.append(" where ");
                PeriodWrapper periodWrapper = new PeriodWrapper(period);
                Date[] dateRegion = qContext.getExeContext().getPeriodAdapter().getPeriodDateRegion(periodWrapper);
                QuerySqlBuilder.appendVersionFilter(cContext.getQueryParam(), unitTableName, unitTableInfo, sql, dateRegion[1], dateRegion[0], false);
                SQLInfoDescr conditionSqlINfo = new SQLInfoDescr(cContext.getQueryParam().getDatabase(), true, 0, 0);
                sql.append(" and (").append(condition.interpret((IContext)cContext, Language.SQL, (Object)conditionSqlINfo)).append(")");
                qContext.getLogger().debug("\u4e3b\u7ef4\u5ea6\u8fc7\u6ee4\u6761\u4ef6    " + formula + "  \u8f6c\u6362\u6210SQL\u67e5\u8be2\uff0c\u53d6\u51fa\u7b26\u5408\u6761\u4ef6\u7684\u5355\u4f4d");
                ArrayList<String> units = new ArrayList<String>();
                try (SqlQueryHelper sqlHelper = DataEngineUtil.createSqlQueryHelper();
                     ResultSet rs = sqlHelper.executeQuery(cContext.getQueryParam().getConnection(), sql.toString(), cContext.getMonitor());){
                    while (rs.next()) {
                        units.add(rs.getString(1));
                    }
                }
                qContext.getLogger().debug("SQL\u8fc7\u6ee4\u540e\u7684\u5355\u4f4d\u5217\u8868   : " + units + ",size=" + units.size());
                masterKeys.setValue(unitDimensionName, units);
                return true;
            }
            catch (Exception e) {
                qContext.getLogger().error(e.getMessage(), (Throwable)e);
            }
        }
        return false;
    }

    private boolean tryTransToUnitDimValues(QueryContext qContext, DimensionValueSet masterKeys, com.jiuqi.np.dataengine.query.QueryContext cContext, String formula, IExpression condition, String unitDimensionName) throws SyntaxException {
        IASTNode root;
        if (qContext.getGatherSchemeCode() == null && !masterKeys.hasValue(unitDimensionName) && !qContext.hasSchemeDimension() && (root = condition.getChild(0)) instanceof In && root.getChild(0).toString().equals("SYS_UNITKEY")) {
            ArrayData array = (ArrayData)root.getChild(1).evaluate((IContext)cContext);
            ArrayList unitKeys = new ArrayList();
            for (Object o : array) {
                unitKeys.add(o);
            }
            masterKeys.setValue(unitDimensionName, unitKeys);
            qContext.getLogger().debug("unitFilter(" + formula + ") \u8f6c\u6362\u6210\u4e3b\u7ef4\u5ea6\u53d6\u503c\u5217\u8868");
            return true;
        }
        return false;
    }

    public List<IEntityRow> initAuthListToMasterKey(QueryContext qContext, DimensionValueSet masterKeys, EntityDimTableReader entityDimTableReader) throws JQException, UnauthorizedEntityException {
        String unitDimensionName = qContext.getUnitEntityDefine().getDimensionName();
        List authList = (List)masterKeys.getValue(unitDimensionName);
        ArrayList<IEntityRow> authEntityRows = new ArrayList<IEntityRow>();
        if (authList == null || authList.size() > 0) {
            IEntityDefine unitEntityDefine = qContext.getUnitEntityDefine();
            authList = entityDimTableReader.getAuthList(qContext, masterKeys, unitEntityDefine, authEntityRows);
            if (this.secretLevelFilter != null) {
                authList = this.secretLevelFilter.filter(authList);
            }
            masterKeys.setValue(unitDimensionName, authList);
        }
        return authEntityRows;
    }

    public String getTableName(QueryContext qContext, String tableName) {
        if (this.splitTableHelper != null) {
            return this.splitTableHelper.getCurrentSplitTableName(qContext.getExeContext(), tableName);
        }
        return tableName;
    }

    private boolean judgeSqlModel(QueryContext qContext, DimensionValueSet masterKeys, IExpression condition, String unitDimensionName) throws ParseException, ExpressionException, JQException {
        if (!qContext.isJdbcQuery()) {
            return false;
        }
        for (IASTNode node : condition) {
            for (int n = 0; n < node.childrenSize(); ++n) {
                IASTNode child = node.getChild(n);
                if (!(child instanceof VariableDataNode)) continue;
                VariableDataNode varNode = (VariableDataNode)child;
                String varName = varNode.getVariable().getVarName();
                IEntityModel unitEntityModel = this.entityMetaService.getEntityModel(qContext.getUnitEntityDefine().getId());
                QueryField newQueryField = null;
                if (varName.equals("SYS_UNITKEY")) {
                    newQueryField = qContext.getExeContext().getCache().extractQueryField(qContext.getExeContext(), (ColumnModelDefine)unitEntityModel.getBizKeyField(), null, null);
                } else if (varName.equals("SYS_UNITCODE") || varName.equals("DWDM")) {
                    newQueryField = qContext.getExeContext().getCache().extractQueryField(qContext.getExeContext(), (ColumnModelDefine)unitEntityModel.getCodeField(), null, null);
                } else if (varName.equals("SYS_UNITTITLE") || varName.equals("DWMC")) {
                    newQueryField = qContext.getExeContext().getCache().extractQueryField(qContext.getExeContext(), (ColumnModelDefine)unitEntityModel.getNameField(), null, null);
                }
                if (newQueryField == null) continue;
                DynamicDataNode newDataNode = new DynamicDataNode(varNode.getToken(), newQueryField);
                node.setChild(n, (IASTNode)newDataNode);
            }
        }
        return qContext.getGatherSchemeCode() == null && !masterKeys.hasValue(unitDimensionName) && !qContext.hasSchemeDimension() && condition.support(Language.SQL);
    }

    private boolean collectQueryFields(QueryContext qContext, IExpression condition, Map<String, List<QueryField>> dataTableFields) throws JQException, ParseException, ExpressionException {
        boolean support = true;
        for (IASTNode node : condition) {
            if (node instanceof DynamicDataNode) {
                DynamicDataNode dataNode = (DynamicDataNode)node;
                if (dataNode.getStatisticInfo() != null) {
                    support = false;
                    break;
                }
                QueryField queryField = dataNode.getQueryField();
                QueryTable table = queryField.getTable();
                if (!table.getIsSimple()) {
                    support = false;
                    break;
                }
                this.addQueryField(dataTableFields, queryField, table);
                continue;
            }
            if (!(node instanceof VariableDataNode)) continue;
            support = false;
            break;
        }
        return support;
    }

    private void addQueryField(Map<String, List<QueryField>> dataTableFields, QueryField queryField, QueryTable table) {
        List<QueryField> queryFields = dataTableFields.get(table.getTableName());
        if (queryFields == null) {
            queryFields = new ArrayList<QueryField>();
            dataTableFields.put(table.getTableName(), queryFields);
        }
        if (!queryFields.contains(queryField)) {
            queryFields.add(queryField);
        }
    }

    public com.jiuqi.np.dataengine.query.QueryContext createUnitCheckContext(QueryContext qContext, QueryParam queryParam, TempResource tempResource, DimensionValueSet masterKeys, String period, String formSchemeKey, UnitChekMonitor condigionMonitor) throws ParseException {
        if (queryParam == null) {
            queryParam = new QueryParam(this.connectionProvider, this.dataDefinitionController, null);
        }
        queryParam.setSplitTableHelper(this.splitTableHelper);
        DimensionValueSet srcDimension = new DimensionValueSet(masterKeys);
        srcDimension.setValue("DATATIME", (Object)period);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionController, this.entityViewRunTimeController, formSchemeKey);
        env.setDataScehmeKey(qContext.getDataScheme().getKey());
        com.jiuqi.np.dataengine.query.QueryContext cContext = new com.jiuqi.np.dataengine.query.QueryContext(qContext.getExeContext(), queryParam, (IMonitor)condigionMonitor);
        cContext.setTempResource(tempResource);
        cContext.getExeContext().setEnv((IFmlExecEnvironment)env);
        cContext.setMasterKeys(srcDimension);
        cContext.setBatch(true);
        if (masterKeys != null) {
            try {
                for (int i = 0; i < masterKeys.size(); ++i) {
                    Object dimValue = masterKeys.getValue(i);
                    if (!(dimValue instanceof List)) continue;
                    List values = (List)dimValue;
                    String dimension = masterKeys.getName(i);
                    tempResource.getTempAssistantTable(queryParam.getDatabase(), dimension, values, 6);
                }
            }
            catch (Exception e) {
                qContext.getLogger().error(e.getMessage(), (Throwable)e);
            }
        }
        return cContext;
    }

    public IConnectionProvider getConnectionProvider() {
        return this.connectionProvider;
    }
}

