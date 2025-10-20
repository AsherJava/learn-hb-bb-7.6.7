/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.SyntaxException
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.function.Parameter
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.nr.function.INrFunction
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool
 *  com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO
 *  com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils
 *  com.jiuqi.gcreport.org.api.enums.GcAuthorityType
 *  com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.parse.AdvanceFunction
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.efdc.extract.IExtractDataUpdator
 *  com.jiuqi.nr.efdc.extract.IExtractRequest
 *  com.jiuqi.nr.efdc.extract.impl.EFDCExtractRequestFactory
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo
 *  com.jiuqi.nr.jtable.service.IJtableResourceService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  javax.annotation.Resource
 */
package com.jiuqi.gcreport.calculate.formula.service.impl.function;

import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.function.Parameter;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.nr.function.INrFunction;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.nr.impl.uploadstate.util.FormUploadStateTool;
import com.jiuqi.gcreport.nr.impl.uploadstate.vo.DimensionParamsVO;
import com.jiuqi.gcreport.nr.impl.util.GcOrgTypeUtils;
import com.jiuqi.gcreport.org.api.enums.GcAuthorityType;
import com.jiuqi.gcreport.org.api.enums.GcOrgKindEnum;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.gcreport.org.api.tool.GcOrgPublicTool;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.org.api.vo.tool.YearPeriodObject;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.parse.AdvanceFunction;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.readwrite.ReadWriteAccessDesc;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.efdc.extract.IExtractDataUpdator;
import com.jiuqi.nr.efdc.extract.IExtractRequest;
import com.jiuqi.nr.efdc.extract.impl.EFDCExtractRequestFactory;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.input.ReportDataQueryInfo;
import com.jiuqi.nr.jtable.service.IJtableResourceService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EfdcExtractFunction
extends AdvanceFunction
implements INrFunction {
    private static final long serialVersionUID = 1L;
    public final String FUNCTION_NAME = "EfdcExtract";
    public final String ORGCODESET = "orgCodeSet";
    @Autowired
    private transient IRunTimeViewController runTimeViewController;
    @Autowired
    private transient IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private transient INvwaSystemOptionService iNvwaSystemOptionService;
    @Resource
    private transient IDataAccessProvider dataAccessProvider;
    @Autowired(required=false)
    private transient IExtractDataUpdator dataUpdator;
    @Autowired
    private transient IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private transient IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private transient IBatchCalculateService batchOperationService;
    @Autowired
    private transient IJtableResourceService iReportDataService;

    public EfdcExtractFunction() {
        this.parameters().add(new Parameter("taskTitle", 6, "\u4efb\u52a1\u540d\u79f0", false));
        this.parameters().add(new Parameter("schemeTitle", 6, "\u62a5\u8868\u65b9\u6848\u540d\u79f0\uff0c\u9ed8\u8ba4\u7b2c\u4e00\u4e2a", true));
        this.parameters().add(new Parameter("efdcFormulaSchemeTitle", 6, "\u8d22\u52a1\u516c\u5f0f\u65b9\u6848\u540d\u79f0\uff0c\u9ed8\u8ba4\u7b2c\u4e00\u4e2a", true));
        this.parameters().add(new Parameter("rptFormulaSchemeTitle", 6, "\u53d6\u6570\u540e\u8fd0\u7b97\u516c\u5f0f\u65b9\u6848,\u9ed8\u8ba4\u4e0d\u6267\u884c", true));
        this.parameters().add(new Parameter("supportMatchParentOrg", 1, "\u5355\u4f4d\u4e0d\u5b58\u5728\u65f6\uff0c\u5339\u914d\u4e0a\u7ea7", true));
        this.parameters().add(new Parameter("formCodes", 6, "\u8868\u5355\u6807\u8bc6\uff0c\u591a\u4e2a\u4ee5\u5206\u53f7\u9694\u5f00", true));
    }

    public String name() {
        return "EfdcExtract";
    }

    public String title() {
        return "EFDC\u63d0\u53d6";
    }

    public int validate(IContext context, List<IASTNode> parameters) throws SyntaxException {
        String taskId = this.getTaskId(parameters);
        if (null == taskId) {
            throw new SyntaxException("\u672a\u6b63\u786e\u914d\u7f6e\u4efb\u52a1\u540d\u79f0");
        }
        FormSchemeDefine formScheme = this.getFormScheme(parameters, taskId);
        if (null == formScheme) {
            throw new SyntaxException("\u672a\u6b63\u786e\u914d\u7f6e\u62a5\u8868\u65b9\u6848\u540d\u79f0");
        }
        FormulaSchemeDefine formulaSchemeDefine = this.getCWFormulaSchemeDefine(parameters, formScheme.getKey());
        if (null == formulaSchemeDefine) {
            throw new SyntaxException("\u672a\u6b63\u786e\u914d\u7f6e\u516c\u5f0f\u65b9\u6848\u540d\u79f0");
        }
        this.validateRptFormulaSchemeDefine(parameters, formScheme.getKey());
        return super.validate(context, parameters);
    }

    public Boolean evalute(IContext context, List<IASTNode> parameters) throws SyntaxException {
        try {
            String logInfo = this.process(context, parameters);
            String detailInfo = String.format("\u516c\u5f0f\u4fe1\u606f\uff1a%1$s%2$s;\u7ef4\u5ea6\u503c\uff1a%3$s;\u6267\u884c\u6210\u529f\u3002%4$s", "EfdcExtract", parameters, ((QueryContext)context).getMasterKeys(), logInfo);
            LogHelper.info((String)"\u8fd0\u7b97\u516c\u5f0f", (String)"\u6267\u884cEFDC\u53d6\u6570\u516c\u5f0f\u6210\u529f", (String)detailInfo);
            return true;
        }
        catch (Exception e) {
            String detailInfo = String.format("\u516c\u5f0f\u4fe1\u606f\uff1a%1$s%2$s;\u7ef4\u5ea6\u503c\uff1a%3$s;\u62a5\u9519\u4fe1\u606f\uff1a%4$s", "EfdcExtract", parameters, ((QueryContext)context).getMasterKeys(), e.getMessage());
            LogHelper.warn((String)"\u8fd0\u7b97\u516c\u5f0f", (String)"\u6267\u884cEFDC\u53d6\u6570\u516c\u5f0f\u5f02\u5e38", (String)detailInfo);
            throw new SyntaxException((Throwable)e);
        }
    }

    private String process(IContext context, List<IASTNode> parameters) throws Exception {
        String taskKey = this.getTaskId(parameters);
        FormSchemeDefine formScheme = this.getFormScheme(parameters, taskKey);
        if (formScheme == null) {
            return "";
        }
        QueryContext queryContext = (QueryContext)context;
        List<FormDefine> formDefineList = this.listFormDefines(parameters, formScheme.getKey());
        FormulaSchemeDefine cwFormulaScheme = this.getCWFormulaSchemeDefine(parameters, formScheme.getKey());
        if (cwFormulaScheme == null) {
            return "";
        }
        BatchCalculateInfo batchCalculateInfo = this.getBatchCalculateInfo(queryContext, parameters, formScheme, formDefineList);
        ReportDataQueryInfo clearReportDataQueryInfo = this.getReportDataQueryInfo(batchCalculateInfo);
        String logInfo = this.doEfdc(parameters, queryContext, formScheme, cwFormulaScheme, formDefineList, clearReportDataQueryInfo);
        if (!StringUtils.isEmpty((String)batchCalculateInfo.getFormulaSchemeKey())) {
            Set orgCacheVOSet = (Set)queryContext.getMasterKeys().getValue("orgCodeSet");
            for (GcOrgCacheVO orgCacheVO : orgCacheVOSet) {
                ((DimensionValue)batchCalculateInfo.getDimensionSet().get("MD_ORG")).setValue(orgCacheVO.getCode());
                this.batchOperationService.batchCalculateForm(batchCalculateInfo);
            }
        }
        return logInfo;
    }

    private String doEfdc(List<IASTNode> parameters, QueryContext queryContext, FormSchemeDefine formScheme, FormulaSchemeDefine cwFormulaScheme, List<FormDefine> formDefineList, ReportDataQueryInfo clearReportDataQueryInfo) throws SyntaxException {
        Map<String, Object> paras = this.buildEfdcParas(queryContext, parameters, formScheme, cwFormulaScheme);
        ExecutorContext executorContext = this.newExecutorContext(formScheme.getKey());
        StringBuilder log = new StringBuilder(128);
        try {
            List formKeys = formDefineList.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            DimensionParamsVO paramsVO = new DimensionParamsVO(clearReportDataQueryInfo.getContext().getDimensionSet(), formScheme.getKey());
            paramsVO.setTaskId(formScheme.getTaskKey());
            String orgCode = ((DimensionValue)clearReportDataQueryInfo.getContext().getDimensionSet().get("MD_ORG")).getValue();
            Set<GcOrgCacheVO> orgCacheVOSet = this.getOrgCodeSet(orgCode, (String)paras.get("DATATIME"), (String)paras.get("MD_GCORGTYPE"));
            queryContext.getMasterKeys().setValue("orgCodeSet", orgCacheVOSet);
            for (GcOrgCacheVO orgCacheVO : orgCacheVOSet) {
                paras.put("UnitCode", orgCacheVO.getCode());
                paras.put("BBLX".toLowerCase(), "0");
                ((DimensionValue)clearReportDataQueryInfo.getContext().getDimensionSet().get("MD_ORG")).setValue(orgCacheVO.getCode());
                List readWriteAccessDescList = FormUploadStateTool.getInstance().writeable((Object)paramsVO, orgCacheVO.getCode(), formKeys);
                for (int i = 0; i < formDefineList.size(); ++i) {
                    FormDefine formDefine = formDefineList.get(i);
                    if (!((ReadWriteAccessDesc)readWriteAccessDescList.get(i)).getAble().booleanValue()) {
                        log.append("[").append(formDefine.getFormCode()).append("]").append(formDefine.getTitle()).append(",");
                        continue;
                    }
                    clearReportDataQueryInfo.getContext().setFormKey(formDefine.getKey());
                    EFDCExtractRequestFactory efdcExtractResultFactory = new EFDCExtractRequestFactory();
                    IExtractRequest extractRequest = efdcExtractResultFactory.createReqeust(cwFormulaScheme, formDefine);
                    extractRequest.doPrepare(executorContext, paras, this.formulaRunTimeController);
                    DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((JtableContext)clearReportDataQueryInfo.getContext());
                    extractRequest.doExtract(executorContext, this.dataAccessProvider, dimensionValueSet, paras, this.dataUpdator);
                }
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        if (log.length() != 0) {
            log.insert(0, "\u5b58\u5728\u53ea\u8bfb\u7684\u8868\u5355\uff1a");
            log.setLength(log.length() - 1);
        }
        return log.toString();
    }

    private Set<GcOrgCacheVO> getOrgCodeSet(String orgCode, String periodStr, String orgType) {
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService orgCenterService = GcOrgPublicTool.getInstance((String)orgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        HashSet<GcOrgCacheVO> orgCodeSet = new HashSet<GcOrgCacheVO>();
        this.getNotParentCode(orgCode, orgCenterService, orgCodeSet);
        return orgCodeSet;
    }

    private void getNotParentCode(String orgCode, GcOrgCenterService orgCenterService, Set<GcOrgCacheVO> orgCodeSet) {
        GcOrgCacheVO orgVo = orgCenterService.getOrgByCode(orgCode);
        if (null != orgVo) {
            GcOrgKindEnum orgKind = orgVo.getOrgKind();
            if (GcOrgKindEnum.UNIONORG.equals((Object)orgKind)) {
                List orgChildrenTree = orgCenterService.getOrgChildrenTree(orgCode);
                for (GcOrgCacheVO orgCacheVO : orgChildrenTree) {
                    if (null == orgCacheVO) continue;
                    this.getNotParentCode(orgCacheVO.getCode(), orgCenterService, orgCodeSet);
                }
            } else if (!GcOrgKindEnum.DIFFERENCE.equals((Object)orgKind)) {
                orgCodeSet.add(orgVo);
            }
        }
    }

    public int getResultType(IContext context, List<IASTNode> parameters) throws SyntaxException {
        return 0;
    }

    public String category() {
        return "\u5408\u5e76\u62a5\u8868\u51fd\u6570";
    }

    private Object getParamValue(List<IASTNode> parameters, int index) throws SyntaxException {
        if (parameters.size() > index) {
            return parameters.get(index).evaluate(null);
        }
        return null;
    }

    private String getTaskId(List<IASTNode> parameters) throws SyntaxException {
        Object taskTitle = this.getParamValue(parameters, 0);
        if (taskTitle == null) {
            return null;
        }
        List taskDefineList = this.runTimeViewController.getAllTaskDefines();
        for (TaskDefine taskDefine : taskDefineList) {
            if (!taskTitle.equals(taskDefine.getTitle())) continue;
            return taskDefine.getKey();
        }
        return null;
    }

    private FormSchemeDefine getFormScheme(List<IASTNode> parameters, String taskId) throws SyntaxException {
        try {
            String schemeTitle = (String)this.getParamValue(parameters, 1);
            if (schemeTitle == null) {
                return null;
            }
            List formSchemeDefines = this.runTimeViewController.queryFormSchemeByTask(taskId);
            if (StringUtils.isEmpty((String)schemeTitle)) {
                return (FormSchemeDefine)formSchemeDefines.get(0);
            }
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                if (!schemeTitle.equals(formSchemeDefine.getTitle())) continue;
                return formSchemeDefine;
            }
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
        return null;
    }

    private FormulaSchemeDefine getCWFormulaSchemeDefine(List<IASTNode> parameters, String formSchemeKey) throws SyntaxException {
        String efdcFormulaSchemeTitle = (String)this.getParamValue(parameters, 2);
        List formulaSchemeDefineList = this.formulaRunTimeController.getAllCWFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (efdcFormulaSchemeTitle == null) {
            return (FormulaSchemeDefine)formulaSchemeDefineList.get(0);
        }
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefineList) {
            if (!efdcFormulaSchemeTitle.equals(formulaSchemeDefine.getTitle())) continue;
            return formulaSchemeDefine;
        }
        return null;
    }

    private FormulaSchemeDefine validateRptFormulaSchemeDefine(List<IASTNode> parameters, String formSchemeKey) throws SyntaxException {
        String formulaSchemeTitle = (String)this.getParamValue(parameters, 3);
        if (formulaSchemeTitle == null) {
            return null;
        }
        List formulaSchemeDefineList = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        for (FormulaSchemeDefine formulaSchemeDefine : formulaSchemeDefineList) {
            if (!formulaSchemeTitle.equals(formulaSchemeDefine.getTitle())) continue;
            return formulaSchemeDefine;
        }
        throw new SyntaxException("\u516c\u5f0f\u65b9\u6848\u4e0d\u5b58\u5728\uff1a" + formulaSchemeTitle);
    }

    private GcOrgCacheVO getDestOrgVo(QueryContext queryContext, List<IASTNode> parameters, FormSchemeDefine formScheme) throws SyntaxException {
        String destOrgType = GcOrgTypeUtils.getOrgTypeByContextOrTaskId((String)formScheme.getTaskKey());
        String selectedOrgCode = (String)queryContext.getMasterKeys().getValue("MD_ORG");
        String periodStr = (String)queryContext.getMasterKeys().getValue("DATATIME");
        YearPeriodObject yp = new YearPeriodObject(null, periodStr);
        GcOrgCenterService destTool = GcOrgPublicTool.getInstance((String)destOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO orgCacheVO = destTool.getOrgByCode(selectedOrgCode);
        if (null != orgCacheVO) {
            return orgCacheVO;
        }
        String currOrgType = (String)queryContext.getMasterKeys().getValue("MD_GCORGTYPE");
        boolean supportMatchParentOrg = Boolean.TRUE.equals(this.getParamValue(parameters, 4));
        if (!supportMatchParentOrg || currOrgType.equals(destOrgType)) {
            return null;
        }
        GcOrgCenterService currTool = GcOrgPublicTool.getInstance((String)destOrgType, (GcAuthorityType)GcAuthorityType.NONE, (YearPeriodObject)yp);
        GcOrgCacheVO selectedOrgCacheVO = currTool.getOrgByCode(selectedOrgCode);
        String[] parentCodes = selectedOrgCacheVO.getParents();
        for (int i = parentCodes.length - 1; i >= 0; --i) {
            String parentCode = parentCodes[i];
            GcOrgCacheVO destOrgCacheVO = destTool.getOrgByCode(parentCode);
            if (null == destOrgCacheVO) continue;
            return destOrgCacheVO;
        }
        return null;
    }

    private List<FormDefine> listFormDefines(List<IASTNode> parameters, String formSchemeKey) throws SyntaxException {
        try {
            String formCodes = (String)this.getParamValue(parameters, 5);
            if (formCodes == null) {
                List formDefineList = this.runTimeViewController.queryAllFormDefinesByFormScheme(formSchemeKey);
                return formDefineList;
            }
            String[] formCodeArr = formCodes.split(";");
            ArrayList<FormDefine> formDefineList = new ArrayList<FormDefine>();
            for (String formCode : formCodeArr) {
                FormDefine formDefine;
                if (StringUtils.isEmpty((String)formCode) || null == (formDefine = this.runTimeViewController.queryFormByCodeInScheme(formSchemeKey, formCode))) continue;
                formDefineList.add(formDefine);
            }
            return formDefineList;
        }
        catch (Exception e) {
            throw new SyntaxException((Throwable)e);
        }
    }

    private ReportDataQueryInfo getReportDataQueryInfo(BatchCalculateInfo batchCalculateInfo) {
        JtableContext clearContext = new JtableContext();
        clearContext.setDimensionSet(batchCalculateInfo.getDimensionSet());
        clearContext.setTaskKey(batchCalculateInfo.getTaskKey());
        clearContext.setFormSchemeKey(batchCalculateInfo.getFormSchemeKey());
        ReportDataQueryInfo reportDataQueryInfo = new ReportDataQueryInfo();
        reportDataQueryInfo.setContext(clearContext);
        return reportDataQueryInfo;
    }

    private BatchCalculateInfo getBatchCalculateInfo(QueryContext queryContext, List<IASTNode> parameters, FormSchemeDefine formScheme, List<FormDefine> formDefineList) throws SyntaxException {
        BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
        batchCalculateInfo.setTaskKey(formScheme.getTaskKey());
        String periodStr = (String)queryContext.getMasterKeys().getValue("DATATIME");
        GcOrgCacheVO destOrgVo = this.getDestOrgVo(queryContext, parameters, formScheme);
        if (destOrgVo == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\u4fe1\u606f\u3002");
        }
        String currencyCode = (String)queryContext.getMasterKeys().getValue("MD_CURRENCY");
        String adjustCode = (String)queryContext.getMasterKeys().getValue("ADJUST");
        Map dimensionSetMap = DimensionUtils.buildDimensionMap((String)formScheme.getTaskKey(), (String)currencyCode, (String)periodStr, (String)destOrgVo.getOrgTypeId(), (String)destOrgVo.getCode(), (String)adjustCode);
        batchCalculateInfo.setDimensionSet(dimensionSetMap);
        String formCodes = (String)this.getParamValue(parameters, 5);
        if (!StringUtils.isEmpty((String)formCodes)) {
            HashMap<String, Object> formulas = new HashMap<String, Object>();
            for (FormDefine formDefine : formDefineList) {
                formulas.put(formDefine.getKey(), null);
            }
            batchCalculateInfo.setFormulas(formulas);
        }
        batchCalculateInfo.setFormSchemeKey(formScheme.getKey());
        FormulaSchemeDefine rptFormulaScheme = this.validateRptFormulaSchemeDefine(parameters, formScheme.getKey());
        if (null == rptFormulaScheme) {
            return batchCalculateInfo;
        }
        batchCalculateInfo.setFormulaSchemeKey(rptFormulaScheme.getKey());
        return batchCalculateInfo;
    }

    private ExecutorContext newExecutorContext(String formSchemeKey) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setJQReportModel(true);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        return executorContext;
    }

    private Map<String, Object> buildEfdcParas(QueryContext queryContext, List<IASTNode> parameters, FormSchemeDefine formScheme, FormulaSchemeDefine extractFormulaScheme) throws SyntaxException {
        GcOrgCacheVO orgVo = this.getDestOrgVo(queryContext, parameters, formScheme);
        if (orgVo == null) {
            throw new BusinessRuntimeException("\u627e\u4e0d\u5230\u5bf9\u5e94\u7684\u5355\u4f4d\u4fe1\u606f\u3002");
        }
        String selectedOrgCode = (String)queryContext.getMasterKeys().getValue("MD_ORG");
        Assert.isNotNull((Object)orgVo, (String)("\u672a\u67e5\u627e\u5230\u5355\u4f4d\uff1a" + selectedOrgCode), (Object[])new Object[0]);
        String address = this.iNvwaSystemOptionService.get("fext-settings-group", "EFDCURL");
        HashMap<String, Object> paras = new HashMap<String, Object>(16);
        paras.put("EFDCAddress", address);
        paras.put("UnitCode", orgVo.getCode());
        paras.put("DATATIME", queryContext.getMasterKeys().getValue("DATATIME"));
        paras.put("includeAdjustPeriod", false);
        paras.put("taskID", formScheme.getTaskKey());
        paras.put("calSchemeKey", extractFormulaScheme.getKey());
        HashMap<String, String> dimMap = new HashMap<String, String>(16);
        dimMap.put("MD_GCORGTYPE", orgVo.getOrgTypeId());
        dimMap.put("MD_CURRENCY", (String)queryContext.getMasterKeys().getValue("MD_CURRENCY"));
        dimMap.put("ORGTYPE_CODE", orgVo.getOrgTypeId());
        if (!dimMap.isEmpty()) {
            paras.put("otherEntity", dimMap);
        }
        return paras;
    }
}

