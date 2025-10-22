/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.CompatiblePosition
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.IReportWorkSheet
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.node.CellRegionNode
 *  com.jiuqi.np.dataengine.node.ExpressionUtils
 *  com.jiuqi.np.dataengine.node.NodeShowInfo
 *  com.jiuqi.np.dataengine.node.RestrictInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.IReportWorkSheet;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.node.CellRegionNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisCellDataNode;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisFormulaParseUtils;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisWildcardCellDataNode;
import java.io.Serializable;
import java.util.List;

public class AnalysisWorkSheet
implements IReportWorkSheet,
Serializable {
    private static final long serialVersionUID = -1688559830464129059L;
    private ReportInfo reportInfo;
    private boolean isDefault = false;

    public ReportInfo getReportInfo() {
        return this.reportInfo;
    }

    public void setReportInfo(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public AnalysisWorkSheet(ReportInfo reportInfo) {
        this.reportInfo = reportInfo;
    }

    public String name() {
        return this.reportInfo.getReportName();
    }

    public String title() {
        return this.reportInfo.getReportTitle();
    }

    public IASTNode findCell(IContext context, Token token, Position pos, int cellOptions) throws CellExcpetion {
        QueryContext qContext = (QueryContext)context;
        AnalysisCellDataNode node = this.createCellDataNode(qContext, token, pos, null);
        return node;
    }

    public IASTNode findCell(IContext context, Token token, CompatiblePosition pos, int cellOptions, List<IASTNode> restrictions) throws CellExcpetion {
        QueryContext qContext = (QueryContext)context;
        Object node = null;
        Position position = pos.getPosition();
        node = !position.isRowWildcard() && !position.isColWildcard() ? this.createCellDataNode(qContext, token, position, restrictions) : new AnalysisWildcardCellDataNode(token, (IWorksheet)this, pos, cellOptions, restrictions);
        return node;
    }

    private String getCellExp(QueryContext qContext, Position position) {
        String cellExp = this.name();
        cellExp = qContext.getExeContext().isJQReportModel() ? cellExp + "[" + position.row() + "," + position.col() + "]" : cellExp + "!" + position;
        return cellExp;
    }

    private AnalysisCellDataNode createCellDataNode(QueryContext qContext, Token token, Position position, List<IASTNode> restrictions) throws CellExcpetion {
        AnalysisCellDataNode node = null;
        try {
            DataModelLinkColumn column = qContext.getExeContext().getDataModelLinkFinder().findDataColumn(this.reportInfo, position.row(), position.col(), !qContext.getExeContext().isJQReportModel());
            if (column != null) {
                RestrictInfo restrictInfo = null;
                if (column.getColumModel() != null) {
                    restrictInfo = ExpressionUtils.parseRestrictInfo((QueryContext)qContext, (String)this.name(), (NodeShowInfo)column.getShowInfo(), restrictions);
                }
                IASTNode dataNode = AnalysisFormulaParseUtils.createNodeByDataLinkColumn(qContext, token, column, restrictInfo);
                node = new AnalysisCellDataNode(token, (IWorksheet)this, column, dataNode, qContext.getExeContext().isJQReportModel());
                column.getShowInfo().setSheetName(this.name());
            }
        }
        catch (Exception e) {
            String cellExp = this.getCellExp(qContext, position);
            throw new CellExcpetion("\u5355\u5143\u683c " + cellExp + " \u89e3\u6790\u51fa\u9519:" + e.getMessage(), (Throwable)e);
        }
        return node;
    }

    public IASTNode findRegion(IContext context, Token token, String startCell, String endCell, int cellOptions) throws CellExcpetion {
        QueryContext qContext = (QueryContext)context;
        ExecutorContext executorContext = qContext.getExeContext();
        Region region = null;
        if (!executorContext.isJQReportModel()) {
            int[] startPos = Position.tryParsePos((String)startCell);
            int[] endPos = Position.tryParsePos((String)endCell);
            if (startPos != null && endPos != null) {
                region = new Region(new Position(startPos[0], startPos[1], cellOptions), new Position(endPos[0], endPos[1], cellOptions));
            }
        }
        if (region == null) {
            DataModelLinkColumn startColumn = executorContext.getDataModelLinkFinder().findDataColumn(this.reportInfo, startCell);
            DataModelLinkColumn endColumn = executorContext.getDataModelLinkFinder().findDataColumn(this.reportInfo, endCell);
            region = new Region(startColumn.getGridPosition(), endColumn.getGridPosition());
        }
        CellRegionNode cellRegionNode = new CellRegionNode(token, (IWorksheet)this, region, cellOptions);
        try {
            cellRegionNode.parseChilds(qContext);
        }
        catch (Exception e) {
            throw new CellExcpetion((Throwable)e);
        }
        return cellRegionNode;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public IASTNode findRegion(IContext context, Token token, Region region, int cellOptions) throws CellExcpetion {
        CellRegionNode cellRegionNode = new CellRegionNode(token, (IWorksheet)this, region, cellOptions);
        try {
            cellRegionNode.parseChilds((QueryContext)context);
        }
        catch (Exception e) {
            throw new CellExcpetion((Throwable)e);
        }
        return cellRegionNode;
    }
}

