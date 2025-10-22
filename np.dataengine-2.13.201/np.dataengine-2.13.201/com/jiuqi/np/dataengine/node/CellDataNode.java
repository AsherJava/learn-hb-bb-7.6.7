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
 *  com.jiuqi.bi.syntax.cell.CellExcpetion
 *  com.jiuqi.bi.syntax.cell.CellNode
 *  com.jiuqi.bi.syntax.cell.Cells
 *  com.jiuqi.bi.syntax.cell.IWorksheet
 *  com.jiuqi.bi.syntax.cell.Region
 *  com.jiuqi.bi.syntax.cell.WildcardCellNode
 *  com.jiuqi.bi.syntax.interpret.ISQLInfo
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.interpret.ScriptInfo
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.np.dataengine.node;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.AdjustException;
import com.jiuqi.bi.syntax.ast.IASTAdjustor;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IAssignable;
import com.jiuqi.bi.syntax.ast.Token;
import com.jiuqi.bi.syntax.cell.CellExcpetion;
import com.jiuqi.bi.syntax.cell.CellNode;
import com.jiuqi.bi.syntax.cell.Cells;
import com.jiuqi.bi.syntax.cell.IWorksheet;
import com.jiuqi.bi.syntax.cell.Region;
import com.jiuqi.bi.syntax.cell.WildcardCellNode;
import com.jiuqi.bi.syntax.interpret.ISQLInfo;
import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.interpret.ScriptInfo;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.executors.StatItem;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaDataLinkPosition;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.node.StatisticInfo;
import com.jiuqi.np.dataengine.parse.WorkSheet;
import com.jiuqi.np.dataengine.query.QueryContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellDataNode
extends CellNode
implements IAssignable {
    private static final Logger logger = LoggerFactory.getLogger(CellDataNode.class);
    private static final long serialVersionUID = 1831104249687884734L;
    private IASTNode dataNode;
    private DataModelLinkColumn dataModelLinkColumn;
    private boolean fromRowWildcard = false;
    private boolean fromColWildcard = false;

    public CellDataNode(Token token, IWorksheet workSheet, DataModelLinkColumn dataModelLinkColumn, IASTNode dataNode, boolean isJQModel) {
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
        if (Language.EXCEL == lang) {
            if (this.dataModelLinkColumn.getDimensionRestriction() != null || this.dataModelLinkColumn.getPeriodModifier() != null) {
                return false;
            }
            return !StringUtils.isNotEmpty((String)((WorkSheet)this.workSheet).getReportInfo().getLinkAlias());
        }
        return this.dataNode.support(lang);
    }

    public boolean isSupportLocate() {
        if (this.dataNode instanceof DynamicDataNode) {
            return ((DynamicDataNode)this.dataNode).isSupportLocate();
        }
        return true;
    }

    protected void toSQL(IContext context, StringBuilder buffer, ISQLInfo info) throws InterpretException {
        this.dataNode.interpret(context, buffer, Language.SQL, (Object)info);
    }

    protected void toFormula(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        DataEngineConsts.FormulaShowType showType;
        int beginIndex = buffer.length();
        NodeShowInfo showInfo = this.dataModelLinkColumn.getShowInfo();
        if (showInfo.isZBExpression()) {
            this.dataNode.interpret(context, buffer, Language.FORMULA, info);
            return;
        }
        FormulaShowInfo formulaShowInfo = (FormulaShowInfo)info;
        DataEngineConsts.FormulaShowType formulaShowType = showType = formulaShowInfo == null ? DataEngineConsts.FormulaShowType.JQ : formulaShowInfo.getFormulaShowType();
        if (showType == DataEngineConsts.FormulaShowType.EXCEL) {
            this.toExcel(context, buffer, info);
        } else {
            WorkSheet sheet = (WorkSheet)this.workSheet;
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
                if (this.dataNode instanceof DynamicDataNode) {
                    try {
                        ExecutorContext exeContext = ((QueryContext)context).getExeContext();
                        TableModelRunInfo tableInfo = exeContext.getCache().getDataModelDefinitionsCache().getTableInfo(((DynamicDataNode)this.dataNode).getQueryField().getTableName());
                        showInfo.setTableName(tableInfo.getTableModelDefine().getCode());
                    }
                    catch (ParseException e) {
                        throw new InterpretException(e.getMessage(), (Throwable)e);
                    }
                }
                this.dataNode.interpret(context, buffer, Language.FORMULA, info);
                return;
            }
        }
        if (formulaShowInfo != null && formulaShowInfo.isCollectPositions()) {
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
        if (this.dataModelLinkColumn.getDimensionRestriction() != null || this.dataModelLinkColumn.getPeriodModifier() != null) {
            throw new InterpretException(this.dataModelLinkColumn.toString() + "\u4e0d\u652f\u6301EXCEL\u8bed\u6cd5");
        }
        if (StringUtils.isNotEmpty((String)((WorkSheet)this.workSheet).getReportInfo().getLinkAlias())) {
            throw new InterpretException(this.dataModelLinkColumn.toString() + "\u4e0d\u652f\u6301EXCEL\u8bed\u6cd5");
        }
        FormulaShowInfo formulaShowInfo = (FormulaShowInfo)info;
        if (this.dataNode != null && this.dataNode instanceof DynamicDataNode) {
            DynamicDataNode dynamicDataNode = (DynamicDataNode)this.dataNode;
            StatisticInfo statisticInfo = dynamicDataNode.getStatisticInfo();
            if (statisticInfo != null) {
                String func;
                QueryContext qContext;
                Region region;
                if (statisticInfo.getCondNode() == null && (region = (qContext = (QueryContext)context).getExeContext().getEnv().getDataModelLinkFinder().getCellRegion(qContext.getExeContext(), this.dataModelLinkColumn)) != null && (func = StatItem.getSqlFunction(statisticInfo.getStatKind())) != null) {
                    buffer.append(func).append("(");
                    this.appendSheetName(context, buffer, formulaShowInfo);
                    buffer.append(region.leftTop());
                    buffer.append(":");
                    this.appendSheetName(context, buffer, formulaShowInfo);
                    buffer.append(region.rightBottom());
                    buffer.append(")");
                    return;
                }
                throw new InterpretException(this.dataModelLinkColumn.toString() + "\u4e0d\u652f\u6301EXCEL\u8bed\u6cd5");
            }
            if (formulaShowInfo != null && formulaShowInfo.getAdjustors() != null) {
                CellDataNode newCellNode = new CellDataNode(this.token, this.workSheet, this.dataModelLinkColumn, null, false);
                for (IASTAdjustor adjustor : formulaShowInfo.getAdjustors()) {
                    try {
                        adjustor.adjust((IASTNode)newCellNode);
                    }
                    catch (AdjustException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
                newCellNode.toExcel(context, buffer, formulaShowInfo);
                return;
            }
        }
        this.appendSheetName(context, buffer, formulaShowInfo);
        if (this.dataNode == null) {
            buffer.append(this.position);
        } else {
            buffer.append(this.dataModelLinkColumn.getGridPosition());
        }
    }

    private void appendSheetName(IContext context, StringBuilder buffer, FormulaShowInfo formulaShowInfo) {
        String sheetName = this.sheetName();
        if ((this.options & 1) != 0 && !StringUtils.isEmpty((String)sheetName)) {
            if (formulaShowInfo != null && formulaShowInfo.getSheetNameProvider() != null) {
                sheetName = formulaShowInfo.getSheetNameProvider().getSheetName(context, sheetName);
            }
            if (StringUtils.isNotEmpty((String)sheetName)) {
                Cells.printSheetName((String)sheetName, (StringBuilder)buffer);
                buffer.append('!');
            }
        }
    }

    protected void toExplain(IContext context, StringBuilder buffer, Object info) throws InterpretException {
        this.dataNode.interpret(context, buffer, Language.EXPLAIN, info);
    }

    public void replaceWildcardStatistic(IContext context) {
        DynamicDataNode dynamicDataNode;
        if (this.dataNode instanceof DynamicDataNode && (dynamicDataNode = (DynamicDataNode)this.dataNode).getStatisticInfo() != null && dynamicDataNode.getStatisticInfo().condNode != null) {
            for (IASTNode child : dynamicDataNode.getStatisticInfo().condNode) {
                for (int i = 0; i < child.childrenSize(); ++i) {
                    IASTNode node = child.getChild(i);
                    if (!(node instanceof WildcardCellNode)) continue;
                    try {
                        IASTNode cellNode = ((WildcardCellNode)node).replaceWildcard(context, this.getCellPosition().col(), this.getCellPosition().row());
                        child.setChild(i, cellNode);
                        continue;
                    }
                    catch (CellExcpetion e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

    public DataModelLinkColumn getDataModelLinkColumn() {
        return this.dataModelLinkColumn;
    }

    public boolean isFromRowWildcard() {
        return this.fromRowWildcard;
    }

    public void setFromRowWildcard(boolean fromRowWildcard) {
        this.fromRowWildcard = fromRowWildcard;
    }

    public boolean isFromColWildcard() {
        return this.fromColWildcard;
    }

    public void setFromColWildcard(boolean fromColWildcard) {
        this.fromColWildcard = fromColWildcard;
    }

    public Object clone() {
        IASTNode newDataNode;
        CellDataNode newNode = (CellDataNode)((Object)super.clone());
        newNode.dataNode = newDataNode = (IASTNode)this.dataNode.clone();
        return newNode;
    }
}

