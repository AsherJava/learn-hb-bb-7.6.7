/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.logging.LogFactory
 *  com.jiuqi.np.logging.Logger
 *  com.jiuqi.nr.definition.controller.IDesignTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskLinkDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskLinkDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.dataengine.common.Convert
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.nr.function.func.floatcopy;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.logging.LogFactory;
import com.jiuqi.np.logging.Logger;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskLinkDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskLinkDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyDestCopyCondition;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyOperand;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyQueryInfo;
import com.jiuqi.nr.function.func.floatcopy.FloatCopySrcQueryCondition;
import com.jiuqi.nr.function.util.FuncExpressionParseUtil;
import com.jiuqi.nvwa.dataengine.common.Convert;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.List;

public class FloatCopyParaParser {
    private static final Logger logger = LogFactory.getLogger(FloatCopyParaParser.class);
    protected boolean isValid = true;
    protected static final String REG_RELA = "^[A-Za-z_\\d]*\\[\\d+[,]\\d+\\](@[A-Za-z_\\d]+)?([;][A-Za-z_\\d]*\\[\\d+[,]\\d+\\](@[A-Za-z_\\d]+)?)*$";
    protected static final String REG_COLUMN_EXP = "^[\\d\\(\\)\\+\\-\\%\\*/]$";
    public static final String REG_POSITIVE_INTEGER = "^[+]?\\d+$";
    protected FloatCopySrcQueryCondition queryCondition = new FloatCopySrcQueryCondition();
    protected FloatCopyDestCopyCondition copyCondition = new FloatCopyDestCopyCondition();
    protected String assignExp;
    protected IDataModelLinkFinder dataLinkFinder;

    public FloatCopyParaParser(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        this.dataLinkFinder = qContext.getExeContext().getEnv().getDataModelLinkFinder();
        this.parse(qContext, parameters);
    }

    protected void parse(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        this.assignExp = (String)parameters.get(3).evaluate((IContext)qContext);
        this.parseSrcFilter((String)parameters.get(2).evaluate((IContext)qContext));
        this.parseFloatRowRelaParas(qContext, (String)parameters.get(0).evaluate((IContext)qContext), (String)parameters.get(1).evaluate((IContext)qContext), this.assignExp);
        this.parsePeriodRelaParas((String)parameters.get(7).evaluate((IContext)qContext), Convert.toInt((Object)parameters.get(6).evaluate((IContext)qContext)), Convert.toInt((Object)parameters.get(8).evaluate((IContext)qContext)));
        this.parseIsDestClear(FloatCopyParaParser.isDestClear(qContext, parameters));
        this.parseDestCopyMode((String)parameters.get(5).evaluate((IContext)qContext));
    }

    protected void parseFloatRowRelaParas(QueryContext qContext, String srcRelaExp, String destRelaExp, String assignExp) throws SyntaxException {
        if (StringUtils.isEmpty((String)srcRelaExp)) {
            throw new SyntaxException("\u6765\u6e90\u6807\u8bc6\u5217\u5fc5\u586b");
        }
        if (StringUtils.isEmpty((String)destRelaExp)) {
            throw new SyntaxException("\u76ee\u6807\u6807\u8bc6\u5217\u5fc5\u586b");
        }
        ArrayList<String> leftAssignExps = new ArrayList<String>();
        ArrayList<String> rightAssignExps = new ArrayList<String>();
        this.parseAssignExp(assignExp, leftAssignExps, rightAssignExps);
        FloatCopyQueryInfo srcRow = this.parseRelaExp(qContext, srcRelaExp, leftAssignExps, this.queryCondition);
        FloatCopyQueryInfo destRow = this.parseRelaExp(qContext, destRelaExp, rightAssignExps, null);
        if (srcRow.getKeyColumns().size() != destRow.getKeyColumns().size()) {
            throw new SyntaxException("\u6765\u6e90\u6807\u8bc6\u5217\u6709" + srcRow.getKeyColumns().size() + "\u4e2a\uff0c\u76ee\u6807\u6807\u8bc6\u5217\u6709" + destRow.getKeyColumns().size() + "\u4e2a\uff0c\u6570\u91cf\u4e0d\u4e00\u81f4");
        }
        if (StringUtils.isNotEmpty((String)this.queryCondition.getFilter())) {
            QueryContext srcQueryContext = FuncExpressionParseUtil.createSrcQueryContext(qContext, srcRow.getReportName(), this.queryCondition.getLinkedAlias());
            try {
                IExpression iExpression = srcQueryContext.getFormulaParser().parseCond(this.queryCondition.getFilter(), (IContext)srcQueryContext);
            }
            catch (ParseException e) {
                throw new SyntaxException("\u53d6\u6570\u6761\u4ef6" + this.queryCondition.getFilter() + "\u6821\u9a8c\u5931\u8d25\uff0c" + e.getMessage(), (Throwable)e);
            }
        }
        this.queryCondition.setRow(srcRow);
        this.copyCondition.setRow(destRow);
    }

    protected void parseAssignExp(String assignExp, List<String> leftAssignExps, List<String> rightAssignExps) throws SyntaxException {
        String[] exps;
        for (String exp : exps = assignExp.split(";")) {
            String[] colNumArr = exp.split("=");
            if (colNumArr.length != 2) {
                throw new SyntaxException("\u6620\u5c04\u5173\u7cfb\u683c\u5f0f\u89e3\u6790\u5931\u8d25");
            }
            leftAssignExps.add(colNumArr[0]);
            rightAssignExps.add(colNumArr[1]);
        }
    }

    protected FloatCopyQueryInfo parseRelaExp(QueryContext qContext, String relaExp, List<String> assignColExps, FloatCopySrcQueryCondition queryCondition) throws SyntaxException {
        DataModelLinkColumn column;
        String type;
        String string = type = queryCondition == null ? "\u76ee\u6807" : "\u6765\u6e90";
        if (!relaExp.matches(REG_RELA)) {
            this.isValid = false;
            throw new SyntaxException(type + "\u6807\u8bc6\u5217\u89e3\u6790\u5931\u8d25\uff0c\u53ea\u652f\u6301\u5750\u6807\u8868\u8fbe\u5f0f");
        }
        FloatCopyQueryInfo row = new FloatCopyQueryInfo();
        String[] expArr = relaExp.split(";");
        FloatCopyOperand operand = new FloatCopyOperand(expArr[0]);
        int floatRowNum = operand.getRowNum();
        String reportName = operand.getReportName();
        if (StringUtils.isEmpty((String)reportName)) {
            reportName = qContext.getDefaultGroupName();
        }
        ReportInfo reportInfo = null;
        if (StringUtils.isNotEmpty((String)operand.getLinkAlias()) && queryCondition != null) {
            queryCondition.setLinkedAlias(operand.getLinkAlias());
            ReportFmlExecEnvironment destEnv = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv();
            IRunTimeViewController controller = destEnv.getController();
            if (controller != null) {
                TaskLinkDefine linkDefine = controller.queryTaskLinkByCurrentFormSchemeAndNumber(destEnv.getFormSchemeKey(), queryCondition.getLinkedAlias());
                if (linkDefine == null) {
                    throw new SyntaxException("\u6765\u6e90\u6807\u8bc6\u5217\u4e0a\u7684\u5173\u8054\u4efb\u52a1@" + queryCondition.getLinkedAlias() + "\u5b9a\u4e49\u4e0d\u5b58\u5728");
                }
                String srcFormSchemeKey = linkDefine.getRelatedFormSchemeKey();
                FormSchemeDefine srcFormScheme = controller.getFormScheme(srcFormSchemeKey);
                if (srcFormScheme == null) {
                    throw new SyntaxException("\u6765\u6e90\u6807\u8bc6\u5217\u4e0a\u7684\u5173\u8054\u4efb\u52a1@" + queryCondition.getLinkedAlias() + "\u5bf9\u5e94\u4efb\u52a1\u672a\u627e\u5230");
                }
                queryCondition.setLinkedSchemePeriodType(srcFormScheme.getPeriodType());
            } else {
                IDesignTimeViewController designController = (IDesignTimeViewController)SpringBeanUtils.getBean(IDesignTimeViewController.class);
                DesignTaskLinkDefine linkDefine = designController.queryLinkByCurrentFormSchemeAndNum(destEnv.getFormSchemeKey(), queryCondition.getLinkedAlias());
                if (linkDefine == null) {
                    throw new SyntaxException("\u6765\u6e90\u6807\u8bc6\u5217\u4e0a\u7684\u5173\u8054\u4efb\u52a1@" + queryCondition.getLinkedAlias() + "\u5b9a\u4e49\u4e0d\u5b58\u5728");
                }
                String srcFormSchemeKey = linkDefine.getRelatedFormSchemeKey();
                DesignFormSchemeDefine srcFormScheme = designController.queryFormSchemeDefine(srcFormSchemeKey);
                if (srcFormScheme == null) {
                    throw new SyntaxException("\u6765\u6e90\u6807\u8bc6\u5217\u4e0a\u7684\u5173\u8054\u4efb\u52a1@" + queryCondition.getLinkedAlias() + "\u5bf9\u5e94\u4efb\u52a1\u672a\u627e\u5230");
                }
                queryCondition.setLinkedSchemePeriodType(srcFormScheme.getPeriodType());
            }
            reportInfo = this.dataLinkFinder.findReportInfo(operand.getLinkAlias(), reportName);
        } else {
            reportInfo = this.dataLinkFinder.findReportInfo(reportName);
        }
        if (queryCondition != null) {
            queryCondition.setReportInfo(reportInfo);
        }
        if ((column = this.dataLinkFinder.findDataColumn(reportInfo, floatRowNum, operand.getColNum(), false)) == null) {
            throw new SyntaxException(reportName + "\u8868\u672a\u627e\u5230" + expArr[0] + "\u6620\u5c04");
        }
        try {
            ReportFmlExecEnvironment destEnv;
            IRunTimeViewController controller;
            row.setRegionKey(column.getRegion());
            if (queryCondition != null && (controller = (destEnv = (ReportFmlExecEnvironment)qContext.getExeContext().getEnv()).getController()) != null) {
                DataRegionDefine region = controller.queryDataRegionDefine(column.getRegion());
                queryCondition.setSortingInfoList(region.getSortFieldsList());
            }
            String tableName = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableName(column.getColumModel());
            TableModelRunInfo tableRunInfo = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getTableInfo(tableName);
            row.setTableInfo(tableRunInfo);
            if (tableRunInfo != null && tableRunInfo.getOrderField() != null) {
                ColumnModelDefine orderColumn = tableRunInfo.getOrderField();
                FieldDefine orderField = qContext.getExeContext().getCache().getDataModelDefinitionsCache().getFieldDefine(orderColumn);
                row.setFloatOrderField(orderField);
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), (Throwable)e);
        }
        row.setKeyColumns(expArr);
        row.setReportName(reportName);
        row.setReportTitle(reportInfo.getReportTitle());
        int cellRow = column.getGridPosition().row();
        for (String lexp : assignColExps) {
            if (lexp.matches(REG_POSITIVE_INTEGER)) {
                StringBuffer exp = new StringBuffer();
                int cellCol = Convert.toInt((String)lexp);
                column = this.dataLinkFinder.findDataColumn(reportInfo, cellRow, cellCol, true);
                if (column != null) {
                    exp = exp.append(reportName).append("[");
                    exp = exp.append(column.getDataPosition().row()).append(",");
                    exp = exp.append(column.getDataPosition().col()).append("]");
                    row.addQueryColumns(exp.toString());
                    continue;
                }
                throw new SyntaxException(reportName + "\u8868\u672a\u627e\u5230" + cellRow + "\u884c" + cellCol + "\u5217\u6620\u5c04");
            }
            if (lexp.matches(REG_COLUMN_EXP)) {
                String colExp = this.parseColExpToZBExp(qContext, reportInfo, reportName, cellRow, lexp);
                row.addQueryColumns(colExp);
                continue;
            }
            FloatCopyOperand op = new FloatCopyOperand(lexp);
            column = this.dataLinkFinder.findDataColumn(reportInfo, op.getRowNum(), op.getColNum(), false);
            if (column == null) {
                throw new SyntaxException(reportName + "\u8868\u672a\u627e\u5230" + lexp + "\u6620\u5c04");
            }
            row.addQueryColumns(lexp);
        }
        return row;
    }

    protected String getSingleZBExp(QueryContext qContext, ReportInfo reportInfo, String reportName, int row, int col) throws SyntaxException {
        StringBuffer exp = new StringBuffer();
        DataModelLinkColumn column = this.dataLinkFinder.findDataColumn(reportInfo, row, col, true);
        if (column == null) {
            throw new SyntaxException(reportName + "\u8868\u672a\u627e\u5230" + row + "\u884c" + col + "\u5217\u6620\u5c04");
        }
        exp = exp.append(reportName).append("[");
        exp = exp.append(column.getDataPosition().row()).append(",");
        exp = exp.append(column.getDataPosition().col()).append("]");
        return exp.toString();
    }

    protected String parseColExpToZBExp(QueryContext qContext, ReportInfo reportInfo, String reportName, int row, String srcColExp) throws SyntaxException {
        StringBuffer resultBuffer = new StringBuffer();
        char[] colExpChararray = srcColExp.toCharArray();
        int len = colExpChararray.length;
        int index = 0;
        while (index < len) {
            StringBuffer numBuffer = new StringBuffer();
            char currentChar = colExpChararray[index];
            ++index;
            if (!this.isNumber(currentChar)) {
                resultBuffer.append(currentChar);
                continue;
            }
            numBuffer.append(currentChar);
            if (index < len) {
                currentChar = colExpChararray[index];
                while (this.isNumber(currentChar) && index + 1 < len) {
                    numBuffer.append(currentChar);
                    currentChar = colExpChararray[++index];
                }
                if (index + 1 == len) {
                    currentChar = colExpChararray[index];
                    numBuffer.append(currentChar);
                    ++index;
                }
            }
            int col = Integer.parseInt(numBuffer.toString());
            resultBuffer.append(this.getSingleZBExp(qContext, reportInfo, reportName, row, col));
            numBuffer.delete(0, numBuffer.length());
        }
        return resultBuffer.toString();
    }

    public static Boolean isDestClear(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        return (Boolean)parameters.get(4).evaluate((IContext)qContext);
    }

    protected boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    protected void parseSrcFilter(String srcFilter) {
        this.queryCondition.setFilter(srcFilter);
    }

    protected void parsePeriodRelaParas(String srcPeriodType, long periodOffset, long periodCount) {
        this.queryCondition.setPeriodType(srcPeriodType);
        this.queryCondition.setPeriodOffset(periodOffset);
        this.queryCondition.setPeriodCount(periodCount);
    }

    protected void parseIsDestClear(boolean isClear) {
        this.copyCondition.setClearBeforeCopy(isClear);
    }

    protected void parseDestCopyMode(String copyMode) {
        this.copyCondition.setCopyMode(copyMode);
    }

    public boolean isValid() {
        return this.isValid;
    }

    public FloatCopySrcQueryCondition getQueryCondition() {
        return this.queryCondition;
    }

    public FloatCopyDestCopyCondition getCopyCondition() {
        return this.copyCondition;
    }

    public String getAssignExp() {
        return this.assignExp;
    }
}

