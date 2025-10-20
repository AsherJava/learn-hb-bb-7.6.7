/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.syntax.ast.IASTNode
 *  com.jiuqi.bi.syntax.ast.IExpression
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.syntax.reportparser.ReportFormulaParser
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.common.util.MapUtils
 *  com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo
 *  com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext
 *  com.jiuqi.gcreport.efdcdatacheck.executor.GcFetchDataExecutor
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo
 *  com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo
 *  com.jiuqi.gcreport.i18n.util.GcI18nUtil
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.DynamicDataNode
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodUtil
 *  com.jiuqi.nr.definition.common.DataRegionKind
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormulaDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.efdc.extract.exception.ExtractException
 *  com.jiuqi.nr.efdc.extract.impl.request.DataFetchEnv
 *  com.jiuqi.nr.efdc.extract.impl.request.DataFetchRequester
 *  com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing
 *  com.jiuqi.nr.efdc.extract.impl.request.FixExpression
 *  com.jiuqi.nr.efdc.extract.impl.request.ReportSoftInfo
 *  com.jiuqi.nr.efdc.extract.impl.response.DataFetchResponser
 *  com.jiuqi.nr.efdc.extract.impl.response.ResultListing
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 *  com.jiuqi.va.domain.org.OrgDO
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.executor.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.syntax.ast.IASTNode;
import com.jiuqi.bi.syntax.ast.IExpression;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.syntax.reportparser.ReportFormulaParser;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.common.util.MapUtils;
import com.jiuqi.gcreport.efdcdatacheck.client.vo.GcFormOperationInfo;
import com.jiuqi.gcreport.efdcdatacheck.env.GcFetchDataEnvContext;
import com.jiuqi.gcreport.efdcdatacheck.executor.GcFetchDataExecutor;
import com.jiuqi.gcreport.efdcdatacheck.impl.constants.GcFormulaSchemeKeyLengthConstant;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataInfo;
import com.jiuqi.gcreport.efdcdatacheck.vo.GcFetchDataResultInfo;
import com.jiuqi.gcreport.i18n.util.GcI18nUtil;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.DynamicDataNode;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodUtil;
import com.jiuqi.nr.definition.common.DataRegionKind;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.efdc.extract.exception.ExtractException;
import com.jiuqi.nr.efdc.extract.impl.request.DataFetchEnv;
import com.jiuqi.nr.efdc.extract.impl.request.DataFetchRequester;
import com.jiuqi.nr.efdc.extract.impl.request.ExpressionListing;
import com.jiuqi.nr.efdc.extract.impl.request.FixExpression;
import com.jiuqi.nr.efdc.extract.impl.request.ReportSoftInfo;
import com.jiuqi.nr.efdc.extract.impl.response.DataFetchResponser;
import com.jiuqi.nr.efdc.extract.impl.response.ResultListing;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import com.jiuqi.va.domain.org.OrgDO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GcEfdcFetchDataExecutorImpl
implements GcFetchDataExecutor {
    private static final Logger logger = LoggerFactory.getLogger(GcEfdcFetchDataExecutorImpl.class);
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private INvwaSystemOptionService systemOptionsService;

    public GcFetchDataResultInfo execute(GcFetchDataEnvContext context) {
        return this.queryEfdcData(context.getOrg(), context.getFormDefine(), context.getFormOperationInfo(), context.getDimensionValueMap(), context.getErrorFormKeySet(), context.getCwFmlListing());
    }

    public boolean isMatch(GcFetchDataEnvContext context) {
        String formulaSchemeKey = context.getFormulaSchemeDefine().getKey();
        if (StringUtils.isEmpty((String)formulaSchemeKey)) {
            return false;
        }
        return GcFormulaSchemeKeyLengthConstant.FORMULA_SCHEME_EFDC_LENGTH == formulaSchemeKey.length();
    }

    public GcFetchDataInfo getFieldDefineList(GcFetchDataEnvContext context) {
        Set errorFormKeySet = context.getErrorFormKeySet();
        Map reportId2ZbGuidMap = context.getReportId2ZbGuidMap();
        FormulaSchemeDefine formulaSchemeDefine = context.getFormulaSchemeDefine();
        FormDefine formDefine = context.getFormDefine();
        return this.fillFormulaGroup(formulaSchemeDefine.getKey(), formDefine.getKey(), errorFormKeySet, reportId2ZbGuidMap);
    }

    private GcFetchDataResultInfo queryEfdcData(OrgDO org, FormDefine formDefine, GcFormOperationInfo formOperationInfo, ConcurrentHashMap<String, Object> dimensionValueMap, Set<String> errorFormKeySet, ExpressionListing cwFmlListing) {
        String address = this.systemOptionsService.get("fext-settings-group", "EFDCURL");
        if (null == address) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notConfiguredAddress"));
        }
        ArrayList retEfdcValues = new ArrayList();
        String unitId = org.getCode();
        String taskId = formOperationInfo.getTaskKey();
        ConcurrentHashMap<String, String> paras = new ConcurrentHashMap<String, String>();
        String period = ConverterUtils.getAsString((Object)dimensionValueMap.get("DATATIME"));
        paras.put("MD_CURRENCY", ConverterUtils.getAsString((Object)dimensionValueMap.get("MD_CURRENCY")));
        paras.put("MD_GCORGTYPE", ConverterUtils.getAsString((Object)dimensionValueMap.get("MD_GCORGTYPE")));
        paras.put("ADJUST", ConverterUtils.getAsString((Object)dimensionValueMap.get("ADJUST"), (String)""));
        DataFetchRequester dfRequester = new DataFetchRequester();
        dfRequester.setFetchEnv(this.initFetchEnv(org, period, taskId, paras, formOperationInfo.getIncludeUncharged()));
        dfRequester.setReportSoft(this.initReportSoft(formDefine.getTitle()));
        GcFetchDataResultInfo fetchDataResultDTO = new GcFetchDataResultInfo();
        fetchDataResultDTO.setErrorFormKeySet(errorFormKeySet);
        dfRequester.setExpListing(cwFmlListing);
        try {
            String penetrableSearchURL = address + "DataFetchService?actionType=DATAFETCH";
            URL urlc = new URL(penetrableSearchURL);
            String resultGsonStr = null;
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            String paraJsonStr = mapper.writeValueAsString((Object)dfRequester);
            URLConnection con = urlc.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
            out.write(paraJsonStr);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            resultGsonStr = reader.readLine();
            DataFetchResponser dfResult = (DataFetchResponser)JsonUtils.readValue((String)resultGsonStr, DataFetchResponser.class);
            ResultListing rl = dfResult.getResultListing();
            if (!rl.isSuccess()) {
                throw new ExtractException(rl.getErrMsg());
            }
            List fixResults = rl.getFixExpResults();
            retEfdcValues.addAll(fixResults);
        }
        catch (Exception e) {
            throw new BusinessRuntimeException("\u5355\u4f4d\uff1a{" + unitId + "} - \u8868\u5355\uff1a{" + formDefine.getTitle() + "} " + e.getMessage());
        }
        fetchDataResultDTO.setRetEfdcValues(retEfdcValues);
        return fetchDataResultDTO;
    }

    private GcFetchDataInfo fillFormulaGroup(String formulaSchemekey, String formKey, Set<String> errorFormKeySet, Map<String, Set<String>> reportId2ZbGuidMap) {
        List allFormulasDefines = null;
        try {
            allFormulasDefines = this.formulaRunTimeController.getAllFormulasInForm(formulaSchemekey, formKey);
            List defines = this.formulaRunTimeController.queryPublicFormulaDefineByScheme(formulaSchemekey, formKey);
            if (!CollectionUtils.isEmpty((Collection)defines)) {
                allFormulasDefines.addAll(defines);
            }
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.fetchFormulaError"), (Throwable)e);
        }
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(formulaSchemekey);
        FormDefine formDefine = this.runTimeViewController.queryFormById(formKey);
        if (null == formDefine) {
            throw new BusinessRuntimeException(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notFoundReport", (Object[])new Object[]{formKey}));
        }
        if (allFormulasDefines == null || allFormulasDefines.size() == 0) {
            String msg = errorFormKeySet.contains(formDefine.getKey()) ? "" : GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notSettingFormula", (Object[])new Object[]{formDefine.getTitle()});
            errorFormKeySet.add(formDefine.getKey());
            throw new BusinessRuntimeException(msg);
        }
        Set<String> fixedFieldKeys = this.getFixedFieldKeys(formKey, formDefine);
        ExpressionListing cwFmlListing = new ExpressionListing();
        ArrayList<FixExpression> fixFmls = new ArrayList<FixExpression>();
        ArrayList<FieldDefine> retEfdcZbFieldDefines = new ArrayList<FieldDefine>();
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formulaSchemeDefine.getFormSchemeKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setDefaultGroupName(formDefine.getFormCode());
        HashSet<String> hasAddedZbCodes = new HashSet<String>();
        boolean filterZb = !MapUtils.isEmpty(reportId2ZbGuidMap);
        Set<String> filterZbGuids = null;
        if (filterZb) {
            filterZbGuids = reportId2ZbGuidMap.get(formDefine.getKey());
        }
        if (null == filterZbGuids) {
            filterZb = false;
            filterZbGuids = new HashSet<String>();
        }
        try {
            QueryContext qContext = new QueryContext(executorContext, null);
            ReportFormulaParser parser = executorContext.getCache().getFormulaParser(executorContext.isJQReportModel());
            for (int i = 0; i < allFormulasDefines.size(); ++i) {
                FormulaDefine formulaDefine = (FormulaDefine)allFormulasDefines.get(i);
                String expression = formulaDefine.getExpression();
                int equalIndex = expression.indexOf("=");
                String assginExp = expression.substring(0, equalIndex);
                String efdcExp = expression.substring(equalIndex + 1);
                if (assginExp.indexOf("*") >= 0) continue;
                try {
                    IExpression exp = parser.parseEval(assginExp, (IContext)qContext);
                    DynamicDataNode dataNode = null;
                    for (IASTNode child : exp) {
                        if (!(child instanceof DynamicDataNode)) continue;
                        dataNode = (DynamicDataNode)child;
                        break;
                    }
                    FieldDefine fieldDefine = dataNode.getDataLink().getField();
                    FixExpression fml = new FixExpression();
                    fml.setFlag(dataNode.getDataLink().toString());
                    fml.setName(dataNode.getDataLink().getDataLinkCode());
                    fml.setPrecision(2);
                    fml.setExpression(efdcExp);
                    if (!fixedFieldKeys.contains(fieldDefine.getKey()) || filterZb && !filterZbGuids.contains(fieldDefine.getKey()) || hasAddedZbCodes.contains(fieldDefine.getCode())) continue;
                    hasAddedZbCodes.add(fieldDefine.getCode());
                    retEfdcZbFieldDefines.add(fieldDefine);
                    fixFmls.add(fml);
                    continue;
                }
                catch (Exception e) {
                    logger.error("", e);
                }
            }
        }
        catch (Exception e) {
            logger.error("", e);
        }
        cwFmlListing.setFixExpressions(fixFmls);
        cwFmlListing.setFloatExpressions(new ArrayList());
        GcFetchDataInfo fetchDataInfoDTO = new GcFetchDataInfo();
        fetchDataInfoDTO.setRetEfdcZbFieldDefines(retEfdcZbFieldDefines);
        fetchDataInfoDTO.setExpressionListing(cwFmlListing);
        return fetchDataInfoDTO;
    }

    private Set<String> getFixedFieldKeys(String formKey, FormDefine formDefine) {
        HashSet<String> fixedFieldKeys = new HashSet<String>();
        List dataRegionDefines = this.runTimeViewController.getAllRegionsInForm(formKey);
        if (CollectionUtils.isEmpty((Collection)dataRegionDefines)) {
            String msg = GcI18nUtil.getMessage((String)"gc.efdcDataCheck.notAuditEmptyRegion", (Object[])new Object[]{formDefine.getTitle()});
            logger.info(msg);
            throw new BusinessRuntimeException(msg);
        }
        dataRegionDefines.stream().filter(Objects::nonNull).filter(dataRegionDefine -> dataRegionDefine != null && dataRegionDefine.getRegionKind() == DataRegionKind.DATA_REGION_SIMPLE).forEach(dataRegionDefine -> {
            List fieldKeysInRegion = this.runTimeViewController.getFieldKeysInRegion(dataRegionDefine.getKey());
            if (fieldKeysInRegion != null) {
                fixedFieldKeys.addAll(fieldKeysInRegion);
            }
        });
        return fixedFieldKeys;
    }

    private DataFetchEnv initFetchEnv(OrgDO org, String period, String taskId, Map<String, String> paras, Boolean includeUncharged) {
        DataFetchEnv env = new DataFetchEnv();
        String unitCode = org.getCode();
        env.setUnitCode(unitCode);
        String[] times = PeriodUtil.getTimesArr((String)period);
        env.setStartTime(times[0]);
        env.setEndTime(times[1]);
        env.setPeriodScheme(period);
        env.setTaskID(taskId);
        env.setStopOnSyntaxErr(true);
        env.setOtherEntity(paras);
        env.setInstance(null);
        env.setBblx(ConverterUtils.getAsString((Object)org.get((Object)"bblx")));
        if (includeUncharged != null) {
            env.setIncludUncharged(includeUncharged.booleanValue());
        }
        return env;
    }

    private ReportSoftInfo initReportSoft(String formTitle) {
        ReportSoftInfo rsInfo = new ReportSoftInfo();
        rsInfo.setSoftWare(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.mergeEfdcFetchData"));
        rsInfo.setSoftVersion(GcI18nUtil.getMessage((String)"gc.efdcDataCheck.mergeEfdc"));
        rsInfo.setReportsName(formTitle);
        rsInfo.setIpAddress("0:0:0:0:0:0:0:1");
        rsInfo.setComputerName("0:0:0:0:0:0:0:1");
        return rsInfo;
    }
}

