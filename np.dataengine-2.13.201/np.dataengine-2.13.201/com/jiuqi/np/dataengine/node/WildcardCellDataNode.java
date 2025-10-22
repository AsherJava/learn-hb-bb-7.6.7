/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.Cells
 *  com.jiuqi.bi.syntax.cell.CompatiblePosition
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Position
 *  com.jiuqi.bi.syntax.cell.WildcardCellNode
 *  com.jiuqi.bi.syntax.data.DataNode
 *  com.jiuqi.bi.syntax.data.DimensionNode
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.period.IPeriodAdapter
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.Cells;
import com.jiuqi.bi.syntax.cell.CompatiblePosition;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Position;
import com.jiuqi.bi.syntax.cell.WildcardCellNode;
import com.jiuqi.bi.syntax.data.DataNode;
import com.jiuqi.bi.syntax.data.DimensionNode;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.node.CellDataNode;
import com.jiuqi.np.dataengine.node.ExpressionUtils;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.PeriodConditionNode;
import com.jiuqi.np.dataengine.node.RestrictInfo;
import com.jiuqi.np.dataengine.parse.WorkSheet;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.period.IPeriodAdapter;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WildcardCellDataNode
extends WildcardCellNode {
    private static final Logger logger = LoggerFactory.getLogger(WildcardCellDataNode.class);
    private static final long serialVersionUID = 1332263154565086497L;
    private List<CellDataNode> expandCells = new ArrayList<CellDataNode>();
    private String endAppend = null;

    public WildcardCellDataNode(Token token, IWorksheet workSheet, CompatiblePosition position, int options, List<IASTNode> restrictions) {
        super(token, workSheet, position, options);
        this.getRestrictions().addAll(restrictions);
    }

    public Set<Position> expandWildcards(IContext context, List<Integer> rowRange, List<Integer> colRange) throws CellExcpetion {
        int i;
        WorkSheet sheet = (WorkSheet)this.workSheet;
        ReportInfo info = sheet.getReportInfo();
        HashSet<Position> positions = new HashSet<Position>();
        if (rowRange == null || rowRange.size() == 0) {
            rowRange = new ArrayList<Integer>();
            if (this.position.isRowWildcard()) {
                int[] validRows = info.getValidRows();
                for (i = 0; i < validRows.length; ++i) {
                    rowRange.add(validRows[i]);
                }
            } else {
                rowRange.add(this.position.row());
            }
        }
        if (colRange == null || colRange.size() == 0) {
            colRange = new ArrayList<Integer>();
            if (this.position.isColWildcard()) {
                int[] validCols = info.getValidCols();
                for (i = 0; i < validCols.length; ++i) {
                    colRange.add(validCols[i]);
                }
            } else {
                colRange.add(this.position.col());
            }
        }
        for (int row : rowRange) {
            for (int col : colRange) {
                Position position = new Position(col, row);
                positions.add(position);
            }
        }
        return positions;
    }

    public IASTNode replaceWildcard(IContext context, int col, int row) throws CellExcpetion {
        QueryContext qContext = (QueryContext)context;
        int posCol = this.position.col();
        int posRow = this.position.row();
        if (this.position.isColWildcard()) {
            posCol = col;
        }
        if (this.position.isRowWildcard()) {
            posRow = row;
        }
        if (this.colMultiply != 0.0) {
            posCol = (int)((double)posCol * this.colMultiply);
        }
        posCol += this.colOffset;
        if (this.rowMultiply != 0.0) {
            posRow = (int)((double)posRow * this.rowMultiply);
        }
        Position newPosition = new Position(posCol, posRow += this.rowOffset);
        CellDataNode node = null;
        try {
            WorkSheet workSheet = (WorkSheet)this.workSheet;
            DataModelLinkColumn column = qContext.getExeContext().getDataModelLinkFinder().findDataColumn(workSheet.getReportInfo(), newPosition.row(), newPosition.col(), false);
            RestrictInfo restrictInfo = null;
            if (column != null) {
                if (column.getColumModel() != null) {
                    restrictInfo = ExpressionUtils.parseRestrictInfo(qContext, workSheet.name(), column.getShowInfo(), this.restrictions);
                }
                IASTNode dataNode = ExpressionUtils.createNodeByDataModelLinkColumn(qContext, this.token, column, restrictInfo);
                node = new CellDataNode(this.token, this.workSheet, column, dataNode, qContext.getExeContext().isJQReportModel());
                node.replaceWildcardStatistic(context);
                node.setFromRowWildcard(this.position.isRowWildcard());
                node.setFromColWildcard(this.position.isColWildcard());
                column.getShowInfo().setSheetName(workSheet.name());
            }
        }
        catch (Exception e) {
            throw new CellExcpetion(e.getMessage(), (Throwable)e);
        }
        if (node != null) {
            this.expandCells.add(node);
            return node;
        }
        throw new CellExcpetion("\u5355\u5143\u683c" + this.workSheet.name() + "[" + posRow + "," + posCol + "]\u672a\u627e\u5230\uff01");
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        DataEngineConsts.FormulaShowType showType;
        if (info instanceof FormulaShowInfo && (showType = ((FormulaShowInfo)info).getFormulaShowType()) == DataEngineConsts.FormulaShowType.DATALINK) {
            DataModelLinkColumn firstColumn = this.expandCells.get(0).getDataModelLinkColumn();
            if ((this.options & 1) != 0 && !StringUtils.isEmpty((String)this.sheetName())) {
                Cells.printSheetName((String)this.sheetName(), (StringBuilder)buffer);
            }
            buffer.append('[');
            if (this.position.isRowWildcard()) {
                buffer.append('*');
                this.printMultiply(buffer, this.rowMultiply);
                this.printOffset(buffer, this.rowOffset);
            } else {
                buffer.append(firstColumn.getDataLinkCode());
                this.printRelatedRestrictions(context, buffer);
                buffer.append(".r");
            }
            if (this.position.isColWildcard()) {
                if ((this.position.options() & 4) == 0) {
                    buffer.append(", *");
                    this.printMultiply(buffer, this.colMultiply);
                    this.printOffset(buffer, this.colOffset);
                }
            } else {
                buffer.append(", ").append(firstColumn.getDataLinkCode());
                this.printRelatedRestrictions(context, buffer);
                buffer.append(".c");
            }
            this.printRestrictions(context, buffer);
            buffer.append(']');
            if (this.endAppend != null) {
                buffer.append(this.endAppend);
            }
            return;
        }
        super.toFormula(context, buffer, info);
        if (this.endAppend != null) {
            buffer.append(this.endAppend);
        }
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        try {
            if (this.position.isRowWildcard() && this.position.isColWildcard()) {
                this.toFormula(context, buffer, info);
                return;
            }
            if ((this.options & 1) != 0 && !StringUtils.isEmpty((String)this.sheetName())) {
                WorkSheet workSheet = (WorkSheet)this.workSheet;
                buffer.append(workSheet.title());
            }
            if (this.position.isRowWildcard()) {
                buffer.append(this.position.col()).append("\u5217");
            } else if (this.position.isColWildcard()) {
                buffer.append(this.position.row()).append("\u884c");
            }
            if (this.restrictions != null) {
                DimensionValueSet dimensionRestriction;
                RestrictInfo restrictInfo = ExpressionUtils.parseRestrictInfo((QueryContext)context, this.workSheet.name(), null, this.restrictions);
                if (restrictInfo.periodModifier != null) {
                    buffer.append(restrictInfo.periodModifier.toTitle());
                }
                if (restrictInfo.isLJ) {
                    buffer.append("\u672c\u5e74\u7d2f\u8ba1");
                }
                if (restrictInfo.statKind > 0) {
                    buffer.append(StatItem.STAT_KIND_MODE_TITLES[restrictInfo.statKind]);
                }
                if ((dimensionRestriction = restrictInfo.getDimensionRestriction()) != null) {
                    Object dimValue = dimensionRestriction.getValue("DATATIME");
                    if (dimValue instanceof String) {
                        String period = (String)dimValue;
                        if (period != null) {
                            QueryContext qContext = (QueryContext)context;
                            IPeriodAdapter periodProvider = qContext.getExeContext().getPeriodAdapter();
                            PeriodWrapper pw = new PeriodWrapper(period);
                            buffer.append(periodProvider.getPeriodTitle(pw));
                        }
                    } else if (dimValue instanceof IASTNode) {
                        IASTNode node = (IASTNode)dimValue;
                        node.interpret(context, buffer, Language.EXPLAIN, info);
                    } else if (dimValue instanceof PeriodConditionNode) {
                        IASTNode node = ((PeriodConditionNode)dimValue).getFilterNode();
                        node.interpret(context, buffer, Language.EXPLAIN, info);
                    }
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void printRelatedRestrictions(IContext context, StringBuilder buffer) throws InterpretException {
        for (IASTNode restriction : this.restrictions) {
            if (!(restriction instanceof DataNode)) continue;
            try {
                String text = (String)restriction.evaluate(context);
                if (!text.startsWith("@")) continue;
                buffer.append("$").append(text.substring(1));
            }
            catch (SyntaxException e) {
                throw new InterpretException(e.getMessage(), (Throwable)e);
            }
        }
    }

    protected void printRestrictions(IContext context, StringBuilder buffer) throws InterpretException {
        for (IASTNode restriction : this.restrictions) {
            if (restriction instanceof DimensionNode) {
                DimensionNode dimensionNode = (DimensionNode)restriction;
                buffer.append(", ").append(dimensionNode.toString());
                continue;
            }
            if (!(restriction instanceof DataNode)) continue;
            try {
                String text = (String)restriction.evaluate(context);
                if (text.startsWith(".") || text.startsWith("@") || text.equals("$")) {
                    this.endAppend = text;
                    continue;
                }
                buffer.append(", ").append(text);
            }
            catch (SyntaxException e) {
                throw new InterpretException(e.getMessage(), (Throwable)e);
            }
        }
    }

    public int getType(IContext context) throws SyntaxException {
        return 0;
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return null;
    }

    public List<CellDataNode> getExpandCells() {
        return this.expandCells;
    }

    public int getExpandRangeCount() {
        int count = 0;
        if (this.position.isRowWildcard()) {
            ++count;
        }
        if (this.position.isColWildcard()) {
            ++count;
        }
        return count;
    }
}

