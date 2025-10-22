/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.definition.facade.IMetaItem
 *  com.jiuqi.nr.datascheme.api.core.DeployResult
 *  com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService
 *  com.jiuqi.nr.definition.api.IDesignTimeFormulaController
 *  com.jiuqi.nr.definition.api.IDesignTimePrintController
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IParamDeployController
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$DeployStatus
 *  com.jiuqi.nr.definition.common.ParamDeployEnum$ParamStatus
 *  com.jiuqi.nr.definition.common.ParamResourceType
 *  com.jiuqi.nr.definition.common.ProgressConsumer
 *  com.jiuqi.nr.definition.exception.NrParamSyncException
 *  com.jiuqi.nr.definition.facade.DesignFormSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus
 *  com.jiuqi.nr.definition.internal.service.RunTimeFormDefineService
 *  com.jiuqi.nr.definition.internal.service.RunTimeFormSchemeDefineService
 *  com.jiuqi.nr.definition.internal.service.RunTimeFormulaSchemeDefineService
 *  com.jiuqi.nr.definition.internal.service.RunTimePrintTemplateSchemeDefineService
 *  com.jiuqi.nr.definition.util.DefinitionUtils
 *  com.jiuqi.nr.definition.util.NrDefinitionHelper
 *  com.jiuqi.nr.graph.IRWLockExecuterManager
 */
package com.jiuqi.nr.definition.deploy;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.definition.facade.IMetaItem;
import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.service.IDataSchemeDeployService;
import com.jiuqi.nr.definition.api.IDesignTimeFormulaController;
import com.jiuqi.nr.definition.api.IDesignTimePrintController;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IParamDeployController;
import com.jiuqi.nr.definition.common.ParamDeployEnum;
import com.jiuqi.nr.definition.common.ParamResourceType;
import com.jiuqi.nr.definition.common.ProgressConsumer;
import com.jiuqi.nr.definition.deploy.common.ParamDeployContext;
import com.jiuqi.nr.definition.deploy.common.ParamDeployException;
import com.jiuqi.nr.definition.deploy.common.ParamDeployItem;
import com.jiuqi.nr.definition.deploy.common.ParamDeployProgressUpdater;
import com.jiuqi.nr.definition.deploy.dto.ParamDeployStatusDTO;
import com.jiuqi.nr.definition.deploy.extend.IParamDeployFinishListener;
import com.jiuqi.nr.definition.deploy.extend.ParamDeployFinishEvent;
import com.jiuqi.nr.definition.deploy.extend.PartialDeployFinishEvent;
import com.jiuqi.nr.definition.deploy.service.IParamDeployService;
import com.jiuqi.nr.definition.deploy.service.IParamDeployStatusService;
import com.jiuqi.nr.definition.deploy.service.IParamDeployTimeService;
import com.jiuqi.nr.definition.deploy.service.IRuntimeParamChangeService;
import com.jiuqi.nr.definition.exception.NrParamSyncException;
import com.jiuqi.nr.definition.facade.DesignFormSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignFormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignPrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.deploy.ParamDeployStatus;
import com.jiuqi.nr.definition.internal.service.RunTimeFormDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimeFormSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimeFormulaSchemeDefineService;
import com.jiuqi.nr.definition.internal.service.RunTimePrintTemplateSchemeDefineService;
import com.jiuqi.nr.definition.util.DefinitionUtils;
import com.jiuqi.nr.definition.util.NrDefinitionHelper;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class ParamDeployController
implements IParamDeployController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ParamDeployController.class);
    @Autowired
    private IParamDeployStatusService paramDeployStatusService;
    @Autowired
    private IParamDeployService paramDeployService;
    @Autowired
    private IParamDeployTimeService paramDeployTimeService;
    @Autowired
    private IRuntimeParamChangeService paramChangeService;
    @Autowired
    private IDataSchemeDeployService dataSchemeDeployService;
    @Autowired
    private IRWLockExecuterManager rwLockExecuterManager;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Autowired
    private IDesignTimeFormulaController designTimeFormulaController;
    @Autowired
    private IDesignTimePrintController designTimePrintController;
    @Autowired
    private RunTimeFormSchemeDefineService runTimeFormSchemeDefineService;
    @Autowired
    private RunTimeFormulaSchemeDefineService runTimeFormulaSchemeDefineService;
    @Autowired
    private RunTimePrintTemplateSchemeDefineService runTimePrintSchemeDefineService;
    @Autowired
    private RunTimeFormDefineService runTimeFormDefineService;
    @Autowired
    private NrDefinitionHelper nrDefinitionHelper;
    @Autowired
    private List<IParamDeployFinishListener> paramDeployFinishServices;
    @Autowired
    protected ApplicationContext applicationContext;

    public ParamDeployStatus getDeployStatus(String schemeKey) {
        return this.paramDeployStatusService.getDeployStatus(schemeKey);
    }

    public Date getDeployTime(ParamResourceType type, String schemeKey) {
        return this.paramDeployTimeService.getDeployTime(type, schemeKey);
    }

    public void updateDeployWarning(String schemeKey, String warning) {
        ParamDeployStatus status = this.paramDeployStatusService.getDeployStatus(schemeKey);
        if (null == status) {
            throw new ParamDeployException("\u65b9\u6848[" + schemeKey + "]\u7684\u53d1\u5e03\u72b6\u6001\u4e0d\u5b58\u5728");
        }
        String detail = status.getDeployDetail();
        detail = StringUtils.hasText(detail) ? detail + "\r\n" + warning : warning;
        ParamDeployStatusDTO deployStatus = new ParamDeployStatusDTO(status);
        if (ParamDeployEnum.DeployStatus.SUCCESS == status.getDeployStatus()) {
            deployStatus.setDeployStatus(ParamDeployEnum.DeployStatus.WARNING);
        }
        deployStatus.setDeployDetail(detail);
        this.paramDeployStatusService.updateDeployStatus(deployStatus);
    }

    public void updateParamStatus(String schemeKey, ParamDeployEnum.ParamStatus paramStatus) {
        ParamDeployStatus status = this.paramDeployStatusService.getDeployStatus(schemeKey);
        if (null == status) {
            ParamDeployStatusDTO deployStatus = new ParamDeployStatusDTO();
            deployStatus.setSchemeKey(schemeKey);
            deployStatus.setParamStatus(paramStatus);
            deployStatus.setDeployStatus(ParamDeployEnum.DeployStatus.NOT_DEPLOYED);
            this.paramDeployStatusService.insertDeployStatus(deployStatus);
        } else {
            ParamDeployStatusDTO deployStatus = new ParamDeployStatusDTO(status);
            deployStatus.setParamStatus(paramStatus);
            this.paramDeployStatusService.updateDeployStatus(deployStatus);
        }
    }

    public void deploy(String formSchemeKey, boolean deployDataScheme, ProgressConsumer progress) {
        FormSchemeDefine formScheme;
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u5f00\u59cb");
        ParamDeployStatus deployStatus = this.getDeployStatus(formSchemeKey);
        if (null != deployStatus && ParamDeployEnum.DeployStatus.DEPLOY == deployStatus.getDeployStatus()) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u62a5\u8868\u65b9\u6848[{}]\u6b63\u5728\u53d1\u5e03\u4e2d\uff0c\u7ed3\u675f", (Object)formSchemeKey);
            return;
        }
        ParamDeployProgressUpdater progressUpdater = deployDataScheme ? ParamDeployProgressUpdater.newUpdater(progress) : ParamDeployProgressUpdater.newNoDataSchemeUpdater(progress);
        DesignFormSchemeDefine formSchemeDes = this.designTimeViewController.getFormScheme(formSchemeKey);
        FormSchemeDefine formSchemeRun = this.runTimeFormSchemeDefineService.queryFormSchemeDefine(formSchemeKey);
        Object object = formScheme = null == formSchemeDes ? formSchemeRun : formSchemeDes;
        if (null == formScheme) {
            progressUpdater.fail("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u5f02\u5e38\uff1a\u62a5\u8868\u65b9\u6848[" + formSchemeKey + "]\u4e0d\u5b58\u5728");
            return;
        }
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        if (null == task) {
            progressUpdater.fail("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u5f02\u5e38\uff1a\u62a5\u8868\u65b9\u6848\u6240\u5c5e\u4efb\u52a1[" + formScheme.getTaskKey() + "]\u4e0d\u5b58\u5728");
            return;
        }
        progressUpdater.updateProgress(50, "\u66f4\u65b0\u53d1\u5e03\u72b6\u6001");
        this.updateDeployStatusStart(formScheme, deployStatus);
        try {
            if (deployDataScheme) {
                this.deploy(formScheme, task.getDataScheme(), progressUpdater);
            } else {
                this.deploy(formScheme, progressUpdater);
            }
        }
        catch (Exception e) {
            progressUpdater.fail("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u5f02\u5e38\uff1a" + e.getMessage());
            this.updateDeployStatusFail(formScheme, e);
            return;
        }
        this.deployFinish(formScheme, formSchemeDes, formSchemeRun, progressUpdater);
    }

    private void deployFinish(FormSchemeDefine formScheme, DesignFormSchemeDefine formSchemeDes, FormSchemeDefine formSchemeRun, ParamDeployProgressUpdater progressUpdater) {
        String deployDetail;
        progressUpdater.nextStep();
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03: \u6267\u884c\u53d1\u5e03\u540e\u4e8b\u4ef6");
        this.applicationContext.publishEvent(new ParamDeployFinishEvent(new ParamDeployFinishEvent.EventSource(formScheme)));
        ArrayList<String> warnings = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(this.paramDeployFinishServices)) {
            for (int i = 0; i < this.paramDeployFinishServices.size(); ++i) {
                int currentProgress = i;
                Consumer<String> progressConsumer = message -> progressUpdater.updateProgress(currentProgress / this.paramDeployFinishServices.size() * 100, (String)message);
                IParamDeployFinishListener service = this.paramDeployFinishServices.get(i);
                try {
                    if (null == formSchemeDes) {
                        service.onDelete(formScheme, warnings::add, progressConsumer);
                        continue;
                    }
                    if (null == formSchemeRun) {
                        service.onAdd(formScheme, warnings::add, progressConsumer);
                        continue;
                    }
                    service.onUpdate(formScheme, warnings::add, progressConsumer);
                    continue;
                }
                catch (Exception e) {
                    warnings.add(service.getClass().getName() + "\u6267\u884c\u5f02\u5e38\uff1a" + e.getMessage());
                    LOGGER.error("\u62a5\u8868\u53c2\u6570\u53d1\u5e03: \u53d1\u5e03\u540e\u4e8b\u4ef6[{}]\u6267\u884c\u5f02\u5e38", (Object)service.getClass().getName());
                }
            }
        }
        if (StringUtils.hasText(deployDetail = this.updateDeployStatusFinish(formScheme, formSchemeDes, formSchemeRun, warnings))) {
            progressUpdater.finish("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u5b8c\u6210\uff1a" + deployDetail);
        } else {
            progressUpdater.finish("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\u5b8c\u6210");
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u5b8c\u6210");
    }

    private void deploy(FormSchemeDefine formScheme, String dataSchemeKey, ParamDeployProgressUpdater progressUpdater) {
        progressUpdater.nextStep();
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03: \u53d1\u5e03\u6570\u636e\u65b9\u6848");
        DeployResult deployResult = this.dataSchemeDeployService.deployDataScheme(dataSchemeKey, p -> progressUpdater.updateProgress(p.getCurrentProgess(), p.isFinished() ? "\u6570\u636e\u65b9\u6848\u53d1\u5e03\u6210\u529f" : p.getMessage()), null);
        if (deployResult != null && !deployResult.isSuccess()) {
            throw new ParamDeployException(deployResult.getDeployErrorMessage());
        }
        this.deploy(formScheme, progressUpdater);
    }

    private void deploy(FormSchemeDefine formScheme, ParamDeployProgressUpdater progressUpdater) {
        progressUpdater.nextStep();
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03: \u53d1\u5e03\u6574\u4e2a\u62a5\u8868\u65b9\u6848");
        ParamDeployContext context = new ParamDeployContext((type, message) -> progressUpdater.updateProgress(type.ordinal() / ParamResourceType.values().length * 100, (String)message));
        this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(ParamResourceType.FORM, formScheme.getKey())).tryWrite(() -> {
            this.paramDeployService.deploy(context, formScheme.getKey());
            return null;
        });
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03: \u5237\u65b0\u7f13\u5b58");
        progressUpdater.updateProgress(100, "\u5237\u65b0\u7f13\u5b58");
        try {
            this.paramChangeService.reload(context.getDeployItems());
        }
        catch (NrParamSyncException e) {
            this.updateDeployWarning(formScheme.getKey(), e.getMessage());
        }
    }

    private void updateDeployStatusStart(FormSchemeDefine formScheme, ParamDeployStatus deployStatus) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5f00\u59cb");
        DefinitionUtils.info((String)"\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5f00\u59cb", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey()});
        ParamDeployStatusDTO deployStatusDTO = null == deployStatus ? new ParamDeployStatusDTO() : new ParamDeployStatusDTO(deployStatus);
        deployStatusDTO.setSchemeKey(formScheme.getKey());
        deployStatusDTO.setDeployStatus(ParamDeployEnum.DeployStatus.DEPLOY);
        NpContext context = NpContextHolder.getContext();
        deployStatusDTO.setUserName(context.getUserName());
        deployStatusDTO.setUserKey(context.getUserId());
        deployStatusDTO.setDeployTime(new Date());
        deployStatusDTO.setDeployDetail(null);
        if (null != deployStatus) {
            deployStatusDTO.setLastDeployTime(deployStatus.getDeployTime());
            this.paramDeployStatusService.updateDeployStatus(deployStatusDTO);
        } else {
            deployStatusDTO.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
            this.paramDeployStatusService.insertDeployStatus(deployStatusDTO);
        }
    }

    private void updateDeployStatusFail(FormSchemeDefine formScheme, Exception e) {
        LOGGER.error("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5931\u8d25", e);
        DefinitionUtils.info((String)"\u53d1\u5e03\u62a5\u8868\u65b9\u6848\u5931\u8d25", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]\uff0c{}", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey(), e.getMessage()});
        ParamDeployStatus status = this.paramDeployStatusService.getDeployStatus(formScheme.getKey());
        ParamDeployStatusDTO deployStatusDTO = new ParamDeployStatusDTO(status);
        deployStatusDTO.setDeployStatus(ParamDeployEnum.DeployStatus.FAIL);
        deployStatusDTO.setDeployDetail(e.getLocalizedMessage());
        this.paramDeployStatusService.updateDeployStatus(deployStatusDTO);
    }

    private String updateDeployStatusFinish(FormSchemeDefine formScheme, DesignFormSchemeDefine formSchemeDes, FormSchemeDefine formSchemeRun, List<String> warnings) {
        if (null == formSchemeDes) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u62a5\u8868\u65b9\u6848\uff08\u5220\u9664\uff09\u53d1\u5e03\u6210\u529f");
            DefinitionUtils.info((String)"\u62a5\u8868\u65b9\u6848\uff08\u5220\u9664\uff09\u53d1\u5e03\u6210\u529f", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey()});
            this.paramDeployStatusService.deleteDeployStatus(formScheme.getKey());
            return null;
        }
        ParamDeployStatus status = this.paramDeployStatusService.getDeployStatus(formScheme.getKey());
        ParamDeployStatusDTO deployStatusDTO = new ParamDeployStatusDTO(status);
        deployStatusDTO.setParamStatus(ParamDeployEnum.ParamStatus.DEFAULT);
        String deployDetail = status.getDeployDetail();
        if (!CollectionUtils.isEmpty(warnings)) {
            deployDetail = StringUtils.hasText(deployDetail) ? deployDetail + "\r\n" + StringUtils.collectionToDelimitedString(warnings, "\r\n") : StringUtils.collectionToDelimitedString(warnings, "\r\n");
            deployStatusDTO.setDeployDetail(deployDetail);
            deployStatusDTO.setDeployStatus(ParamDeployEnum.DeployStatus.WARNING);
        } else if (ParamDeployEnum.DeployStatus.DEPLOY == status.getDeployStatus()) {
            deployStatusDTO.setDeployStatus(ParamDeployEnum.DeployStatus.SUCCESS);
        } else {
            deployStatusDTO.setDeployStatus(status.getDeployStatus());
        }
        if (StringUtils.hasText(deployStatusDTO.getDeployDetail())) {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u62a5\u8868\u65b9\u6848\u53d1\u5e03\u8b66\u544a");
            DefinitionUtils.warn((String)"\u62a5\u8868\u65b9\u6848\u53d1\u5e03\u8b66\u544a", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]\r\n\u8b66\u544a\u4fe1\u606f\uff1a\r\n{}", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey(), deployStatusDTO.getDeployDetail()});
        } else {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u62a5\u8868\u65b9\u6848\u53d1\u5e03\u6210\u529f");
            DefinitionUtils.info((String)"\u62a5\u8868\u65b9\u6848\u53d1\u5e03\u6210\u529f", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey()});
        }
        this.paramDeployStatusService.updateDeployStatus(deployStatusDTO);
        return deployDetail;
    }

    public void deploy(String formSchemeKey, List<String> formKeys, boolean deployDataTable, BiPredicate<ParamResourceType, IMetaItem> filter) {
        if (CollectionUtils.isEmpty(formKeys)) {
            return;
        }
        FormSchemeDefine formScheme = this.runTimeFormSchemeDefineService.queryFormSchemeDefine(formSchemeKey);
        if (null == formScheme) {
            return;
        }
        DesignTaskDefine task = this.designTimeViewController.getTask(formScheme.getTaskKey());
        if (null == task) {
            return;
        }
        List forms = this.designTimeViewController.listForm(formKeys);
        if (CollectionUtils.isEmpty(forms)) {
            forms = this.runTimeFormDefineService.queryFormDefines(formKeys.toArray(new String[0]));
        }
        if (CollectionUtils.isEmpty(forms)) {
            return;
        }
        List formulaSchemes = this.runTimeFormulaSchemeDefineService.queryFormulaSchemeDefineByFormScheme(formSchemeKey);
        List printSchemes = this.runTimePrintSchemeDefineService.getAllPrintSchemeByFormScheme(formSchemeKey);
        ParamResourceType resourceType = ParamResourceType.FORM;
        ArrayList<ParamDeployItem> deployItems = new ArrayList<ParamDeployItem>();
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u90e8\u5206{}\uff0c\u65b9\u6848\uff1a{}\uff0c\u8d44\u6e90\uff1a{}", resourceType.getName(), formSchemeKey, formKeys);
        DefinitionUtils.info((String)"\u53d1\u5e03\u90e8\u5206\u62a5\u8868", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]\uff0c\u62a5\u8868\uff1a{}", (Object[])new Object[]{formScheme.getTitle(), formSchemeKey, forms.stream().map(f -> f.getTitle() + "[" + f.getKey() + "]").collect(Collectors.toList())});
        ParamDeployException exception = (ParamDeployException)this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(resourceType, formSchemeKey)).tryWrite(() -> {
            List dataTables = null;
            DeployResult deployResult = null;
            if (deployDataTable && !CollectionUtils.isEmpty(dataTables = this.designTimeViewController.listDataTableByForm(new HashSet(formKeys)))) {
                deployResult = this.dataSchemeDeployService.deployDataTable(task.getDataScheme(), new HashSet(dataTables), true, null);
            }
            if (deployResult != null && !deployResult.isSuccess()) {
                return new ParamDeployException(deployResult.getDeployErrorMessage());
            }
            deployItems.add(new ParamDeployItem(resourceType, formSchemeKey, formKeys));
            this.paramDeployService.deploy(resourceType, formSchemeKey, formKeys);
            for (FormulaSchemeDefine scheme : formulaSchemes) {
                if (!filter.test(ParamResourceType.FORMULA, (IMetaItem)scheme)) continue;
                deployItems.add(new ParamDeployItem(ParamResourceType.FORMULA, scheme.getKey()));
                this.paramDeployService.deploy(ParamResourceType.FORMULA, scheme.getKey(), formKeys);
            }
            for (FormulaSchemeDefine scheme : printSchemes) {
                if (!filter.test(ParamResourceType.PRINT_TEMPLATE, (IMetaItem)scheme)) continue;
                deployItems.add(new ParamDeployItem(ParamResourceType.PRINT_TEMPLATE, scheme.getKey()));
                this.paramDeployService.deploy(ParamResourceType.PRINT_TEMPLATE, scheme.getKey(), formKeys);
            }
            return null;
        });
        if (exception != null) {
            throw exception;
        }
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03: \u5237\u65b0\u90e8\u5206{}\u7f13\u5b58", (Object)resourceType.getName());
        this.tryReloadCache(deployItems, () -> {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u53d1\u5e03\u90e8\u5206{}\u5b8c\u6210\uff0c\u65b9\u6848\uff1a{}\uff0c\u8d44\u6e90\uff1a{}", resourceType.getName(), formSchemeKey, formKeys);
            PartialDeployFinishEvent.EventSource source = new PartialDeployFinishEvent.EventSource(resourceType, formSchemeKey, formKeys);
            this.applicationContext.publishEvent(new PartialDeployFinishEvent(source));
            this.updateDeployTime(formScheme);
        });
    }

    private void tryReloadCache(List<ParamDeployItem> deployItems, Runnable callback) {
        NrParamSyncException paramSyncExp = null;
        try {
            this.paramChangeService.reload(deployItems);
        }
        catch (NrParamSyncException e) {
            paramSyncExp = e;
        }
        callback.run();
        if (null != paramSyncExp) {
            throw new ParamDeployException(paramSyncExp.getMessage(), paramSyncExp);
        }
    }

    private void updateDeployTime(FormSchemeDefine formScheme) {
        this.updateDeployTime(formScheme.getKey());
    }

    private void updateDeployTime(String formScheme) {
        ParamDeployStatus status = this.paramDeployStatusService.getDeployStatus(formScheme);
        if (null != status) {
            ParamDeployStatusDTO deployStatusDTO = new ParamDeployStatusDTO(status);
            deployStatusDTO.setLastDeployTime(status.getDeployTime());
            deployStatusDTO.setDeployTime(new Date());
            this.paramDeployStatusService.updateDeployStatus(deployStatusDTO);
        }
    }

    public void deploy(ParamResourceType resourceType, String schemeKey, List<String> resourceKeys) {
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u4ec5\u53d1\u5e03{}\uff0c\u65b9\u6848\uff1a{}\uff0c\u8d44\u6e90\uff1a{}", resourceType.getName(), schemeKey, resourceKeys);
        this.sysLogInfo(resourceType, schemeKey, resourceKeys);
        this.rwLockExecuterManager.getRWLockExecuter(this.nrDefinitionHelper.getLockName(resourceType, schemeKey)).tryWrite(() -> {
            this.paramDeployService.deploy(resourceType, schemeKey, resourceKeys);
            return null;
        });
        LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03: \u4ec5\u5237\u65b0{}\u7f13\u5b58", (Object)resourceType.getName());
        ParamDeployItem deployItem = new ParamDeployItem(resourceType, schemeKey, resourceKeys);
        this.tryReloadCache(Collections.singletonList(deployItem), () -> {
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u53d1\u5e03\uff1a\u4ec5\u53d1\u5e03{}\u5b8c\u6210\uff0c\u65b9\u6848\uff1a{}\uff0c\u8d44\u6e90\uff1a{}", resourceType.getName(), schemeKey, resourceKeys);
            PartialDeployFinishEvent.EventSource source = new PartialDeployFinishEvent.EventSource(resourceType, schemeKey, resourceKeys);
            this.applicationContext.publishEvent(new PartialDeployFinishEvent(source));
            if (ParamResourceType.FORM == resourceType) {
                this.updateDeployTime(schemeKey);
            }
        });
    }

    private void sysLogInfo(ParamResourceType resourceType, String schemeKey, List<String> resourceKeys) {
        switch (resourceType) {
            case FORM: {
                DesignFormSchemeDefine formScheme = this.designTimeViewController.getFormScheme(schemeKey);
                List forms = this.designTimeViewController.listForm(resourceKeys);
                DefinitionUtils.info((String)"\u4ec5\u53d1\u5e03\u62a5\u8868", (String)"\u62a5\u8868\u65b9\u6848\uff1a{}[{}]\uff0c\u62a5\u8868\uff1a{}", (Object[])new Object[]{formScheme.getTitle(), formScheme.getKey(), forms.stream().map(f -> f.getTitle() + "[" + f.getKey() + "]").collect(Collectors.toList())});
                break;
            }
            case FORMULA: {
                List keys = resourceKeys.stream().filter(Objects::nonNull).collect(Collectors.toList());
                List forms = this.designTimeViewController.listForm(keys);
                List formTitles = forms.stream().map(f -> f.getTitle() + "[" + f.getKey() + "]").collect(Collectors.toList());
                if (keys.size() != resourceKeys.size()) {
                    formTitles.add(0, "\u8868\u95f4\u516c\u5f0f");
                }
                DesignFormulaSchemeDefine formulaScheme = this.designTimeFormulaController.getFormulaScheme(schemeKey);
                DefinitionUtils.info((String)"\u4ec5\u53d1\u5e03\u516c\u5f0f", (String)"\u516c\u5f0f\u65b9\u6848\uff1a{}[{}]\uff0c\u62a5\u8868\uff1a{}", (Object[])new Object[]{formulaScheme.getTitle(), formulaScheme.getKey(), formTitles});
                break;
            }
            case PRINT_TEMPLATE: {
                List forms = this.designTimeViewController.listForm(resourceKeys);
                DesignPrintTemplateSchemeDefine printScheme = this.designTimePrintController.getPrintTemplateScheme(schemeKey);
                DefinitionUtils.info((String)"\u4ec5\u53d1\u5e03\u6253\u5370\u6a21\u677f", (String)"\u6253\u5370\u65b9\u6848\uff1a{}[{}]\uff0c\u62a5\u8868\uff1a{}", (Object[])new Object[]{printScheme.getTitle(), printScheme.getKey(), forms.stream().map(f -> f.getTitle() + "[" + f.getKey() + "]").collect(Collectors.toList())});
                break;
            }
            case REPORT_TEMPLATE: {
                DefinitionUtils.info((String)"\u4ec5\u53d1\u5e03\u62a5\u544a\u6a21\u677f", (String)"\u62a5\u544a\u6a21\u677f\uff1a{}\uff0c\u6807\u7b7e\uff1a{}", (Object[])new Object[]{schemeKey, resourceKeys});
                break;
            }
            default: {
                DefinitionUtils.info((String)("\u4ec5\u53d1\u5e03" + resourceType.getName()), (String)"\u65b9\u6848\uff1a{}\uff0c\u8d44\u6e90\uff1a{}", (Object[])new Object[]{schemeKey, resourceKeys});
            }
        }
    }
}

