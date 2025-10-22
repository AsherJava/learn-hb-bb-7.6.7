/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.AdjustException
 *  com.jiuqi.bi.syntax.ast.IASTAdjustor
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IAssignable
 *  com.jiuqi.bi.syntax.ast.Token
 *  com.jiuqi.bi.syntax.cell.CellNode
 *  com.jiuqi.bi.syntax.cell.Cells
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.grid.GridCellNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.executors.StatItem
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaDataLinkPosition
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.NodeShowInfo
 */
package com.jiuqi.nr.data.engine.analysis.parse;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.AdjustException;
import com.jiuqi.bi.syntax.ast.IASTAdjustor;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IAssignable;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellNode;
import com.jiuqi.bi.syntax.cell.Cells;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.grid.GridCellNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaDataLinkPosition;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.nr.data.engine.analysis.parse.AnalysisWorkSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnalysisCellDataNode
extends CellNode
implements IAssignable {
    private static final Logger logger = LoggerFactory.getLogger(AnalysisCellDataNode.class);
    private static final long serialVersionUID = 1831104249687884734L;
    private IASTNode dataNode;
    private DataModelLinkColumn dataModelLinkColumn;

    public AnalysisCellDataNode(Token token, IWorksheet workSheet, DataModelLinkColumn dataModelLinkColumn, IASTNode dataNode, boolean isJQModel) {
        super(token, workSheet, isJQModel ? dataModelLinkColumn.getDataPosition() : dataModelLinkColumn.getGridPosition());
        this.dataModelLinkColumn = dataModelLinkColumn;
        this.dataNode = dataNode;
    }

    public int setValue(IContext context, Object value) throws SyntaxException {
        if (this.dataNode instanceof IAssignable) {
            IAssignable assignable = (IAssignable)this.dataNode;
            assignable.setValue(context, value);
            return 1;
        }
        return 0;
    }

    public int getType(IContext context) throws SyntaxException {
        return this.dataNode.getType(context);
    }

    public Object evaluate(IContext context) throws SyntaxException {
        return this.dataNode.evaluate(context);
    }

    public int childrenSize() {
        return 1;
    }

    public IASTNode getChild(int index) {
        return this.dataNode;
    }

    public void setChild(int index, IASTNode node) {
        this.dataNode = node;
    }

    public boolean support(Language lang) {
        return this.dataNode.support(lang);
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        this.dataNode.interpret(context, buffer, Language.SQL, (Object)info);
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        int beginIndex = buffer.length();
        NodeShowInfo showInfo = this.dataModelLinkColumn.getShowInfo();
        if (showInfo.isZBExpression()) {
            this.dataNode.interpret(context, buffer, Language.FORMULA, info);
            return;
        }
        FormulaShowInfo formulaShowInfo = (FormulaShowInfo)info;
        DataEngineConsts.FormulaShowType showType = formulaShowInfo.getFormulaShowType();
        if (showType == DataEngineConsts.FormulaShowType.EXCEL) {
            if (this.dataNode instanceof DynamicDataNode) {
                DynamicDataNode dynamicDataNode = (DynamicDataNode)this.dataNode;
                if (dynamicDataNode.getStatisticInfo() != null) {
                    String statisticType = StatItem.STAT_KIND_MODE_NAMES[dynamicDataNode.getStatisticInfo().statKind];
                    if (statisticType.equals("AVG")) {
                        statisticType = "AVERAGE";
                    }
                    buffer.append(statisticType).append("(");
                    this.toExcel(context, buffer, info);
                    buffer.append(")");
                    if (formulaShowInfo.isCollectPositions()) {
                        formulaShowInfo.getDataLinkPositions().add(new FormulaDataLinkPosition(beginIndex, buffer.length(), this.dataModelLinkColumn));
                    }
                    return;
                }
                if (formulaShowInfo.getAdjustors() != null) {
                    GridCellNode newCellNode = new GridCellNode(this.token, this.workSheet, this.position, this.options);
                    newCellNode.setCellPosition(this.dataModelLinkColumn.getGridPosition());
                    for (IASTAdjustor adjustor : formulaShowInfo.getAdjustors()) {
                        try {
                            adjustor.adjust((IASTNode)newCellNode);
                        }
                        catch (AdjustException e) {
                            logger.error(e.getMessage(), e);
                        }
                    }
                    newCellNode.interpret(context, buffer, Language.EXCEL, (Object)formulaShowInfo);
                    if (formulaShowInfo.isCollectPositions()) {
                        formulaShowInfo.getDataLinkPositions().add(new FormulaDataLinkPosition(beginIndex, buffer.length(), this.dataModelLinkColumn));
                    }
                    return;
                }
            }
            this.toExcel(context, buffer, info);
        } else {
            AnalysisWorkSheet sheet = (AnalysisWorkSheet)this.workSheet;
            if (showType == DataEngineConsts.FormulaShowType.JQ) {
                if (this.dataModelLinkColumn.getDataPosition() == null) {
                    buffer.append(this.dataNode.interpret(context, Language.FORMULA, info));
                } else {
                    if (showInfo.getSheetName() != null && !sheet.isDefault()) {
                        buffer.append(showInfo.getSheetName());
                    }
                    if (showInfo.isHasBracket()) {
                        buffer.append("[");
                    }
                    buffer.append(this.dataModelLinkColumn.getDataPosition().row()).append(",").append(this.dataModelLinkColumn.getDataPosition().col());
                    this.appendRestriction(buffer, showInfo);
                }
            } else if (showType == DataEngineConsts.FormulaShowType.DATALINK) {
                if (showInfo.getSheetName() != null && !sheet.isDefault()) {
                    buffer.append(this.workSheet.name());
                }
                if (showInfo.isHasBracket()) {
                    buffer.append("[");
                }
                buffer.append(this.dataModelLinkColumn.getDataLinkCode());
                this.appendRestriction(buffer, showInfo);
            } else {
                this.dataNode.interpret(context, buffer, Language.FORMULA, info);
                return;
            }
        }
        if (formulaShowInfo.isCollectPositions()) {
            formulaShowInfo.getDataLinkPositions().add(new FormulaDataLinkPosition(beginIndex, buffer.length(), this.dataModelLinkColumn));
        }
    }

    public void appendRestriction(StringBuilder buffer, NodeShowInfo showInfo) {
        if (showInfo.getInnerAppend() != null) {
            buffer.append(",").append(showInfo.getInnerAppend());
        }
        if (showInfo.isHasBracket()) {
            buffer.append("]");
        }
        if (showInfo.getEndAppend() != null) {
            buffer.append(showInfo.getEndAppend());
        }
    }

    protected void toJavaScript(IContext context, StringBuilder buffer, ScriptInfo info) throws InterpretException {
        this.dataNode.interpret(context, buffer, Language.JavaScript, (Object)info);
    }

    protected void toExcel(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        if ((this.options & 1) != 0 && !StringUtils.isEmpty((String)this.sheetName())) {
            Cells.printSheetName((String)this.sheetName(), (StringBuilder)buffer);
            buffer.append('!').append(this.dataModelLinkColumn.getGridPosition());
        } else {
            buffer.append(this.dataModelLinkColumn.getGridPosition());
        }
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        this.dataNode.interpret(context, buffer, Language.EXPLAIN, info);
    }

    public DataModelLinkColumn getDataModelLinkColumn() {
        return this.dataModelLinkColumn;
    }

    public Object clone() {
        IASTNode newDataNode;
        AnalysisCellDataNode newNode = (AnalysisCellDataNode)((Object)super.clone());
        newNode.dataNode = newDataNode = (IASTNode)this.dataNode.clone();
        return newNode;
    }
}

