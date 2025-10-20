/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.interpret.Language
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaShowType
 *  com.jiuqi.np.dataengine.common.DataModelLinkColumn
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IColumnModelFinder
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.NodeShowInfo
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.data.engine.provider.DataSchemeColumnModelFinder
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.env.ReportColumnModelImpl
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.gcreport.inputdata.function.sumhb.dto;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.interpret.Language;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.gcreport.inputdata.function.sumhb.dto.SumhbParam;
import com.jiuqi.gcreport.inputdata.util.InputDataNameProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DataModelLinkColumn;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IColumnModelFinder;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.NodeShowInfo;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.data.engine.provider.DataSchemeColumnModelFinder;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.env.ReportColumnModelImpl;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class SumhbParamBuilder {
    private static final Logger logger = LoggerFactory.getLogger(SumhbParamBuilder.class);
    private final QueryContext queryContext;
    private final String funcName;
    private final String inputTableName;
    private final String amtZBId;
    private final String inputTableId;
    private final IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private final DataModelService dataModelService;

    public SumhbParamBuilder(QueryContext queryContext, String funcName, String inputTableName) {
        FieldDefine amtField;
        TableDefine inputTableDefine;
        this.queryContext = queryContext;
        this.funcName = funcName;
        this.inputTableName = inputTableName;
        this.dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        this.dataModelService = (DataModelService)SpringContextUtils.getBean(DataModelService.class);
        TableModelDefine tableModelDefine = this.dataModelService.getTableModelDefineByName(inputTableName);
        this.inputTableId = tableModelDefine.getID();
        try {
            inputTableDefine = this.dataDefinitionRuntimeController.queryTableDefineByCode(tableModelDefine.getCode());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        try {
            amtField = this.dataDefinitionRuntimeController.queryFieldByCodeInTable("AMT", inputTableDefine.getKey());
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        this.amtZBId = amtField.getKey();
    }

    public SumhbParam build(List<IASTNode> parameters) {
        if ("SUMHBZB".equals(this.funcName)) {
            return this.buildSumhbzbParam(parameters);
        }
        return this.buildSumhbParam(parameters);
    }

    private SumhbParam buildSumhbzbParam(List<IASTNode> parameters) {
        ColumnModelDefine zbColumnDefine = null;
        ReportInfo reportInfo = null;
        String regionKey = "";
        String filter = "";
        String compileFilter = "";
        for (IASTNode child : parameters.get(0)) {
            DynamicDataNode dataNode;
            DataModelLinkColumn column;
            if (!(child instanceof DynamicDataNode) || (column = (dataNode = (DynamicDataNode)child).getDataModelLink()) == null) continue;
            zbColumnDefine = column.getColumModel();
            regionKey = column.getRegion();
            reportInfo = column.getReportInfo();
        }
        if (parameters.size() > 1) {
            try {
                filter = (String)parameters.get(1).evaluate((IContext)this.queryContext);
            }
            catch (SyntaxException e) {
                throw new BusinessRuntimeException("\u8fc7\u6ee4\u6761\u4ef6\u83b7\u53d6\u5931\u8d25\u3002");
            }
            compileFilter = SumhbParamBuilder.getReplaceFilter(this.queryContext, filter);
        }
        if (zbColumnDefine == null) {
            throw new BusinessRuntimeException("\u672a\u89e3\u6790\u5230\u8fd0\u7b97\u7684\u5b57\u6bb5\u4fe1\u606f\u3002");
        }
        if (reportInfo == null) {
            throw new BusinessRuntimeException("\u4e3a\u89e3\u6790\u5230\u8fd0\u7b97\u7684\u62a5\u8868\u4fe1\u606f\u3002");
        }
        ColumnModelDefine nvwaColumnDefine = this.getColumnModelDefine(zbColumnDefine);
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        DataRegionDefine regionDefine = runTimeViewController.queryDataRegionDefine(regionKey);
        if (regionDefine == null) {
            throw new BusinessRuntimeException("\u672a\u89e3\u6790\u5230\u8fd0\u7b97\u7684\u533a\u57df\u3002");
        }
        SumhbParam sumhbParam = new SumhbParam();
        sumhbParam.setFormCode(reportInfo.getReportName());
        sumhbParam.setReginId(regionKey);
        sumhbParam.setFieldDefine(nvwaColumnDefine);
        sumhbParam.setRegionFilter(regionDefine.getFilterCondition());
        sumhbParam.setFilter(filter);
        sumhbParam.setCompiledFilter(compileFilter);
        return sumhbParam;
    }

    private SumhbParam buildSumhbParam(List<IASTNode> parameters) {
        ColumnModelDefine columnModelDefine;
        DataRegionDefine currDataRegionDefine;
        String filter;
        String compiledFilter;
        String formCode;
        block19: {
            FormDefine currFormDefine;
            String fieldCode;
            String formSchemeKey = ((ReportFmlExecEnvironment)this.queryContext.getExeContext().getEnv()).getFormSchemeKey();
            try {
                formCode = (String)parameters.get(2).evaluate((IContext)this.queryContext);
            }
            catch (SyntaxException e) {
                logger.error("\u53c2\u6570" + parameters + "\u89e3\u6790\u9519\u8bef\u3002", e);
                return null;
            }
            try {
                fieldCode = (String)parameters.get(0).evaluate((IContext)this.queryContext);
                fieldCode = fieldCode.replaceAll("\\s", "");
            }
            catch (SyntaxException e) {
                logger.error("\u53c2\u6570" + parameters + "\u89e3\u6790\u9519\u8bef\u3002", e);
                return null;
            }
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            try {
                currFormDefine = runTimeViewController.queryFormByCodeInScheme(formSchemeKey, formCode);
            }
            catch (Exception e) {
                logger.error("\u67e5\u8be2\u62a5\u8868\u65b9\u6848{}\u7684\u8868\u5355{}\u5f02\u5e38", formSchemeKey, formCode, e);
                return null;
            }
            compiledFilter = "";
            try {
                filter = (String)parameters.get(1).evaluate((IContext)this.queryContext);
                if (StringUtils.hasText(filter) && !StringUtils.hasText(compiledFilter = SumhbParamBuilder.getReplaceFilter(this.queryContext, filter))) {
                    return null;
                }
            }
            catch (SyntaxException e) {
                logger.error("\u53c2\u6570" + parameters + "\u89e3\u6790\u9519\u8bef\u3002", e);
                return null;
            }
            IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
            String patternStr = "\\[\\d+,\\d+\\]";
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(fieldCode);
            if (matcher.find()) {
                try {
                    String zbCell = matcher.group(0);
                    String[] posValStr = zbCell.substring(1, zbCell.length() - 1).split(",");
                    int row = Integer.parseInt(posValStr[0]);
                    int col = Integer.parseInt(posValStr[1]);
                    DataLinkDefine dataLinkDefine = runTimeViewController.queryDataLinkDefineByColRow(currFormDefine.getKey(), col, row);
                    currDataRegionDefine = runTimeViewController.queryDataRegionDefine(dataLinkDefine.getRegionKey());
                    List deployInfos = runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{dataLinkDefine.getLinkExpression()});
                    if (deployInfos.size() != 1) {
                        throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5355\u5143\u683c[" + row + "," + col + "]\u5173\u8054\u7684\u5b57\u6bb5\u3002");
                    }
                    columnModelDefine = this.dataModelService.getColumnModelDefineByID(((DataFieldDeployInfo)deployInfos.get(0)).getColumnModelKey());
                    if (!this.inputTableName.equals(((DataFieldDeployInfo)deployInfos.get(0)).getTableName())) {
                        Object[] i18Args = new String[]{((DataFieldDeployInfo)deployInfos.get(0)).getTableName()};
                        throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbparamstablenotmatch", (Object[])i18Args));
                    }
                    break block19;
                }
                catch (Exception e) {
                    logger.error("\u67e5\u8be2\u8868\u5b57\u6bb5\u5931\u8d25\u3002", formSchemeKey, formCode, e);
                    return null;
                }
            }
            List allRegionsInForms = runTimeViewController.getAllRegionsInForm(currFormDefine.getKey());
            currDataRegionDefine = allRegionsInForms.stream().filter(dataRegionDefine -> {
                boolean isSimple;
                boolean bl = isSimple = dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE;
                if (isSimple) {
                    return false;
                }
                List zbIds = runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
                return zbIds.contains(this.amtZBId);
            }).findFirst().orElse(null);
            if (currDataRegionDefine == null) {
                return null;
            }
            try {
                columnModelDefine = this.dataModelService.getColumnModelDefineByCode(this.inputTableId, fieldCode);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException((Throwable)e);
            }
            if (columnModelDefine == null) {
                throw new RuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbzberrorformatmsg"));
            }
        }
        if (currDataRegionDefine == null) {
            return null;
        }
        SumhbParam sumhbParam = new SumhbParam();
        sumhbParam.setFormCode(formCode);
        sumhbParam.setReginId(currDataRegionDefine.getKey());
        sumhbParam.setFieldDefine(columnModelDefine);
        sumhbParam.setFilter(filter);
        sumhbParam.setCompiledFilter(compiledFilter);
        sumhbParam.setRegionFilter(currDataRegionDefine.getFilterCondition());
        return sumhbParam;
    }

    private static String getReplaceFilter(QueryContext queryContext, String originalFilter) {
        String replaceFilter;
        try {
            ExecutorContext executorContext = queryContext.getExeContext();
            ReportFormulaParser reportFormulaParser = executorContext.getCache().getFormulaParser(executorContext);
            QueryContext parseContext = new QueryContext(executorContext, null);
            IExpression iExpression = reportFormulaParser.parseCond(originalFilter, (IContext)parseContext);
            FormulaShowInfo formulaShowInfo = new FormulaShowInfo(DataEngineConsts.FormulaShowType.DATA);
            for (IASTNode node : iExpression) {
                if (!(node instanceof DynamicDataNode)) continue;
                DynamicDataNode dataNode = (DynamicDataNode)node;
                NodeShowInfo showInfo = dataNode.getShowInfo();
                showInfo.setTableName(dataNode.getQueryField().getTableName());
            }
            replaceFilter = iExpression.interpret((IContext)parseContext, Language.FORMULA, (Object)formulaShowInfo);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u8fc7\u6ee4\u516c\u5f0f\u89e3\u6790\u51fa\u9519" + originalFilter, (Throwable)e);
        }
        return replaceFilter;
    }

    private ColumnModelDefine getColumnModelDefine(ColumnModelDefine columnModelDefine) {
        try {
            IColumnModelFinder iColumnModelFinder = (IColumnModelFinder)SpringContextUtils.getBean(DataSchemeColumnModelFinder.class);
            FieldDefine ZbFieldDefine = iColumnModelFinder.findFieldDefine(columnModelDefine);
            FieldDefine fieldDefine = this.dataDefinitionRuntimeController.queryFieldDefine(ZbFieldDefine.getKey());
            String tableName = ((InputDataNameProvider)SpringContextUtils.getBean(InputDataNameProvider.class)).getInputDataTableNameByDataTableKey(fieldDefine.getOwnerTableKey());
            if (!this.inputTableName.equals(tableName)) {
                Object[] i18Args = new String[]{tableName};
                throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.inputdata.function.sumhbparamstablenotmatch", (Object[])i18Args));
            }
            return new ReportColumnModelImpl((DataField)fieldDefine);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6SUMHB\u6307\u6807\u4fe1\u606f\u5f02\u5e38" + columnModelDefine.getName(), (Throwable)e);
        }
    }
}

