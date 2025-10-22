/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  com.jiuqi.gcreport.basedata.impl.bean.GcBaseData
 *  com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.common.util.UUIDOrderUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.common.ReportInfo
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.parse.IReportFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.period.PeriodModifier
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataLinkDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.function.func.floatcopy.FloatCopyExecutor
 *  com.jiuqi.nr.function.func.floatcopy.FloatCopyOperand
 *  com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser
 *  com.jiuqi.nr.function.func.floatcopy.FloatCopyRowData
 */
package com.jiuqi.gcreport.nr.impl.function.impl.floatcopyT;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.basedata.impl.bean.GcBaseData;
import com.jiuqi.gcreport.basedata.impl.util.GcBaseDataCenterTool;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.common.util.UUIDOrderUtils;
import com.jiuqi.gcreport.nr.impl.function.impl.util.CrossTaskFunctionUtil;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.common.ReportInfo;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.parse.IReportFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.period.PeriodModifier;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nr.bpm.impl.common.NvwaDataModelCreateUtil;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyExecutor;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyOperand;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyParaParser;
import com.jiuqi.nr.function.func.floatcopy.FloatCopyRowData;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrossTaskFloatCopyExecutor
extends FloatCopyExecutor {
    private static final DecimalFormat df = new DecimalFormat("00");
    private String convertZbs;
    private int periodOffset;
    private int period = -1;
    private String orgCode;
    private String reportName;
    private String currSchemeKey;

    public CrossTaskFloatCopyExecutor(FloatCopyParaParser parser, IReportFunction reportFunction) {
        super(parser, reportFunction);
    }

    public boolean execute(QueryContext qContext, List<IASTNode> parameters) throws Exception {
        ReportInfo reportInfo;
        this.convertZbs = (String)parameters.get(6).evaluate((IContext)qContext);
        int n = this.periodOffset = parameters.size() > 7 ? Convert.toInt((Object)parameters.get(7).evaluate((IContext)qContext)) : 0;
        if (parameters.size() >= 9) {
            this.period = Convert.toInt((Object)parameters.get(8).evaluate(null));
        }
        if (parameters.size() >= 10) {
            this.orgCode = Convert.toString((Object)parameters.get(9).evaluate(null));
        }
        if ((reportInfo = this.queryCondition.getReportInfo()) == null) {
            this.reportName = this.getReportName(qContext, parameters.get(0));
            this.currSchemeKey = ((ReportFmlExecEnvironment)qContext.getExeContext().getEnv()).getFormSchemeKey();
            return super.execute(qContext);
        }
        String periodDim = reportInfo.getPeriodDim();
        PeriodModifier periodModifier = reportInfo.getPeriodModifier();
        reportInfo.setPeriodModifierStr("");
        reportInfo.setPeriodDim(null);
        boolean executeResult = super.execute(qContext);
        reportInfo.setPeriodDim(periodDim);
        String periodModifierStr = periodModifier == null ? "" : periodModifier.toString();
        reportInfo.setPeriodModifierStr(periodModifierStr);
        return executeResult;
    }

    protected void list(List<FloatCopyRowData> srcRowDatas, List<FloatCopyRowData> destRowDatas, IDataTable destResult, int destColSize) throws IncorrectQueryException, DataTypeException {
        int srcRowsize = srcRowDatas.size();
        int destRowSize = destRowDatas.size();
        Map<String, DataField> zbCode2FieldDefineMap = this.listSourceFieldDefines();
        HashMap tableName2MapDictTitle2CodeCache = new HashMap();
        IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
        NvwaDataModelCreateUtil nvwaDataModelCreateUtil = (NvwaDataModelCreateUtil)SpringContextUtils.getBean(NvwaDataModelCreateUtil.class);
        for (int i = 0; i < srcRowsize; ++i) {
            FloatCopyRowData srcRowData = srcRowDatas.get(i);
            int newFloatOrder = (destRowSize + i) * 1000;
            IDataRow dataRow = destResult.appendRow(srcRowData.getRowKey());
            for (int j = 0; j < destColSize; ++j) {
                Object destZbValue = srcRowData.getValue(j).getAsObject();
                String columnCode = (String)this.queryCondition.getRow().getQueryColumns().get(j);
                if (zbCode2FieldDefineMap.containsKey(columnCode = columnCode.substring(columnCode.indexOf("["), columnCode.indexOf("]")).concat("]").trim())) {
                    DataField sourceFileDefine = zbCode2FieldDefineMap.get(columnCode);
                    try {
                        TableDefine tableDefine = null;
                        try {
                            tableDefine = dataDefinitionRuntimeController.queryTableDefine(sourceFileDefine.getDataTableKey());
                        }
                        catch (Exception e) {
                            throw new IncorrectQueryException((Throwable)e);
                        }
                        AbstractData sourceZbValue = NrTool.getZbValue((DimensionValueSet)srcRowData.getRowKey(), (String)(tableDefine.getCode() + "[" + sourceFileDefine.getCode() + "]"));
                        String sourceDictTableName = nvwaDataModelCreateUtil.queryTableModel(sourceFileDefine.getRefDataEntityKey()).getName();
                        GcBaseData sourceBaseData = GcBaseDataCenterTool.getInstance().queryBasedataByCode(sourceDictTableName, sourceZbValue.getAsObject().toString());
                        String sourceBaseDataTitle = sourceBaseData.getTitle();
                        FieldDefine destFieldDefine = dataRow.getFieldsInfo().getFieldDefine(j);
                        String desteDictTableName = nvwaDataModelCreateUtil.queryTableModel(destFieldDefine.getEntityKey()).getName();
                        String dictCode = NrTool.getDictCodeByTitle((String)desteDictTableName, (String)sourceBaseDataTitle, tableName2MapDictTitle2CodeCache);
                        if (!StringUtils.isEmpty((String)dictCode)) {
                            destZbValue = dictCode;
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                dataRow.setValue(j, destZbValue);
            }
            dataRow.getRowKeys().setValue("RECORDKEY", (Object)UUIDOrderUtils.newUUIDStr());
            if (dataRow.getFieldsInfo().getFieldCount() != destColSize + 1) continue;
            dataRow.setValue(destColSize, (Object)newFloatOrder);
        }
    }

    private Map<String, DataField> listSourceFieldDefines() {
        HashMap<String, DataField> zbCode2FieldDefineMap = new HashMap<String, DataField>();
        try {
            String[] convertZbsArr;
            IRuntimeDataSchemeService runtimeDataSchemeService = (IRuntimeDataSchemeService)SpringContextUtils.getBean(IRuntimeDataSchemeService.class);
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class);
            IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)SpringContextUtils.getBean(IDataDefinitionRuntimeController.class);
            String formKey = this.getFormKeyByReportName();
            for (String zb : convertZbsArr = this.convertZbs.split(";")) {
                DataField fieldDefine;
                String[] colNumArr = zb.split(",");
                if (colNumArr.length != 2) continue;
                int row = Integer.valueOf(colNumArr[0].trim().replace("[", ""));
                int col = Integer.valueOf(colNumArr[1].trim().replace("]", ""));
                DataLinkDefine dataLinkDefine = runTimeViewController.queryDataLinkDefineByColRow(formKey, col, row);
                if (dataLinkDefine == null || (fieldDefine = runtimeDataSchemeService.getDataField(dataLinkDefine.getLinkExpression())) == null) continue;
                zbCode2FieldDefineMap.put(zb.trim(), fieldDefine);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return zbCode2FieldDefineMap;
    }

    public List<FloatCopyRowData> getSrcRowDatas(QueryContext qContext, List<DimensionValueSet> vsList) throws Exception {
        FormSchemeDefine destSchemeDefine = CrossTaskFunctionUtil.getDestFormSchemeDefine((ReportFmlExecEnvironment)qContext.getExeContext().getEnv(), this.queryCondition.getLinkedAlias());
        String orgType = CrossTaskFunctionUtil.getOrgTypeBySchemeId(destSchemeDefine);
        DimensionValueSet currValueSet = qContext.getCurrentMasterKey();
        String currPeriodValue = (String)currValueSet.getValue("DATATIME");
        vsList.stream().forEach(ds -> {
            if (!StringUtils.isEmpty((String)this.orgCode)) {
                ds.setValue("MD_ORG", (Object)this.orgCode);
            }
            String currPeriodStr = CrossTaskFunctionUtil.getSrcPeriod(currPeriodValue, this.periodOffset, String.valueOf(currPeriodValue.charAt(4))).toString();
            try {
                PeriodType currPeriodType = PeriodType.fromCode((int)currPeriodStr.charAt(4));
                PeriodType srcPeriodType = PeriodType.fromCode((int)((String)((DimensionValueSet)vsList.get(0)).getValue("DATATIME")).charAt(4));
                String srcPeriodStr = CrossTaskFunctionUtil.convertPeriod(currPeriodStr, currPeriodType, srcPeriodType);
                if (this.period > 0) {
                    srcPeriodStr = srcPeriodStr.substring(0, 7).concat(df.format(this.period));
                }
                ds.setValue("DATATIME", (Object)srcPeriodStr);
            }
            catch (Exception e) {
                throw new BusinessRuntimeException("\u65f6\u671f\u8f6c\u6362\u9519\u8bef:" + currPeriodStr);
            }
            YearPeriodObject yp = new YearPeriodObject(null, (String)ds.getValue("DATATIME"));
            GcOrgCenterService orgTool = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
            GcOrgCacheVO mergeOrg = orgTool.getOrgByCode((String)ds.getValue("MD_ORG"));
            if (mergeOrg == null) {
                throw new BusinessRuntimeException("\u672a\u83b7\u53d6\u5230\u5355\u4f4d:" + ds.getValue("MD_ORG"));
            }
            ds.setValue("MD_GCORGTYPE", (Object)mergeOrg.getOrgTypeId());
        });
        return super.getSrcRowDatas(qContext, vsList);
    }

    private String getReportName(QueryContext qContext, IASTNode parameter) throws SyntaxException {
        String srcRelaExp = (String)parameter.evaluate((IContext)qContext);
        String[] expArr = srcRelaExp.split(";");
        FloatCopyOperand operand = new FloatCopyOperand(expArr[0]);
        String reportName = operand.getReportName();
        if (StringUtils.isEmpty((String)reportName)) {
            reportName = qContext.getDefaultGroupName();
        }
        return reportName;
    }

    private String getFormKeyByReportName() throws Exception {
        String formKey;
        if (this.queryCondition.getReportInfo() == null) {
            FormDefine currFormDefine = ((IRunTimeViewController)SpringContextUtils.getBean(IRunTimeViewController.class)).queryFormByCodeInScheme(this.currSchemeKey, this.reportName);
            formKey = currFormDefine.getKey();
        } else {
            formKey = this.queryCondition.getReportInfo().getReportKey();
        }
        return formKey;
    }
}

