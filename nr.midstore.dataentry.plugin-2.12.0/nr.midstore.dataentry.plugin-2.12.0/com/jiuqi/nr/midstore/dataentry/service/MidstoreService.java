/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.data.access.common.AccessLevel$FormAccessLevel
 *  com.jiuqi.nr.data.access.param.AccessFormParam
 *  com.jiuqi.nr.data.access.param.DimensionAccessFormInfo$AccessFormInfo
 *  com.jiuqi.nr.data.access.service.IDataAccessServiceProvider
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.dataentry.internal.service.FormGroupProvider
 *  com.jiuqi.nr.dataentry.monitor.DataEntryAsyncProgressMonitor
 *  com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo
 *  com.jiuqi.nr.dataentry.service.IBatchCalculateService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo
 *  com.jiuqi.nr.jtable.service.IJtableEntityService
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  nr.midstore.core.definition.bean.MidstoreResultObject
 *  nr.midstore.core.definition.bean.MistoreWorkFailInfo
 *  nr.midstore.core.definition.bean.MistoreWorkFormInfo
 *  nr.midstore.core.definition.bean.MistoreWorkResultObject
 *  nr.midstore.core.definition.bean.MistoreWorkUnitInfo
 *  nr.midstore.core.definition.db.MidstoreException
 *  nr.midstore.core.work.service.IMidstoreExcuteGetService
 */
package com.jiuqi.nr.midstore.dataentry.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.Workflow;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.data.access.common.AccessLevel;
import com.jiuqi.nr.data.access.param.AccessFormParam;
import com.jiuqi.nr.data.access.param.DimensionAccessFormInfo;
import com.jiuqi.nr.data.access.service.IDataAccessServiceProvider;
import com.jiuqi.nr.dataentry.internal.service.FormGroupProvider;
import com.jiuqi.nr.dataentry.monitor.DataEntryAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.input.EntityQueryByKeyInfo;
import com.jiuqi.nr.jtable.service.IJtableEntityService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.midstore.dataentry.bean.MidstoreParam;
import com.jiuqi.nr.midstore.dataentry.bean.MidstoreReturnInfo;
import com.jiuqi.nr.midstore.dataentry.event.MidstorePullFinishEvent;
import com.jiuqi.nr.midstore.dataentry.service.IMidstoreService;
import com.jiuqi.util.StringUtils;
import java.lang.invoke.LambdaMetafactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import nr.midstore.core.definition.bean.MidstoreResultObject;
import nr.midstore.core.definition.bean.MistoreWorkFailInfo;
import nr.midstore.core.definition.bean.MistoreWorkFormInfo;
import nr.midstore.core.definition.bean.MistoreWorkResultObject;
import nr.midstore.core.definition.bean.MistoreWorkUnitInfo;
import nr.midstore.core.definition.db.MidstoreException;
import nr.midstore.core.work.service.IMidstoreExcuteGetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class MidstoreService
implements IMidstoreService {
    private static final Logger logger = LoggerFactory.getLogger(MidstoreService.class);
    private static final String EORROR_OTHER = "\u5176\u4ed6";
    @Autowired
    private IMidstoreExcuteGetService midstoreExcuteGetService;
    @Autowired
    FormGroupProvider formGroupProvider;
    @Autowired
    IDataAccessServiceProvider dataAccessServiceProvider;
    @Autowired
    IJtableEntityService jtableEntityService;
    @Autowired
    IJtableParamService jtableParamService;
    @Autowired
    private ApplicationContext applicationContext;
    @Resource
    IRunTimeViewController runtimeView;
    @Autowired
    private ITaskOptionController taskOptionController;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private Workflow workflow;
    @Autowired
    private IEntityMetaService entityMetaService;

    /*
     * Could not resolve type clashes
     * Unable to fully structure code
     */
    @Override
    public void midstorePull(MidstoreParam midstoreParam, AsyncTaskMonitor asyncTaskMonitor) {
        unitFormKeys = new HashMap<DimensionValueSet, List>();
        jtableContext = midstoreParam.getContext();
        dataFormAccess = this.dataAccessServiceProvider.getDataAccessFormService();
        formKeyStr = midstoreParam.getFormKeys();
        formKeys = new ArrayList<E>();
        if (org.springframework.util.StringUtils.hasLength(formKeyStr)) {
            formKeys = Arrays.asList(formKeyStr.split(";"));
        } else {
            formDefines = this.runtimeView.queryAllFormDefinesByTask(jtableContext.getTaskKey());
            formKeys = formDefines.stream().map((Function<FormDefine, String>)LambdaMetafactory.metafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, getKey(), (Lcom/jiuqi/nr/definition/facade/FormDefine;)Ljava/lang/String;)()).collect(Collectors.toList());
        }
        accessFormParam = new AccessFormParam();
        accessFormParam.setCollectionMasterKey(com.jiuqi.nr.data.access.util.DimensionValueSetUtil.buildDimensionCollection((Map)jtableContext.getDimensionSet(), (String)jtableContext.getFormSchemeKey()));
        accessFormParam.setTaskKey(jtableContext.getTaskKey());
        accessFormParam.setFormSchemeKey(jtableContext.getFormSchemeKey());
        accessFormParam.setFormKeys(formKeys);
        accessFormParam.setFormAccessLevel(AccessLevel.FormAccessLevel.FORM_DATA_WRITE);
        batchDimensionValueFormInfo = dataFormAccess.getBatchAccessForms(accessFormParam);
        acessFormInfos = batchDimensionValueFormInfo.getAccessForms();
        dimensionName = this.getDimensionName(midstoreParam.getContext().getTaskKey());
        unitKeys = new HashSet<String>();
        for (DimensionAccessFormInfo.AccessFormInfo accessFormInfo : acessFormInfos) {
            dimensions = accessFormInfo.getDimensions();
            accessFormKeys = accessFormInfo.getFormKeys();
            dimensionValueSetList = DimensionValueSetUtil.getDimensionValueSetList((Map)dimensions);
            for (DimensionValueSet dimension : dimensionValueSetList) {
                value = dimension.getValue(dimensionName);
                if (!unitKeys.contains(value.toString())) {
                    unitFormKeys.put(dimension, accessFormKeys);
                } else {
                    list = (List)unitFormKeys.get(dimension);
                    list.addAll(accessFormKeys);
                    unitFormKeys.put(dimension, list);
                }
                unitKeys.add(value.toString());
            }
        }
        noAccessForms = batchDimensionValueFormInfo.getNoAccessForms();
        mapper = new ObjectMapper();
        result = new MidstoreResultObject();
        result.setSuccess(true);
        if (!unitFormKeys.isEmpty()) {
            try {
                asyncProgressMonitor = new DataEntryAsyncProgressMonitor(asyncTaskMonitor, 0.8, 0.0);
                result = this.midstoreExcuteGetService.excuteGetDataByCode(midstoreParam.getMidstoreCode(), unitFormKeys, midstoreParam.isOverImport(), (AsyncTaskMonitor)asyncProgressMonitor);
                dataEntryAsyncProgressMonitor = new DataEntryAsyncProgressMonitor(asyncTaskMonitor, 0.2, 0.8);
                calculate = this.taskOptionController.getValue(jtableContext.getTaskKey(), "AUTOCALCULATE_AFTER_MIDSTOREPULL");
                if (!"1".equals(calculate)) ** GOTO lbl70
                batchCalculateInfo = new BatchCalculateInfo();
                batchCalculateInfo.setDimensionSet(jtableContext.getDimensionSet());
                batchCalculateInfo.setFormSchemeKey(jtableContext.getFormSchemeKey());
                batchCalculateInfo.setFormulaSchemeKey(jtableContext.getFormulaSchemeKey());
                batchCalculateInfo.setTaskKey(jtableContext.getTaskKey());
                batchCalculateInfo.setVariableMap(jtableContext.getVariableMap());
                formulas = new HashMap<String, ArrayList<E>>();
                for (String formKey : formKeys) {
                    formulas.put(formKey, new ArrayList<E>());
                }
                batchCalculateInfo.setFormulas(formulas);
                this.batchCalculateService.batchCalculateForm(batchCalculateInfo, (AsyncTaskMonitor)dataEntryAsyncProgressMonitor);
            }
            catch (Exception e) {
                MidstoreService.logger.error(e.getMessage());
                asyncTaskMonitor.error(e.getMessage(), (Throwable)e);
                return;
            }
        } else {
            result.setMessage("");
        }
lbl70:
        // 3 sources

        if (result.isSuccess()) {
            formScheme = this.runtimeView.getFormScheme(jtableContext.getFormSchemeKey());
            targetEntityInfo = this.jtableParamService.getDwEntity(formScheme.getKey());
            entityQueryByKeyInfo = new EntityQueryByKeyInfo();
            entityQueryByKeyInfo.setContext(jtableContext);
            entityQueryByKeyInfo.setEntityViewKey(targetEntityInfo.getKey());
            midstoreReturnInfo = new MidstoreReturnInfo();
            value = ((DimensionValue)jtableContext.getDimensionSet().get(targetEntityInfo.getDimensionName())).getValue();
            unitCount = value.split(";").length;
            if (value.equals("")) {
                taskDefine = this.runtimeView.queryTaskDefine(jtableContext.getTaskKey());
                allEntityKey = this.jtableEntityService.getAllEntityKey(taskDefine.getDw(), jtableContext.getDimensionSet(), jtableContext.getFormSchemeKey());
                unitCount = allEntityKey.size();
            }
            midstoreReturnInfo.setAllCount(unitCount);
            unAccessUnits = new HashMap<String, List<String>>();
            unAccessForms = new HashMap<String, List<String>>();
            failedUnitKeys = new HashSet<String>();
            for (Object noAccessForm : noAccessForms) {
                if (((DimensionValue)noAccessForm.getDimensions().get(targetEntityInfo.getDimensionName())).getValue().contains(noAccessForm.getFormKey())) {
                    unitKey = noAccessForm.getFormKey();
                    entityQueryByKeyInfo.setEntityKey(unitKey);
                    entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                    title = unitKey + " " + entityByKeyReturnInfo.getEntity().getTitle();
                    reason = noAccessForm.getReason();
                    formUnAccess /* !! */  = new ArrayList<String>();
                    if (unAccessUnits.containsKey(title)) {
                        formUnAccess /* !! */  = (List)unAccessUnits.get(title);
                    }
                    formUnAccess /* !! */ .add(title);
                    failedUnitKeys.add(unitKey);
                    unAccessUnits.put(reason, formUnAccess /* !! */ );
                    continue;
                }
                unitKey = ((DimensionValue)noAccessForm.getDimensions().get(targetEntityInfo.getDimensionName())).getValue();
                entityQueryByKeyInfo.setEntityKey(unitKey);
                entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                unitTitle = unitKey + " " + entityByKeyReturnInfo.getEntity().getTitle();
                reason = noAccessForm.getReason();
                if ("\u62a5\u8868\u4e0d\u7b26\u5408\u9002\u5e94\u6027\u6761\u4ef6".equals(reason)) continue;
                failedUnitKeys.add(unitKey);
                units = new ArrayList<E>();
                if (unAccessUnits.containsKey(reason) && (units = (List)unAccessUnits.get(reason)).contains(unitTitle)) break;
                form = this.jtableParamService.getReport(noAccessForm.getFormKey(), formScheme.getKey());
                if (this.workflow.queryStartType(jtableContext.getFormSchemeKey()) == WorkFlowType.ENTITY && reason.equals("\u8be5\u5de5\u4f5c\u6d41\u8282\u70b9\u4e0d\u53ef\u7f16\u8f91")) {
                    reason = "\u5355\u4f4d\u5df2\u4e0a\u62a5";
                    if (unAccessUnits.containsKey(reason)) {
                        units = (List)unAccessUnits.get(reason);
                    }
                    if (!units.contains(unitTitle)) {
                        units.add(unitTitle);
                    }
                    unAccessUnits.put(reason, units);
                    continue;
                }
                forms /* !! */  = new ArrayList<String>();
                if (unAccessForms.containsKey(unitTitle)) {
                    forms /* !! */  = (List)unAccessForms.get(unitTitle);
                }
                forms /* !! */ .add(form.getCode() + " " + form.getTitle() + " " + reason);
                unAccessForms.put(unitTitle, forms /* !! */ );
            }
            if (!result.getWorkResults().isEmpty()) {
                failInfoList = ((MistoreWorkResultObject)result.getWorkResults().get(0)).getFailInfoList();
                for (MistoreWorkFailInfo mistoreWorkFailInfo : failInfoList) {
                    message = mistoreWorkFailInfo.getMessage();
                    unitInfos = mistoreWorkFailInfo.getUnitInfos();
                    if (!message.equals("\u5176\u4ed6")) {
                        for (String unitKey : unitInfos.keySet()) {
                            unitInfo = (MistoreWorkUnitInfo)unitInfos.get(unitKey);
                            if (StringUtils.isEmpty((String)unitInfo.getUnitTitle())) {
                                entityQueryByKeyInfo.setEntityKey(unitInfo.getUnitCode());
                                entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                                unitInfo.setUnitTitle(entityByKeyReturnInfo.getEntity().getTitle());
                            }
                            unitTitle = unitInfo.getUnitCode() + " " + unitInfo.getUnitTitle();
                            unAccess = (ArrayList<String>)unAccessUnits.get(message);
                            if (unAccess == null) {
                                unAccess = new ArrayList<String>();
                                unAccess.add(unitTitle);
                                unAccessUnits.put(message, unAccess);
                                failedUnitKeys.add(unitInfo.getUnitCode());
                                continue;
                            }
                            ((List)unAccessUnits.get(message)).add(unitTitle);
                            failedUnitKeys.add(unitInfo.getUnitCode());
                        }
                        continue;
                    }
                    for (String unitKey : unitInfos.keySet()) {
                        unitInfo = (MistoreWorkUnitInfo)unitInfos.get(unitKey);
                        formInfos = unitInfo.getFormInfos();
                        forms = new ArrayList<E>();
                        if (StringUtils.isEmpty((String)unitInfo.getUnitTitle())) {
                            entityQueryByKeyInfo.setEntityKey(unitInfo.getUnitCode());
                            entityByKeyReturnInfo = this.jtableEntityService.queryEntityDataByKey(entityQueryByKeyInfo);
                            unitInfo.setUnitTitle(entityByKeyReturnInfo.getEntity().getTitle());
                        }
                        if (unAccessForms.containsKey(unitTitle = unitInfo.getUnitCode() + " " + unitInfo.getUnitTitle())) {
                            forms = (List)unAccessForms.get(unitTitle);
                        }
                        for (String formKey : formInfos.keySet()) {
                            formInfo = (MistoreWorkFormInfo)formInfos.get(formKey);
                            formInfo.getFormCode();
                            if (StringUtils.isEmpty((String)formInfo.getFormTitle()) || StringUtils.isEmpty((String)formInfo.getFormCode())) {
                                form = this.jtableParamService.getReport(formInfo.getFormKey(), formScheme.getKey());
                                formInfo.setFormCode(form.getCode());
                                formInfo.setFormTitle(form.getTitle());
                            }
                            forms.add(formInfo.getFormCode() + " " + formInfo.getFormTitle() + " " + message);
                            unAccessForms.put(unitTitle, forms);
                        }
                    }
                }
            }
            midstoreReturnInfo.setUnAccessForms(unAccessForms);
            midstoreReturnInfo.setUnAccessUnits(unAccessUnits);
            midstoreReturnInfo.setFailedCount(failedUnitKeys.size());
            midstoreReturnInfo.setSuccessCount(midstoreReturnInfo.getAllCount() - failedUnitKeys.size());
            retStr = null;
            try {
                retStr = mapper.writeValueAsString((Object)midstoreReturnInfo);
            }
            catch (JsonProcessingException e) {
                MidstoreService.logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (midstoreReturnInfo.getFailedCount() == 0) {
                asyncTaskMonitor.finish("midstore_pull_success", (Object)retStr);
            } else {
                asyncTaskMonitor.error("midstore_pull_failed", null, retStr);
            }
            this.applicationContext.publishEvent(new MidstorePullFinishEvent(midstoreParam));
        } else {
            asyncTaskMonitor.error(result.getMessage(), (Throwable)new MidstoreException(result.getMessage()));
        }
    }

    private String getDimensionName(String taskKey) {
        TaskDefine taskDefine = this.runtimeView.queryTaskDefine(taskKey);
        String dw = taskDefine.getDw();
        String dimensionName = this.entityMetaService.getDimensionName(dw);
        return dimensionName;
    }
}

