/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.cache.TableCache
 *  com.jiuqi.bi.adhoc.cache.graph.TableGraph
 *  com.jiuqi.bi.adhoc.cache.graph.TableNode
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.syntax.ast.ASTNode
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.data.DimensionNode
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.node.RestrictInfo
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataScheme
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.bql.interpret;

import com.jiuqi.bi.adhoc.cache.TableCache;
import com.jiuqi.bi.adhoc.cache.graph.TableGraph;
import com.jiuqi.bi.adhoc.cache.graph.TableNode;
import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.syntax.ast.ASTNode;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.data.DimensionNode;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.nr.bql.dsv.adapter.DSVAdapter;
import com.jiuqi.nr.bql.interpret.BiAdaptDimNode;
import com.jiuqi.nr.bql.interpret.BiAdaptNode;
import com.jiuqi.nr.bql.interpret.BiAdaptParam;
import com.jiuqi.nr.bql.interpret.BiAdaptTable;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataScheme;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;

public class BiAdaptContext
implements IContext {
    private IRuntimeDataSchemeService dataSchemeService;
    private IEntityMetaService entityMetaService;
    private PeriodEngineService periodEngineService;
    private ReportFormulaParser formulaParser;
    private ExecutorContext executorContext;
    private BiAdaptParam param;
    private Map<String, BiAdaptTable> tableCache = new HashMap<String, BiAdaptTable>();
    private Map<String, TableGraph> tableGraphs = new HashMap<String, TableGraph>();
    private TableNode unitTableNode;
    private TableNode periodTableNode;
    private Logger logger;

    public void init() {
        try {
            if (this.param != null && !this.param.getSelectedFields().isEmpty()) {
                for (String selectedField : this.param.getSelectedFields()) {
                    String[] strs = selectedField.split("\\.");
                    String tableName = strs[0];
                    DataTable dataTable = this.dataSchemeService.getDataTableByCode(tableName);
                    if (dataTable == null) continue;
                    DataScheme dataScheme = this.dataSchemeService.getDataScheme(dataTable.getDataSchemeKey());
                    String dsvName = DSVAdapter.getDSVName(dataScheme);
                    TableGraph tableGraph = TableCache.getGlobal().findGraph(dsvName);
                    if (tableGraph == null || this.tableGraphs.containsKey(dsvName)) continue;
                    this.tableGraphs.put(dsvName, tableGraph);
                    this.initUnitAndPeriod(dataScheme, tableGraph);
                }
            }
        }
        catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
    }

    public BiAdaptTable getTableByCode(String tableCode) throws ParseException {
        DataTable dataTable;
        BiAdaptTable table = this.tableCache.get(tableCode);
        if (table == null && (dataTable = this.dataSchemeService.getDataTableByCode(tableCode)) != null) {
            table = new BiAdaptTable(dataTable, this.findTableNode(tableCode));
            DataModelDefinitionsCache dataDefinitionsCache = this.executorContext.getCache().getDataModelDefinitionsCache();
            List keyFields = this.dataSchemeService.getBizDataFieldByTableKey(dataTable.getKey());
            for (DataField keyField : keyFields) {
                List deployInfos;
                if (keyField.getDataFieldKind() == DataFieldKind.TABLE_FIELD_DIM) {
                    table.getInnerDimFields().add(keyField.getCode());
                }
                if ((deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{keyField.getKey()})) == null || deployInfos.isEmpty()) continue;
                ColumnModelDefine columnModel = dataDefinitionsCache.findField(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
                String dimName = dataDefinitionsCache.getDimensionName(columnModel);
                table.addDimField(dimName, keyField);
                if (!dimName.equals("DATATIME")) continue;
                String periodEntityId = keyField.getRefDataEntityKey();
                table.setPeriodTableName("NR_PERIOD_" + periodEntityId);
                IPeriodEntity periodEntity = this.periodEngineService.getPeriodAdapter().getPeriodEntity(periodEntityId);
                table.setPeriodType(periodEntity.getPeriodType());
            }
        }
        return table;
    }

    public ASTNode findNode(String tableCode, String fieldCode) throws ParseException {
        BiAdaptTable table = this.getTableByCode(tableCode);
        if (table != null) {
            DataField dataField = this.dataSchemeService.getDataFieldByTableKeyAndCode(table.getDataTable().getKey(), fieldCode);
            if (dataField != null) {
                BiAdaptNode node = new BiAdaptNode(table, dataField);
                return node;
            }
        } else if (!this.tableGraphs.isEmpty()) {
            FieldInfo fieldInfo;
            TableNode tableNode;
            if (tableCode.startsWith("MD_ORG")) {
                tableCode = "MD_ORG";
            }
            if ((tableNode = this.findTableNode(tableCode)) != null && (fieldInfo = tableNode.findField(fieldCode)) != null) {
                BiAdaptDimNode dimNode = new BiAdaptDimNode(tableCode, fieldCode, fieldInfo.getDataType());
                return dimNode;
            }
        } else {
            IEntityModel entityModel;
            IEntityAttribute attribute;
            String entityId = this.entityMetaService.getEntityIdByCode(tableCode);
            if (entityId != null && (attribute = (entityModel = this.entityMetaService.getEntityModel(entityId)).getAttribute(fieldCode)) != null) {
                if (tableCode.startsWith("MD_ORG")) {
                    tableCode = "MD_ORG";
                }
                BiAdaptDimNode dimNode = new BiAdaptDimNode(tableCode, fieldCode, attribute.getColumnType().getValue());
                return dimNode;
            }
        }
        return null;
    }

    public TableNode findTableNode(String tableCode) {
        TableGraph tableGraph;
        TableNode tableNode = null;
        Iterator<TableGraph> iterator = this.tableGraphs.values().iterator();
        while (iterator.hasNext() && (tableNode = (tableGraph = iterator.next()).findTable(tableCode)) == null) {
        }
        return tableNode;
    }

    public DataField findDataField(String tableCode, String fieldCode) throws ParseException {
        BiAdaptTable table = this.getTableByCode(tableCode);
        if (table != null) {
            return this.dataSchemeService.getDataFieldByTableKeyAndCode(table.getCode(), fieldCode);
        }
        return null;
    }

    public IEntityAttribute findEntityAttribute(String tableCode, String fieldCode) {
        String entityId = this.entityMetaService.getEntityIdByCode(tableCode);
        if (entityId != null) {
            IEntityModel entityModel = this.entityMetaService.getEntityModel(entityId);
            return entityModel.getAttribute(fieldCode);
        }
        return null;
    }

    public RestrictInfo parseRestrictInfo(String tableCode, List<IASTNode> specExprs) throws DynamicNodeException {
        RestrictInfo restrictInfo = new RestrictInfo();
        if (specExprs == null || specExprs.isEmpty()) {
            return restrictInfo;
        }
        for (IASTNode spec : specExprs) {
            if (spec instanceof DimensionNode) {
                DimensionNode dimesionNode = (DimensionNode)spec;
                restrictInfo.getDimesionNodes().add(dimesionNode);
                continue;
            }
            if (!(spec instanceof DataNode)) continue;
            try {
                DataNode dataNode = (DataNode)spec;
                String text = (String)dataNode.evaluate((IContext)this);
                if (text.startsWith("'") && text.endsWith("'") || text.startsWith("\"") && text.endsWith("\"")) {
                    String statCondition = text.substring(1, text.length() - 1);
                    IExpression expression = this.formulaParser.parseCond(statCondition, (IContext)this);
                    restrictInfo.conditionNode = expression;
                    continue;
                }
                int statKind = -1;
                statKind = StatItem.tryParseStatKind((String)text);
                if (statKind >= 0) {
                    if (statKind > 7) {
                        throw new DynamicNodeException("\u4e0d\u652f\u6301\u7684\u7edf\u8ba1\u7c7b\u578b\uff1a" + text);
                    }
                    restrictInfo.statKind = statKind;
                    continue;
                }
                PeriodModifier pm = PeriodModifier.parse((String)text);
                if (pm == null || pm.isEmpty()) continue;
                if (restrictInfo.periodModifier == null) {
                    restrictInfo.periodModifier = pm;
                    continue;
                }
                restrictInfo.periodModifier.union(pm);
            }
            catch (Exception e) {
                throw new DynamicNodeException("\u89e3\u6790\u6570\u636e\u9650\u5b9a\u4fe1\u606f\u51fa\u9519:" + spec + "\n" + e.getMessage(), (Throwable)e);
            }
        }
        return restrictInfo;
    }

    private void initUnitAndPeriod(DataScheme dataScheme, TableGraph tableGraph) {
        if (this.unitTableNode == null) {
            List schemeDimensions = this.dataSchemeService.getDataSchemeDimension(dataScheme.getKey());
            for (DataDimension dataDimension : schemeDimensions) {
                if (dataDimension.getDimensionType() == DimensionType.UNIT) {
                    IEntityDefine entityDefine = this.entityMetaService.queryEntity(dataDimension.getDimKey());
                    this.unitTableNode = tableGraph.findTable(entityDefine.getDimensionName());
                    continue;
                }
                if (dataDimension.getDimensionType() != DimensionType.PERIOD) continue;
                this.periodTableNode = tableGraph.findTable("NR_PERIOD_" + dataDimension.getDimKey());
            }
        }
    }

    public IRuntimeDataSchemeService getDataSchemeService() {
        return this.dataSchemeService;
    }

    public IEntityMetaService getEntityMetaService() {
        return this.entityMetaService;
    }

    public PeriodEngineService getPeriodEngineService() {
        return this.periodEngineService;
    }

    public ReportFormulaParser getFormulaParser() {
        return this.formulaParser;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setDataSchemeService(IRuntimeDataSchemeService dataSchemeService) {
        this.dataSchemeService = dataSchemeService;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public void setPeriodEngineService(PeriodEngineService periodEngineService) {
        this.periodEngineService = periodEngineService;
    }

    public void setFormulaParser(ReportFormulaParser formulaParser) {
        this.formulaParser = formulaParser;
    }

    public void setExecutorContext(ExecutorContext executorContext) {
        this.executorContext = executorContext;
    }

    public BiAdaptParam getParam() {
        return this.param;
    }

    public void setParam(BiAdaptParam param) {
        this.param = param;
    }

    public TableNode getUnitTableNode() {
        return this.unitTableNode;
    }

    public TableNode getPeriodTableNode() {
        return this.periodTableNode;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
}

