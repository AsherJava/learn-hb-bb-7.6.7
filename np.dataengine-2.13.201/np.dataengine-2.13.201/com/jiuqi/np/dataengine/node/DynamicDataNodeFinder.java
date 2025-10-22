/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IAssignable
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.dynamic.DynamicNodeException
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.syntax.reportparser.IReportDynamicNodeProvider
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IAssignable;
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
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.dataengine.parse.WorkSheet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicDataNodeFinder
implements IReportDynamicNodeProvider {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataNodeFinder.class);
    private String defaultTableName;

    public IASTNode find(IContext context, Token token, String refName) throws DynamicNodeException {
        IASTNode node;
        QueryContext qContext = (QueryContext)context;
        IColumnModelFinder columnModelFinder = qContext.getColumnModelFinder();
        String groupName = qContext.getDefaultGroupName();
        try {
            DataModelLinkColumn column;
            IDataModelLinkFinder dataModelLinkFinder;
            if (groupName != null && (node = this.find(qContext, token, groupName, refName, false)) != null) {
                return node;
            }
            ColumnModelDefine columnModel = columnModelFinder.findColumnModelDefine(qContext.getExeContext(), refName.toUpperCase());
            if (columnModel == null && (dataModelLinkFinder = qContext.getExeContext().getDataModelLinkFinder()) != null && (column = dataModelLinkFinder.findDataColumnByFieldName(qContext.getExeContext(), null, refName.toUpperCase())) != null) {
                columnModel = column.getColumModel();
            }
            if (columnModel != null) {
                DataModelDefinitionsCache tableCache = qContext.getExeContext().getCache().getDataModelDefinitionsCache();
                groupName = tableCache.getTableName(columnModel);
            }
        }
        catch (Exception e) {
            qContext.getMonitor().exception(e);
        }
        node = this.find(qContext, token, groupName, refName, false);
        return node;
    }

    public IASTNode findSpecial(IContext context, Token token, String refName) throws DynamicNodeException {
        QueryContext qContext = (QueryContext)context;
        IDataModelLinkFinder dataModelLinkFinder = qContext.getExeContext().getDataModelLinkFinder();
        DataModelLinkColumn column = null;
        boolean showDictTitle = false;
        IASTNode node = null;
        if (dataModelLinkFinder != null && (column = dataModelLinkFinder.findDataColumnByFieldName(qContext.getExeContext(), null, refName.toUpperCase())) == null) {
            if (refName.endsWith("$")) {
                refName = refName.substring(0, refName.length() - 1);
                column = dataModelLinkFinder.findDataColumnByFieldName(qContext.getExeContext(), null, refName.toUpperCase());
                showDictTitle = true;
            } else {
                int atIndex = refName.indexOf("@");
                if (atIndex > 0) {
                    String fmdmColumn = refName.substring(0, atIndex);
                    String relateItem = refName.substring(atIndex + 1);
                    column = dataModelLinkFinder.findFMDMColumnByLinkAlias(qContext.getExeContext(), fmdmColumn.toUpperCase(), relateItem);
                    if (column != null) {
                        column.getShowInfo().setEndAppend("@" + relateItem);
                        column.getShowInfo().setHasBracket(false);
                        column.getShowInfo().setTableName(null);
                        column.getShowInfo().setZBExpression(true);
                    }
                }
            }
        }
        if (column == null) {
            node = this.find((IContext)qContext, token, refName);
            if (node != null) {
                if (node instanceof DynamicDataNode) {
                    DynamicDataNode dataNode = (DynamicDataNode)node;
                    dataNode.getShowInfo().setHasBracket(false);
                }
                return node;
            }
            return null;
        }
        column.getShowInfo().setZBExpression(true);
        CellDataNode cellNode = null;
        try {
            node = ExpressionUtils.createNodeByDataModelLinkColumn(qContext, token, column, null);
            if (node != null) {
                if (node instanceof DynamicDataNode) {
                    DynamicDataNode dataNode = (DynamicDataNode)node;
                    dataNode.getShowInfo().setHasBracket(false);
                    if (showDictTitle) {
                        dataNode.setShowDictTitle(true);
                        dataNode.getShowInfo().setEndAppend("$");
                    }
                }
                cellNode = new CellDataNode(token, (IWorksheet)new WorkSheet(column.getReportInfo()), column, node, qContext.getExeContext().isJQReportModel());
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
        IAssignable node = null;
        try {
            NodeShowInfo showInfo = new NodeShowInfo();
            ColumnModelDefine columnModelDefine = null;
            RestrictInfo restrictInfo = null;
            if (groupName != null) {
                columnModelDefine = qContext.findColumnModel(groupName.toUpperCase(), refName.toUpperCase(), this.defaultTableName);
                if (columnModelDefine == null) {
                    columnModelDefine = columnModelFinder.findColumnModelDefine(qContext.getExeContext(), groupName.toUpperCase(), refName.toUpperCase());
                }
            } else {
                Object value;
                IFmlExecEnvironment env = qContext.getExeContext().getEnv();
                int periodType = 0;
                if (env != null) {
                    periodType = env.getPeriodType();
                }
                restrictInfo = ExpressionUtils.parseRestrictInfo(qContext, null, showInfo, restrictItems);
                if (restrictInfo.periodModifier != null && env != null) {
                    PeriodWrapper pw = new PeriodWrapper(2000, periodType, 1);
                    env.getPeriodAdapter(qContext.getExeContext()).modify(pw, restrictInfo.periodModifier);
                    periodType = pw.getType();
                } else if (restrictInfo.getDimensionRestriction() != null && restrictInfo.getDimensionRestriction().hasValue("DATATIME") && (value = restrictInfo.getDimensionRestriction().getValue("DATATIME")) instanceof String) {
                    PeriodWrapper pw = new PeriodWrapper((String)value);
                    periodType = pw.getType();
                }
                columnModelDefine = columnModelFinder.findColumnModelDefine(qContext.getExeContext(), refName.toUpperCase(), periodType);
            }
            if (columnModelDefine != null) {
                IDataModelLinkFinder dataLinkFinder;
                if (restrictInfo == null) {
                    restrictInfo = ExpressionUtils.parseRestrictInfo(qContext, null, showInfo, restrictItems);
                }
                boolean supportLocate = true;
                PeriodModifier periodModifier = restrictInfo.periodModifier;
                DimensionValueSet restriction = restrictInfo.getDimensionRestriction(qContext, columnModelDefine);
                if (restriction != null && !restriction.isAllNull() && restrictInfo.hasDimensionNodes() || periodModifier != null) {
                    supportLocate = false;
                }
                if (restrictInfo.relateItem != null && (dataLinkFinder = qContext.getExeContext().getDataModelLinkFinder()) != null) {
                    Map<String, String> dimValuesByLinkAlias;
                    ReportInfo periodInfo = dataLinkFinder.findPeriodInfo(qContext.getExeContext(), restrictInfo.relateItem);
                    if (periodInfo != null && periodModifier == null) {
                        if (periodInfo.getPeriodModifier() != null) {
                            periodModifier = periodInfo.getPeriodModifier();
                        } else if (StringUtils.isNotEmpty((String)periodInfo.getPeriodDim())) {
                            if (restriction == null) {
                                restriction = new DimensionValueSet();
                            }
                            restriction.setValue("DATATIME", periodInfo.getPeriodDim());
                        }
                    }
                    if ((dimValuesByLinkAlias = dataLinkFinder.getDimValuesByLinkAlias(qContext.getExeContext(), restrictInfo.relateItem)) != null) {
                        if (restriction == null) {
                            restriction = new DimensionValueSet();
                        }
                        for (Map.Entry<String, String> entry : dimValuesByLinkAlias.entrySet()) {
                            restriction.setValue(entry.getKey(), entry.getValue());
                        }
                    }
                }
                DynamicDataNode dataNode = this.createNode(qContext, columnModelDefine, periodModifier, restriction);
                ExpressionUtils.bindRestrictToNode(context, restrictInfo, dataNode);
                dataNode.setSupportLocate(supportLocate);
                dataNode.getShowInfo().setTableName(groupName);
                dataNode.getShowInfo().addInnerAppend(showInfo.getInnerAppend());
                dataNode.getShowInfo().addEndAppend(showInfo.getEndAppend());
                node = dataNode;
            } else {
                String sheetName = groupName == null ? null : groupName.toUpperCase();
                showInfo = new NodeShowInfo();
                restrictInfo = ExpressionUtils.parseRestrictInfo(qContext, sheetName, showInfo, restrictItems);
                CellDataNode cellNode = this.tryCreateNodeByDataLink(qContext, token, sheetName, refName, restrictInfo);
                cellNode.getDataModelLinkColumn().getShowInfo().setSheetName(groupName);
                cellNode.getDataModelLinkColumn().getShowInfo().addInnerAppend(showInfo.getInnerAppend());
                cellNode.getDataModelLinkColumn().getShowInfo().addEndAppend(showInfo.getEndAppend());
                node = cellNode;
            }
        }
        catch (ParseException e) {
            throw new DynamicNodeException(e.getMessage(), (Throwable)e);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return node;
    }

    protected IASTNode find(QueryContext qContext, Token token, String groupName, String refName, boolean hasGroupName) throws DynamicNodeException {
        block12: {
            if (groupName == null) {
                return null;
            }
            groupName = groupName.toUpperCase();
            refName = refName.toUpperCase();
            try {
                DataModelLinkColumn column;
                String fieldCode;
                CellDataNode cellNode = this.tryCreateNodeByDataLink(qContext, token, groupName.toUpperCase(), refName.toUpperCase(), null);
                if (cellNode != null) {
                    if (hasGroupName) {
                        cellNode.getDataModelLinkColumn().getShowInfo().setSheetName(groupName);
                        IASTNode node = cellNode.getChild(0);
                        if (node instanceof DynamicDataNode) {
                            DynamicDataNode dataNode = (DynamicDataNode)node;
                            dataNode.getShowInfo().setSheetName(groupName);
                        }
                    }
                    return cellNode;
                }
                String tableCode = groupName.toUpperCase();
                ColumnModelDefine columnModel = qContext.findColumnModel(tableCode, fieldCode = refName.toUpperCase(), this.defaultTableName);
                if (columnModel == null) break block12;
                IDataModelLinkFinder dataLinkFinder = qContext.getExeContext().getDataModelLinkFinder();
                if (dataLinkFinder != null && (column = dataLinkFinder.findDataColumnByTableFieldName(qContext.getExeContext(), tableCode, fieldCode)) != null) {
                    try {
                        column.getShowInfo().setZBExpression(true);
                        IASTNode node = ExpressionUtils.createNodeByDataModelLinkColumn(qContext, token, column, null);
                        if (node != null) {
                            cellNode = new CellDataNode(token, (IWorksheet)new WorkSheet(column.getReportInfo()), column, node, qContext.getExeContext().isJQReportModel());
                            if (hasGroupName && node instanceof DynamicDataNode) {
                                DynamicDataNode dataNode = (DynamicDataNode)node;
                                dataNode.getShowInfo().setTableName(groupName);
                            }
                            return cellNode;
                        }
                    }
                    catch (Exception e) {
                        throw new DynamicNodeException((Throwable)e);
                    }
                }
                DynamicDataNode dataNode = this.createNode(qContext, columnModel, null, null);
                if (hasGroupName) {
                    dataNode.getShowInfo().setTableName(groupName);
                }
                return dataNode;
            }
            catch (Exception e) {
                throw new DynamicNodeException(e.getMessage(), (Throwable)e);
            }
        }
        return null;
    }

    protected CellDataNode tryCreateNodeByDataLink(QueryContext qContext, Token token, String groupName, String itemName, RestrictInfo restrictInfo) throws DynamicNodeException {
        DataModelLinkColumn column;
        IDataModelLinkFinder dataLinkFinder = qContext.getExeContext().getDataModelLinkFinder();
        if (dataLinkFinder == null) {
            return null;
        }
        String sheetName = groupName;
        if (sheetName == null) {
            sheetName = qContext.getDefaultGroupName();
        }
        if ((column = this.findDataModelLinkColumn(qContext, sheetName, itemName, restrictInfo, dataLinkFinder)) == null) {
            return null;
        }
        CellDataNode cellNode = null;
        try {
            IASTNode node = ExpressionUtils.createNodeByDataModelLinkColumn(qContext, token, column, restrictInfo);
            if (node != null) {
                cellNode = new CellDataNode(token, (IWorksheet)new WorkSheet(column.getReportInfo()), column, node, qContext.getExeContext().isJQReportModel());
            }
        }
        catch (Exception e) {
            throw new DynamicNodeException((Throwable)e);
        }
        return cellNode;
    }

    protected DataModelLinkColumn findDataModelLinkColumn(QueryContext qContext, String groupName, String itemName, RestrictInfo restrictInfo, IDataModelLinkFinder dataLinkFinder) {
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
        if (reportInfo != null && column == null && (column = dataLinkFinder.findDataColumnByFieldName(qContext.getExeContext(), reportInfo, itemName)) != null) {
            column.getShowInfo().setZBExpression(true);
        }
        return column;
    }

    protected DynamicDataNode createNode(QueryContext qContext, ColumnModelDefine columnModel, PeriodModifier modifier, DimensionValueSet dimensionRestriction) throws DynamicNodeException {
        if (columnModel != null) {
            try {
                ExecutorContext exeContext = qContext.getExeContext();
                QueryField queryField = exeContext.getCache().extractQueryField(exeContext, columnModel, modifier, dimensionRestriction);
                DynamicDataNode fieldNode = new DynamicDataNode(queryField);
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

    protected void tryBindDataLink(QueryContext qContext, QueryField queryField, DynamicDataNode fieldNode) {
        DataModelLinkColumn column;
        ReportInfo reportInfo;
        String groupName = qContext.getDefaultGroupName();
        ExecutorContext exeContext = qContext.getExeContext();
        IDataModelLinkFinder dataLinkFinder = exeContext.getDataModelLinkFinder();
        if (dataLinkFinder != null && !StringUtils.isEmpty((String)groupName) && (reportInfo = dataLinkFinder.findReportInfo(groupName)) != null && (column = dataLinkFinder.findDataColumnByFieldName(qContext.getExeContext(), reportInfo, queryField.getFieldCode())) != null && column.getColumModel().getID().equals(queryField.getUID())) {
            column.getShowInfo().setZBExpression(true);
            fieldNode.setDataModelLink(column);
            if (qContext.isNeedTableRegion() && exeContext.getEnv() != null && exeContext.getEnv().getTableNameFinder() != null) {
                queryField.setRegion(column.getRegion());
            }
        }
    }

    public String getDefaultTableName() {
        return this.defaultTableName;
    }

    public void setDefaultTableName(String defaultTableName) {
        this.defaultTableName = defaultTableName;
    }

    public IASTNode findSpec(IContext context, Token token, String refName, String spec) throws DynamicNodeException {
        return null;
    }
}

