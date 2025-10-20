/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.intf.IDataModelLinkFinder
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.GcSimpleCopyOperand;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.GcSimpleCopyParam;
import com.jiuqi.gcreport.nr.impl.function.impl.gcFloatCopy.QueryCondition;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.intf.IDataModelLinkFinder;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GcSimpleCopyParaParser {
    private boolean isValid = true;
    private static final String REG_RELA = "^[A-Za-z_\\d]*(\\[[A-Za-z_\\d]*\\])?([;][A-Za-z_\\d]*(\\[[A-Za-z_\\d]*\\])?)*$";
    private static final Logger logger = LoggerFactory.getLogger(GcSimpleCopyParaParser.class);
    public static final String REG_POSITIVE_INTEGER = "^[+]?\\d+$";
    private GcSimpleCopyParam param = new GcSimpleCopyParam();
    private IDataModelLinkFinder dataLinkFinder;

    public GcSimpleCopyParaParser(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        this.dataLinkFinder = qContext.getExeContext().getEnv().getDataModelLinkFinder();
        this.parse(qContext, parameters);
    }

    private void parse(QueryContext qContext, List<IASTNode> parameters) throws SyntaxException {
        this.parseBase(qContext, (String)parameters.get(0).evaluate((IContext)qContext), (Boolean)parameters.get(1).evaluate((IContext)qContext), ((Double)parameters.get(2).evaluate((IContext)qContext)).intValue(), parameters.size() > 8 ? (String)parameters.get(8).evaluate((IContext)qContext) : "", parameters.size() > 9 ? (String)parameters.get(9).evaluate((IContext)qContext) : "");
        this.parseSrcFilter((String)parameters.get(5).evaluate((IContext)qContext));
        this.parseFloatRowRelaParas(qContext, (String)parameters.get(3).evaluate((IContext)qContext), (String)parameters.get(4).evaluate((IContext)qContext), (String)parameters.get(6).evaluate((IContext)qContext), (String)parameters.get(7).evaluate((IContext)qContext));
    }

    private void parseBase(QueryContext qContext, String reportName, boolean copyType, int periodOffset, String orgCode, String period) {
        if (StringUtils.isEmpty((String)reportName)) {
            this.param.setReportName(qContext.getExeContext().getDefaultGroupName());
        } else {
            int atIndex = reportName.indexOf("@");
            if (atIndex > 0) {
                String alias = reportName.substring(atIndex + 1);
                this.param.setAlias(alias);
                this.param.setReportName(reportName.substring(0, atIndex));
            } else {
                this.param.setReportName(reportName);
            }
        }
        this.param.setClearBeforeCopy(copyType);
        this.param.setPeriodOffset(periodOffset);
        this.param.setOrgCode(orgCode);
        this.param.setPeriod(period);
    }

    private void parseSrcFilter(String srcFilter) {
        this.param.getSrcCondition().setFilter(srcFilter);
    }

    private void parseFloatRowRelaParas(QueryContext qContext, String srcRelaExp, String destRelaExp, String assignExp, String insertExp) {
        this.parseAssignExp(assignExp);
        this.parseInsertExp(insertExp);
        this.parseRelaExp(qContext, this.param.getSrcCondition(), srcRelaExp, this.param.getReportName(), this.param.getAlias());
        this.parseRelaExp(qContext, this.param.getDesCondition(), destRelaExp, qContext.getDefaultGroupName(), null);
    }

    private void parseInsertExp(String insertExp) {
        String[] exps;
        if (StringUtils.isEmpty((String)insertExp)) {
            return;
        }
        for (String exp : exps = insertExp.split(";")) {
            String[] colNumArr = exp.split("=");
            if (colNumArr == null || colNumArr.length <= 0) continue;
            if (colNumArr.length == 1) {
                this.param.addInsertColumn(colNumArr[0], colNumArr[0]);
                continue;
            }
            this.param.addInsertColumn(colNumArr[0], colNumArr[1]);
        }
    }

    private void parseAssignExp(String assignExp) {
        String[] exps;
        for (String exp : exps = assignExp.split(";")) {
            String[] colNumArr = exp.split("=");
            if (colNumArr == null || colNumArr.length <= 0) continue;
            if (colNumArr.length == 1) {
                this.param.addUpdateColumn(colNumArr[0], colNumArr[0]);
                continue;
            }
            this.param.addUpdateColumn(colNumArr[0], colNumArr[1]);
        }
    }

    private void parseRelaExp(QueryContext qContext, QueryCondition condition, String relaExp, String reportName, String alias) {
        if (StringUtils.isEmpty((String)relaExp) || !relaExp.matches(REG_RELA)) {
            this.isValid = false;
            return;
        }
        String[] expArr = relaExp.split(";");
        GcSimpleCopyOperand operand = new GcSimpleCopyOperand(expArr[0]);
        ReportInfo reportInfo = !StringUtils.isEmpty((String)alias) ? this.dataLinkFinder.findReportInfo(alias, reportName) : this.dataLinkFinder.findReportInfo(reportName);
        if (Objects.isNull(reportInfo)) {
            throw new BusinessRuntimeException("\u62a5\u8868\u4fe1\u606f\u67e5\u8be2\u4e3a\u7a7a");
        }
        IRunTimeViewController controller = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
        List allRegionsInForm = controller.getAllRegionsInForm(reportInfo.getReportKey());
        if (allRegionsInForm.isEmpty()) {
            throw new BusinessRuntimeException("\u8868\u4e2d\u4e0d\u5b58\u5728\u533a\u57df\uff0creportName:" + reportName);
        }
        IRuntimeDataSchemeService iRuntimeDataSchemeService = (IRuntimeDataSchemeService)SpringBeanUtils.getBean(IRuntimeDataSchemeService.class);
        DataRegionDefine matchedRegion = null;
        for (DataRegionDefine dataRegionDefine : allRegionsInForm) {
            List dataFields = iRuntimeDataSchemeService.getDataFields(controller.getFieldKeysInRegion(dataRegionDefine.getKey()));
            if (!dataFields.stream().anyMatch(x -> x.getCode().equals(operand.getFieldName()))) continue;
            matchedRegion = dataRegionDefine;
            break;
        }
        if (matchedRegion == null) {
            throw new BusinessRuntimeException("\u5728\u8868\u4e2d\u7684\u533a\u57df\u5185\u672a\u627e\u5230\u5b57\u6bb5" + operand.getFieldName());
        }
        String filterCondition = matchedRegion.getFilterCondition();
        if (StringUtils.isEmpty((String)condition.getFilter())) {
            condition.setFilter(filterCondition);
        } else if (!StringUtils.isEmpty((String)filterCondition) && !condition.getFilter().contains(filterCondition)) {
            condition.setFilter(" ( " + filterCondition + " )  AND " + condition.getFilter());
        }
        condition.setRegionKey(matchedRegion.getKey());
        IDataDefinitionRuntimeController iDataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringBeanUtils.getBean(IDataDefinitionRuntimeController.class);
        try {
            FieldDefine orderField = iDataDefinitionRuntimeController.queryFieldDefine(matchedRegion.getInputOrderFieldKey());
            if (Objects.nonNull(orderField)) {
                condition.setFloatOrderField(orderField);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u533a\u57df\u5185\u6392\u5e8f\u5b57\u6bb5\u5931\u8d25", (Throwable)e);
        }
        condition.setKeyColumns(expArr);
        List allFieldKeys = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).getFieldKeysInRegion(condition.getRegionKey());
        try {
            List fieldDefines = iDataDefinitionRuntimeController.queryFieldDefines((Collection)allFieldKeys);
            ArrayList<String> allFields = new ArrayList<String>();
            HashMap<String, Integer> allQueryFieldTypes = new HashMap<String, Integer>(16);
            for (FieldDefine fieldDefine : fieldDefines) {
                DataTable dataTable = iRuntimeDataSchemeService.getDataTable(fieldDefine.getOwnerTableKey());
                if (Objects.isNull(dataTable)) {
                    throw new BusinessRuntimeException("\u6307\u6807" + fieldDefine.getCode() + "\u5bf9\u5e94\u7684\u6570\u636e\u8868\u4e0d\u5b58\u5728,key-" + fieldDefine.getOwnerTableKey());
                }
                String fieldCode = dataTable.getCode() + "[" + fieldDefine.getCode() + "]";
                allFields.add(fieldCode);
                allQueryFieldTypes.put(fieldCode, fieldDefine.getType().getValue());
            }
            condition.addQueryColumns(allFields);
            List<String> queryColumns = condition.getQueryColumns();
            for (int i = 0; i < queryColumns.size(); ++i) {
                condition.addQueryColumnType(i, (Integer)allQueryFieldTypes.get(queryColumns.get(i)));
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u83b7\u53d6\u533a\u57df\u5185\u6240\u6709\u6307\u6807\u6620\u5c04\u5931\u8d25");
        }
    }

    public boolean isValid() {
        return this.isValid;
    }

    public GcSimpleCopyParam getParam() {
        return this.param;
    }
}

