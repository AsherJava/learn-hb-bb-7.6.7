/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.definition.deploy.DeployItem
 *  com.jiuqi.np.definition.observer.MessageType
 *  com.jiuqi.np.definition.observer.NpDefinitionObserverable
 *  com.jiuqi.np.definition.progress.ProgressCacheService
 *  com.jiuqi.np.definition.progress.ProgressItem
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.graph.IRWLockExecuterManager
 *  com.jiuqi.nr.graph.function.IRunnable
 *  org.springframework.transaction.PlatformTransactionManager
 *  org.springframework.transaction.TransactionDefinition
 *  org.springframework.transaction.TransactionStatus
 *  org.springframework.transaction.annotation.Propagation
 *  org.springframework.transaction.annotation.Transactional
 *  org.springframework.transaction.support.DefaultTransactionDefinition
 */
package com.jiuqi.nr.definition.internal.service;

import com.jiuqi.np.common.exception.ErrorEnum;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.definition.deploy.DeployItem;
import com.jiuqi.np.definition.observer.MessageType;
import com.jiuqi.np.definition.observer.NpDefinitionObserverable;
import com.jiuqi.np.definition.progress.ProgressCacheService;
import com.jiuqi.np.definition.progress.ProgressItem;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.controller.IDesignTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaDesignTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.deploy.AnalyseDefinitionService;
import com.jiuqi.nr.definition.deploy.DeployDefinitionService;
import com.jiuqi.nr.definition.deploy.DeployFinishedEvent;
import com.jiuqi.nr.definition.deploy.DeployParams;
import com.jiuqi.nr.definition.deploy.DeployPrepareEvent;
import com.jiuqi.nr.definition.deploy.DeployProgress;
import com.jiuqi.nr.definition.deploy.FCConditionDeployParam;
import com.jiuqi.nr.definition.exception.DefinitonException;
import com.jiuqi.nr.definition.exception.NrDefinitionErrorEnum;
import com.jiuqi.nr.definition.exception.NrDeployErrorEnum;
import com.jiuqi.nr.definition.exception.NrParamSyncException;
import com.jiuqi.nr.definition.facade.DataLinkDefine;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.formula.DesignFormulaConditionLink;
import com.jiuqi.nr.definition.facade.formula.FormulaConditionLink;
import com.jiuqi.nr.definition.internal.provider.ConditionalStyleProvider;
import com.jiuqi.nr.definition.internal.runtime.service.FormFieldInfoService;
import com.jiuqi.nr.definition.internal.service.DesignFormFoldingService;
import com.jiuqi.nr.definition.internal.service.DesignReportTemplateService;
import com.jiuqi.nr.definition.internal.service.DesignTaskDefineService;
import com.jiuqi.nr.definition.paramcheck.NODDLDeployExecutor;
import com.jiuqi.nr.definition.service.IParamCacheManagerService;
import com.jiuqi.nr.definition.util.NrDefinitionHelper;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import com.jiuqi.nr.graph.function.IRunnable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class ViewDeployService
implements NODDLDeployExecutor {
    private static final Logger logger = LoggerFactory.getLogger(ViewDeployService.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AnalyseDefinitionService analyseDefinitionService;
    @Autowired
    private DeployDefinitionService deployDefinitionService;
    @Autowired
    private ProgressCacheService progressCacheService;
    @Autowired
    private IFormulaDesignTimeController formulaDesignTimeController;
    @Autowired
    private NpDefinitionObserverable observerable;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private DesignTaskDefineService designTaskDefineService;
    @Autowired
    private IDataSchemeDeployService iDataSchemeDeployService;
    @Autowired
    private DesignReportTemplateService designReportTemplateService;
    @Autowired
    private ConditionalStyleProvider conditionalStyleProvider;
    @Autowired
    private FormFieldInfoService formFieldInfoService;
    @Autowired
    private IRWLockExecuterManager rwLockExecuterManager;
    @Autowired
    private NrDefinitionHelper nrDefinitionHelper;
    @Autowired
    private IParamCacheManagerService paramCacheManagerService;
    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private DesignFormFoldingService formFoldingService;

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class, RuntimeException.class})
    public void deployFormDefine(String formKey) throws Exception {
        FormDefine formDefine = this.designTimeViewController.querySoftFormDefine(formKey);
        if (null == formDefine) {
            formDefine = this.runTimeViewController.queryFormById(formKey);
        }
        if (null == formDefine) {
            return;
        }
        try {
            DeployParams deployParams = new DeployParams();
            DeployItem form = new DeployItem();
            form.setDesignTimeKeys(new HashSet<String>(Arrays.asList(formKey)));
            form.setRunTimeKeys(new HashSet<String>(Arrays.asList(formKey)));
            deployParams.setForm(form);
            this.analyseDefinitionService.getDeployParams(deployParams);
            this.deployParamToRunTime(deployParams, new ProgressItem(), true);
            this.paramCacheManagerService.refreshCache(Collections.singleton(formDefine.getFormScheme()), Collections.emptySet(), () -> this.applicationContext.publishEvent(new DeployFinishedEvent((Object)formKey, deployParams)));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public void deployTask(String taskKey) throws Exception {
        this.deployTask(taskKey, true);
    }

    public void deployTask(String taskKey, boolean deployDataScheme) throws Exception {
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u51c6\u5907\u53d1\u5e03");
        ProgressItem progressItem = DeployProgress.getProgressItem(taskKey, deployDataScheme);
        this.updateProgressItem(progressItem, DeployProgress.ANALYSE.getMessage(), DeployProgress.ANALYSE.getCurrentProgress());
        DeployParams deployParams = this.analyseDefinitionService.getFullDeployParamsByTask(taskKey);
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u53d1\u5e03\u524d\u4e8b\u4ef6");
        this.applicationContext.publishEvent(new DeployPrepareEvent((Object)taskKey, deployParams));
        if (deployDataScheme) {
            logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u6570\u636e\u65b9\u6848");
            this.deployDataScheme(taskKey, progressItem);
        }
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u4efb\u52a1");
        this.updateProgressItem(progressItem, DeployProgress.DEPLOY_TASK);
        this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(ParamResourceType.TASK, taskKey)).tryWrite(() -> {
            this.deployTask(taskKey, deployParams, progressItem);
            return null;
        });
        try {
            this.updateProgressItem(progressItem, DeployProgress.DEPLOY_CACHE.getMessage(), DeployProgress.DEPLOY_CACHE.getCurrentProgress());
            this.paramCacheManagerService.refreshCache(deployParams.getFormScheme().getRuntimeUninDesignTimeKeys(), deployParams.getFormulaScheme().getRuntimeUninDesignTimeKeys(), () -> {
                this.updateProgressItem(progressItem, DeployProgress.DEPLOY_EVENT);
                logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u53d1\u5e03\u540e\u4e8b\u4ef6");
                this.applicationContext.publishEvent(new DeployFinishedEvent((Object)taskKey, deployParams));
                logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u4efb\u52a1\u53d1\u5e03\u5b8c\u6210, \u901a\u77e5\u89c2\u5bdf\u8005\u6267\u884c\u53d1\u5e03\u540e\u4e8b\u4ef6\u5f00\u59cb");
                Map<Boolean, List<String>> observerResult = this.notifyObserver(new Object[]{taskKey});
                logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u4efb\u52a1\u53d1\u5e03\u5b8c\u6210, \u901a\u77e5\u89c2\u5bdf\u8005\u6267\u884c\u53d1\u5e03\u540e\u4e8b\u4ef6\u5b8c\u6210");
                List<String> errorList = observerResult.get(false);
                if (errorList != null && !errorList.isEmpty()) {
                    progressItem.setWarnList(errorList);
                }
                this.updateProgressItem(progressItem, DeployProgress.FINISH);
            });
        }
        catch (NrParamSyncException e) {
            logger.error("\u53d1\u5e03\u4efb\u52a1\uff1a\u7f13\u5b58\u5237\u65b0\u5f02\u5e38", e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_071, e.getMessage());
        }
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u4efb\u52a1\u53d1\u5e03\u5b8c\u6210");
    }

    private void deployTask(String taskKey, DeployParams deployParams, ProgressItem progressItem) throws Exception {
        TransactionStatus transaction = null;
        try {
            transaction = this.getTransactionStatus();
            this.deployParamToRunTime(deployParams, progressItem, true);
            this.updateProgressItem(progressItem, DeployProgress.DEPLOY_FORM_FIELD_INFO.getMessage(), DeployProgress.DEPLOY_FORM_FIELD_INFO.getCurrentProgress());
            this.formFieldInfoService.deploy(taskKey);
            this.updateProgressItem(progressItem, DeployProgress.DEPLOY_CONDITIONAL_STYLE.getMessage(), DeployProgress.DEPLOY_CONDITIONAL_STYLE.getCurrentProgress());
            this.conditionalStyleProvider.deployCS(taskKey);
            this.formFoldingService.deploy(taskKey);
            logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u5206\u6790\u62a5\u544a\u6a21\u677f\u4fe1\u606f");
            this.updateProgressItem(progressItem, DeployProgress.DEPLOY_REPORT_TEMPLATE.getMessage(), DeployProgress.DEPLOY_REPORT_TEMPLATE.getCurrentProgress());
            this.designReportTemplateService.doDeploy(taskKey);
            this.platformTransactionManager.commit(transaction);
        }
        catch (Exception e) {
            if (null != transaction) {
                this.platformTransactionManager.rollback(transaction);
            }
            logger.error("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u5f02\u5e38\uff1a{}", (Object)e.getMessage());
            this.updateProgressItem(progressItem, e);
            throw e;
        }
    }

    private void deployDataScheme(String taskKey, ProgressItem progressItem) throws Exception {
        DesignTaskDefine taskDefine = this.designTaskDefineService.queryTaskDefine(taskKey);
        DeployResult deployResult = null;
        if (taskDefine != null) {
            this.updateProgressItem(progressItem, DeployProgress.DEPLOY_TABLE);
            deployResult = this.iDataSchemeDeployService.deployDataScheme(taskDefine.getDataScheme(), p -> this.updateProgressItem(progressItem, p.isFinished() ? "\u6570\u636e\u65b9\u6848\u53d1\u5e03\u6210\u529f" : p.getMessage(), p.getCurrentProgess()), null);
        }
        if (deployResult != null && !deployResult.isSuccess()) {
            this.updateProgressItem(progressItem, deployResult.getDeployErrorMessage());
            throw new JQException((ErrorEnum)NrDeployErrorEnum.NRDEPLOY_DATASCHEME_ERROR_PARAM, deployResult.getDeployErrorMessage());
        }
    }

    public Map<Boolean, List<String>> notifyObserver(Object[] obj) throws Exception {
        if (this.observerable != null) {
            return this.observerable.notify(MessageType.NRPUBLISHTASK, obj);
        }
        return Collections.emptyMap();
    }

    public void notifyObserver(String task) {
        try {
            if (this.observerable != null && task != null) {
                this.observerable.notify("DEPLOY_CUSTOM_TYPE", new Object[]{task});
            }
        }
        catch (Exception e) {
            throw new DefinitonException(e);
        }
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor={Exception.class})
    public void deployTaskToDes(String taskKey) throws Exception {
        try {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
            if (taskDefine == null) {
                return;
            }
            DeployParams deployParams = this.analyseDefinitionService.getFullDeployParamsByTask(taskKey);
            this.revertParamToDes(deployParams, null);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    private void updateProgressItem(ProgressItem progressItem, DeployProgress progress) {
        if (progressItem == null) {
            return;
        }
        if (DeployProgress.FINISH == progress) {
            progressItem.finish();
            progressItem.setMessage(progress.getMessage());
        } else {
            progressItem.nextStep();
            progressItem.setCurrentProgess(progress.getCurrentProgress());
            progressItem.setMessage(progress.getMessage());
        }
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
    }

    private void updateProgressItem(ProgressItem progressItem, String message, int currentProgress) {
        if (progressItem == null) {
            return;
        }
        progressItem.setCurrentProgess(currentProgress);
        progressItem.setMessage(message);
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
    }

    private void updateProgressItem(ProgressItem progressItem, Exception e) {
        progressItem.markException(e);
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
    }

    private void updateProgressItem(ProgressItem progressItem, String errorMessage) {
        progressItem.setFailed(true);
        progressItem.setFinished(true);
        progressItem.setMessage(errorMessage);
        this.progressCacheService.setProgress(progressItem.getProgressId(), progressItem);
    }

    private void revertParamToDes(DeployParams deployParams, ProgressItem progressItem) throws Exception {
        this.deployParamToRunTime(deployParams, progressItem, false);
    }

    private void deployParamToRunTime(DeployParams deployParams, ProgressItem progressItem, boolean isFromDesToSys) throws Exception {
        try {
            this.updateProgressItem(progressItem, DeployProgress.DEPLOY_FORM.getMessage(), DeployProgress.DEPLOY_FORM.getCurrentProgress());
            this.deployTaskI18n(deployParams, isFromDesToSys);
            this.deployFlowData(deployParams, isFromDesToSys);
            this.deployFormScheme(deployParams, isFromDesToSys);
            this.deployFormGroup(deployParams, isFromDesToSys);
            boolean regionChange = this.deployItemChanged(deployParams.getDataRegion());
            boolean linkChange = this.deployItemChanged(deployParams.getDataLink());
            this.deployFormDefine(deployParams, !regionChange, !linkChange, isFromDesToSys);
            this.deployDataRegionDefine(deployParams, isFromDesToSys);
            this.deployDataLinkDefine(deployParams, isFromDesToSys);
            this.deployDataRegionSetting(deployParams, isFromDesToSys);
            this.updateProgressItem(progressItem, DeployProgress.DEPLOY_FORMULA.getMessage(), DeployProgress.DEPLOY_FORMULA.getCurrentProgress());
            this.deployFormulaScheme(deployParams, isFromDesToSys);
            this.deployFormulaDefine(deployParams, isFromDesToSys);
            this.deployFormulaVariableDefine(deployParams, isFromDesToSys);
            this.updateProgressItem(progressItem, DeployProgress.DEPLOY_PRINT.getMessage(), DeployProgress.DEPLOY_PRINT.getCurrentProgress());
            this.deployPrintScheme(deployParams, isFromDesToSys);
            this.deployPrintSetting(deployParams, isFromDesToSys);
            this.deployPrintTemplate(deployParams, isFromDesToSys);
            this.deployTaskLink(deployParams, isFromDesToSys);
            this.deployDataLinkMapping(deployParams, isFromDesToSys);
            this.deployDimensionFilter(deployParams, isFromDesToSys);
            this.deployFormulaConditionLinks(deployParams, isFromDesToSys);
            this.deployFormulaConditions(deployParams, isFromDesToSys);
        }
        catch (Exception e) {
            throw new JQException((ErrorEnum)NrDeployErrorEnum.NRDEPLOYERRORENUM_PARAM, (Throwable)e);
        }
    }

    private void deployTaskI18n(DeployParams deployParams, boolean isFromDesToSys) {
        Set taskKeys = deployParams.getTaskDefine().getRuntimeUninDesignTimeKeys();
        this.deployDefinitionService.deleteTaskI18n(taskKeys, isFromDesToSys);
        this.deployDefinitionService.insertTaskI18n(taskKeys, isFromDesToSys);
    }

    private void deployFlowData(DeployParams deployParams, boolean isFromDesToSys) throws Exception {
        Set taskKeys = deployParams.getTaskDefine().getRuntimeUninDesignTimeKeys();
        this.deployDefinitionService.deleteFlowDataByTask(taskKeys, isFromDesToSys);
        this.deployDefinitionService.insertFlowDataByTask(taskKeys, isFromDesToSys);
    }

    private void deployDataLinkMapping(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem dataLinkMapping = deployParams.getDataLinkMapping();
        if (dataLinkMapping == null) {
            return;
        }
        Set designTimeKeys = dataLinkMapping.getDesignTimeKeys();
        Set runTimeKeys = dataLinkMapping.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deleteDataLinkMapping(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertDataLinkMapping(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertDataLinkMapping(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployDataRegionSetting(DeployParams deployParams, boolean isFromDesToSys) throws Exception {
        DeployItem regionSetting = deployParams.getRegionSetting();
        if (regionSetting == null) {
            return;
        }
        Set designTimeKeys = regionSetting.getDesignTimeKeys();
        Set runTimeKeys = regionSetting.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.fliterRunTimeKeys(deployParams.getForm().getRunTimeKeys(), deployParams.getForm().getDesignTimeKeys(), null, null, null, null, runTimeKeys, designTimeKeys, false);
            this.deployDefinitionService.deleteRegionSettings(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertRegionSettings(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertRegionSettings(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployPrintSetting(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem printScheme = deployParams.getPrintScheme();
        if (printScheme == null) {
            return;
        }
        Set designTimeKeys = printScheme.getDesignTimeKeys();
        Set runTimeKeys = printScheme.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deletePrintSetting(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertPrintSetting(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertPrintSetting(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployPrintTemplate(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem printTemplate = deployParams.getPrintTemplate();
        if (printTemplate == null) {
            return;
        }
        Set designTimeKeys = printTemplate.getDesignTimeKeys();
        Set runTimeKeys = printTemplate.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deletePrintTemplates(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertPrintTemplates(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertPrintTemplates(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployPrintScheme(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem printScheme = deployParams.getPrintScheme();
        if (printScheme == null) {
            return;
        }
        Set designTimeKeys = printScheme.getDesignTimeKeys();
        Set runTimeKeys = printScheme.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deletePrintSchemes(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertPrintSchemes(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertPrintSchemes(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployTaskLink(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem taskLink = deployParams.getTaskLink();
        if (taskLink == null) {
            return;
        }
        Set designTimeKeys = taskLink.getDesignTimeKeys();
        Set runTimeKeys = taskLink.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deleteTaskLinks(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertTaskLinks(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertTaskLinks(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployFormulaDefine(DeployParams deployParams, boolean isFromDesToSys) throws Exception {
        DeployItem formula = deployParams.getFormula();
        if (formula == null) {
            return;
        }
        Set designTimeKeys = formula.getDesignTimeKeys();
        Set runTimeKeys = formula.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deleteFormulas(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertFormulas(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertFormulas(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployFormulaVariableDefine(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem formulaVariable = deployParams.getFormulaVariable();
        if (formulaVariable == null) {
            return;
        }
        Set designTimeKeys = formulaVariable.getDesignTimeKeys();
        Set runTimeKeys = formulaVariable.getRunTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deleteFormulaVariables(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertFormulaVariables(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertFormulaVariables(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployFormulaScheme(DeployParams deployParams, boolean isFromDesToSys) throws Exception {
        DeployItem formulaScheme = deployParams.getFormulaScheme();
        if (formulaScheme == null) {
            return;
        }
        Set designTimeKeys = formulaScheme.getDesignTimeKeys();
        Set runTimeKeys = formulaScheme.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deleteFormulaSchemes(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertFormulaSchemes(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertFormulaSchemes(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployDataLinkDefine(DeployParams deployParams, boolean isFromDesToSys) throws Exception {
        DeployItem dataLink = deployParams.getDataLink();
        if (dataLink == null) {
            return;
        }
        Set task = deployParams.getTaskDefine().getRuntimeUninDesignTimeKeys();
        String taskKey = null != task && 1 == task.size() ? (String)task.stream().findFirst().orElse(null) : null;
        Set designTimeKeys = dataLink.getDesignTimeKeys();
        Set runTimeKeys = dataLink.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.fliterRunTimeKeys(deployParams.getForm().getRunTimeKeys(), deployParams.getForm().getDesignTimeKeys(), null, null, runTimeKeys, designTimeKeys, null, null, false);
            this.deployDefinitionService.deleteDataLinks(taskKey, runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertDataLinks(taskKey, designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertDataLinks(taskKey, runTimeKeys, isFromDesToSys);
            }
        }
    }

    private boolean deployItemChanged(DeployItem deployItem) {
        if (deployItem == null) {
            return false;
        }
        boolean hasChange = false;
        Set designTimeKeys = deployItem.getDesignTimeKeys();
        Set runTimeKeys = deployItem.getRunTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            hasChange = true;
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            hasChange = true;
        }
        return hasChange;
    }

    private void deployDataRegionDefine(DeployParams deployParams, boolean isFromDesToSys) throws Exception {
        DeployItem dataRegion = deployParams.getDataRegion();
        if (dataRegion == null) {
            return;
        }
        Set designTimeKeys = dataRegion.getDesignTimeKeys();
        Set runTimeKeys = dataRegion.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.fliterRunTimeKeys(deployParams.getForm().getRunTimeKeys(), deployParams.getForm().getDesignTimeKeys(), runTimeKeys, designTimeKeys, null, null, null, null, false);
            this.deployDefinitionService.deleteDataRegions(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertDataRegions(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertDataRegions(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployFormDefine(DeployParams deployParams, boolean deployRegion, boolean deployLink, boolean isFromDesToSys) throws Exception {
        DeployItem form = deployParams.getForm();
        if (form == null) {
            return;
        }
        Set designTimeKeys = form.getDesignTimeKeys();
        Set runTimeKeys = form.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.fliterRunTimeKeys(runTimeKeys, designTimeKeys, null, null, null, null, null, null, true);
            this.deployDefinitionService.deleteFormDefines(runTimeKeys, isFromDesToSys);
            if (deployRegion) {
                this.deployDefinitionService.deleteDataRegionsByForm(runTimeKeys, isFromDesToSys);
            }
            if (deployLink) {
                this.deployDefinitionService.deleteDataLinksByForm(runTimeKeys, isFromDesToSys);
            }
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertFormDefines(designTimeKeys, isFromDesToSys);
                if (deployRegion) {
                    this.deployDefinitionService.insertDataRegionsByForm(designTimeKeys, isFromDesToSys);
                }
                if (deployLink) {
                    this.deployDefinitionService.insertDataLinksByForm(designTimeKeys, isFromDesToSys);
                }
            } else {
                this.deployDefinitionService.insertFormDefines(runTimeKeys, isFromDesToSys);
                if (deployRegion) {
                    this.deployDefinitionService.insertDataRegionsByForm(runTimeKeys, isFromDesToSys);
                }
                if (deployLink) {
                    this.deployDefinitionService.insertDataLinksByForm(runTimeKeys, isFromDesToSys);
                }
            }
        }
    }

    private void fliterRunTimeKeys(Set<String> runTimeFormKeys, Set<String> designTimeFormKeys, Set<String> runTimeRegionKeys, Set<String> designTimeRegionKeys, Set<String> runTimeLinkKeys, Set<String> designTimeLinkKeys, Set<String> runTimeRegionSettingKeys, Set<String> designTimeRegionSettingKeys, Boolean deployForm) throws Exception {
        List<FormDefine> formlist;
        if (runTimeFormKeys != null && (formlist = this.runTimeViewController.queryFormsById(new ArrayList<String>(runTimeFormKeys))) != null) {
            for (FormDefine formDefine : formlist) {
                if (formDefine == null || !formDefine.getQuoteType() || designTimeFormKeys != null && designTimeFormKeys.contains(formDefine.getKey())) continue;
                TreeSet taskKeySet = new TreeSet();
                List<FormSchemeDefine> formSchemes = this.runTimeViewController.queryFormSchemeByForm(formDefine.getKey());
                if (formSchemes != null) {
                    formSchemes.stream().forEach(item -> taskKeySet.add(item.getTaskKey()));
                }
                if (taskKeySet.size() <= 1) continue;
                if (deployForm.booleanValue()) {
                    runTimeFormKeys.remove(formDefine.getKey());
                    continue;
                }
                this.fliterRunTimeRegionKeys(runTimeRegionKeys, designTimeRegionKeys, formDefine.getKey(), runTimeLinkKeys, designTimeLinkKeys, runTimeRegionSettingKeys, designTimeRegionSettingKeys);
            }
        }
    }

    private void fliterRunTimeRegionKeys(Set<String> runTimeRegionKeys, Set<String> designTimeRegionKeys, String formKey, Set<String> runTimeLinkKeys, Set<String> designTimeLinkKeys, Set<String> runTimeRegionSettingKeys, Set<String> designTimeRegionSettingKeys) {
        List<DataRegionDefine> Regions = this.runTimeViewController.getAllRegionsInForm(formKey);
        for (DataRegionDefine region : Regions) {
            List<DataLinkDefine> links;
            if (runTimeRegionKeys != null) {
                runTimeRegionKeys.remove(region.getKey());
            }
            if (runTimeLinkKeys != null && (links = this.runTimeViewController.getAllLinksInRegion(region.getKey())) != null) {
                links.stream().forEach(item -> runTimeLinkKeys.remove(item.getKey()));
            }
            if (runTimeRegionSettingKeys == null) continue;
            runTimeRegionSettingKeys.remove(region.getRegionSettingKey());
        }
    }

    private void deployFormGroup(DeployParams deployParams, boolean isFromDesToSys) throws Exception {
        DeployItem formGroup = deployParams.getFormGroup();
        if (formGroup == null) {
            return;
        }
        Set designTimeKeys = formGroup.getDesignTimeKeys();
        Set runTimeKeys = formGroup.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deleteFormGroupDefines(runTimeKeys, isFromDesToSys);
            this.deployDefinitionService.deleteFormGroupLinks(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertFormGroupDefines(designTimeKeys, isFromDesToSys);
                this.deployDefinitionService.insertFormGroupLinks(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertFormGroupDefines(runTimeKeys, isFromDesToSys);
                this.deployDefinitionService.insertFormGroupLinks(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployFormScheme(DeployParams deployParams, boolean isFromDesToSys) throws Exception {
        DeployItem formScheme = deployParams.getFormScheme();
        if (formScheme == null) {
            return;
        }
        Set designTimeKeys = formScheme.getDesignTimeKeys();
        Set runTimeKeys = formScheme.getRuntimeUninDesignTimeKeys();
        if (runTimeKeys != null && runTimeKeys.size() > 0) {
            this.deployDefinitionService.deleteFormSchemeDefines(runTimeKeys, isFromDesToSys);
        }
        if (designTimeKeys != null && designTimeKeys.size() > 0) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertFormSchemeDefines(designTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertFormSchemeDefines(runTimeKeys, isFromDesToSys);
            }
        }
    }

    private void deployDimensionFilter(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem taskDefine = deployParams.getTaskDefine();
        DeployItem formScheme = deployParams.getFormScheme();
        if (taskDefine == null || formScheme == null) {
            return;
        }
        Set taskDesignTimeKeys = taskDefine.getDesignTimeKeys();
        Set formSchemeRunTimeKeys = formScheme.getRunTimeKeys();
        Set formSchemeDesignTimeKeys = formScheme.getDesignTimeKeys();
        if (formSchemeRunTimeKeys != null && !formSchemeRunTimeKeys.isEmpty()) {
            if (isFromDesToSys) {
                this.deployDefinitionService.deleteDimensionFilters(formSchemeRunTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.deleteDimensionFilters(taskDesignTimeKeys, isFromDesToSys);
            }
        }
        if (formSchemeDesignTimeKeys != null && !formSchemeDesignTimeKeys.isEmpty()) {
            if (isFromDesToSys) {
                this.deployDefinitionService.insertDimensionFilters(formSchemeDesignTimeKeys, isFromDesToSys);
            } else {
                this.deployDefinitionService.insertDimensionFilters(formSchemeRunTimeKeys, isFromDesToSys);
            }
        }
    }

    private TransactionStatus getTransactionStatus() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(3);
        return this.platformTransactionManager.getTransaction((TransactionDefinition)def);
    }

    public void deployFormulaScheme(String formulaSchemeKey, boolean deployFormula) throws Exception {
        this.checkByFormulaScheme(formulaSchemeKey);
        logger.info("\u53d1\u5e03\u516c\u5f0f\uff1a\u5f00\u59cb");
        DeployParams deployParams = new DeployParams();
        deployParams.setFormulaScheme(new DeployItem(Collections.singleton(formulaSchemeKey), Collections.singleton(formulaSchemeKey)));
        FCConditionDeployParam deployParam = new FCConditionDeployParam(formulaSchemeKey);
        if (deployFormula) {
            this.analyseDefinitionService.getDeployParams(deployParams);
            this.analyseDefinitionService.getFormulaConditionDeployParam(deployParam);
            deployParams.setFormulaCondition(new DeployItem(deployParam.getDesignKeys(), deployParam.getRunKeys()));
        }
        this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(ParamResourceType.FORMULA, formulaSchemeKey)).tryWrite(() -> {
            this.deployFormulaScheme(deployParams);
            return null;
        });
        try {
            this.paramCacheManagerService.refreshCache(Collections.emptySet(), Collections.singleton(formulaSchemeKey), () -> {
                this.applicationContext.publishEvent(new DeployFinishedEvent((Object)formulaSchemeKey, deployParams));
                logger.info("\u53d1\u5e03\u516c\u5f0f\uff1a\u7ed3\u675f");
                this.notifyObserver(this.getTaskKey(formulaSchemeKey));
                logger.info("\u53d1\u5e03\u516c\u5f0f\uff1a\u901a\u77e5\u89c2\u5bdf\u8005\u6267\u884c\u53d1\u5e03\u540e\u4e8b\u4ef6\u5b8c\u6210");
            });
        }
        catch (NrParamSyncException e) {
            logger.error("\u53d1\u5e03\u516c\u5f0f\uff1a\u7f13\u5b58\u5237\u65b0\u5f02\u5e38", e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_071, e.getMessage());
        }
    }

    private String getTaskKey(String formulaSchemeKey) {
        String formSchemeKey;
        DesignFormSchemeDefine formSchemeDefine;
        DesignFormulaSchemeDefine designFormulaSchemeDefine = this.formulaDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        if (designFormulaSchemeDefine != null && (formSchemeDefine = this.designTimeViewController.queryFormSchemeDefine(formSchemeKey = designFormulaSchemeDefine.getFormSchemeKey())) != null) {
            return formSchemeDefine.getTaskKey();
        }
        return null;
    }

    private void checkByFormulaScheme(String formulaSchemeKey) {
        DesignFormulaSchemeDefine designFormulaSchemeDefine = this.formulaDesignTimeController.queryFormulaSchemeDefine(formulaSchemeKey);
        String formSchemeKey = designFormulaSchemeDefine.getFormSchemeKey();
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme == null) {
            throw new RuntimeException("\u62a5\u8868\u65b9\u6848\u672a\u53d1\u5e03");
        }
    }

    public void deployFormulaScheme(String formulaSchemeKey, String formKey) throws Exception {
        this.checkByFormulaScheme(formulaSchemeKey);
        logger.info("\u53d1\u5e03\u516c\u5f0f\uff1a\u5f00\u59cb");
        DeployParams deployParams = new DeployParams();
        deployParams.setFormulaScheme(new DeployItem(Collections.singleton(formulaSchemeKey), Collections.singleton(formulaSchemeKey)));
        this.analyseDefinitionService.getFormulaParams(formulaSchemeKey, formKey, deployParams);
        FCConditionDeployParam deployParam = new FCConditionDeployParam(formulaSchemeKey);
        this.analyseDefinitionService.getFormulaConditionDeployParam(deployParam, formKey);
        deployParams.setFormulaCondition(new DeployItem(deployParam.getDesignKeys(), deployParam.getRunKeys()));
        this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(ParamResourceType.FORMULA, formulaSchemeKey)).tryWrite(() -> {
            this.deployFormulaScheme(deployParams);
            return null;
        });
        try {
            this.paramCacheManagerService.refreshCache(Collections.emptySet(), Collections.singleton(formulaSchemeKey), () -> {
                this.applicationContext.publishEvent(new DeployFinishedEvent((Object)formulaSchemeKey, deployParams));
                logger.info("\u53d1\u5e03\u516c\u5f0f\uff1a\u7ed3\u675f");
                this.notifyObserver(this.getTaskKey(formulaSchemeKey));
                logger.info("\u53d1\u5e03\u516c\u5f0f\uff1a\u901a\u77e5\u89c2\u5bdf\u8005\u6267\u884c\u53d1\u5e03\u540e\u4e8b\u4ef6\u5b8c\u6210");
            });
        }
        catch (NrParamSyncException e) {
            logger.error("\u53d1\u5e03\u516c\u5f0f\uff1a\u7f13\u5b58\u5237\u65b0\u5f02\u5e38", e);
            throw new JQException((ErrorEnum)NrDefinitionErrorEnum.NRDEFINITION_EXCEPTION_071, e.getMessage());
        }
    }

    private void deployFormulaScheme(DeployParams deployParams) throws Exception {
        TransactionStatus transaction = null;
        try {
            transaction = this.getTransactionStatus();
            this.deployParamToRunTime(deployParams, null, true);
            this.platformTransactionManager.commit(transaction);
        }
        catch (Exception e) {
            if (null != transaction) {
                this.platformTransactionManager.rollback(transaction);
            }
            throw e;
        }
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void deployPrintScheme(String printSchemeKey, boolean deployTemplate) throws Exception {
        logger.info("\u53d1\u5e03\u6253\u5370\u6a21\u677f\uff1a\u5f00\u59cb");
        try {
            DeployParams deployParams = new DeployParams();
            DeployItem printScheme = new DeployItem();
            printScheme.setDesignTimeKeys(new HashSet<String>(Arrays.asList(printSchemeKey)));
            printScheme.setRunTimeKeys(new HashSet<String>(Arrays.asList(printSchemeKey)));
            deployParams.setPrintScheme(printScheme);
            if (deployTemplate) {
                this.analyseDefinitionService.getDeployParams(deployParams);
            }
            this.deployParamToRunTime(deployParams, null, true);
            this.applicationContext.publishEvent(new DeployFinishedEvent((Object)printSchemeKey, deployParams));
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u6253\u5370\u6a21\u677f\uff1a\u5931\u8d25", e);
            throw e;
        }
        logger.info("\u53d1\u5e03\u6253\u5370\u6a21\u677f\uff1a\u7ed3\u675f");
    }

    @Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW, rollbackFor={Exception.class})
    public void deployPrintTemplate(String printTemplateKey) throws Exception {
        logger.info("\u53d1\u5e03\u6253\u5370\u6a21\u677f\uff1a\u5f00\u59cb");
        try {
            DeployParams deployParams = new DeployParams();
            DeployItem printTemplate = new DeployItem();
            printTemplate.setDesignTimeKeys(new HashSet<String>(Arrays.asList(printTemplateKey)));
            printTemplate.setRunTimeKeys(new HashSet<String>(Arrays.asList(printTemplateKey)));
            deployParams.setPrintTemplate(printTemplate);
            this.deployParamToRunTime(deployParams, null, true);
            this.applicationContext.publishEvent(new DeployFinishedEvent((Object)printTemplateKey, deployParams));
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u6253\u5370\u6a21\u677f\uff1a\u5931\u8d25", e);
            throw e;
        }
        logger.info("\u53d1\u5e03\u6253\u5370\u6a21\u677f\uff1a\u7ed3\u675f");
    }

    @Override
    public List<String> preDeploy(String taskKey) {
        try {
            DesignTaskDefine taskDefine = this.designTaskDefineService.queryTaskDefine(taskKey);
            return this.iDataSchemeDeployService.preDeployDataScheme(taskDefine.getDataScheme(), true, p -> {});
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doDeploy(String taskKey) {
        DeployParams deployParams;
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u51c6\u5907\u53d1\u5e03");
        ProgressItem progressItem = DeployProgress.getProgressItem(taskKey);
        this.updateProgressItem(progressItem, DeployProgress.ANALYSE);
        try {
            deployParams = this.analyseDefinitionService.getFullDeployParamsByTask(taskKey);
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u5f02\u5e38", e);
            throw new RuntimeException(e);
        }
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u53d1\u5e03\u524d\u4e8b\u4ef6");
        this.applicationContext.publishEvent(new DeployPrepareEvent((Object)taskKey, deployParams));
        this.updateProgressItem(progressItem, DeployProgress.DEPLOY_TABLE);
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u6570\u636e\u65b9\u6848");
        this.deployTableModel(taskKey, progressItem);
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u4efb\u52a1");
        this.updateProgressItem(progressItem, DeployProgress.DEPLOY_TASK);
        this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(ParamResourceType.TASK, taskKey)).tryWrite(() -> {
            this.deployTask(taskKey, deployParams, progressItem);
            return null;
        });
        this.updateProgressItem(progressItem, DeployProgress.DEPLOY_EVENT);
        try {
            this.paramCacheManagerService.refreshCache(deployParams.getFormScheme().getRuntimeUninDesignTimeKeys(), deployParams.getFormulaScheme().getRuntimeUninDesignTimeKeys(), () -> {
                logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u53d1\u5e03\u540e\u4e8b\u4ef6");
                this.applicationContext.publishEvent(new DeployFinishedEvent((Object)taskKey, deployParams));
            });
        }
        catch (NrParamSyncException e) {
            logger.error("\u53d1\u5e03\u4efb\u52a1\uff1a\u7f13\u5b58\u5237\u65b0\u5f02\u5e38", e);
            throw new RuntimeException(e);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        logger.info("\u53d1\u5e03\u4efb\u52a1\uff1a\u4efb\u52a1\u53d1\u5e03\u5b8c\u6210");
    }

    @Override
    public double getOrder() {
        return 1.0;
    }

    private void deployTableModel(String taskKey, ProgressItem progressItem) {
        try {
            DesignTaskDefine taskDefine = this.designTaskDefineService.queryTaskDefine(taskKey);
            if (taskDefine != null) {
                this.updateProgressItem(progressItem, DeployProgress.DEPLOY_TABLE);
                this.iDataSchemeDeployService.deployTableModel(taskDefine.getDataScheme());
            }
        }
        catch (Exception e) {
            logger.error("\u53d1\u5e03\u4efb\u52a1\uff1a\u53d1\u5e03\u6570\u636e\u65b9\u6848\uff1a{}", (Object)e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void deployFormulaConditions(String task, String ... keys) {
        if (task == null) {
            return;
        }
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(task);
        if (taskDefine == null) {
            throw new RuntimeException("\u4efb\u52a1\u672a\u53d1\u5e03");
        }
        logger.info("\u53d1\u5e03\u516c\u5f0f\u9002\u5e94\u6761\u4ef6\uff1a\u5f00\u59cb");
        FCConditionDeployParam param = new FCConditionDeployParam();
        this.analyseDefinitionService.getFormulaConditionDeployParamByTask(param, task, keys);
        IRunnable run = () -> {
            TransactionStatus transaction = null;
            try {
                transaction = this.getTransactionStatus();
                this.deployFormulaConditions(param);
                logger.info("\u5c06\u53d8\u5316\u7684\u516c\u5f0f\u9002\u5e94\u6761\u4ef6\u8bbe\u8ba1\u671f\u53d1\u5e03\u5230\u8fd0\u884c\u671f");
                this.platformTransactionManager.commit(transaction);
            }
            catch (Exception e) {
                if (null != transaction) {
                    this.platformTransactionManager.rollback(transaction);
                }
                throw e;
            }
            return null;
        };
        this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(ParamResourceType.TASK, task)).tryWrite(run);
        DeployParams deployParams = new DeployParams();
        deployParams.setFormulaScheme(new DeployItem(param.getRefreshSchemeKeys(), param.getRefreshSchemeKeys()));
        try {
            this.paramCacheManagerService.refreshCache(deployParams.getFormScheme().getRuntimeUninDesignTimeKeys(), deployParams.getFormulaScheme().getRuntimeUninDesignTimeKeys(), () -> {
                this.applicationContext.publishEvent(new DeployFinishedEvent((Object)task, deployParams));
                logger.info("\u53d1\u5e03\u516c\u5f0f\u9002\u5e94\u6761\u4ef6\uff1a\u7ed3\u675f");
                this.notifyObserver(task);
                logger.info("\u53d1\u5e03\u516c\u5f0f\u9002\u5e94\u6761\u4ef6\uff1a\u901a\u77e5\u89c2\u5bdf\u8005\u6267\u884c\u53d1\u5e03\u540e\u4e8b\u4ef6\u5b8c\u6210");
            });
        }
        catch (NrParamSyncException e) {
            logger.error("\u53d1\u5e03\u516c\u5f0f\u9002\u5e94\u6761\u4ef6\uff1a\u7f13\u5b58\u5237\u65b0\u5f02\u5e38", e);
            throw new RuntimeException(e);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deployFormulaConditions(FCConditionDeployParam param) {
        this.deployDefinitionService.deleteFormulaConditions(null, param.getRunKeys(), true);
        this.deployDefinitionService.insertFormulaConditions(param.getDesignKeys(), null, true);
        Set<DesignFormulaConditionLink> designLinks = param.getDesignLinks();
        Set<FormulaConditionLink> runLinks = param.getRunLinks();
        runLinks.removeIf(designLinks::contains);
        this.deployDefinitionService.deployFormulaConditionLink(designLinks, runLinks);
    }

    private void deployFormulaConditionLinks(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem formula = deployParams.getFormulaConditionLink();
        if (formula == null) {
            return;
        }
        Set designTimeKeys = formula.getDesignTimeKeys();
        Set runTimeKeys = formula.getRunTimeKeys();
        this.deployDefinitionService.deleteFormulaConditionLinks(designTimeKeys, runTimeKeys, isFromDesToSys);
        this.deployDefinitionService.insertFormulaConditionLinks(designTimeKeys, runTimeKeys, isFromDesToSys);
    }

    private void deployFormulaConditions(DeployParams deployParams, boolean isFromDesToSys) {
        DeployItem condition = deployParams.getFormulaCondition();
        if (condition == null) {
            return;
        }
        Set designTimeKeys = condition.getDesignTimeKeys();
        Set runTimeKeys = condition.getRunTimeKeys();
        this.deployDefinitionService.deleteFormulaConditions(designTimeKeys, runTimeKeys, isFromDesToSys);
        this.deployDefinitionService.insertFormulaConditions(designTimeKeys, runTimeKeys, isFromDesToSys);
    }

    public void deployTaskLinkByFormScheme(String formSchemeKey) {
        TransactionStatus transaction = null;
        try {
            transaction = this.getTransactionStatus();
            this.deployDefinitionService.deleteTaskLinksByFormScheme(Collections.singleton(formSchemeKey), true);
            this.deployDefinitionService.insertTaskLinksByFormScheme(Collections.singleton(formSchemeKey), true);
            this.platformTransactionManager.commit(transaction);
        }
        catch (Exception e) {
            if (null != transaction) {
                this.platformTransactionManager.rollback(transaction);
            }
            throw e;
        }
        DeployParams deployParams = new DeployParams();
        DeployItem formScheme = deployParams.getFormScheme();
        formScheme.getDesignTimeKeys().add(formSchemeKey);
        formScheme.getRunTimeKeys().add(formSchemeKey);
        try {
            this.paramCacheManagerService.refreshCache(Collections.singleton(formSchemeKey), Collections.emptySet(), () -> this.applicationContext.publishEvent(new DeployFinishedEvent((Object)formSchemeKey, deployParams)));
        }
        catch (NrParamSyncException e) {
            logger.error("\u53d1\u5e03\u5173\u8054\u4efb\u52a1\uff1a\u7f13\u5b58\u5237\u65b0\u5f02\u5e38", e);
            throw new RuntimeException(e);
        }
        catch (RuntimeException e) {
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

