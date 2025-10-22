/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.QueryField
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.NodeShowInfo
 *  com.jiuqi.np.dataengine.node.RestrictInfo
 *  com.jiuqi.np.dataengine.parse.WorkSheet
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.dynamic.DynamicNodeException;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.QueryField;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.dataengine.parse.WorkSheet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisCellDataNode;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisDynamicDataNode;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisFormulaParseUtils;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisDynamicDataNodeFinder
implements IReportDynamicNodeProvider {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisDynamicDataNodeFinder.class);

    protected AnalysisDynamicDataNode createNode(QueryContext qContext, ColumnModelDefine columnModel, PeriodModifier modifier, DimensionValueSet dimensionRestriction) throws DynamicNodeException {
        if (columnModel != null) {
            try {
                ExecutorContext exeContext = qContext.getExeContext();
                QueryField queryField = exeContext.getCache().extractQueryField(exeContext, columnModel, modifier, dimensionRestriction);
                AnalysisDynamicDataNode fieldNode = new AnalysisDynamicDataNode(queryField);
                if (modifier == null && dimensionRestriction == null) {
                    this.tryBindDataLink(qContext, queryField, fieldNode);
                }
                return fieldNode;
            }
            catch (Exception e) {
                throw new DynamicNodeException((Throwable)e);
            }
        }
        return null;
    }

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        QueryContext qContext = (QueryContext)context;
        IColumnModelFinder columnModelFinder = qContext.getColumnModelFinder();
        String groupName = qContext.getDefaultGroupName();
        try {
            ColumnModelDefine columnModel = columnModelFinder.findColumnModelDefine(qContext.getExeContext(), refName.toUpperCase());
            if (columnModel != null) {
                String tableName = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableModel(columnModel).getName();
                TableModelRunInfo tableInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(tableName);
                groupName = tableInfo.getTableModelDefine().getCode();
            }
        }
        catch (Exception e) {
            qContext.getMonitor().exception(e);
        }
        IASTNode node = this.find(qContext, token, groupName, refName, false);
        return node;
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        QueryContext qContext = (QueryContext)context;
        IDataModelLinkFinder dataLinkFinder = qContext.getExeContext().getDataModelLinkFinder();
        DataModelLinkColumn column = null;
        boolean showDictTitle = false;
        if (dataLinkFinder != null && (column = dataLinkFinder.findDataColumnByFieldName(null, refName.toUpperCase())) == null && refName.endsWith("$")) {
            refName = refName.substring(0, refName.length() - 1);
            column = dataLinkFinder.findDataColumnByFieldName(null, refName.toUpperCase());
            showDictTitle = true;
        }
        if (column == null) {
            IASTNode node = this.find((IContext)qContext, token, refName);
            if (node instanceof AnalysisDynamicDataNode) {
                AnalysisDynamicDataNode dataNode = (AnalysisDynamicDataNode)node;
                dataNode.getShowInfo().setHasBracket(false);
            }
            return node;
        }
        column.getShowInfo().setZBExpression(true);
        AnalysisCellDataNode cellNode = null;
        try {
            IASTNode node = AnalysisFormulaParseUtils.createNodeByDataLinkColumn(qContext, token, column, null);
            if (node != null) {
                if (node instanceof AnalysisDynamicDataNode) {
                    AnalysisDynamicDataNode dataNode = (AnalysisDynamicDataNode)node;
                    dataNode.getShowInfo().setHasBracket(false);
                    if (showDictTitle) {
                        dataNode.setShowDictTitle(true);
                        dataNode.getShowInfo().setEndAppend("$");
                    }
                }
                cellNode = new AnalysisCellDataNode(token, (IWorksheet)new WorkSheet(column.getReportInfo()), column, node, qContext.getExeContext().isJQReportModel());
            }
        }
        catch (Exception e) {
            throw new DynamicNodeException((Throwable)e);
        }
        return cellNode;
    }

    public IASTNode find(IContext context, Token token, List<String> objPath) throws DynamicNodeException {
        QueryContext qContext = (QueryContext)context;
        String itemName = null;
        String groupName = null;
        if (objPath.size() == 1) {
            itemName = objPath.get(0);
            return this.find((IContext)qContext, token, itemName);
        }
        if (objPath.size() == 2) {
            groupName = objPath.get(0);
            itemName = objPath.get(1);
            return this.find(qContext, token, groupName, itemName, true);
        }
        return null;
    }

    public IASTNode findRestrict(IContext context, Token token, List<String> objPath, List<IASTNode> restrictItems) throws DynamicNodeException {
        QueryContext qContext = (QueryContext)context;
        IColumnModelFinder columnModelFinder = qContext.getColumnModelFinder();
        String groupName = null;
        String refName = null;
        if (objPath.size() == 1) {
            refName = objPath.get(0);
        } else if (objPath.size() == 2) {
            groupName = objPath.get(0);
            refName = objPath.get(1);
        }
        Object node = null;
        try {
            NodeShowInfo showInfo = new NodeShowInfo();
            ColumnModelDefine columnModel = null;
            RestrictInfo restrictInfo = null;
            if (groupName != null) {
                columnModel = qContext.getExeContext().getCache().getDataModelDefinitionsCache().parseSearchField(groupName.toUpperCase(), refName.toUpperCase(), null);
                if (columnModel == null) {
                    columnModel = columnModelFinder.findColumnModelDefine(qContext.getExeContext(), groupName.toUpperCase(), refName.toUpperCase());
                }
            } else {
                IFmlExecEnvironment env = qContext.getExeContext().getEnv();
                int periodType = 0;
                if (env != null) {
                    periodType = env.getPeriodType();
                }
                restrictInfo = ExpressionUtils.parseRestrictInfo((QueryContext)qContext, null, (NodeShowInfo)showInfo, restrictItems);
                if (restrictInfo.periodModifier != null && env != null) {
                    PeriodWrapper pw = new PeriodWrapper(2000, periodType, 1);
                    env.getPeriodAdapter(qContext.getExeContext()).modify(pw, restrictInfo.periodModifier);
                    periodType = pw.getType();
                } else if (restrictInfo.getDimensionRestriction() != null && restrictInfo.getDimensionRestriction().hasValue("DATATIME")) {
                    PeriodWrapper pw = new PeriodWrapper((String)restrictInfo.getDimensionRestriction().getValue("DATATIME"));
                    periodType = pw.getType();
                }
                columnModel = columnModelFinder.findColumnModelDefine(qContext.getExeContext(), refName.toUpperCase(), periodType);
            }
            if (columnModel != null) {
                if (restrictInfo == null) {
                    restrictInfo = ExpressionUtils.parseRestrictInfo((QueryContext)qContext, null, (NodeShowInfo)showInfo, restrictItems);
                }
                AnalysisDynamicDataNode dataNode = this.createNode(qContext, columnModel, restrictInfo.periodModifier, restrictInfo.getDimensionRestriction(qContext, columnModel));
                ExpressionUtils.bindRestrictToNode((IContext)context, (RestrictInfo)restrictInfo, (DynamicDataNode)dataNode);
                dataNode.getShowInfo().setTableName(groupName);
                dataNode.getShowInfo().addInnerAppend(showInfo.getInnerAppend());
                dataNode.getShowInfo().addEndAppend(showInfo.getEndAppend());
                node = dataNode;
            } else {
                String sheetName = groupName == null ? null : groupName.toUpperCase();
                restrictInfo = ExpressionUtils.parseRestrictInfo((QueryContext)qContext, (String)sheetName, (NodeShowInfo)showInfo, restrictItems);
                AnalysisCellDataNode cellNode = this.tryCreateNodeByDataLink(qContext, token, sheetName, refName.toUpperCase(), restrictInfo);
                cellNode.getDataModelLinkColumn().getShowInfo().setSheetName(groupName);
                cellNode.getDataModelLinkColumn().getShowInfo().addInnerAppend(showInfo.getInnerAppend());
                cellNode.getDataModelLinkColumn().getShowInfo().addEndAppend(showInfo.getEndAppend());
                node = cellNode;
            }
        }
        catch (ParseException e) {
            throw new DynamicNodeException((Throwable)e);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return node;
    }

    protected IASTNode find(QueryContext qContext, Token token, String groupName, String refName, boolean hasGroupName) throws DynamicNodeException {
        if (groupName == null) {
            return null;
        }
        groupName = groupName.toUpperCase();
        refName = refName.toUpperCase();
        try {
            AnalysisCellDataNode cellNode = this.tryCreateNodeByDataLink(qContext, token, groupName.toUpperCase(), refName.toUpperCase(), null);
            if (cellNode != null) {
                if (hasGroupName) {
                    cellNode.getDataModelLinkColumn().getShowInfo().setSheetName(groupName);
                    IASTNode node = cellNode.getChild(0);
                    if (node instanceof AnalysisDynamicDataNode) {
                        AnalysisDynamicDataNode dataNode = (AnalysisDynamicDataNode)node;
                        dataNode.getShowInfo().setSheetName(groupName);
                    }
                }
                return cellNode;
            }
            DataModelDefinitionsCache dataDefinitionsCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
            ColumnModelDefine columnModel = dataDefinitionsCache.parseSearchField(groupName.toUpperCase(), refName.toUpperCase(), null);
            if (columnModel != null) {
                AnalysisDynamicDataNode dataNode = this.createNode(qContext, columnModel, null, null);
                if (hasGroupName) {
                    dataNode.getShowInfo().setTableName(groupName);
                }
                return dataNode;
            }
        }
        catch (Exception e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
        return null;
    }

    protected AnalysisCellDataNode tryCreateNodeByDataLink(QueryContext qContext, Token token, String groupName, String itemName, RestrictInfo restrictInfo) throws DynamicNodeException {
        DataModelLinkColumn column;
        IDataModelLinkFinder dataLinkFinder = qContext.getExeContext().getDataModelLinkFinder();
        if (dataLinkFinder == null) {
            return null;
        }
        String sheetName = groupName;
        if (sheetName == null) {
            sheetName = qContext.getDefaultGroupName();
        }
        if ((column = this.findDataLinkColumn(qContext, sheetName, itemName, restrictInfo, dataLinkFinder)) == null) {
            return null;
        }
        AnalysisCellDataNode cellNode = null;
        try {
            IASTNode node = AnalysisFormulaParseUtils.createNodeByDataLinkColumn(qContext, token, column, restrictInfo);
            if (node != null) {
                cellNode = new AnalysisCellDataNode(token, (IWorksheet)new WorkSheet(column.getReportInfo()), column, node, qContext.getExeContext().isJQReportModel());
            }
        }
        catch (Exception e) {
            throw new DynamicNodeException((Throwable)e);
        }
        return cellNode;
    }

    protected DataModelLinkColumn findDataLinkColumn(QueryContext qContext, String groupName, String itemName, RestrictInfo restrictInfo, IDataModelLinkFinder dataLinkFinder) {
        List relatedReportInfos;
        if (dataLinkFinder == null) {
            return null;
        }
        ReportInfo reportInfo = null;
        if (restrictInfo != null && restrictInfo.relateItem != null) {
            reportInfo = dataLinkFinder.findReportInfo(restrictInfo.relateItem, groupName);
        }
        if (reportInfo == null && restrictInfo == null && !StringUtils.isEmpty((String)qContext.getDefaultLinkAlias())) {
            reportInfo = dataLinkFinder.findReportInfo(qContext.getDefaultLinkAlias(), groupName);
        }
        if (reportInfo == null) {
            reportInfo = dataLinkFinder.findReportInfo(groupName);
        }
        DataModelLinkColumn column = null;
        if (reportInfo != null) {
            column = dataLinkFinder.findDataColumn(reportInfo, itemName);
            if (column == null && (column = dataLinkFinder.findDataColumnByFieldName(reportInfo, itemName)) != null) {
                column.getShowInfo().setZBExpression(true);
            }
        } else if ((restrictInfo == null || restrictInfo.relateItem == null) && (relatedReportInfos = dataLinkFinder.findAllRelatedReportInfo(groupName)) != null) {
            for (ReportInfo relatedReportInfo : relatedReportInfos) {
                column = dataLinkFinder.findDataColumn(relatedReportInfo, itemName);
                if (column == null) continue;
                return column;
            }
        }
        return column;
    }

    protected void tryBindDataLink(QueryContext qContext, QueryField queryField, AnalysisDynamicDataNode fieldNode) {
        DataModelLinkColumn column;
        ReportInfo reportInfo;
        String groupName = qContext.getDefaultGroupName();
        IDataModelLinkFinder dataLinkFinder = qContext.getExeContext().getDataModelLinkFinder();
        if (dataLinkFinder != null && !StringUtils.isEmpty((String)groupName) && (reportInfo = dataLinkFinder.findReportInfo(groupName)) != null && (column = dataLinkFinder.findDataColumnByFieldName(reportInfo, queryField.getFieldName())) != null && column.getColumModel().getID().equals(queryField.getUID())) {
            column.getShowInfo().setZBExpression(true);
            fieldNode.setDataModelLink(column);
            queryField.setRegion(column.getRegion());
        }
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }
}

