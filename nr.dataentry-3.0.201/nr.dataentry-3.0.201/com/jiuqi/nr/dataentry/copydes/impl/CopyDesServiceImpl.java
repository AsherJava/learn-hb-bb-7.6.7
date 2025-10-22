/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.interpret.InterpretException
 *  com.jiuqi.bi.syntax.parser.ParseException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.node.FormulaShowInfo
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.dataengine.query.QueryContext
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessFormService
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckDescription
 *  com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.data.logic.internal.util.ParamUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.formulamapping.IFormulaMappingController
 *  com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.nr.dataentry.copydes.impl;

import com.jiuqi.bi.syntax.interpret.InterpretException;
import com.jiuqi.bi.syntax.parser.ParseException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.node.FormulaShowInfo;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.dataengine.query.QueryContext;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessFormService;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesBatchSaveObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesQueryParam;
import com.jiuqi.nr.data.logic.facade.param.output.CheckDescription;
import com.jiuqi.nr.data.logic.facade.service.ICheckErrorDescriptionService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.data.logic.internal.util.ParamUtil;
import com.jiuqi.nr.dataentry.copydes.CheckDesCopyParam;
import com.jiuqi.nr.dataentry.copydes.CheckDesFmlObj;
import com.jiuqi.nr.dataentry.copydes.CopyDesResult;
import com.jiuqi.nr.dataentry.copydes.HandleParam;
import com.jiuqi.nr.dataentry.copydes.ICopyDesService;
import com.jiuqi.nr.dataentry.copydes.IDimMappingProvider;
import com.jiuqi.nr.dataentry.copydes.IFmlMappingProvider;
import com.jiuqi.nr.dataentry.copydes.IUnsupportedDesHandler;
import com.jiuqi.nr.dataentry.copydes.impl.FormulaFinder;
import com.jiuqi.nr.dataentry.service.ICopyDesDimensionService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.formulamapping.IFormulaMappingController;
import com.jiuqi.nr.definition.formulamapping.bean.FormulaMappingDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class CopyDesServiceImpl
implements ICopyDesService {
    private static final Logger logger = LoggerFactory.getLogger(CopyDesServiceImpl.class);
    private static final DateTimeFormatter DATETIME_FORMAT_ZONE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss ZZZZ VV");
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired(required=false)
    private IFormulaMappingController formulaMappingController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRunTimeViewController runtimeController;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private ICheckErrorDescriptionService checkErrorDescriptionService;
    @Autowired(required=false)
    private ICopyDesDimensionService copyDesDimensionService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    private ParamUtil paramUtil;
    @Autowired(required=false)
    private IUnsupportedDesHandler unsupportedDesHandler;

    @Override
    public CopyDesResult copy(CheckDesCopyParam copyParam) {
        String sourceFormulaSchemeKey;
        String startTime = DATETIME_FORMAT_ZONE.format(ZonedDateTime.now());
        Instant curTime = null;
        String userId = null;
        String userNickName = null;
        if (copyParam.isUpdateUserTime()) {
            curTime = Instant.now(Clock.systemDefaultZone());
            userId = NpContextHolder.getContext().getUserId();
            userNickName = this.paramUtil.getUserNickNameById(userId);
        }
        if (copyParam.getUnsupportedDesHandler() == null) {
            copyParam.setUnsupportedDesHandler(this.unsupportedDesHandler);
        }
        CopyDesResult copyDesResult = new CopyDesResult();
        StringBuilder copyCheckDesLog = new StringBuilder();
        StringBuilder logText = new StringBuilder();
        copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5f00\u59cb\u2014\u2014").append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5f00\u59cb\u2014\u2014").append("\n");
        String targetFormulaSchemeKey = copyParam.getTargetFormulaSchemeKey();
        String string = sourceFormulaSchemeKey = StringUtils.isEmpty((String)copyParam.getSrcFormulaSchemeKey()) ? this.getSourceFormulaSchemeKey(targetFormulaSchemeKey, copyParam.getFmlMappingProvider()) : copyParam.getSrcFormulaSchemeKey();
        if (StringUtils.isEmpty((String)sourceFormulaSchemeKey)) {
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u516c\u5f0f\u65b9\u6848\u4e3a\u7a7a\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u516c\u5f0f\u65b9\u6848\u4e3a\u7a7a\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            this.appendTimeLog(copyCheckDesLog, logText, startTime);
            copyDesResult.setSysLogText(copyCheckDesLog.toString());
            copyDesResult.setUsrLogText(logText.toString());
            return copyDesResult;
        }
        List<FormulaMappingDefine> formulaMappings = this.getFormulaMappingDefines(targetFormulaSchemeKey, copyParam.getFmlMappingProvider(), sourceFormulaSchemeKey);
        if (CollectionUtils.isEmpty(formulaMappings)) {
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u65e0\u516c\u5f0f\u6620\u5c04\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u65e0\u516c\u5f0f\u6620\u5c04\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            this.appendTimeLog(copyCheckDesLog, logText, startTime);
            copyDesResult.setSysLogText(copyCheckDesLog.toString());
            copyDesResult.setUsrLogText(logText.toString());
            return copyDesResult;
        }
        FormulaSchemeDefine sourceFormulaSchemeDefine = this.formulaRunTimeController.queryFormulaSchemeDefine(sourceFormulaSchemeKey);
        if (sourceFormulaSchemeDefine == null) {
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u4e0d\u5230\u6e90\u516c\u5f0f\u65b9\u6848\uff0ckey:").append(sourceFormulaSchemeKey).append("\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u4e0d\u5230\u6e90\u516c\u5f0f\u65b9\u6848\uff0ckey:").append(sourceFormulaSchemeKey).append("\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            this.appendTimeLog(copyCheckDesLog, logText, startTime);
            copyDesResult.setSysLogText(copyCheckDesLog.toString());
            copyDesResult.setUsrLogText(logText.toString());
            return copyDesResult;
        }
        copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u516c\u5f0f\u65b9\u6848\uff1a").append(sourceFormulaSchemeDefine.getKey()).append("|").append(sourceFormulaSchemeDefine.getTitle()).append("\n");
        String sourceFormSchemeKey = sourceFormulaSchemeDefine.getFormSchemeKey();
        FormSchemeDefine srcFormScheme = this.runtimeController.getFormScheme(sourceFormSchemeKey);
        String sourceTaskKey = srcFormScheme.getTaskKey();
        String targetFormSchemeKey = copyParam.getTargetFormSchemeKey();
        FormSchemeDefine targetFormScheme = this.runtimeController.getFormScheme(targetFormSchemeKey);
        String targetTaskKey = targetFormScheme.getTaskKey();
        TaskDefine tarTask = this.runtimeController.queryTaskDefine(targetTaskKey);
        TaskDefine srcTask = this.runtimeController.queryTaskDefine(sourceTaskKey);
        FormulaSchemeDefine tarFormulaScheme = this.formulaRunTimeController.queryFormulaSchemeDefine(targetFormulaSchemeKey);
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u4efb\u52a1\uff1a").append(srcTask.getTitle()).append("|").append(srcTask.getTaskCode()).append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u62a5\u8868\u65b9\u6848\uff1a").append(srcFormScheme.getTitle()).append("|").append(srcFormScheme.getFormSchemeCode()).append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u516c\u5f0f\u65b9\u6848\uff1a").append(sourceFormulaSchemeDefine.getTitle()).append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u76ee\u6807\u4efb\u52a1\uff1a").append(tarTask.getTitle()).append("|").append(tarTask.getTaskCode()).append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u76ee\u6807\u62a5\u8868\u65b9\u6848\uff1a").append(targetFormScheme.getTitle()).append("|").append(targetFormScheme.getFormSchemeCode()).append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u76ee\u6807\u516c\u5f0f\u65b9\u6848\uff1a").append(tarFormulaScheme.getTitle()).append("\n");
        Map<String, DimensionValue> targetDimensionSet = copyParam.getTargetDimensionSet();
        List<CheckDesObj> sourceCheckDesList = this.getCheckDesObjs(sourceFormulaSchemeKey, sourceFormSchemeKey, sourceTaskKey, targetTaskKey, targetDimensionSet, copyParam.getDimMappingProvider());
        FmlEnv fmlenv = this.getFmlEnv(copyCheckDesLog, logText, targetFormSchemeKey, targetFormulaSchemeKey, formulaMappings);
        if (copyParam.getDimMappingProvider() == null) {
            ArrayList<CheckDesObj> targetCheckDesList = new ArrayList<CheckDesObj>();
            ArrayList<CheckDesFmlObj> unsupportedSrcDes = new ArrayList<CheckDesFmlObj>();
            int unsupportedCount = 0;
            HashMap<String, List<CheckDesFmlObj>> unsupportedDstDesMap = new HashMap<String, List<CheckDesFmlObj>>();
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u5f80\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u8fc1\u79fb\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u5f80\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u8fc1\u79fb\u2014\u2014").append("\n");
            int i = 0;
            int copyNum = 0;
            List<Map<String, DimensionValue>> targetDimList = this.splitDimension(targetDimensionSet, targetFormSchemeKey);
            if (!CollectionUtils.isEmpty(targetDimList) && !CollectionUtils.isEmpty(sourceCheckDesList)) {
                List<String> sourceDimNames = this.getSourceDimNames(sourceFormSchemeKey);
                List<String> sameMasterDimNames = CopyDesServiceImpl.getSameMasterDimNames(targetDimList, sourceCheckDesList);
                Map<String, List<Map<String, DimensionValue>>> masterKeyGroup = CopyDesServiceImpl.getMasterKeyGroup(targetDimList, sameMasterDimNames);
                FormulaFinder srcFormulaFinder = new FormulaFinder(this.formulaRunTimeController, sourceFormulaSchemeKey);
                for (CheckDesObj fromFormulaCheckDes : sourceCheckDesList) {
                    String masterKeyStr = CopyDesServiceImpl.getMasterKeyStr(sameMasterDimNames, fromFormulaCheckDes.getDimensionSet());
                    List<Map<String, DimensionValue>> curTargetDims = masterKeyGroup.get(masterKeyStr);
                    if (CollectionUtils.isEmpty(curTargetDims)) continue;
                    for (Map<String, DimensionValue> dimensionValueMap : curTargetDims) {
                        ++i;
                        String expressionKey = fromFormulaCheckDes.getFormulaExpressionKey();
                        IParsedExpression parsedExpression = null;
                        try {
                            parsedExpression = srcFormulaFinder.findExpression(expressionKey.substring(0, 36), fromFormulaCheckDes.getGlobRow(), fromFormulaCheckDes.getGlobCol());
                        }
                        catch (Exception e) {
                            logger.warn(e.getMessage(), e);
                        }
                        if (parsedExpression == null) {
                            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u6e90\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u516c\u5f0f\uff0ckey:").append(expressionKey).append(",\u5ba1\u6838\u51fa\u9519\u8bf4\u660e:").append(fromFormulaCheckDes.getCheckDescription().getDescription()).append("\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u6e90\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u516c\u5f0f:").append(expressionKey).append("-").append(fromFormulaCheckDes.getGlobRow()).append("-").append(fromFormulaCheckDes.getGlobCol()).append(",\u5ba1\u6838\u51fa\u9519\u8bf4\u660e:").append(fromFormulaCheckDes.getCheckDescription().getDescription()).append("\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                            continue;
                        }
                        IParsedExpression targetParsedExpression = null;
                        String sourceFormulaKey = "";
                        try {
                            String formula = parsedExpression.getFormula(fmlenv.getQueryContext(), fmlenv.getFormulaShowInfo());
                            sourceFormulaKey = parsedExpression.getSource().getId() + "|" + formula;
                            sourceFormulaKey = sourceFormulaKey.replace(" ", "");
                            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u516c\u5f0fKey:").append(sourceFormulaKey).append("\u2014\u2014").append("\n");
                            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u8bf4\u660e\u6240\u5c5e\u516c\u5f0fCODE:").append(parsedExpression.getSource().getCode()).append("|").append("\u516c\u5f0f\u5185\u5bb9:").append(formula).append("\u2014\u2014").append("\n");
                            if (fmlenv.getFormulaMap().containsKey(sourceFormulaKey)) {
                                FormulaMappingDefine formulaMapping = fmlenv.getFormulaMap().get(sourceFormulaKey);
                                String targetFormulaKey = formulaMapping.getTargetKey() + "|" + formulaMapping.getTargetExpression();
                                targetFormulaKey = targetFormulaKey.replace(" ", "");
                                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0fkey:").append(targetFormulaKey).append("\n");
                                if (fmlenv.getTargetFormulaMap().containsKey(targetFormulaKey)) {
                                    targetParsedExpression = fmlenv.getTargetFormulaMap().get(targetFormulaKey);
                                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                                } else {
                                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                                    logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f").append("\n");
                                }
                            } else {
                                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0fkey\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                                logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f").append("\n");
                            }
                        }
                        catch (InterpretException e) {
                            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                        }
                        if (targetParsedExpression == null) {
                            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0fkey\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f").append("\n");
                            continue;
                        }
                        FormulaMappingDefine formulaMapping = fmlenv.getFormulaMap().get(sourceFormulaKey);
                        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f\u2014\u2014\u7b2c").append(++copyNum).append("\u6761\u2014\u2014").append("\n");
                        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0fCODE:").append(formulaMapping.getTargetCode()).append("|").append("\u516c\u5f0f\u5185\u5bb9:").append(formulaMapping.getTargetExpression()).append("\n");
                        if (fromFormulaCheckDes.getGlobRow() != parsedExpression.getRealExpression().getWildcardRow() || fromFormulaCheckDes.getGlobCol() != parsedExpression.getRealExpression().getWildcardCol()) {
                            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5f53\u524d\u6765\u6e90\u8bf4\u660e\u6570\u636e\u4e0a\u7684\u516c\u5f0f\u884c\u5217\u53f7\u4e0e\u516c\u5f0f\u53c2\u6570\u4e0d\u5339\u914d\u65e0\u6cd5\u540c\u6b65\n");
                            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5f53\u524d\u6765\u6e90\u8bf4\u660e\u6570\u636e\u4e0a\u7684\u516c\u5f0f\u884c\u5217\u53f7\u4e0e\u516c\u5f0f\u53c2\u6570\u4e0d\u5339\u914d\u65e0\u6cd5\u540c\u6b65\n");
                            continue;
                        }
                        CheckDesObj targetFormulaCheckDes = new CheckDesObj();
                        targetFormulaCheckDes.setFormulaSchemeKey(targetFormulaSchemeKey);
                        String formKey = targetParsedExpression.getFormKey();
                        targetFormulaCheckDes.setFormKey(StringUtils.isEmpty((String)formKey) ? "00000000-0000-0000-0000-000000000000" : formKey);
                        targetFormulaCheckDes.setFormulaExpressionKey(targetParsedExpression.getKey());
                        targetFormulaCheckDes.setFormulaCode(targetParsedExpression.getSource().getCode());
                        targetFormulaCheckDes.setGlobRow(targetParsedExpression.getRealExpression().getWildcardRow());
                        targetFormulaCheckDes.setGlobCol(targetParsedExpression.getRealExpression().getWildcardCol());
                        targetFormulaCheckDes.setFloatId(fromFormulaCheckDes.getFloatId());
                        Map<String, DimensionValue> dimStrDimension = CopyDesServiceImpl.getTargetDimensionSet(dimensionValueMap, fromFormulaCheckDes.getDimensionSet(), sourceDimNames);
                        targetFormulaCheckDes.setDimensionSet(dimStrDimension);
                        CheckDescription descriptionInfo = new CheckDescription();
                        descriptionInfo.setDescription(fromFormulaCheckDes.getCheckDescription().getDescription());
                        if (copyParam.isUpdateUserTime()) {
                            descriptionInfo.setUpdateTime(curTime);
                            descriptionInfo.setUserId(userId);
                            descriptionInfo.setUserNickName(userNickName);
                        } else {
                            descriptionInfo.setUpdateTime(fromFormulaCheckDes.getCheckDescription().getUpdateTime());
                            descriptionInfo.setUserId(fromFormulaCheckDes.getCheckDescription().getUserId());
                            descriptionInfo.setUserNickName(fromFormulaCheckDes.getCheckDescription().getUserNickName());
                        }
                        targetFormulaCheckDes.setCheckDescription(descriptionInfo);
                        if (this.supported(targetFormulaCheckDes)) {
                            targetCheckDesList.add(targetFormulaCheckDes);
                            continue;
                        }
                        if (copyParam.getUnsupportedDesHandler() == null) continue;
                        ++unsupportedCount;
                        if (unsupportedDstDesMap.containsKey(fromFormulaCheckDes.getRecordId())) {
                            ((List)unsupportedDstDesMap.get(fromFormulaCheckDes.getRecordId())).add(new CheckDesFmlObj(targetFormulaCheckDes, targetParsedExpression));
                            continue;
                        }
                        unsupportedSrcDes.add(new CheckDesFmlObj(fromFormulaCheckDes, parsedExpression));
                        ArrayList<CheckDesFmlObj> checkDesFmlObjs = new ArrayList<CheckDesFmlObj>(3);
                        checkDesFmlObjs.add(new CheckDesFmlObj(targetFormulaCheckDes, targetParsedExpression));
                        unsupportedDstDesMap.put(fromFormulaCheckDes.getRecordId(), checkDesFmlObjs);
                    }
                }
                if (!targetCheckDesList.isEmpty()) {
                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
                    logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
                    CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
                    CheckDesQueryParam queryInfo = new CheckDesQueryParam();
                    ArrayList<String> fmlSchemeKeys = new ArrayList<String>();
                    fmlSchemeKeys.add(targetFormulaSchemeKey);
                    queryInfo.setFormulaSchemeKey(fmlSchemeKeys);
                    DimensionCollection targetDimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(targetDimensionSet, targetFormSchemeKey);
                    queryInfo.setDimensionCollection(targetDimensionCollection);
                    checkDesBatchSaveObj.setCheckDesQueryParam(queryInfo);
                    checkDesBatchSaveObj.setCheckDesObjs(targetCheckDesList);
                    this.checkErrorDescriptionService.batchSaveFormulaCheckDes(checkDesBatchSaveObj);
                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58").append(targetCheckDesList.size()).append("\u6761\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
                    logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58").append(targetCheckDesList.size()).append("\u6761\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
                } else {
                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u53ef\u8fc1\u79fb\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
                    logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u53ef\u8fc1\u79fb\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
                }
                if (!unsupportedSrcDes.isEmpty() && copyParam.getUnsupportedDesHandler() != null) {
                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u540c\u6b65\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
                    logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u540c\u6b65\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
                    HandleParam handleParam = new HandleParam();
                    handleParam.setDstFmlSchemeKey(targetFormulaSchemeKey);
                    handleParam.setDstFormSchemeKey(targetFormSchemeKey);
                    handleParam.setSrcFmlSchemeKey(sourceFormulaSchemeKey);
                    handleParam.setSrcFormSchemeKey(sourceFormSchemeKey);
                    handleParam.setUnsupportedSrcDes(unsupportedSrcDes);
                    handleParam.setUnsupportedDstDesMap(unsupportedDstDesMap);
                    handleParam.setUpdateUserTime(copyParam.isUpdateUserTime());
                    int handleNum = copyParam.getUnsupportedDesHandler().handleUnsupportedDes(handleParam);
                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u540c\u6b65\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5904\u7406").append(unsupportedCount).append("\u6761\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58").append(handleNum).append("\u6761\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
                    logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u540c\u6b65\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5904\u7406").append(unsupportedCount).append("\u6761\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
                    logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58").append(handleNum).append("\u6761\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
                }
            } else {
                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u6570\u636e\u6216\u76ee\u6807\u7ef4\u5ea6\u4e3a\u7a7a\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
                logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u6570\u636e\u6216\u76ee\u6807\u7ef4\u5ea6\u4e3a\u7a7a\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            }
        } else {
            CopyEnv copyEnv = new CopyEnv(tarTask, targetFormScheme, tarFormulaScheme, srcTask, srcFormScheme, sourceFormulaSchemeDefine, copyParam);
            copyEnv.setCurTime(curTime);
            copyEnv.setUserId(userId);
            copyEnv.setUserNickName(userNickName);
            this.copyDesByMapping(copyCheckDesLog, logText, fmlenv, copyEnv, sourceCheckDesList);
        }
        this.appendTimeLog(copyCheckDesLog, logText, startTime);
        copyDesResult.setSysLogText(copyCheckDesLog.toString());
        copyDesResult.setUsrLogText(logText.toString());
        return copyDesResult;
    }

    private void appendTimeLog(StringBuilder sysLogText, StringBuilder usrLogText, String startTime) {
        String endTime = DATETIME_FORMAT_ZONE.format(ZonedDateTime.now());
        sysLogText.append("\u5f00\u59cb\u65f6\u95f4: ").append(startTime).append("\n");
        sysLogText.append("\u7ed3\u675f\u65f6\u95f4: ").append(endTime).append("\n");
        usrLogText.append("\u5f00\u59cb\u65f6\u95f4: ").append(startTime).append("\n");
        usrLogText.append("\u7ed3\u675f\u65f6\u95f4: ").append(endTime).append("\n");
    }

    private boolean supported(CheckDesObj checkDesObj) {
        DimensionValue bizKeyOrder = (DimensionValue)checkDesObj.getDimensionSet().get("ID");
        return bizKeyOrder == null || StringUtils.isEmpty((String)bizKeyOrder.getValue()) || "null".equals(bizKeyOrder.getValue());
    }

    private static Map<String, List<Map<String, DimensionValue>>> getMasterKeyGroup(List<Map<String, DimensionValue>> targetDimList, List<String> sameMasterDimNames) {
        return targetDimList.stream().collect(Collectors.groupingBy(o -> CopyDesServiceImpl.getMasterKeyStr(sameMasterDimNames, o)));
    }

    private static String getMasterKeyStr(List<String> sameMasterDimNames, Map<String, DimensionValue> dimensionValueMap) {
        StringBuilder masterKeyStr = new StringBuilder();
        for (String masterDimName : sameMasterDimNames) {
            masterKeyStr.append(dimensionValueMap.get(masterDimName).getValue()).append(";");
        }
        return masterKeyStr.toString();
    }

    private static List<String> getSameMasterDimNames(List<Map<String, DimensionValue>> targetDimList, List<CheckDesObj> sourceCheckDesList) {
        ArrayList<String> masterDimNames = new ArrayList<String>();
        Map<String, DimensionValue> dim0 = targetDimList.get(0);
        CheckDesObj checkDesObj0 = sourceCheckDesList.get(0);
        for (Map.Entry<String, DimensionValue> e : dim0.entrySet()) {
            String dimName = e.getKey();
            if (!checkDesObj0.getDimensionSet().containsKey(dimName)) continue;
            masterDimNames.add(dimName);
        }
        return masterDimNames;
    }

    private FmlEnv getFmlEnv(StringBuilder copyCheckDesLog, StringBuilder logText, String targetFormSchemeKey, String targetFormulaSchemeKey, List<FormulaMappingDefine> formulaMappings) {
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, targetFormSchemeKey);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        QueryContext queryContext = null;
        try {
            queryContext = new QueryContext(executorContext, null);
        }
        catch (ParseException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        FormulaShowInfo formulaShowInfo = executorContext.getFormulaShowInfo();
        Map<String, IParsedExpression> targetFormulaMap = this.getTargetFormulaMap(copyCheckDesLog, logText, targetFormulaSchemeKey, queryContext, formulaShowInfo);
        Map<String, FormulaMappingDefine> formulaMap = CopyDesServiceImpl.getFmlMappingMapBySrc(copyCheckDesLog, logText, formulaMappings);
        return new FmlEnv(queryContext, formulaShowInfo, targetFormulaMap, formulaMap);
    }

    private void copyDesByMapping(StringBuilder copyCheckDesLog, StringBuilder logText, FmlEnv fmlEnv, CopyEnv copyEnv, List<CheckDesObj> sourceCheckDesList) {
        copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u5f80\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u8fc1\u79fb\u2014\u2014").append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u5ba1\u6838\u51fa\u9519\u8bf4\u660e\u5f80\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u8fc1\u79fb\u2014\u2014").append("\n");
        int i = 0;
        int findFmlCount = 0;
        List<String> sourceDimNames = this.getSourceDimNames(copyEnv.getSrcFormScheme().getKey());
        ArrayList<CheckDesObj> tarCheckDesList = new ArrayList<CheckDesObj>();
        ArrayList<CheckDesFmlObj> unsupportedSrcDes = new ArrayList<CheckDesFmlObj>();
        HashMap<String, List<CheckDesFmlObj>> unsupportedDstDesMap = new HashMap<String, List<CheckDesFmlObj>>();
        int unsupportedCount = 0;
        HashMap<String, Set<String>> targetDimCartesian = new HashMap<String, Set<String>>();
        FormulaFinder srcFormulaFinder = new FormulaFinder(this.formulaRunTimeController, copyEnv.getSrcFormulaScheme().getKey());
        for (CheckDesObj fromFormulaCheckDes : sourceCheckDesList) {
            ++i;
            String expressionKey = fromFormulaCheckDes.getFormulaExpressionKey();
            IParsedExpression parsedExpression = null;
            try {
                parsedExpression = srcFormulaFinder.findExpression(expressionKey.substring(0, 36), fromFormulaCheckDes.getGlobRow(), fromFormulaCheckDes.getGlobCol());
            }
            catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
            if (parsedExpression == null) {
                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u6e90\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u516c\u5f0f\uff0ckey:").append(expressionKey).append(",\u5ba1\u6838\u51fa\u9519\u8bf4\u660e:").append(fromFormulaCheckDes.getCheckDescription().getDescription()).append("\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u6e90\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u516c\u5f0f:").append(expressionKey).append("-").append(fromFormulaCheckDes.getGlobRow()).append("-").append(fromFormulaCheckDes.getGlobCol()).append(",\u5ba1\u6838\u51fa\u9519\u8bf4\u660e:").append(fromFormulaCheckDes.getCheckDescription().getDescription()).append("\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                continue;
            }
            IParsedExpression targetParsedExpression = null;
            String sourceFormulaKey = "";
            try {
                String formula = parsedExpression.getFormula(fmlEnv.getQueryContext(), fmlEnv.getFormulaShowInfo());
                sourceFormulaKey = parsedExpression.getSource().getId() + "|" + formula;
                sourceFormulaKey = sourceFormulaKey.replace(" ", "");
                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u516c\u5f0fKey:").append(sourceFormulaKey).append("\u2014\u2014").append("\n");
                logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6e90\u8bf4\u660e\u6240\u5c5e\u516c\u5f0fCODE:").append(parsedExpression.getSource().getCode()).append("|").append("\u516c\u5f0f\u5185\u5bb9:").append(formula).append("\u2014\u2014").append("\n");
                if (fmlEnv.getFormulaMap().containsKey(sourceFormulaKey)) {
                    FormulaMappingDefine formulaMapping = fmlEnv.getFormulaMap().get(sourceFormulaKey);
                    String targetFormulaKey = formulaMapping.getTargetKey() + "|" + formulaMapping.getTargetExpression();
                    targetFormulaKey = targetFormulaKey.replace(" ", "");
                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0fkey:").append(targetFormulaKey).append("\n");
                    if (fmlEnv.getTargetFormulaMap().containsKey(targetFormulaKey)) {
                        targetParsedExpression = fmlEnv.getTargetFormulaMap().get(targetFormulaKey);
                        copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                    } else {
                        copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f").append("\n");
                    }
                } else {
                    copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0fkey\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                    logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f").append("\n");
                }
            }
            catch (InterpretException e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (targetParsedExpression == null) {
                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f").append("\n");
                continue;
            }
            FormulaMappingDefine formulaMapping = fmlEnv.getFormulaMap().get(sourceFormulaKey);
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0f\u2014\u2014\u7b2c").append(++findFmlCount).append("\u6761\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u627e\u5230\u5bf9\u5e94\u7684\u76ee\u6807\u516c\u5f0fCODE:").append(formulaMapping.getTargetCode()).append("|").append("\u516c\u5f0f\u5185\u5bb9:").append(formulaMapping.getTargetExpression()).append("\n");
            if (fromFormulaCheckDes.getGlobRow() != parsedExpression.getRealExpression().getWildcardRow() || fromFormulaCheckDes.getGlobCol() != parsedExpression.getRealExpression().getWildcardCol()) {
                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5f53\u524d\u6765\u6e90\u8bf4\u660e\u6570\u636e\u4e0a\u7684\u516c\u5f0f\u884c\u5217\u53f7\u4e0e\u516c\u5f0f\u53c2\u6570\u4e0d\u5339\u914d\u65e0\u6cd5\u540c\u6b65\n");
                logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5f53\u524d\u6765\u6e90\u8bf4\u660e\u6570\u636e\u4e0a\u7684\u516c\u5f0f\u884c\u5217\u53f7\u4e0e\u516c\u5f0f\u53c2\u6570\u4e0d\u5339\u914d\u65e0\u6cd5\u540c\u6b65\n");
                continue;
            }
            DimensionValueSet srcMasterKey = fromFormulaCheckDes.getDimensionValueSet(sourceDimNames);
            DimensionCombination srcCombination = new DimensionCombinationBuilder(srcMasterKey).getCombination();
            List<DimensionCombination> targetMasterKey = copyEnv.getCopyParam().getDimMappingProvider().getTargetMasterKey(srcCombination);
            if (CollectionUtils.isEmpty(targetMasterKey)) {
                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u627e\u5230").append(fromFormulaCheckDes.getRecordId()).append("-").append(srcCombination).append("\u6620\u5c04\u7684\u7ef4\u5ea6\u2014\u2014\u7b2c").append(i).append("\u6761\u2014\u2014").append("\n");
                continue;
            }
            for (DimensionCombination tarDim : targetMasterKey) {
                this.addDim(targetDimCartesian, tarDim);
                CheckDesObj targetFormulaCheckDes = new CheckDesObj();
                targetFormulaCheckDes.setFormulaSchemeKey(copyEnv.getTargetFormulaScheme().getKey());
                String formKey = targetParsedExpression.getFormKey();
                targetFormulaCheckDes.setFormKey(StringUtils.isEmpty((String)formKey) ? "00000000-0000-0000-0000-000000000000" : formKey);
                targetFormulaCheckDes.setFormulaExpressionKey(targetParsedExpression.getKey());
                targetFormulaCheckDes.setFormulaCode(targetParsedExpression.getSource().getCode());
                targetFormulaCheckDes.setGlobRow(targetParsedExpression.getRealExpression().getWildcardRow());
                targetFormulaCheckDes.setGlobCol(targetParsedExpression.getRealExpression().getWildcardCol());
                targetFormulaCheckDes.setFloatId(fromFormulaCheckDes.getFloatId());
                Map<String, DimensionValue> dimStrDimension = CopyDesServiceImpl.getTargetDimensionSet(DimensionValueSetUtil.getDimensionSet((DimensionValueSet)tarDim.toDimensionValueSet()), fromFormulaCheckDes.getDimensionSet(), sourceDimNames);
                targetFormulaCheckDes.setDimensionSet(dimStrDimension);
                CheckDescription descriptionInfo = new CheckDescription();
                descriptionInfo.setDescription(fromFormulaCheckDes.getCheckDescription().getDescription());
                if (copyEnv.getCopyParam().isUpdateUserTime()) {
                    descriptionInfo.setUpdateTime(copyEnv.getCurTime());
                    descriptionInfo.setUserId(copyEnv.getUserId());
                    descriptionInfo.setUserNickName(copyEnv.getUserNickName());
                } else {
                    descriptionInfo.setUpdateTime(fromFormulaCheckDes.getCheckDescription().getUpdateTime());
                    descriptionInfo.setUserId(fromFormulaCheckDes.getCheckDescription().getUserId());
                    descriptionInfo.setUserNickName(fromFormulaCheckDes.getCheckDescription().getUserNickName());
                }
                targetFormulaCheckDes.setCheckDescription(descriptionInfo);
                if (this.supported(targetFormulaCheckDes)) {
                    tarCheckDesList.add(targetFormulaCheckDes);
                    continue;
                }
                if (copyEnv.getCopyParam().getUnsupportedDesHandler() == null) continue;
                ++unsupportedCount;
                if (unsupportedDstDesMap.containsKey(fromFormulaCheckDes.getRecordId())) {
                    ((List)unsupportedDstDesMap.get(fromFormulaCheckDes.getRecordId())).add(new CheckDesFmlObj(targetFormulaCheckDes, targetParsedExpression));
                    continue;
                }
                unsupportedSrcDes.add(new CheckDesFmlObj(fromFormulaCheckDes, parsedExpression));
                ArrayList<CheckDesFmlObj> checkDesFmlObjs = new ArrayList<CheckDesFmlObj>(3);
                checkDesFmlObjs.add(new CheckDesFmlObj(targetFormulaCheckDes, targetParsedExpression));
                unsupportedDstDesMap.put(fromFormulaCheckDes.getRecordId(), checkDesFmlObjs);
            }
        }
        if (!tarCheckDesList.isEmpty()) {
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
            CheckDesBatchSaveObj checkDesBatchSaveObj = new CheckDesBatchSaveObj();
            CheckDesQueryParam queryInfo = new CheckDesQueryParam();
            ArrayList<String> fmlSchemeKeys = new ArrayList<String>();
            fmlSchemeKeys.add(copyEnv.getTargetFormulaScheme().getKey());
            queryInfo.setFormulaSchemeKey(fmlSchemeKeys);
            DimensionCollectionBuilder dimensionCollectionBuilder = new DimensionCollectionBuilder();
            for (Map.Entry e : targetDimCartesian.entrySet()) {
                dimensionCollectionBuilder.setValue((String)e.getKey(), ((Set)e.getValue()).toArray());
            }
            queryInfo.setDimensionCollection(dimensionCollectionBuilder.getCollection());
            checkDesBatchSaveObj.setCheckDesQueryParam(queryInfo);
            checkDesBatchSaveObj.setCheckDesObjs(tarCheckDesList);
            this.checkErrorDescriptionService.batchSaveFormulaCheckDes(checkDesBatchSaveObj);
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58").append(tarCheckDesList.size()).append("\u6761\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58").append(tarCheckDesList.size()).append("\u6761\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
        } else {
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u53ef\u8fc1\u79fb\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u6ca1\u6709\u53ef\u8fc1\u79fb\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
        }
        if (!unsupportedSrcDes.isEmpty() && copyEnv.getCopyParam().getUnsupportedDesHandler() != null) {
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u540c\u6b65\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u540c\u6b65\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
            HandleParam handleParam = new HandleParam();
            handleParam.setDstFmlSchemeKey(copyEnv.getTargetFormulaScheme().getKey());
            handleParam.setDstFormSchemeKey(copyEnv.getTargetFormScheme().getKey());
            handleParam.setSrcFmlSchemeKey(copyEnv.getSrcFormulaScheme().getKey());
            handleParam.setSrcFormSchemeKey(copyEnv.getSrcFormScheme().getKey());
            handleParam.setUnsupportedSrcDes(unsupportedSrcDes);
            handleParam.setUnsupportedDstDesMap(unsupportedDstDesMap);
            handleParam.setUpdateUserTime(copyEnv.getCopyParam().isUpdateUserTime());
            int handleNum = copyEnv.getCopyParam().getUnsupportedDesHandler().handleUnsupportedDes(handleParam);
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u540c\u6b65\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5904\u7406").append(unsupportedCount).append("\u6761\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58").append(handleNum).append("\u6761\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u540c\u6b65\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u5904\u7406").append(unsupportedCount).append("\u6761\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014").append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u4fdd\u5b58").append(handleNum).append("\u6761\u5141\u8bb8\u91cd\u7801\u6570\u636e\u7684\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u7ed3\u675f\u2014\u2014").append("\n");
        }
    }

    private void addDim(Map<String, Set<String>> targetDimCartesian, DimensionCombination tarDim) {
        for (FixedDimensionValue fixedDimensionValue : tarDim) {
            if (targetDimCartesian.containsKey(fixedDimensionValue.getName())) {
                targetDimCartesian.get(fixedDimensionValue.getName()).add(String.valueOf(fixedDimensionValue.getValue()));
                continue;
            }
            HashSet<String> values = new HashSet<String>();
            values.add(String.valueOf(fixedDimensionValue.getValue()));
            targetDimCartesian.put(fixedDimensionValue.getName(), values);
        }
    }

    private List<CheckDesObj> getCheckDesObjs(String sourceFormulaSchemeKey, String sourceFormSchemeKey, String sourceTaskKey, String targetTaskKey, Map<String, DimensionValue> targetDimensionSet, IDimMappingProvider dimMappingProvider) {
        DimensionCollection sourceDimensionCollection;
        CheckDesQueryParam checkDesQueryParam = new CheckDesQueryParam();
        if (dimMappingProvider == null) {
            if (this.copyDesDimensionService != null && this.srcDimLost(sourceFormSchemeKey, targetDimensionSet)) {
                Map<String, DimensionValue> copySourceDimensionSet = this.getCopySourceDimensionSet(targetTaskKey, sourceTaskKey, targetDimensionSet);
                sourceDimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(copySourceDimensionSet, sourceFormSchemeKey);
            } else {
                sourceDimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(targetDimensionSet, sourceFormSchemeKey);
            }
        } else {
            sourceDimensionCollection = dimMappingProvider.getQuerySrcDim();
        }
        ArrayList<String> formulaSchemeKeys = new ArrayList<String>();
        formulaSchemeKeys.add(sourceFormulaSchemeKey);
        checkDesQueryParam.setFormulaSchemeKey(formulaSchemeKeys);
        checkDesQueryParam.setDimensionCollection(sourceDimensionCollection);
        return this.checkErrorDescriptionService.queryFormulaCheckDes(checkDesQueryParam);
    }

    @NotNull
    private Map<String, IParsedExpression> getTargetFormulaMap(StringBuilder copyCheckDesLog, StringBuilder logText, String targetFormulaSchemeKey, QueryContext queryContext, FormulaShowInfo formulaShowInfo) {
        List parsedExpressions = this.formulaRunTimeController.getParsedExpressionByForm(targetFormulaSchemeKey, null, DataEngineConsts.FormulaType.CHECK);
        copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u67e5\u8be2\u76ee\u6807\u516c\u5f0fMap\u2014\u2014").append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u67e5\u8be2\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u4e0b\u7684\u516c\u5f0f\u4fe1\u606f\u2014\u2014").append("\n");
        HashMap<String, IParsedExpression> targetFormulaMap = new HashMap<String, IParsedExpression>();
        try {
            for (IParsedExpression parsedExpression : parsedExpressions) {
                String formula = parsedExpression.getFormula(queryContext, formulaShowInfo);
                String targetFormulaKey = parsedExpression.getSource().getId() + "|" + formula;
                targetFormulaKey = targetFormulaKey.replace(" ", "");
                targetFormulaMap.put(targetFormulaKey, parsedExpression);
                copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u76ee\u6807\u516c\u5f0fKey:").append(targetFormulaKey).append("\n");
                logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u76ee\u6807\u516c\u5f0fCODE:").append(parsedExpression.getSource().getCode()).append("|").append("\u516c\u5f0f\u5185\u5bb9:").append(formula).append("\n");
            }
        }
        catch (InterpretException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return targetFormulaMap;
    }

    private static Map<String, FormulaMappingDefine> getFmlMappingMapBySrc(StringBuilder copyCheckDesLog, StringBuilder logText, List<FormulaMappingDefine> formulaMappings) {
        HashMap<String, FormulaMappingDefine> formulaMap = new HashMap<String, FormulaMappingDefine>();
        copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u67e5\u8be2\u6e90\u516c\u5f0f\u65b9\u6848\u548c\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u7684\u516c\u5f0f\u5bf9\u5e94\u5173\u7cfb\u2014\u2014").append("\n");
        logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u67e5\u8be2\u6e90\u516c\u5f0f\u65b9\u6848\u548c\u76ee\u6807\u516c\u5f0f\u65b9\u6848\u7684\u516c\u5f0f\u6620\u5c04\u5173\u7cfb\u2014\u2014").append("\n");
        for (FormulaMappingDefine formulaMapping : formulaMappings) {
            if (StringUtils.isEmpty((String)formulaMapping.getSourceKey())) continue;
            String sourceFormulaKey = formulaMapping.getSourceKey() + "|" + formulaMapping.getSourceExpression();
            sourceFormulaKey = sourceFormulaKey.replace(" ", "");
            String targetFormulaKey = formulaMapping.getTargetKey() + "|" + formulaMapping.getTargetExpression();
            targetFormulaKey = targetFormulaKey.replace(" ", "");
            formulaMap.put(sourceFormulaKey, formulaMapping);
            copyCheckDesLog.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u516c\u5f0f\u5bf9\u5e94\u5173\u7cfb\u2014\u2014\u6e90\u516c\u5f0fKey:").append(sourceFormulaKey).append("\u2014\u2014\u76ee\u6807\u516c\u5f0fkey:").append(targetFormulaKey).append("\n");
            logText.append("\u2014\u2014\u590d\u5236\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u2014\u2014\u516c\u5f0f\u6620\u5c04\u5173\u7cfb\u2014\u2014\u6e90\u516c\u5f0fCODE:").append(formulaMapping.getSourceCode()).append("|").append("\u516c\u5f0f\u5185\u5bb9:").append(formulaMapping.getSourceExpression()).append("\u2014\u2014\u76ee\u6807\u516c\u5f0fCODE:").append(formulaMapping.getTargetCode()).append("|").append("\u516c\u5f0f\u5185\u5bb9:").append(formulaMapping.getTargetExpression()).append("\n");
        }
        return formulaMap;
    }

    private String getSourceFormulaSchemeKey(String targetFormulaSchemeKey, IFmlMappingProvider fmlMappingProvider) {
        return fmlMappingProvider == null ? this.formulaMappingController.querySourceFormulaSchemeKey(targetFormulaSchemeKey) : fmlMappingProvider.getSrcFmlScheme(targetFormulaSchemeKey);
    }

    private List<FormulaMappingDefine> getFormulaMappingDefines(String targetFormulaSchemeKey, IFmlMappingProvider fmlMappingProvider, String srcFormulaSchemeKey) {
        return fmlMappingProvider == null ? this.formulaMappingController.queryValidFormulaMappings(targetFormulaSchemeKey, srcFormulaSchemeKey) : fmlMappingProvider.getFormulaMapping(targetFormulaSchemeKey);
    }

    private List<CheckDesObj> filter(List<CheckDesObj> desObjs, String taskKey, String formSchemeKey, DimensionCollection dimensionCollection) {
        HashSet formKeys = new HashSet();
        desObjs.forEach(o -> formKeys.add(o.getFormKey()));
        formKeys.remove("00000000-0000-0000-0000-000000000000");
        if (formKeys.size() == 0) {
            return desObjs;
        }
        IDataAccessFormService dataAccessFormService = this.dataAccessServiceProvider.getDataAccessFormService();
        AccessFormParam accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(dimensionCollection);
        accessFormParam.setTaskKey(taskKey);
        accessFormParam.setFormSchemeKey(formSchemeKey);
        accessFormParam.setFormKeys(new ArrayList(formKeys));
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_WRITE);
        DimensionAccessFormInfo dimensionAccessFormInfo = dataAccessFormService.getBatchAccessForms(accessFormParam);
        List accessForms = dimensionAccessFormInfo.getAccessForms();
        ArrayList<CheckDesObj> result = new ArrayList<CheckDesObj>();
        HashSet<String> desId = new HashSet<String>();
        for (DimensionAccessFormInfo.AccessFormInfo accessForm : accessForms) {
            List accessFormFormKeys = accessForm.getFormKeys();
            Map dimensions = accessForm.getDimensions();
            HashMap accessDims = new HashMap();
            dimensions.forEach((k, v) -> accessDims.put(k, Arrays.stream(v.getValue().split(";")).collect(Collectors.toSet())));
            for (CheckDesObj desObj : desObjs) {
                if (!"00000000-0000-0000-0000-000000000000".equals(desObj.getFormKey()) && !accessFormFormKeys.contains(desObj.getFormKey())) continue;
                boolean pass = true;
                for (Map.Entry entry : accessDims.entrySet()) {
                    String desDimV = ((DimensionValue)desObj.getDimensionSet().get(entry.getKey())).getValue();
                    if (((Set)entry.getValue()).contains(desDimV)) continue;
                    pass = false;
                    break;
                }
                if (!pass || desId.contains(desObj.getRecordId())) continue;
                result.add(desObj);
                desId.add(desObj.getRecordId());
            }
        }
        return result;
    }

    private List<String> getSourceDimNames(String sourceFormSchemeKey) {
        ArrayList<String> result = new ArrayList<String>();
        List entityList = this.jtableParamService.getEntityList(sourceFormSchemeKey);
        entityList.forEach(o -> result.add(o.getDimensionName()));
        if (this.formSchemeService.enableAdjustPeriod(sourceFormSchemeKey)) {
            result.add("ADJUST");
        }
        return result;
    }

    private static Map<String, DimensionValue> getTargetDimensionSet(Map<String, DimensionValue> targetDimensionSet, Map<String, DimensionValue> sourceDimensionSet, List<String> sourceDimNames) {
        HashMap<String, DimensionValue> dimStrDimension = new HashMap<String, DimensionValue>(sourceDimensionSet);
        for (String sourceDimName : sourceDimNames) {
            dimStrDimension.remove(sourceDimName);
        }
        dimStrDimension.putAll(targetDimensionSet);
        return dimStrDimension;
    }

    private boolean validDim(Map<String, DimensionValue> sourceDimensionSet, Map<String, DimensionValue> targetDimensionSet) {
        for (Map.Entry<String, DimensionValue> source : sourceDimensionSet.entrySet()) {
            String key = source.getKey();
            DimensionValue value = source.getValue();
            if (!targetDimensionSet.containsKey(key) || targetDimensionSet.get(key).equals((Object)value)) continue;
            return false;
        }
        return true;
    }

    private Map<String, DimensionValue> getCopySourceDimensionSet(String targetTaskKey, String sourceTaskKey, Map<String, DimensionValue> targetDimensionSet) {
        HashMap<String, DimensionValue> sourceDimensionSet = new HashMap<String, DimensionValue>();
        HashMap<String, List<String>> targetDimValues = new HashMap<String, List<String>>();
        for (Map.Entry<String, DimensionValue> entry : targetDimensionSet.entrySet()) {
            Object split;
            String key = entry.getKey();
            DimensionValue value = entry.getValue();
            String dimValue = value.getValue();
            List<Object> dimValueList = new ArrayList();
            if (StringUtils.isNotEmpty((String)dimValue) && ((String[])(split = dimValue.split(";"))).length > 0) {
                dimValueList = Arrays.asList(split);
            }
            targetDimValues.put(key, dimValueList);
        }
        List<DimensionValueSet> sourceDimensionValueList = this.copyDesDimensionService.getSourceDimensionValueList(targetTaskKey, sourceTaskKey, targetDimValues);
        if (sourceDimensionValueList != null && !sourceDimensionValueList.isEmpty()) {
            HashSet<String> dimNameSet = new HashSet<String>();
            DimensionValueSet dimensionValueSet = sourceDimensionValueList.get(0);
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                dimNameSet.add(dimensionValueSet.getName(i));
            }
            for (String dimName : dimNameSet) {
                HashSet<String> dimValue = new HashSet<String>();
                for (DimensionValueSet dimensionValueSet2 : sourceDimensionValueList) {
                    Object value = dimensionValueSet2.getValue(dimName);
                    if (value instanceof String) {
                        dimValue.add((String)value);
                        continue;
                    }
                    if (!(value instanceof List)) continue;
                    List list = (List)value;
                    for (Object o : list) {
                        dimValue.add((String)o);
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (String s : dimValue) {
                    stringBuilder.append(s);
                    stringBuilder.append(";");
                }
                if (dimValue.size() > 0) {
                    stringBuilder.setLength(stringBuilder.length() - 1);
                }
                DimensionValue dimensionValue = new DimensionValue();
                dimensionValue.setName(dimName);
                dimensionValue.setValue(stringBuilder.toString());
                sourceDimensionSet.put(dimName, dimensionValue);
            }
        }
        return sourceDimensionSet;
    }

    private List<Map<String, DimensionValue>> splitDimension(Map<String, DimensionValue> dimensionSet, String formSchemeKey) {
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionSet, formSchemeKey);
        ArrayList<Map<String, DimensionValue>> result = new ArrayList<Map<String, DimensionValue>>();
        for (DimensionValueSet dimensionValueSet : dimensionCollection) {
            HashMap<String, DimensionValue> o = new HashMap<String, DimensionValue>();
            for (int i = 0; i < dimensionValueSet.size(); ++i) {
                DimensionValue v = new DimensionValue();
                v.setValue(dimensionValueSet.getValue(i).toString());
                v.setName(dimensionValueSet.getName(i));
                o.put(dimensionValueSet.getName(i), v);
            }
            if (CollectionUtils.isEmpty(o)) continue;
            result.add(o);
        }
        return result;
    }

    private boolean srcDimLost(String srcFormSchemeKey, Map<String, DimensionValue> targetDimensionSet) {
        if (StringUtils.isNotEmpty((String)srcFormSchemeKey)) {
            Boolean enableAdjustPeriod = this.formSchemeService.enableAdjustPeriod(srcFormSchemeKey);
            List entityList = this.jtableParamService.getEntityList(srcFormSchemeKey);
            Set<String> keySet = targetDimensionSet.keySet();
            for (EntityViewData entityViewData : entityList) {
                if (keySet.contains(entityViewData.getDimensionName())) continue;
                return true;
            }
            return enableAdjustPeriod != false && !keySet.contains("ADJUST");
        }
        return false;
    }

    private static class CopyEnv {
        private final TaskDefine targetTask;
        private final FormSchemeDefine targetFormScheme;
        private final FormulaSchemeDefine targetFormulaScheme;
        private final TaskDefine srcTask;
        private final FormSchemeDefine srcFormScheme;
        private final FormulaSchemeDefine srcFormulaScheme;
        private final CheckDesCopyParam copyParam;
        private Instant curTime;
        private String userId;
        private String userNickName;

        public CopyEnv(TaskDefine targetTask, FormSchemeDefine targetFormScheme, FormulaSchemeDefine targetFormulaScheme, TaskDefine srcTask, FormSchemeDefine srcFormScheme, FormulaSchemeDefine srcFormulaScheme, CheckDesCopyParam copyParam) {
            this.targetTask = targetTask;
            this.targetFormScheme = targetFormScheme;
            this.targetFormulaScheme = targetFormulaScheme;
            this.srcTask = srcTask;
            this.srcFormScheme = srcFormScheme;
            this.srcFormulaScheme = srcFormulaScheme;
            this.copyParam = copyParam;
        }

        public CheckDesCopyParam getCopyParam() {
            return this.copyParam;
        }

        public TaskDefine getTargetTask() {
            return this.targetTask;
        }

        public FormSchemeDefine getTargetFormScheme() {
            return this.targetFormScheme;
        }

        public FormulaSchemeDefine getTargetFormulaScheme() {
            return this.targetFormulaScheme;
        }

        public TaskDefine getSrcTask() {
            return this.srcTask;
        }

        public FormSchemeDefine getSrcFormScheme() {
            return this.srcFormScheme;
        }

        public FormulaSchemeDefine getSrcFormulaScheme() {
            return this.srcFormulaScheme;
        }

        public Instant getCurTime() {
            return this.curTime;
        }

        public void setCurTime(Instant curTime) {
            this.curTime = curTime;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserNickName() {
            return this.userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }
    }

    private static class FmlEnv {
        private final QueryContext queryContext;
        private final FormulaShowInfo formulaShowInfo;
        private final Map<String, IParsedExpression> targetFormulaMap;
        private final Map<String, FormulaMappingDefine> formulaMap;

        public FmlEnv(QueryContext queryContext, FormulaShowInfo formulaShowInfo, Map<String, IParsedExpression> targetFormulaMap, Map<String, FormulaMappingDefine> formulaMap) {
            this.queryContext = queryContext;
            this.formulaShowInfo = formulaShowInfo;
            this.targetFormulaMap = targetFormulaMap;
            this.formulaMap = formulaMap;
        }

        public QueryContext getQueryContext() {
            return this.queryContext;
        }

        public FormulaShowInfo getFormulaShowInfo() {
            return this.formulaShowInfo;
        }

        public Map<String, IParsedExpression> getTargetFormulaMap() {
            return this.targetFormulaMap;
        }

        public Map<String, FormulaMappingDefine> getFormulaMap() {
            return this.formulaMap;
        }
    }
}

